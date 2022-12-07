package day07p2

import readInput

fun main() {

//    val input = readInput("day07/data_test")
    val input = readInput("day07/data")

    val root = Dir()
    var current: Dir? = root

    input.forEach {
        when {
            it == "$ cd /" -> current = root
            it == "$ cd .." -> current = current?.parent ?: root
            it == "$ ls" -> Unit
            it.startsWith("$ cd ") -> current = current!!.dirs[it.substring(5)]
            it.startsWith("dir ") -> current!!.addDir(it.substring(4))
            it[0].isDigit() -> current!!.addFile(it)
            else -> throw Exception(it)
        }
    }

    val toFree = 30000000 - (70000000 - root.size)

    val min = root.getAllDirs()
        .map {it.size}
        .filter { it >= toFree }
        .min()

    println(min) // 9608311
}

class Dir(parentDir: Dir? = null) {
    val parent: Dir? = parentDir
    val dirs: MutableMap<String, Dir> = mutableMapOf()
    val files: MutableMap<String, Int> = mutableMapOf()

    fun addDir(name: String) {
        dirs += name to Dir(this)
    }

    fun addFile(row: String) {
        val parts = row.split(" ")
        addFile(parts[1], parts[0].toInt())
    }

    fun addFile(name: String, size: Int) {
        files += name to size
    }

    fun getAllDirs(): List<Dir> {
        val all = mutableListOf(this)
        all += dirs.values.map { it.getAllDirs() }.flatten()
        return all
    }

    val size: Int get() = files.values.sum() + dirs.values.sumOf { it.size }
}