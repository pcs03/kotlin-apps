package nl.pcstet.scratch

class Song(val title: String, val artist: String, val year: Int, val playCount: Long) {
    val isPopular: Boolean
        get() = playCount >= 1000

    fun description() {
        println("$title, performed by $artist, was released in $year")
    }
}

fun main() {
    val song = Song("Bohemian Rhapsody", "Queen", 1975, 1234981236498216)
    song.description()
    println(song.isPopular)
}