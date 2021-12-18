package day6.attempts

class Lanterfish(var days: Int = 9 /*instead of skipping a day of counting we count an extra day*/) {
    fun passDay(){
        this.days -= 1
    }

    fun spawnNew() : Lanterfish? {
        if (this.days == 0){
            this.days = 7
            return Lanterfish()
        }
        return null
    }
}