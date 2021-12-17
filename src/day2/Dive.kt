package day2

import java.io.File

fun main() {
    val input = ArrayList<Pair<String, Int>>()

    File(File("src", "day2").absolutePath + File.separator + "input.txt").forEachLine {
        val splitIt = it.split(" ")
        input.add(
                Pair(
                        splitIt[0],
                        splitIt[1].toInt()
                )
        )
    }

    var horPos1 = 0
    var depth1 = 0

    input.forEach{(command, amount) ->
        run {
            when (command) {
                "forward" -> horPos1 += amount
                "down" -> depth1 += amount
                "up" -> depth1 -= amount
            }
        }
    }

    println("Horizontal: $horPos1")
    println("Depth: $depth1")
    println("Part 1: " + horPos1 * depth1)

    var horPos2 = 0
    var depth2 = 0
    var aim = 0

    input.forEach{(command, amount) ->
        run {
            when (command) {
                "forward" -> {
                    horPos2 += amount
                    depth2 += aim * amount
                }
                "down" -> aim += amount
                "up" -> aim -= amount
            }
        }
    }

    println("Horizontal: $horPos2")
    println("Depth: $depth2")
    println("Aim: $aim")
    println("Part 2: " + horPos2 * depth2)
}