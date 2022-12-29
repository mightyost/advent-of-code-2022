package day17p1

import readTextInput

fun main() {

    val shapes = listOf(
        setOf(Pos(0, 0), Pos(1,0), Pos(2, 0), Pos(3, 0)),
        setOf(Pos(1, 0), Pos(0, 1), Pos(1,1), Pos(2, 1), Pos(1, 2)),
        setOf(Pos(2, 2), Pos(2, 1), Pos(0,0), Pos(1, 0), Pos(2, 0)),
        setOf(Pos(0, 0), Pos(0, 1), Pos(0,2), Pos(0, 3)),
        setOf(Pos(0, 0), Pos(1, 0), Pos(0,1), Pos(1, 1))
    )

//    val input = readTextInput("day17/data_test")
    val input = readTextInput("day17/data")

    var windIndex = 0

    val shaft = IntRange(0, 6).map { Pos(it, 0) }.toMutableSet()

    for(i in 0 until 2022) {

        val shape = shapes[i % shapes.size]

        val highest = shaft.maxOf { it.y }

        val w = shape.maxOf { it.x } + 1

        var x = 2
        var y = highest + 3 + 1

        while(true) {
            val wind = input[windIndex++ % input.length]

            var newX = x
            if (wind == '<') {
                if (x > 0) newX = x-1
            } else {
                if (x + w < 7) newX = x+1
            }

            val collisionX = shape.map { Pos(it.x + newX, it.y + y) }.any { shaft.contains(it) }

            if (!collisionX) {
                x = newX
            }

            val newY = y - 1

            val collisionY = shape.map { Pos(it.x + x, it.y + newY) }.any { shaft.contains(it) }

            if (collisionY) {
                shaft += shape.map { Pos(it.x + x, it.y + y) }
                break
            }

            y = newY
        }
    }

    println(shaft.maxOf { it.y })
}

data class Pos(
    val x: Int,
    val y: Int
)

fun Set<Pos>.print(shape: List<Pos>) {
    val max = (this + shape).maxOf { it.y }

    for (y in max downTo 0) {
        for (x in 0 until 7) {
            if (contains(Pos(x, y))) {
                print("#")
            } else if (shape.contains(Pos(x, y))) {
                print("@")
            } else {
                print(".")
            }
        }
        println()
    }
    println()
}