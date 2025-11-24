plugins {
    alias(libs.plugins.sample.android.library)
    alias(libs.plugins.sample.compose.library)
    alias(libs.plugins.sample.buildtype.library)
    alias(libs.plugins.sample.koin.compose)
    alias(libs.plugins.sample.android.feature)
}

android {
    namespace = "nl.pcstet.feature.auth"
}

dependencies {
}