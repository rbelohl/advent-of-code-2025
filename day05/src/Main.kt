import java.io.File
import kotlin.math.max
import kotlin.math.min

fun mergeRanges(a: LongRange, b: LongRange) : LongRange {
    return min(a.first, b.first) .. max(a.last, b.last)
}

fun rangesOverlap(a: LongRange, b: LongRange) : Boolean {
    return a.contains(b.first) || b.contains(a.first)
}

fun main(args: Array<String>) {
    val filename = args[0]

    val lines = File(filename).readLines()

    val ranges = lines.takeWhile { it.isNotBlank() }
        .map { it.split('-') }
        .map { it[0].toLong() .. it[1].toLong() }

    val ids = lines.takeLastWhile { it.isNotBlank() }
        .map { it.toLong() }

    val part1 = ids.count { id ->
       ranges.any { range -> range.contains(id) }
    }

    val mergedRanges = mutableSetOf<LongRange>()
    ranges.forEach { range ->
        val overlaps = mergedRanges.filter { rangesOverlap(it, range) }
        val mergedRange = overlaps.fold(range,::mergeRanges)
        mergedRanges.removeAll(overlaps.toSet())
        mergedRanges.add(mergedRange)
    }

    val part2 = mergedRanges.sumOf { it.last - it.first + 1 }

    println(part1)
    println(part2)
}