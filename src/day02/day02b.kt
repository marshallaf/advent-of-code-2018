import java.io.File

fun main(args: Array<String>) {
    val boxIds = File("day02a-input01.txt").readLines()
    val boxIdsWithSortedChars = boxIds.map {
        it.toCharArray().sortedBy { it }.joinToString("")
    }
    val boxIdsSortMap = boxIdsWithSortedChars.mapIndexed { index, boxId -> boxId to boxIds[index] }.toMap()
    val boxIdsWithSortedCharsSorted = boxIdsWithSortedChars.sortedBy { it }
    val expectedLength = boxIds[0].length - 1
    var sameChars = ""
    for (i in 1 until boxIdsWithSortedCharsSorted.size) {
        val id1 = boxIdsSortMap[boxIdsWithSortedCharsSorted[i-1]] ?: "none"
        val id2 = boxIdsSortMap[boxIdsWithSortedCharsSorted[i]] ?: "none"
        sameChars = sameChars(id1, id2)
        if (sameChars.length == expectedLength) break
    }
    println("Two correct box IDs have the common letters: $sameChars")
}

fun sameChars(id1: String, id2: String): String {
    var sameList = mutableListOf<Char>()
    for (i in 0 until id1.length) {
        if (id1[i] == id2[i]) {
            sameList.add(id1[i])
        }
    }
    return sameList.joinToString("")
}