import java.io.File

fun main(args: Array<String>) {
    val filename = args[0]

    val lines = File(filename).readLines()

    val part1 = lines.sumOf { line ->
        val i = line.dropLast(1).indices.maxBy { line[it] }
        val j = line.drop(i + 1).max()
        line[i].digitToInt() * 10 + j.digitToInt()
    }

    val part2 = lines.sumOf { line ->
        var n = 11
        var joltage = 0L
        var list = line

        while (n >= 0) {
            joltage *= 10
            val i = list.dropLast(n).indices.maxBy { list[it] }
            joltage += list[i].digitToInt()
            list = list.drop(i + 1)
            n--
        }
        joltage
    }

    println(part1)
    println(part2)
}