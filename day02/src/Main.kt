import java.io.File

fun isInvalidIdPart1(id: Long) : Boolean {
    val str = id.toString()
    if (str.length % 2 == 1) {
        return false
    }
    val half = str.length / 2
    return str.take(half) == str.takeLast(half)
}

fun main(args: Array<String>) {
    val filename = args[0]

    val line = File(filename).readLines().first()

    val ranges = line.split(",")
        .map { it.split("-").map(String::toLong) }
        .map { it[0]..it[1] }

    val part1 = ranges.flatMap { it.filter(::isInvalidIdPart1) }.sum()

    println(part1)

}