package nl.pcstet.scratch

open class Phone(var isScreenLightOn: Boolean = false){
    open fun switchOn() {
        isScreenLightOn = true
    }

    fun switchOff() {
        isScreenLightOn = false
    }

    fun checkPhoneScreenLight() {
        val phoneScreenLight = if (isScreenLightOn) "on" else "off"
        println("The phone screen's light is $phoneScreenLight.")
    }
}

class FoldablePhone(var isFolded: Boolean = true) : Phone(isScreenLightOn = !isFolded) {
    fun foldOpen() {
        isFolded = false
    }

    fun foldClosed() {
        isFolded = true
        isScreenLightOn = false
    }

    override fun switchOn() {
        if (!isFolded) {
            isScreenLightOn = true
        }
    }
}

fun main() {
    val phone: FoldablePhone = FoldablePhone()

    phone.checkPhoneScreenLight()
    phone.switchOn()
    phone.checkPhoneScreenLight()
    phone.foldOpen()
    phone.switchOn()
    phone.checkPhoneScreenLight()
    phone.foldClosed()
    phone.checkPhoneScreenLight()


}