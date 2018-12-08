package day05

import java.io.File
import kotlin.math.absoluteValue

fun main(args: Array<String>) {
    val originalPolymer = File("./src/day05/day05a-input.txt")
        .readLines()
        .map { letters ->
            letters.map {
                if (it.isUpperCase()) {
                    it.toInt()
                } else {
                    it.toUpperCase().toInt() * -1
                }
            }
        }[0]

    val types = mutableMapOf<Char, Int>()
    for (type in 'A'..'Z') {
        val polymer = originalPolymer.toMutableList().apply {
            removeAll {
                it.absoluteValue.toChar() == type
            }
        }
        types[type] = reactPolymer(polymer)
    }

    val offendingType = types.minBy {
        it.value
    }?.key ?: '*'

    println("The offending type is $offendingType the shortest polymer size is ${types[offendingType]}.")
}

fun reactPolymer(polymer: MutableList<Int>): Int {
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
    return polymer.size
}