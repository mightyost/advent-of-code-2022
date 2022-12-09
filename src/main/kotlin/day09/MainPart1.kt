package day09p1

import readInput
import java.lang.Math.abs

fun main() {

//    val input = readInput("day09/data_test")
    val input = readInput("day09/data")

    val moves = input
        .map { it.split(" ") }
        .map { it[0] to it[1].toInt() }

    var hX = 1000
    var hY = 1000
    var tX = 1000
    var tY = 1000

    val visited = mutableListOf(tX to tY)

    for (move in moves) {
        when (move.first) {
            "R" -> hX += move.second
            "L" -> hX -= move.second
            "U" -> hY += move.second
            "D" -> hY -= move.second
        }

        while (!isAdjent(hX, hY, tX, tY)) {
            when {
                hX < tX -> tX--
                hX > tX -> tX++
            }
            when {
                hY < tY -> tY--
                hY > tY -> tY++
            }

            visited += tX to tY
        }
    }

    val count = visited.distinct().size

    println(count) // 5960
}

fun isAdjent(x1: Int, y1: Int, x2: Int, y2: Int) = abs(x1 - x2) <= 1 && abs(y1 - y2) <= 1
