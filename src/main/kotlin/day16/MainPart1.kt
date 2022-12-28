package day16p1

import readInput
import java.lang.Integer.max

fun main() {

//    val input = readInput("day16/data_test")
    val input = readInput("day16/data")

    val valves = input.map { Valve.from(it) }
        .associateBy { it.id }

    val start = valves["AA"]!!

    val movingTimes: MutableMap<Valve, MutableMap<Valve, Int>> = mutableMapOf()

    // Calc moving times between all valves - Dijkstra
    for (from in valves.values) {
        for (to in valves.values) {
            if (from == to || to.pressure == 0) {
                continue
            }

            if (from != start && from.pressure == 0) {
                continue
            }

            val time = getMovingTime(from, to, valves)

            movingTimes.getOrPut(from) { mutableMapOf() }[to] = time
        }
    }

    // Calc max pressure - Travelling salesman
    val max = maxPressure(movingTimes, start)

    println(max)
}

fun maxPressure(times: MutableMap<Valve, MutableMap<Valve, Int>>, start: Valve): Int {

    var maxPressure = 0
    val allVisits = times.keys.toSet() + times.values.map { it.keys }.flatten()
    val maxVisits = allVisits.size

    val stack = ArrayDeque(listOf(PressureState(start)))

    while (stack.isNotEmpty()) {
        val state = stack.removeLast()

        var time = state.time
        var pressure = state.pressure
        val visit = state.visit
        val visited = state.visited + visit

        val path = visited.map { it.id }.joinToString(",")
        if ("AA,DD,BB,JJ,HH,EE,CC".startsWith(path)) {
            println("$time: Visit ${visit.id} (${visit.pressure}), total: $pressure, visited: $path")
        }

        if (time >= 30) {
            maxPressure = max(maxPressure, pressure)
            continue
        }

        // Open valve
        if (visit.pressure > 0) {
            time += 1
            pressure += (30 - time) * visit.pressure

            if ("AA,DD,BB,JJ,HH,EE,CC".startsWith(path)) {
                println("$time: Open ${visit.id} (${visit.pressure}), adds: ${30 - time}x${visit.pressure}=${(30 - time) * visit.pressure}, total: $pressure, visited: $path")
            }
        }

        if (visited.size == maxVisits) {
            maxPressure = max(maxPressure, pressure)
            continue
        }

        times[visit]!!
            .map { PressureState(it.key, time + it.value, pressure, visited) }
            .filter { !visited.contains(it.visit) }
            .forEach { stack.addLast(it) }
    }

    return maxPressure
}

data class PressureState(
    val visit: Valve,
    val time: Int = 0,
    val pressure: Int = 0,
    val visited: List<Valve> = emptyList()
)

fun getMovingTime(from: Valve, to: Valve, valves: Map<String, Valve>): Int {

    val leastTimes = mutableMapOf<Valve, Int>()

    val stack = ArrayDeque(listOf(MovingState(from)))

    while (stack.isNotEmpty()) {
        val state = stack.removeLast()

        val leastTime = leastTimes[state.valve]
        if (leastTime != null && state.time >= leastTime) {
            continue
        }

        leastTimes[state.valve] = state.time

        if (state.valve == to || state.time > 30) {
            continue
        }

        state.valve.neighbours.map {
            stack.addLast(MovingState(valves[it]!!, state.time+1))
        }
    }

    return leastTimes[to]!!
}

data class MovingState(
    val valve: Valve,
    val time: Int = 0
)

data class Valve(
    val id: String,
    val pressure: Int,
    val neighbours: List<String>
) {
    companion object {
        fun from(string: String): Valve {
            val regExp = """Valve (.*) has flow rate=(\d+); tunnels? leads? to valves? (.*)""".toRegex()
            val (id, flow, tunnels) = regExp.find(string)!!.destructured
            return Valve(id, flow.toInt(), tunnels.split(", "))
        }
    }
}