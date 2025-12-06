import java.io.File

fun <T> List<List<T>>.transpose() : List<List<T>> {
    val width = this[0].size;
    val height = this.size;
    return List(width) { i ->
        List(height) { j ->
            this[j][i]
        }
    }
}

fun main(args: Array<String>) {
    val filename = args[0]

    val lines = File(filename).readLines()

    val input = lines.map { line ->
        line.split(" " ).filter{ it.isNotBlank() }
    }.transpose()

    val part1 = input.sumOf {
        val operation = when (it.last()) {
            "+" -> { a: Long, b: Long -> a + b }
            "*" -> { a: Long, b: Long -> a * b }
            else -> throw Exception()
        }
        it.dropLast(1).map(String::toLong).reduce(operation)
    }

    val part2 = 0

    println(part1)
    println(part2)
}