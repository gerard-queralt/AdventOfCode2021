package day11

import java.io.File

fun simulate(octopus: List<List<Octopus>>, steps: Int) : Int {
    var flashCount = 0
    for (s in 1..steps) {
        octopus.forEach { it.forEach { o -> o.increaseEnergy() } }
        for (i in octopus.indices) {
            for (j in octopus[i].indices) {
                flashCount += checkFlash(octopus, i, j)
            }
        }
        octopus.forEach { it.forEach { o -> o.reset() } }
    }
    return flashCount
}

fun findSync(octopus: List<List<Octopus>>) : Int {
    var step = 0
    var flashCount = 0
    val matSize = octopus.size * octopus[0].size
    while (flashCount != matSize) {
        flashCount = simulate(octopus, 1)
        ++step
    }
    return step
}

fun checkFlash(octopus: List<List<Octopus>>, i: Int, j: Int) : Int {
    val oct = octopus[i][j]
    if (!oct.flashedThisStep && oct.maybeFlash()){
        return increaseAdjacent(octopus, i, j) + 1
    }
    return 0
}

fun increaseAdjacent(octopus: List<List<Octopus>>, i: Int, j: Int) : Int {
    var flashCount = 0
    if (0 <= i - 1) { //up
        octopus[i - 1][j].increaseEnergy()
        flashCount += checkFlash(octopus, i - 1, j)
    }
    if (i + 1 < octopus.size) { //down
        octopus[i + 1][j].increaseEnergy()
        flashCount += checkFlash(octopus, i + 1, j)
    }
    if (0 <= j - 1) { //left
        octopus[i][j - 1].increaseEnergy()
        flashCount += checkFlash(octopus, i, j - 1)
    }
    if (j + 1 < octopus[i].size) { //right
        octopus[i][j + 1].increaseEnergy()
        flashCount += checkFlash(octopus, i, j + 1)
    }
    if (0 <= i - 1 && 0 <= j - 1) { //up-left
        octopus[i - 1][j - 1].increaseEnergy()
        flashCount += checkFlash(octopus, i - 1, j - 1)
    }
    if (0 <= i - 1 && j + 1 < octopus[i].size) { //up-right
        octopus[i - 1][j + 1].increaseEnergy()
        flashCount += checkFlash(octopus, i - 1, j + 1)
    }
    if (i + 1 < octopus.size && 0 <= j - 1) { //down-left
        octopus[i + 1][j - 1].increaseEnergy()
        flashCount += checkFlash(octopus, i + 1, j - 1)
    }
    if (i + 1 < octopus.size && j + 1 < octopus[i].size) { //down-right
        octopus[i + 1][j + 1].increaseEnergy()
        flashCount += checkFlash(octopus, i + 1, j + 1)
    }
    return flashCount
}

fun copyMat(octopus: List<List<Octopus>>) : List<List<Octopus>> {
    val copy = ArrayList<ArrayList<Octopus>>()
    octopus.forEach {
        val copyLine = ArrayList<Octopus>()
        it.forEach { o -> copyLine.add(Octopus(o.energy)) }
        copy.add(copyLine)
    }
    return copy
}

fun main() {
    val inputFile = File(File("src", "day11").absolutePath + File.separator + "input.txt")
    val input = inputFile.useLines {
        it.toList().map{
                l -> l.split("").subList( 1, l.length + 1 ).map { //remove first and last empty chars
                    s -> s.toInt()
                }
        }
    }
    val octopus = input.map { l -> l.map { n -> Octopus(n) } }
    println("Part 1: " + simulate(copyMat(octopus), 100))
    println("Part 2: " + findSync(copyMat(octopus)))
}