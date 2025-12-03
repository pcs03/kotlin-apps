package nl.pcstet.core.model

import kotlinx.serialization.Serializable

@Serializable
data class UserPreferences(
    val apiUrl: String? = null,
    val rememberedEmail: String? = null,
    val accessToken: String? = null,
    val onboardingComplete: Boolean = false,
    val appTheme: AppTheme = AppTheme.SYSTEM,
)

enum class AppTheme {
    LIGHT,
    DARK,
    SYSTEM,
}
