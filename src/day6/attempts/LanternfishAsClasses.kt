package day6.attempts

import java.io.File

fun main(){
    val inputFile = File(File("src", "day6").absolutePath + File.separator + "input.txt")
    val input = inputFile.useLines { it.first() }.split(",")
    val school = ArrayList<Lanternfish>()
    input.forEach {
        school.add(Lanternfish(it.toInt()))
    }

    //the fish spawned the previous day which will be added at the beginning of the day
    val newFish = ArrayList<Lanternfish>()

    for (i in 1..80){
        newFish.forEach {
            school.add(it)
        }
        newFish.clear()
        school.forEach {
            it.passDay()
            it.spawnNew()?.let { spawned -> newFish.add(spawned) }
        }
    }
    println("Part 1: " + school.size)

    //I'd never seen an outofmemoryerror before
    /*for (i in 81..256){
        newFish.forEach {
            school.add(it)
        }
        newFish.clear()
        school.forEach {
            it.passDay()
            it.spawnNew()?.let { spawned -> newFish.add(spawned) }
        }
    }
    println("Part 2: " + school.size)*/
}