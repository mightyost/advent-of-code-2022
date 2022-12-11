package day11p1

import readInput

fun main() {

    val input = readInput("day11/data_test")
//    val input = readInput("day11/data")

    val monkeys = input.chunked(7).map { Monkey.from(it) }.associate { it.id to it }

    var round = 1

    repeat(20) {
        for (monkey in monkeys.values) {
            while (monkey.items.isNotEmpty()) {
                val item = monkey.inspectAndRemoveItem()
                val dest = monkey.getDestination(item)

                monkeys[dest]!!.catch(item)
            }
        }
        round++
    }

    val result = monkeys.values.map { it.inspections }.sortedDescending().take(2).fold(1) { total, it -> total * it }

    println(result)
}

data class Monkey(
    val id: Int,
    val items: MutableList<Int> = mutableListOf(),
    val operation: List<String>,
    val test: Int,
    val trueMonkey: Int,
    val falseMonkey: Int,
    var inspections: Int = 0
) {
    //   Operation: new = old + 6
    fun inspectAndRemoveItem(): Int {
        val item = items.removeAt(0)
        val v1 = if (operation[0] == "old") item else operation[0].toInt()
        val v2 = if (operation[2] == "old") item else operation[2].toInt()

        val level = when (operation[1]) {
            "+" -> v1 + v2
            "-" -> v1 - v2
            "*" -> v1 * v2
            else -> throw Exception()
        }

        inspections++

        return level / 3
    }

    fun getDestination(item: Int) = if (item % test == 0) trueMonkey else falseMonkey

    fun catch(item: Int) {
        items += item
    }

    companion object {
        fun from(text: List<String>): Monkey {
            val id = text[0].split(" ", ":")[1].toInt()
            val items = text[1].split(" ", ":", ",").map { it.toIntOrNull() }.filterNotNull().toMutableList()
            val operation = text[2].split("=")[1].trim().split(" ")
            val test = text[3].split(" ").last().toInt()
            val trueMonkey = text[4].split(" ").last().toInt()
            val falseMonkey = text[5].split(" ").last().toInt()

            return Monkey(id, items, operation, test, trueMonkey, falseMonkey)
        }
    }
}
