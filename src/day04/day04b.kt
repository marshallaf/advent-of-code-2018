package day04

import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

fun main(args: Array<String>) {
    val idPattern = Pattern.compile("#(\\d+)")
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
    val events = File("./src/day04/day04a-input.txt")
        .readLines()
        .map { eventLine ->
            val time = dateFormat.parse(eventLine.subSequence(1, 17).toString())
            val eventType = when {
                eventLine.contains("begins") -> GuardEventType.START
                eventLine.contains("asleep") -> GuardEventType.ASLEEP
                else -> GuardEventType.AWAKE
            }
            val guardId = if (eventLine.contains("begins")) {
                idPattern.matcher(eventLine).run {
                    if (find()) group(1).toInt() else -2
                }
            } else -1
            GuardEvent(time, guardId, eventType)
        }.sortedBy { it.time }
    val guards = mutableMapOf<Int, Array<Int>>()
    var currentGuardId = -1
    var asleepMinute = -1
    events.forEach { guardEvent ->
        when (guardEvent.type) {
            GuardEventType.START -> {
//                println("Guard #${guardEvent.id} starting shift.")
                if (asleepMinute != -1) {
                    for (minute in asleepMinute..59) {
//                        println("Guard #$currentGuardId has been asleep from minute $asleepMinute to 59.")
                        guards[currentGuardId]?.set(minute, guards[currentGuardId]?.get(minute)?.plus(1) ?: 1)
                    }
                }
                currentGuardId = guardEvent.id
                asleepMinute = -1
                guards.putIfAbsent(currentGuardId, Array(60) { 0 })
            }
            GuardEventType.ASLEEP -> {
                asleepMinute = Calendar.getInstance().apply {
                    time = guardEvent.time
                }.get(Calendar.MINUTE)
//                println("Guard #$currentGuardId has fallen asleep at minute $asleepMinute.")
            }
            GuardEventType.AWAKE -> {
                val awakeMinute = Calendar.getInstance().apply {
                    time = guardEvent.time
                }.get(Calendar.MINUTE)

                for (minute in asleepMinute until awakeMinute) {
                    guards[currentGuardId]?.set(minute, guards[currentGuardId]?.get(minute)?.plus(1) ?: 1)
                }
//                println("Guard #$currentGuardId has been asleep from minute $asleepMinute to ${awakeMinute - 1}.")
                asleepMinute = -1
            }
        }
    }

    val sleepiestGuard = guards.maxBy {
        it.value.max() ?: 0
    }?.key ?: -1
    val sleepiestMinute = guards[sleepiestGuard]?.indexOf(guards[sleepiestGuard]?.max())

    println("""The guard that was the sleepiest is #$sleepiestGuard, with ${guards[sleepiestGuard]?.sum() ?: 0} minutes sleeping.
        |The guard spent minute $sleepiestMinute asleep the most.
    """.trimMargin())
}