package day11

import java.io.File

fun main() {
    val inputFile = File(File("src", "day11").absolutePath + File.separator + "input.txt")
    val input = inputFile.useLines { it.toList() }
    input.map { l -> l.toCharArray().map { it.digitToInt() } }
}