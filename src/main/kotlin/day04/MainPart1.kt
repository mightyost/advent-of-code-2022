package day04

import readInput

fun main() {

//    val input = readInput("day04/data_test")
    val input = readInput("day04/data")

    val count = input
        .map { it.split(",") }
        .map { it.first().set to it.last().set }
        .filter { it.first.containsAll(it.second) || it.second.containsAll(it.first) }
        .count()

    println(count) // 444
}

private val String.set: Set<Int> get() =
    split("-").let { IntRange(it.first().toInt(), it.last().toInt()).toSet() }
