package day06

import readInput

fun main() {

//    val input = readInput("day06/data_test").first()
    val input = readInput("day06/data").first()

    for (i in 14 until input.length) {
        val sub = input.substring(i-14, i)
        if (sub.toSet().count() == 14) {
            println(i) // 3551
            break
        }
    }

    // As a one liner
    val pos = input.toList().windowed(14, 1).indexOfFirst { it.distinct() == it } + 14

    println(pos) // 3551
}
