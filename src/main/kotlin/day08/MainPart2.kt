package day08p2

import readInput

fun main() {

//    val input = readInput("day08/data_test")
    val input = readInput("day08/data")

    val trees = input.map { it.toCharArray().map { it.digitToInt() } }

    val scores = trees.mapIndexed { y, ints ->
        ints.mapIndexed { x, len -> trees.score(x, y) }
    }

    val max = scores.flatten().max()

    println(max) // 590824
}

fun List<List<Int>>.score(x: Int, y: Int): Int {
    val rowScore = row(y).score(x)
    val colScore = col(x).score(y)
    return rowScore * colScore
}

fun List<Int>.score(pos: Int): Int {
    var treesLeft = take(pos).reversed().takeWhile { it < get(pos) }.size
    var treesRight = drop(pos+1).takeWhile { it < get(pos) }.size

    if (treesLeft != pos) treesLeft++
    if (treesRight != size - pos - 1) treesRight++

    return treesLeft * treesRight
}

fun List<List<Int>>.row(y: Int) = this.get(y)
fun List<List<Int>>.col(x: Int) = this.map { it.get(x) }
