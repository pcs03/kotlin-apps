package nl.pcstet.scratch

fun main() {
    val amanda = Person("Amanda", 33, "play tennis", null)
    val atiqah = Person("Atiqah", 28, "climb", amanda)

    amanda.showProfile()
    atiqah.showProfile()
}


class Person(val name: String, val age: Int, val hobby: String?, val referrer: Person?) {
    fun showProfile() {
        val referrerDescription =  if (referrer != null) "Has a referrer name ${referrer.name}, who likes to ${referrer.hobby}" else "Doesn't have a referrer"

        println(name)
        println(age)
        println("Likes to $hobby. $referrerDescription")
    }
}