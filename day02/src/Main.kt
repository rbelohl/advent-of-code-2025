import java.io.File

fun isInvalidIdPart1(id: Long) : Boolean {
    return id.toString().matches("(\\d+)\\1".toRegex())
}

fun isInvalidIdPart2(id: Long) : Boolean {
    return id.toString().matches("(\\d+)\\1+".toRegex())
}

fun main(args: Array<String>) {
    val filename = args[0]

    val line = File(filename).readLines().first()

    val ranges = line.split(",")
        .map { it.split("-").map(String::toLong) }
        .map { it[0]..it[1] }

    val part1 = ranges.flatMap { it.filter(::isInvalidIdPart1) }.sum()
    val part2 = ranges.flatMap { it.filter(::isInvalidIdPart2) }.sum()

    println(part1)
    println(part2)
}