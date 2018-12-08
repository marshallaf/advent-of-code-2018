package day01

import java.io.File

fun main(args: Array<String>) {
    val reader = File("./src/day01/day01a-input.txt").bufferedReader()
    var sum = 0
    reader.useLines { lines ->
        lines.forEach {
            sum += it.toInt()
        }
    }
    println(sum)
}
