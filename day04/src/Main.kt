import java.io.File
import kotlin.math.absoluteValue

fun getAdjacent(lines: List<String>, i : Int, j : Int) : String {
    return lines.mapIndexed { ii, line ->
        if ((i - ii).absoluteValue <= 1) {
            line.filterIndexed { jj, c ->
                !(i == ii && j == jj) && (j - jj).absoluteValue <= 1
            }
        } else {
            ""
        }
    }.joinToString("")
}

fun main(args: Array<String>) {
    val filename = args[0]

    val lines = File(filename).readLines()

    val part1 = lines.mapIndexed { i, line ->
        line.filterIndexed { j, c ->
            c == '@' && getAdjacent(lines, i, j).count { it == '@' } < 4
        }.count()
    }.sum()

    val part2 = 0

    println(part1)
    println(part2)
}