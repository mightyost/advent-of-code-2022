package day10

import readInput

fun main() {

//    val input = readInput("day10/data_test")
    val input = readInput("day10/data")

    var cycle = 0
    var x = 1
    val strengths = mutableListOf<Int>()
    val probeAt = setOf(20, 60, 100, 140, 180, 220)

    var time = 0

    var nextX = x

    for (op in input) {

        while (time > 0) {
            time--
            cycle++
            if (cycle in probeAt) {
                strengths += cycle * x
            }
        }

        x = nextX

        if (op == "noop") {
            time = 1
        }
        else {
            time = 2
            nextX = x + op.substring(5).toInt()
        }
    }

    val sum = strengths.sumOf { it }

    println(sum) // 13480
}
