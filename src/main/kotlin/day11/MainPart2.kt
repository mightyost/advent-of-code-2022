package day11p2

import readInput

fun main() {

//    val input = readInput("day11/data_test")
    val input = readInput("day11/data")

    val monkeys = input.chunked(7).map { Monkey.from(it) }.associate { it.id to it }

    repeat(10000) {
        for (monkey in monkeys.values) {
            while (monkey.items.isNotEmpty()) {
                val item = monkey.inspectAndRemoveItem()
                val dest = monkey.getDestination(item)

                monkeys[dest]!!.catch(item)
            }
        }
    }

    val result = monkeys.values.map { it.inspections }.sortedDescending().take(2).fold(1L) { total, it -> total * it }

    println()
    println(result) // 28244037010
}

data class Item(
    val first: Int,
    val values: MutableList<Pair<String, String>> = mutableListOf()
)

data class Monkey(
    val id: Int,
    val items: MutableList<Item> = mutableListOf(),
    val operation: List<String>,
    val divisor: Int,
    val trueMonkey: Int,
    val falseMonkey: Int,
    var inspections: Long = 0
) {
    //   Operation: new = old + 6
    fun inspectAndRemoveItem(): Item {
        val item = items.removeAt(0)

        item.values += operation[1] to operation[2]

        inspections++

        return item
    }

    fun getDestination(item: Item): Int {
        var acc = item.first % divisor

        for (value in item.values) {
            val operation = value.first
            val v2 = if (value.second == "old") acc else value.second.toInt()
            if (operation == "+") {
                acc += v2
            }
            else {
                acc *= v2
            }

            acc %= divisor
        }

        return if (acc == 0) trueMonkey else falseMonkey
    }

    fun catch(item: Item) {
        items += item
    }

    companion object {
        fun from(text: List<String>): Monkey {
            val id = text[0].split(" ", ":")[1].toInt()
            val items = text[1].split(" ", ":", ",")
                .map { it.toIntOrNull() }
                .filterNotNull()
                .map { Item(it) }
                .toMutableList()
            val operation = text[2].split("=")[1].trim().split(" ")
            val test = text[3].split(" ").last().toInt()
            val trueMonkey = text[4].split(" ").last().toInt()
            val falseMonkey = text[5].split(" ").last().toInt()

            return Monkey(id, items, operation, test, trueMonkey, falseMonkey)
        }
    }
}
