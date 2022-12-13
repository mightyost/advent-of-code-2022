package day13p1

import readInput

fun main() {

//    val input = readInput("day13/data_test")
    val input = readInput("day13/data")

    val pairs = input.chunked(3).map { it[0] to it[1] }

    val sum = pairs.mapIndexed { index, pair -> index+1 to compareLists(pair.first, pair.second) }
        .filter { it.second != null && it.second!! }
        .sumOf { it.first }

    println(sum)
}

fun extractList(string: String): String {
    var depth = 0

    for (i in string.indices) {
        if (string[i] == '[') depth++
        if (string[i] == ']') depth--
        if (depth == 0) {
            return string.substring(0, i+1)
        }
    }
    throw Exception()
}

fun extractValue(string: String): String {
    var result = ""

    for (char in string) {
        if (!char.isDigit()) {
            break
        }
        result += char
    }
    return result
}

fun compareLists(leftString: String, rightString: String): Boolean? {
    var i = 0
    var j = 0

    while(true) {

        if (i == leftString.length || j == rightString.length) {
            return null
        }

        val leftChar = leftString[i]
        val rightChar = rightString[j]

        val leftVal = extractValue(leftString.substring(i))
        val rightVal = extractValue(rightString.substring(j))

        if (leftChar.isDigit() && rightChar.isDigit()) {
            if (leftVal == rightVal) {
                i += leftVal.length
                j += rightVal.length
                continue
            }
            return leftVal.toInt() < rightVal.toInt()
        }


        if (leftChar == rightChar) {
            i++
            j++
            continue
        }

        if (leftChar == ']') {
            return true
        }

        if (rightString[j] == ']') {
            return false
        }

        if (leftChar.isDigit() && rightChar == '[') {
            val subList = extractList(rightString.substring(j))
            val result = compareLists("[$leftVal]", subList)
            if (result != null) {
                return result
            }
            i += leftVal.length
            j += subList.length
            continue
        }

        if (rightChar.isDigit() && leftChar == '[') {
            val subList = extractList(leftString.substring(i))
            val result = compareLists(subList, "[$rightVal]")
            if (result != null) {
                return result
            }
            i += subList.length
            j += rightVal.length
            continue
        }

        throw Exception()
    }
}