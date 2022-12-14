package day14p1

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

    val maxX = walls.maxOf { it.x }
    val maxY = walls.maxOf { it.y }

    val map = Array(maxY+1) { Array(maxX+1) { ' ' } }

    // Draw walls
    walls.forEach {
        map[it.y][it.x] = '#'
    }

    var count = 0

    while (dropSand(map)) {
        count++
    }

    println(count) // 1061
}

fun dropSand(map: Array<Array<Char>>): Boolean {
    var x = 500
    var y = 0

    while (true) {
        if (y+1 >= map.size || x+1 >= map[0].size || x-1 < 0) {
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