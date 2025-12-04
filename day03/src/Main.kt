import java.io.File

fun main(args: Array<String>) {
    val filename = args[0]

    val lines = File(filename).readLines()

    val part1 = lines.sumOf { line ->
        val i = line.dropLast(1).indices.maxBy { line[it] }
        val j = line.drop(i + 1).max()
        line[i].digitToInt() * 10 + j.digitToInt()
    }

    val part2 = 0

    println(part1)
    println(part2)
}