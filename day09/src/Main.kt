import java.io.File
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min

fun area(a: Pair<Long, Long>, b: Pair<Long, Long>) : Long {
    return ((a.first - b.first).absoluteValue  + 1) * ((a.second - b.second).absoluteValue + 1)
}

fun <T> List<T>.allPairs() : List<Pair<T, T>> {
    val result = mutableListOf<Pair<T, T>>()
    this.withIndex().forEach { (i, first) ->
        this.subList(0, i).forEach { second ->
            result.add(Pair(first, second))
        }
    }
    return result.toList()
}

fun rectangleLineIntersect(rectangle: Pair<Pair<Long, Long>, Pair<Long, Long>>, line: Pair<Pair<Long, Long>, Pair<Long,Long>>) : Boolean {
    // TODO maybe use this?
    val rectMinX = min(rectangle.first.first, rectangle.second.first)
    val rectMaxX = max(rectangle.first.first, rectangle.second.first)
    val rectMinY = min(rectangle.first.second, rectangle.second.second)
    val rectMaxY = max(rectangle.first.second, rectangle.second.second)

    val l1 = Pair(Pair(rectMinX, rectMinY), Pair(rectMaxX, rectMinY))
    val l2 = Pair(Pair(rectMaxX, rectMinY), Pair(rectMaxX, rectMaxY))

    val l3 = Pair(Pair(rectMaxX, rectMaxY), Pair(rectMinX, rectMaxY))
    val l4 = Pair(Pair(rectMinX, rectMaxY), Pair(rectMinX, rectMinY))

    if (linesIntersect(line, l1)) return true
    if (linesIntersect(line, l2)) return true
    if (linesIntersect(line, l3)) return true
    if (linesIntersect(line, l4)) return true

    return false
}

fun linesIntersect(lineA: Pair<Pair<Long, Long>, Pair<Long, Long>>, lineB: Pair<Pair<Long, Long>, Pair<Long,Long>>) : Boolean {
    val ax1 = lineA.first.first
    val ax2 = lineA.second.first
    val ay1 = lineA.first.second
    val ay2 = lineA.second.second

    val bx1 = lineB.first.first
    val bx2 = lineB.second.first
    val by1 = lineB.first.second
    val by2 = lineB.second.second

    if (ax1 == ax2 && bx1 == bx2) return false
    if (ay1 == ay2 && by1 == by2) return false

    if (ax1 == ax2) {
        if (ax1 in (min(bx1, bx2))..<max(bx1, bx2)) {
            if (by1 in (min(ay1, ay2))..<max(ay1, ay2)) {
                return true
            }
        }
    } else {
        if (bx1 in (min(ax1, ax2))..<max(ax1, ax2)) {
            if (ay1 in (min(by1, by2))..<max(by1, by2)) {
                return true
            }
        }
    }

    return false
}

fun rectangleInsidePolygon(rectangle: Pair<Pair<Long, Long>, Pair<Long, Long>>, lines: List<Pair<Pair<Long, Long>, Pair<Long,Long>>>) : Boolean {
    val rectMinX = min(rectangle.first.first, rectangle.second.first)
    val rectMaxX = max(rectangle.first.first, rectangle.second.first)
    val rectMinY = min(rectangle.first.second, rectangle.second.second)
    val rectMaxY = max(rectangle.first.second, rectangle.second.second)

    val a = Pair(rectMinX, rectMinY)
    val b = Pair(rectMaxX, rectMinY)
    val c = Pair(rectMaxX, rectMaxY)
    val d = Pair(rectMinX, rectMaxY)

    if (!pointInsidePolygon(a, lines)) return false
    if (!pointInsidePolygon(b, lines)) return false
    if (!pointInsidePolygon(c, lines)) return false
    if (!pointInsidePolygon(d, lines)) return false

    return true
}

fun pointOnLine(point: Pair<Long, Long>, line: Pair<Pair<Long, Long>, Pair<Long,Long>>) : Boolean {
    if (line.first.first == line.second.first) {
        if (point.first != line.first.first) return false
        val start = min(line.first.second, line.second.second)
        val end = max(line.first.second, line.second.second)
        return (point.second in start .. end)
    }
    else if (line.first.second == line.second.second) {
        if (point.second != line.first.second) return false
        val start = min(line.first.first, line.second.first)
        val end = max(line.first.first, line.second.first)
        return (point.first in start .. end)
    }
    return false
}


fun pointInsidePolygon(point: Pair<Long, Long>, lines: List<Pair<Pair<Long, Long>, Pair<Long, Long>>>) : Boolean {
    val rayStart = Pair(point.first, 0L)
    val rayEnd =  Pair(point.first, point.second)
    val ray = Pair(rayStart, rayEnd)

    var count = 0
    lines.forEach { line ->
        if (pointOnLine(point, line)) return true
        if (linesIntersect(ray, line)) {
            count++
        }
    }
    return count % 2 == 1
}

fun perimeter(rectangle: Pair<Pair<Long, Long>, Pair<Long, Long>>) : List<Pair<Long,Long>> {
    val rectMinX = min(rectangle.first.first, rectangle.second.first)
    val rectMaxX = max(rectangle.first.first, rectangle.second.first)
    val rectMinY = min(rectangle.first.second, rectangle.second.second)
    val rectMaxY = max(rectangle.first.second, rectangle.second.second)

    val result = mutableListOf<Pair<Long, Long>>()

    for (x in rectMinX..rectMaxX) {
        result.add(Pair(x, rectMinY))
        result.add(Pair(x, rectMaxY))
    }
    for (y in rectMinY .. rectMaxY) {
        result.add(Pair(rectMinX, y))
        result.add(Pair(rectMaxX, y))
    }
    return result.toList()
}

fun main(args: Array<String>) {
    val filename = args[0]

    val points = File(filename).readLines()
        .map { it.split(",") }
        .map { Pair(it[0].toLong(), it[1].toLong()) }

    val part1 = points.allPairs().maxOf { area(it.first, it.second) }

    val mutPoints = points.toMutableList()
    mutPoints.add(points.first())
    val lines = mutPoints.windowed(2).map { Pair(it[0], it[1]) }

    val part2 = points.allPairs()
        .parallelStream()
        .filter { rectangle ->
            rectangleInsidePolygon(rectangle, lines)
        }.filter { rectangle ->
            perimeter(rectangle).all { point ->
                pointInsidePolygon(point, lines)
            }
        }.map { area(it.first, it.second) }
        .max { o1, o2 -> o1.compareTo(o2) }
        .get()

    println(part1)
    println(part2)
}