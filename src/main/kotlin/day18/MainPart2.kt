package day18p2

import readInput

//val input = readInput("day18/data_test")
val input = readInput("day18/data")

val coords = input.map { Pos.from(it) }.toSet()

val maxX = coords.map { it.x }.max()
val minX = coords.map { it.x }.min()
val maxY = coords.map { it.y }.max()
val minY = coords.map { it.y }.min()
val maxZ = coords.map { it.z }.max()
val minZ = coords.map { it.z }.min()

val inside = mutableSetOf<Pos>()
val outside = mutableSetOf<Pos>()


fun main() {

    var count = 0

    for (pos in coords) {
        pos.neighbours().forEach {
            if (!coords.contains(it) && isOutside(it)) count++
        }
    }

    println(count) // 2006
}

fun isOutside(pos: Pos): Boolean {

    val visited: MutableSet<Pos> = mutableSetOf()

    val stack = ArrayDeque(listOf(pos))

    while (stack.isNotEmpty()) {
        val pos = stack.removeLast()

        visited += pos

        if (outside.contains(pos)) {
            outside += visited
            return true
        }

        if (inside.contains(pos)) {
            inside += visited
            return false
        }

        if (pos.x < minX || pos.x > maxX || pos.y < minY || pos.y > maxY || pos.z < minZ || pos.z > maxZ) {
            outside += visited
            return true
        }

        pos.neighbours()
            .filter { !coords.contains(it) }
            .filter { !visited.contains(it) }
            .forEach {
                stack.addLast(it)
            }
    }

    inside += visited
    return false
}

data class Pos(
    val x: Int,
    val y: Int,
    val z: Int
) {

    fun neighbours() = listOf(
        Pos(x-1, y, z),
        Pos(x+1, y, z),
        Pos(x, y-1, z),
        Pos(x, y+1, z),
        Pos(x, y, z-1),
        Pos(x, y, z+1),
    )

    companion object {
        fun from(string: String) : Pos {
            val parts = string.split(",").map { it.toInt() }
            return Pos(parts[0], parts[1], parts[2])
        }
    }
}