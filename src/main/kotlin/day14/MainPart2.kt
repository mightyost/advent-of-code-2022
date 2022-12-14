package day14p2

import readInput

fun main() {

//    val input = readInput("day14/data_test")
    val input = readInput("day14/data")

    // Get all wall coordinates
    val walls = input.map {
        it.split(" -> ").map {
            it.split(",").map { it.toInt() }.let { Pos(it[0], it[1]) }
        }.zipWithNext { a, b ->
            getLine(a, b)
        }.flatten().distinct()
    }.flatten()

    val maxY = walls.maxOf { it.y }

    val map = Array(maxY+3) { Array(1000) { ' ' } }

    // Draw walls
    walls.forEach {
        map[it.y][it.x] = '#'
    }

    // Draw floor
    for (x in 0 until 1000) map[maxY+2][x] = '='

    var count = 0

    while (dropSand(map)) {
        count++
    }

    println(count) // 25055
}

fun dropSand(map: Array<Array<Char>>): Boolean {
    var x = 500
    var y = 0

    while (true) {

        if (map[y][x] != ' ') {
            return false
        }

        if (map[y+1][x] == ' ') {
            y += 1
            continue
        }
        if (map[y+1][x-1] == ' ') {
            x -= 1
            y += 1
            continue
        }
        if (map[y+1][x+1] == ' ') {
            x += 1
            y += 1
            continue
        }

        map[y][x] = 'o'

        return true
    }
}

fun getLine(start: Pos, end: Pos): List<Pos> {
    var pos = start

    val line = mutableListOf(start)

    while (pos != end) {
        val deltaX = end.x.compareTo(pos.x)
        val deltaY = end.y.compareTo(pos.y)
        pos = Pos(pos.x+deltaX, pos.y+deltaY)
        line += pos
    }

    return line
}

data class Pos(
    val x: Int,
    val y: Int
)