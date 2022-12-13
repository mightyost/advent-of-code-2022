package day12p2

import readInput

typealias Grid = List<List<Char>>

lateinit var map: Grid

lateinit var start: Pos
lateinit var end: Pos

val costs = mutableMapOf<Pos, Int>()

fun main() {

    //val input = readInput("day12/data_test")
    val input = readInput("day12/data")

    val map = input.mapIndexed { y, row ->
        row.mapIndexed { x, h ->
            when (h) {
                'S' -> {
                    start = Pos(x, y); 'a'
                }

                'E' -> {
                    end = Pos(x, y); 'z'
                }

                else -> h
            }
        }
    }

    val lowest = map.mapIndexed { y, row ->
        row.mapIndexed { x, h ->
            if (h == 'a') {
                Pos(x, y)
            } else {
                null
            }
        }.filterNotNull()
    }.flatten()

    val cost = lowest
        .map { walk(it, map, 0) }
        .filterNotNull()
        .min()

    println(cost) // 375
}

fun walk(pos: Pos, map: Grid, visited: Int): Int? {

    if (visited >= costs.getOrDefault(pos, Int.MAX_VALUE)) {
        return null
    }

    costs[pos] = visited

    if (pos == end) {
        return visited
    }

    return getMoves(pos, map)
        .map { walk(it, map, visited+1) }
        .filterNotNull()
        .minOrNull()
}

fun getMoves(pos: Pos, map: List<List<Char>>): List<Pos> {
    val moves = mutableListOf<Pos>()
    if (pos.x-1 >= 0 && map[pos.y][pos.x-1] - map[pos.y][pos.x] <= 1) {
        moves += Pos(pos.x-1, pos.y)
    }
    if (pos.x+1 < map.w && map[pos.y][pos.x+1] - map[pos.y][pos.x] <= 1) {
        moves += Pos(pos.x+1, pos.y)
    }
    if (pos.y-1 >= 0 && map[pos.y-1][pos.x] - map[pos.y][pos.x] <= 1) {
        moves += Pos(pos.x, pos.y-1)
    }
    if (pos.y+1 < map.h && map[pos.y+1][pos.x] - map[pos.y][pos.x] <= 1) {
        moves += Pos(pos.x, pos.y+1)
    }
    return moves
}

data class Pos(
    val x: Int,
    val y: Int
)

val Grid.w:Int get() = first().size
val Grid.h:Int get() = size