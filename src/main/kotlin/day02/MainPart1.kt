package day02part1

import readInput

fun main() {

    /*
        A for Rock, B for Paper, and C for Scissors
        X for Rock, Y for Paper, and Z for Scissors

        Score fo shape you selected (1 for Rock, 2 for Paper, and 3 for Scissors)
        Score for outcome of the round (0 if you lost, 3 if the round was a draw, and 6 if you won)
    */

//    val data = readInput("day02/data_test")
    val data = readInput("day02/data")

    val guide = data.map { it.split(" ").map { it.item } }

    var score = 0

    guide.forEach {
        val item1 = it.first()
        val item2 = it.last()

        score += item2.score

        score += when {
            item1 trumps item2 -> 0
            item2 trumps item1 -> 6
            else -> 3
        }
    }

    println(score) // 14069
}

private enum class Item(val score: Int) {
    Rock(1),
    Paper(2),
    Scissors(3);

    infix fun trumps(other: Item) = when(this) {
        Rock -> other == Scissors
        Paper -> other == Rock
        Scissors -> other == Paper
    }
}

private val String.item: Item get() = when (this) {
    "A", "X" -> Item.Rock
    "B", "Y" -> Item.Paper
    "C", "Z" -> Item.Scissors
    else -> throw Exception()
}
