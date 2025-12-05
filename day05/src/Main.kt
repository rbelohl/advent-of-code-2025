import java.io.File
import kotlin.math.max
import kotlin.math.min

fun mergeRanges(range1: LongRange, range2: LongRange) : LongRange {
    return min(range1.first, range2.first) .. max(range1.last, range2.last)
}

fun rangesOverlap(a: LongRange, b: LongRange) : Boolean {
    return if (a.first >= b.first) {
        a.first <= b.last
    } else {
        b.first <= a.last
    }
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

    val rs = mutableListOf<LongRange>()
    ranges.forEach { range ->
        val overlaps = rs.filter { rangesOverlap(it, range) }

        if (overlaps.isNotEmpty()) {
            val x = overlaps.fold(range) { acc, range -> mergeRanges(acc, range) }
            rs.removeAll(overlaps)
            rs.add(x)
        } else {
            rs.add(range)
        }
    }

    val part2 = rs.sumOf { it.last - it.first + 1 }

    println(part1)
    println(part2)
}