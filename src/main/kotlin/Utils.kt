import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

fun readInput(name: String) = File("src/main/kotlin", "$name.txt").readLines()
fun readIntInput(name: String): List<Int> = File("src/main/kotlin", "$name.txt").readLines().map { it.toInt() }
fun readTextInput(name: String) = File("src/main/kotlin", "$name.txt").readText()

fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)
