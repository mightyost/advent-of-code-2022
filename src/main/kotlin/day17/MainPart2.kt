package day17p2

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

    /*
        Running program outputs:

        0-0 (0-0): 0
        1748-10091 (3-0): 2773
        3473-20182 (3-0): 5507
        5198-30273 (3-0): 8241
        6923-40364 (3-0): 10975
        8648-50455 (3-0): 13709
        10373-60546 (3-0): 16443

        I.e. it is cyclic after i >= 1725 adding 2734 to the height for every new round.

        We have 1000000000000/1725 = 579710144 rounds giving 579710144*2734 = 1584927533696 in height

        We need to get height at round 1600 (1000000000000 - 1000000000000/1725) since that is outside the cycle, after
        simulating that we get height to 2551.

        Total answer is 2551 + 2734*579710144 = 1584927533696
    */

    for(i in 0 until 10000) {

        val shape = shapes[i % shapes.size]

        val highest = shaft.maxOf { it.y }

        val w = shape.maxOf { it.x } + 1

        var x = 2
        var y = highest + 3 + 1

        if (windIndex % input.length == 0) {
            println("$i-$windIndex (${i%shapes.size}-${windIndex%input.length}): $highest")
        }

        if (i == 1600) {
            println("Hight at 1600 shapes: $highest")
        }

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