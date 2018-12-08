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
    swatchCoords.forEach {
        val (_, left, top, width, height) = it

        for (yPoint in top until (top + height)) {
            points.putIfAbsent(yPoint, mutableMapOf())
            val yMap = points[yPoint] as MutableMap

            for (xPoint in left until (left + width)) {
                if (!yMap.containsKey(xPoint)) {
                    yMap[xPoint] = 0
                }
                yMap[xPoint] = yMap[xPoint]?.plus(1) ?: 0
            }
        }
    }

    val sum = points.entries.map { yEntry ->
        yEntry.value.entries.map { xEntry ->
            if (xEntry.value >= 2) 1 else 0
        }.sum()
    }.sum()

    println("There are $sum square inches allocated to more than one swatch.")
}