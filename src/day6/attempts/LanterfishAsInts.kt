package day6.attempts

import java.io.File

fun main(){
    val inputFile = File(File("src", "day6").absolutePath + File.separator + "input.txt")
    val input = inputFile.useLines { it.first() }.split(",")
    val school = ArrayList<Int>()
    input.forEach {
        school.add(it.toInt())
    }
    for(i in 1..80/*256*/){
        val nFish = school.size - 1
        for(f in 0..nFish){
            if (school[f] == 0){
                school[f] = 6
                school.add(8)
            }
            else{
                school[f] -= 1
            }
        }
    }
    println(school.size)
}