package day06

import readInput

fun main() {

//    val input = readInput("day06/data_test").first()
    val input = readInput("day06/data").first()

    for (i in 4 until input.length) {
        val sub = input.substring(i-4, i)
        if (sub.toSet().count() == 4) {
            println(i) // 1802
            break
        }
    }

    // As a one liner
    val pos = input.toList().windowed(4, 1).indexOfFirst { it.distinct() == it } + 4

    println(pos) // 1802
}
