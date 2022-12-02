package day02part2

import readInput

fun main() {

    /*
        A for Rock, B for Paper, and C for Scissors
        X for Lose, Y for Draw, and Z for Win

        Score fo shape you selected (1 for Rock, 2 for Paper, and 3 for Scissors)
        Score for outcome of the round (0 if you lost, 3 if the round was a draw, and 6 if you won)
    */

//    val data = readInput("day02/data_test")
    val data = readInput("day02/data")

    val guide = data.map { it.split(" ").let { Item.from(it[0]) to Outcome.from(it[1]) } }

    var score = 0

    guide.forEach {
        val opponent = it.first
        val outcome = it.second

        val me = opponent.toDraw(outcome)

        score += outcome.score + me.score
    }

    println(score) // 12411
}

private enum class Item(val score: Int) {
    Rock(1), Paper(2), Scissors(3);

    fun toDraw(outcome: Outcome) =
        when (outcome) {
            Outcome.Win -> when (this) {
                Rock -> Paper
                Paper -> Scissors
                Scissors -> Rock
            }
            Outcome.Lose -> when (this) {
                Rock -> Scissors
                Paper -> Rock
                Scissors -> Paper
            }
            Outcome.Draw -> this
        }

    companion object {
        fun from(value: String) = when (value) {
            "A" -> Rock
            "B" -> Paper
            "C" -> Scissors
            else -> throw Exception()
        }
    }
}

private enum class Outcome(val score: Int) {
    Lose(0), Draw(3), Win(6);

    companion object {
        fun from(value: String) = when (value) {
            "X" -> Lose
            "Y" -> Draw
            "Z" -> Win
            else -> throw Exception()
        }
    }
}