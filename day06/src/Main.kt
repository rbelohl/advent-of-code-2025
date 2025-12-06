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

fun <T> List<T>.split(predicate: (T) -> Boolean) : List<List<T>> {
    val result = mutableListOf<List<T>>()
    val tmp = mutableListOf<T>()

    this.forEach {
        if (predicate(it)) {
            result.add(tmp.toList())
            tmp.clear()
        } else {
            tmp.add(it)
        }
    }
    result.add(tmp.toList())

    return result.toList()
}

fun getOperation(str: String) : (Long, Long) -> Long {
    return when (str) {
        "+" -> { a, b -> a + b }
        "*" -> { a, b -> a * b }
        else -> throw Exception()
    }
}

fun main(args: Array<String>) {
    val filename = args[0]

    val lines = File(filename).readLines()

    val inputPart1 = lines.map { line ->
        line.split(" " ).filter{ it.isNotBlank() }
    }.transpose()

    val part1 = inputPart1.sumOf {
        val operation = getOperation(it.last())
        it.dropLast(1).map(String::toLong).reduce(operation)
    }

    val inputPart2 = lines.dropLast(1)
        .map { it.toList() }
        .transpose()
        .map { it.joinToString("") }
    val operations = lines.last()
        .split(" ")
        .filter { it.isNotBlank() }
        .map(::getOperation)

    val part2 = inputPart2.split { it.isBlank() }
        .zip(operations)
        .sumOf { (list, operation) ->
            list.map(String::trim)
                .map(String::toLong)
                .reduce(operation)
        }

    println(part1)
    println(part2)
}