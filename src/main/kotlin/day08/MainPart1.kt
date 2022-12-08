package day08p1

import readInput

fun main() {


//    val input = readInput("day08/data_test")
    val input = readInput("day08/data")

    val trees = input.map { it.toCharArray().map { it.digitToInt() } }

    val w = trees[0].size
    val h = trees.size

    var count = 0

    for (y in 0 until h) {
        for (x in 0 until w) {
            if (trees.isVisible(x, y)) {
                count++
            }
        }
    }

    println(count) // 1719
}

fun List<List<Int>>.isVisible(x: Int, y: Int): Boolean {
    if (row(y).isVisible(x) || col(x).isVisible(y)) {
        return true
    }
    return false
}

fun List<List<Int>>.row(y: Int) = this.get(y)
fun List<List<Int>>.col(x: Int) = this.map { it.get(x) }

fun List<Int>.isVisible(x: Int): Boolean {
    if (x == 0 || take(x).max() < get(x)) {
        return true
    }
    if (x == size-1 || drop(x+1).max() < get(x)) {
        return true
    }

    return false
}