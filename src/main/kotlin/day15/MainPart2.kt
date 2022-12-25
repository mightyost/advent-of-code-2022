package day15p2

import readInput
import java.lang.Math.abs

fun main() {

//    val input = readInput("day15/data_test")
//    val maxY = 20
    val input = readInput("day15/data")
    val maxY = 4000000

    val readings = input.map { Reading.from(it) }

    for (y in 0..maxY) {

        val ranges = readings.map { it.isCovered(y) }.filterNotNull()

        val x = getHiddenBeaconX(ranges) ?: continue

        println("${x*4000000L + y}") // 13071206703981
        break
    }
}

fun getHiddenBeaconX(ranges: List<IntRange>): Int? {

    val starts = ranges.map { it.start }.toSet()

    val maybeHidden = ranges
        .map { it.last+1 }
        .filter { starts.contains(it+1) }
        .toSet()

    if (maybeHidden.isEmpty() || maybeHidden.size > 1) {
        return null
    }

    if (ranges.any { it.contains(maybeHidden.single()) }) {
        return null
    }

    return maybeHidden.single()
}

data class Reading(
    val sX: Int,
    val sY: Int,
    val bX: Int,
    val bY: Int
) {

    fun isCovered(y: Int): IntRange? {
        val distance = abs(sX - bX) + abs(sY - bY)
        val xDistance = distance - abs(y - sY)

        if (xDistance < 0) {
            return null
        }

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