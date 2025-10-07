package nl.pcstet.romeplaces.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import nl.pcstet.romeplaces.R

enum class RomePlaceCategory(@StringRes val title: Int, @DrawableRes val icon: Int) {
    ROMAN_REPUBLIC(R.string.category_republic, R.drawable.category_republic_icon),
    ROMAN_EMPIRE(R.string.category_empire, R.drawable.category_empire_icon),
    BYZANTINE(R.string.category_byzantine, R.drawable.category_byzantine_icon),
    RENAISSANCE(R.string.category_renaissance, R.drawable.category_renaissance_icon),
    MODERN_ROME(R.string.category_modern, R.drawable.category_modern_icon)
}