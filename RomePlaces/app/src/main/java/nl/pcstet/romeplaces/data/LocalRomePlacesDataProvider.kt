package nl.pcstet.romeplaces.data

import nl.pcstet.romeplaces.R
import nl.pcstet.romeplaces.model.RomePlace
import nl.pcstet.romeplaces.model.RomePlaceCategory
import nl.pcstet.romeplaces.model.RomePlaceCitySection

object LocalRomePlacesDataProvider {
    val defaultCategory = RomePlaceCategory.ROMAN_REPUBLIC
    val defaultRomePlace =
        getRomePlacesData().filter { romePlace -> romePlace.category == defaultCategory }[0]

    fun getRomePlacesData(): List<RomePlace> {
        return listOf(
            RomePlace(
                id = 1,
                titleResourceId = R.string.colosseum,
                descriptionResourceId = R.string.place_description,
                imageResourceId = R.drawable.colosseum,
                category = RomePlaceCategory.ROMAN_EMPIRE,
                citySection = RomePlaceCitySection.CENTRE,
            ),
            RomePlace(
                id = 2,
                titleResourceId = R.string.spanish_steps,
                descriptionResourceId = R.string.place_description,
                imageResourceId = R.drawable.spanish_steps,
                category = RomePlaceCategory.RENAISSANCE,
                citySection = RomePlaceCitySection.CENTRE,
            ),
            RomePlace(
                id = 3,
                titleResourceId = R.string.pantheon,
                descriptionResourceId = R.string.place_description,
                imageResourceId = R.drawable.pantheon,
                category = RomePlaceCategory.ROMAN_EMPIRE,
                citySection = RomePlaceCitySection.CAMPUS_MARTIUS,
            ),
            RomePlace(
                id = 4,
                titleResourceId = R.string.arch_of_constantine,
                descriptionResourceId = R.string.place_description,
                imageResourceId = R.drawable.arch_of_constantine,
                category = RomePlaceCategory.BYZANTINE,
                citySection = RomePlaceCitySection.COLOSSEUM_AREA,
            ),
            RomePlace(
                id = 5,
                titleResourceId = R.string.curia_julia,
                descriptionResourceId = R.string.place_description,
                imageResourceId = R.drawable.curia_julia,
                category = RomePlaceCategory.ROMAN_REPUBLIC,
                citySection = RomePlaceCitySection.FORUM_ROMANUM,
            ),
            RomePlace(
                id = 6,
                titleResourceId = R.string.vittorio_emanuele_ii,
                descriptionResourceId = R.string.place_description,
                imageResourceId = R.drawable.vittorio_emanuele_ii,
                category = RomePlaceCategory.MODERN_ROME,
                citySection = RomePlaceCitySection.CENTRE,
            ),
            RomePlace(
                id = 7,
                titleResourceId = R.string.peters_basilica,
                descriptionResourceId = R.string.place_description,
                imageResourceId = R.drawable.peters_basilica,
                category = RomePlaceCategory.RENAISSANCE,
                citySection = RomePlaceCitySection.VATICAN,
            ),
        )
    }
}