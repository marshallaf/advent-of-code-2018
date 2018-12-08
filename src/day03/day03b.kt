package day03

import java.io.File
import java.util.regex.Pattern

fun main(args: Array<String>) {
    val splitPattern = Pattern.compile("[#\\s@,:x]+")
    val swatchCoords = File("./src/day03/day03a-input.txt")
        .readLines()
        .map { coordLine ->
            val splitLine = coordLine.split(splitPattern)
            splitLine.filter { it.isNotEmpty() }.map { it.toInt() }
        }

    val points = mutableMapOf<Int, MutableMap<Int, Int>>()
    val swatches = mutableMapOf<Int, Boolean>()
    swatchCoords.forEach {
        val (swatchNumber, left, top, width, height) = it
//        println("Processing swatch $swatchNumber")
        swatches[swatchNumber] = true

        for (yPoint in top until (top + height)) {
            points.putIfAbsent(yPoint, mutableMapOf())
            val yMap = points[yPoint]!!

            xLoop@ for (xPoint in left until (left + width)) {
                if (!yMap.containsKey(xPoint)) {
//                    println("Point $xPoint, $yPoint is unclaimed. Marking with swatch #$swatchNumber")
                    yMap[xPoint] = -swatchNumber
                    continue@xLoop
                }
                val presentValue = yMap[xPoint]!!
                if (presentValue < 0) {
//                    println("Point $xPoint, $yPoint is already claimed by #${-presentValue}. Marking both #${-presentValue} and #$swatchNumber as overlapping.")
                    swatches[-presentValue] = false
                    swatches[swatchNumber] = false
                    yMap[xPoint] = 1
                } else if (presentValue == 1) {
//                    println("Point $xPoint, $yPoint is already claimed by multiple other swatches. Marking #$swatchNumber as overlapping.")
                    swatches[swatchNumber] = false
                }
            }
        }
    }

    val nonOverlappingSwatch = swatches.filter {
        it.value
    }

    println("There are ${nonOverlappingSwatch.size} swatches that don't overlap.")
    nonOverlappingSwatch.keys.forEach { println(it) }
}