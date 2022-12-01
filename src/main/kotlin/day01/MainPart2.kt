package day01

import readInput

fun main() {

//    val data = readInput("day2201/data_test")
    val data = readInput("day01/data")

    val cals = mutableListOf<Int>()

    var sum = 0
    for (cal in data) {
        if (cal.isBlank()) {
            cals += sum
            sum = 0
            continue
        }

        sum += cal.toInt(10)
    }

    val max = cals.max()

    println(max)

    val top3 = cals
        .sortedDescending()
        .take(3)
        .sum()

    println(top3)
}