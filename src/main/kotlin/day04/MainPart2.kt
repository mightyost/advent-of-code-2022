package day04

import readInput

fun main() {

//    val input = readInput("day04/data_test")
    val input = readInput("day04/data")

    val count = input
        .map { it.split(",") }
        .map { it.first().set to it.last().set }
        .filter { it.first.intersect(it.second).count() > 0 }
        .count()

    println(count) // 801
}

private val String.set: Set<Int> get() =
    split("-").let { IntRange(it.first().toInt(), it.last().toInt()).toSet() }
