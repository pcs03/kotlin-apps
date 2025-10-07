package nl.pcstet.romeplaces.model

import androidx.annotation.StringRes
import nl.pcstet.romeplaces.R

enum class RomePlaceCitySection(@StringRes val title: Int) {
    COLOSSEUM_AREA(R.string.citysection_colosseum),
    FORUM_ROMANUM(R.string.citysection_forum_romanum),
    FORUM_IMPERIAL(R.string.citysection_forum_imperial),
    CAMPUS_MARTIUS(R.string.citysection_campus_martius),
    CENTRE(R.string.citysection_centre),
    VATICAN(R.string.citysection_vatican),
    AVENTINE(R.string.citysection_aventine),
}