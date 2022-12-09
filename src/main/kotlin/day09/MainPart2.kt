package day09p2

import readInput
import java.lang.Math.abs

fun main() {

//    val input = readInput("day09/data_test2")
    val input = readInput("day09/data")

    val moves = input
        .map { it.split(" ") }
        .map { it[0] to it[1].toInt() }

    var hX = 1000
    var hY = 1000

    val segments = MutableList(9) { hX to hY }
    val visited = mutableSetOf(segments.last())

    for (move in moves) {
        when (move.first) {
            "R" -> hX += move.second
            "L" -> hX -= move.second
            "U" -> hY += move.second
            "D" -> hY -= move.second
        }

        while (!isAdjent(hX, hY, segments)) {
            var prevX = hX
            var prevY = hY

            segments.forEachIndexed { i, segment ->
                var sX = segment.first
                var sY = segment.second

                if (!isAdjent(prevX, prevY, sX, sY)) {
                    when {
                        prevX < sX -> sX--
                        prevX > sX -> sX++
                    }
                    when {
                        prevY < sY -> sY--
                        prevY > sY -> sY++
                    }

                    segments.set(i, sX to sY)
                }
                visited += segments.last()

                prevX = sX
                prevY = sY
            }
        }
    }

    val count = visited.size

    println(count) // 2327
}

fun isAdjent(x1: Int, y1: Int, x2: Int, y2: Int) = abs(x1 - x2) <= 1 && abs(y1 - y2) <= 1

fun isAdjent(hX: Int, hY: Int, segments: List<Pair<Int, Int>>): Boolean {
    var prevX = hX
    var prevY = hY
    for (segment in segments) {
        val sX = segment.first
        val sY = segment.second
        if (!isAdjent(prevX, prevY, sX, sY)) {
            return false
        }
        prevX = sX
        prevY = sY
    }
    return true
}