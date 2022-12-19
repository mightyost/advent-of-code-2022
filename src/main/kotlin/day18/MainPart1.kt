package day18p1

import readInput

fun main() {

//    val input = readInput("day18/data_test")
    val input = readInput("day18/data")

    val coords = input.map { Pos.from(it) }.toSet()

    var count = 0

    for (coord in coords) {
        if (!coords.contains(Pos(coord.x-1, coord.y, coord.z))) count++
        if (!coords.contains(Pos(coord.x+1, coord.y, coord.z))) count++
        if (!coords.contains(Pos(coord.x, coord.y-1, coord.z))) count++
        if (!coords.contains(Pos(coord.x, coord.y+1, coord.z))) count++
        if (!coords.contains(Pos(coord.x, coord.y, coord.z-1))) count++
        if (!coords.contains(Pos(coord.x, coord.y, coord.z+1))) count++
    }

    println(count) // 3364
}

data class Pos(
    val x: Int,
    val y: Int,
    val z: Int
) {
    companion object {
        fun from(string: String) : Pos {
            val parts = string.split(",").map { it.toInt() }
            return Pos(parts[0], parts[1], parts[2])
        }
    }
}