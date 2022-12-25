package day15p1

import readInput
import java.lang.Math.abs

fun main() {

//    val input = readInput("day15/data_test")
    val input = readInput("day15/data")
    val y = 2000000

    val readings = input.map { Reading.from(it) }
    val ranges = readings.map { it.empty(y) }

    val min = ranges.minOf { it.start }
    val max = ranges.maxOf { it.last }

    var count = 0

    for (i in min..max) {
        if (ranges.any { it.contains(i) } && readings.none { it.bX == i && it.bY == y }) count++
    }

    println(count) // 5166077
}

data class Reading(
    val sX: Int,
    val sY: Int,
    val bX: Int,
    val bY: Int
) {

    fun empty(y: Int): IntRange {
        val distance = abs(sX - bX) + abs(sY - bY)
        val xDistance = distance - abs(y - sY)

        return IntRange(sX - xDistance, sX + xDistance)
    }

    companion object {
        fun from(string: String): Reading {
            val regExp = """Sensor at x=(-?\d+), y=(-?\d+): closest beacon is at x=(-?\d+), y=(-?\d+)""".toRegex()
            val (sX, sY, bX, bY) = regExp.find(string)!!.destructured
            return Reading(sX.toInt(), sY.toInt(), bX.toInt(), bY.toInt())
        }
    }
}