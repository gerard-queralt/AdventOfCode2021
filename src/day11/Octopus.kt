package day11

class Octopus(var energy: Int) {
    var flashedThisStep: Boolean = false

    fun increaseEnergy() {
        energy++
    }

    fun maybeFlash() {
        if (energy > 9) {
            flashedThisStep = true
        }
    }

    fun reset() {
        if (flashedThisStep) {
            energy = 0
            flashedThisStep = false
        }
    }
}