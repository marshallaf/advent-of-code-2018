package day05

import java.io.File

fun main(args: Array<String>) {
    val polymer = File("./src/day05/day05a-input.txt")
        .readLines()
        .map { letters ->
            letters.map {
                if (it.isUpperCase()) {
                    it.toInt()
                } else {
                    it.toUpperCase().toInt() * -1
                }
            }
        }[0].toMutableList()

    var loopAgain = true
    while (loopAgain && polymer.size > 1) {
        for (index in 1 until polymer.size) {
            if (polymer[index-1] + polymer[index] == 0) {
                polymer[index-1] = 0
                polymer[index] = 0
            }
        }
        loopAgain = polymer.removeAll { it == 0 }
    }

    val stringPolymer = polymer.map {
        if (it > 0) {
            it.toChar()
        } else {
            (it * -1).toChar().toLowerCase()
        }
    }.joinToString("")

    println("The resulting polymer is $stringPolymer and is size ${stringPolymer.length}.")
}