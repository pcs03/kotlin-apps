package nl.pcstet.amphibians.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Amphibian (
    val name: String,
    val type: String,
    val description: String,
    @SerialName("img_src")
    val imageSource: String,
)