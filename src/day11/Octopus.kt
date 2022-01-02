package day11

class Octopus(var energy: Int) {
    var flashedThisStep: Boolean = false

    fun increaseEnergy() {
        energy++
    }

    fun maybeFlash() : Boolean {
        if (energy > 9) {
            flashedThisStep = true
        }
        return flashedThisStep
    }

    fun reset() {
        if (flashedThisStep) {
            energy = 0
            flashedThisStep = false
        }
    }
}