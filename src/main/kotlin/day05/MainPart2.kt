package day05p2

import readInput

fun main() {

    val input = readInput("day05/data_test")
//    val input = readInput("day05/data")

    val cols = input.first().split(" ").last().toInt()
    val rows = input.filter { it.contains("[") }.count()

    val stacks = input
        .drop(1)
        .take(rows)
        .map { it.extract(cols) }
        .transpose()
        .map { it.reversed() }
        .map { it.filter { it != ' ' }.toMutableList() }
        .toMutableList()

    val instructions = input
        .drop(1)
        .drop(rows)
        .drop(1)
        .map {
            val regexp = """move (\d*) from (\d*) to (\d*)""".toRegex().find(it)!!.groups
            listOf(regexp[1]!!.value.toInt(), regexp[2]!!.value.toInt()-1, regexp[3]!!.value.toInt()-1)
        }

    instructions.forEach {
        val load = stacks[it[1]].takeLast(it[0])
        stacks[it[1]] = stacks[it[1]].dropLast(it[0]).toMutableList()
        stacks[it[2]] += load
    }

    val result = stacks.map { it.last() }.joinToString("")

    println(result)
}

fun String.extract(cols: Int) : List<Char> {
    val chars = mutableListOf<Char>()
    var pos = 1
    while (pos < length) {
        chars += get(pos)
        pos += 4
    }
    while (pos < cols*4) {
        chars += ' '
        pos += 4
    }
    return chars
}

fun <T> List<List<T>>.transpose(): List<List<T>> {
    val result = (first().indices).map { mutableListOf<T>() }.toMutableList()
    forEach { list -> result.zip(list).forEach { it.first.add(it.second) } }
    return result
}