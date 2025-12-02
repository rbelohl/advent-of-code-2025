import java.io.File
import kotlin.math.absoluteValue

fun main(args: Array<String>) {
    val filename = args[0]

    val lines = File(filename).readLines()

    val rotations = lines.map { line ->
        val sign = if (line.first() == 'L') -1 else 1
        line.drop(1).toInt() * sign
    }

    val part1 = rotations.runningFold(50) { acc, rotation -> (acc + rotation).mod(100) }.count{ it == 0 }

    val part2 = rotations.runningFold(Pair(0,50)) { acc, rotation ->
        val zeroCount = ((acc.second + rotation) / 100).absoluteValue + if (acc.second != 0 && (acc.second + rotation) <= 0) 1 else 0
        val newPosition = (acc.second + rotation).mod(100)
        Pair(zeroCount, newPosition)
    }.sumOf { it.first }

    println(part1)
    println(part2)
}