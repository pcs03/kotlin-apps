package nl.pcstet.romeplaces.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class RomePlace(
    val id: Int,
    @StringRes val titleResourceId: Int,
    @StringRes val descriptionResourceId: Int,
    @DrawableRes val imageResourceId: Int,
    val category: RomePlaceCategory,
    val citySection: RomePlaceCitySection,
)
