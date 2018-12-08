package day01

import java.io.File

fun main(args: Array<String>) {
    val frequencies = File("./src/day01/day01a-input.txt").readLines()
    var sum = 0
    val sums: MutableSet<Int> = mutableSetOf()
    foundLoop@ while (true) {
        for (frequency in frequencies) {
            sums.add(sum)
            sum += frequency.toInt()
            if (sums.contains(sum)) {
                break@foundLoop
            }
        }
    }
    println("First frequency repeated twice: $sum")
}
