package nl.pcstet.amphibians.data

import nl.pcstet.amphibians.model.Amphibian

object LocalAmphibiansDataSource {
    fun getAmphibians(): List<Amphibian> {
        return listOf(
            Amphibian(
                name = "Great Basin Spadefoot",
                type = "Toad",
                description = "This toad spends most of its life underground due to the arid desert conditions in which it lives. Spadefoot toads earn the name because of their hind legs which are wedged to aid in digging. They are typically grey, green, or brown with dark spots.",
                imageSource = "https://developer.android.com/codelabs/basic-android-kotlin-compose-amphibians-app/img/great-basin-spadefoot.png"
            )
        )
    }
}