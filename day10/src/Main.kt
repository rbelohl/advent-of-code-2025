import java.io.File

fun buttonsToBits(buttons: List<Int>) : Int {
    return buttons.fold(0) { acc, i ->
        acc or (1 shl i)
    }
}

fun bfs(target: Int, buttons: List<Int>) : Int {
    // first = lights
    // second = distance
    val start = Pair(0, 0)

    val queue = mutableListOf(start)
    val visited = mutableSetOf<Int>()

    while (queue.isNotEmpty()) {
        val (currentLights, distance) = queue.removeFirst()
        if (currentLights == target) return distance
        if (visited.contains(currentLights)) continue
        visited += currentLights
        queue += buttons.map { button -> Pair(currentLights xor button, distance + 1) }
    }
    return -1
}

fun main(args: Array<String>) {
    val filename = args[0]
    val lines = File(filename).readLines()

    val input = lines.map { line ->
        line.split(" ")
            .map { it.drop(1).dropLast(1) }
    }

    val targetLights = input.map {
        it.first()
            .replace('.', '0')
            .replace('#', '1')
            .reversed()
            .toInt(2)
    }

    val buttons = input
        .map { list ->
            list.drop(1)
                .dropLast(1)
                .map { it.split(",").map(String::toInt) }
                .map (::buttonsToBits)
    }

    val part1 = targetLights.zip(buttons).sumOf { (target, buttons) ->
        bfs(target, buttons)
    }

    val part2 = 0

    println(part1)
    println(part2)
}