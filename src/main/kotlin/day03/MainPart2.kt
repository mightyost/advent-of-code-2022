package day03

import readInput

fun main() {

    /*
        vJrwpWtwJgWrhcsFMMfFFhFp
        jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
        PmmdzqPrVvPwwTWBwg
        And the second group's rucksacks are the next three lines:

        wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
        ttgJtRGJQctTZtZT
        CrZsJsPPZsGzwwsLwLmpwMDw

        In the first group, the only item type that appears in all three rucksacks is lowercase r;
        this must be their badges. In the second group, their badge item type must be Z.

        Lowercase item types a through z have priorities 1 through 26.
        Uppercase item types A through Z have priorities 27 through 52.
    */

//    val data = readInput("day03/data_test")
    val data = readInput("day03/data")

    val sum = data.chunked(3)
        .map { it.map { it.toSet() } }
        .map { it.fold(it.first()) { a, b -> a.intersect(b) } }
        .map { require(it.size == 1); it }
        .map { it.single() }
        .map { it.prio }
        .sum()

    println(sum) // 2276
}

private val Char.prio: Int get() = when(this) {
    in 'a'..'z' -> this - 'a' + 1
    in 'A'..'Z' -> this - 'A' + 27
    else -> throw Exception()
}