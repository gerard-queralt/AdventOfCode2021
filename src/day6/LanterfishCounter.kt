package day6

import java.io.File

fun main(){
    val inputFile = File(File("src", "day6").absolutePath + File.separator + "input.txt")
    val input = inputFile.useLines { it.first() }.split(",")
    val counter = LongArray(10) { 0 } //counter[9] are the fish that will spawn the next day
    input.forEach {
        counter[it.toInt()] += 1L
    }
    for (d in 1..256){
        counter[9] = counter[0]
        counter[7] += counter[0]
        for (f in 1..9){
            counter[f - 1] = counter[f]
        }
    }
    println(counter.take(9).sum())
}