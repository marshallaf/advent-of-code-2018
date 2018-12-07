import java.io.File
import java.io.BufferedReader

fun main(args: Array<String>) {
    val reader = File("day1a-input.txt").bufferedReader()
    var sum = 0
    reader.useLines { lines -> lines.forEach { 
        sum += it.toInt()
    }}
    println(sum)
}
