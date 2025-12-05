import java.io.File
import kotlin.text.digitToInt

fun getMaxJoltage(batteries: String, canTurnOn: Int) : Long {
    if (canTurnOn <= 0) {
        return 0
    }
    val i = batteries.dropLast(canTurnOn - 1).indices.maxBy { batteries[it] }
    val pow = 10.toBigInteger().pow(canTurnOn - 1).toLong()
    return batteries[i].digitToInt() * pow + getMaxJoltage(batteries.drop(i + 1), canTurnOn - 1)
}

fun main(args: Array<String>) {
    val filename = args[0]

    val lines = File(filename).readLines()

    val part1 = lines.sumOf { getMaxJoltage(it, 2) }
    val part2 = lines.sumOf { getMaxJoltage(it, 12) }

    println(part1)
    println(part2)
}