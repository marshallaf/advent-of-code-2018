import java.io.File

fun main(args: Array<String>) {
    val boxIds = File("day02a-input01.txt").readLines()
    var count2 = 0
    var count3 = 0
    boxIds.forEach { boxId ->
        val charCount = mutableMapOf<Char, Int>()
        boxId.toCharArray().forEach {
            charCount.put(it, charCount.get(it)?.plus(1) ?: 1)
        }
        if (charCount.values.contains(2)) {
            count2++
        }
        if (charCount.values.contains(3)) {
            count3++
        }
    }
    println("Checksum is $count2 * $count3 = ${count2 * count3}")
}