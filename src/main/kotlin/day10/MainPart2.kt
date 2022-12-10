package day10

import readInput

fun main() {

//    val input = readInput("day10/data_test")
    val input = readInput("day10/data")

    val display = List(6) { MutableList(40) { ' ' } }
    var row = 0
    var col = 0

    var cycle = 0
    var x = 1

    var time = 0
    var nextX = 1

    for (op in input) {

        while (time > 0) {
            time--
            cycle++

            if (col >= x-1 && col <= x+1) {
                display[row][col] = '#'
            }

            if (col++ == 39) {
                row++
                col = 0
            }
        }

        x = nextX

        if (op == "noop") {
            time = 1
        }
        else {
            time = 2
            nextX = x + op.substring(5).toInt()
        }
    }

    display.print() // EGJBGCFK
}

fun List<MutableList<Char>>.print() {
    forEach { println(it.joinToString("")) }
}