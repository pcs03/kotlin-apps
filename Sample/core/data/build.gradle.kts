plugins {
    alias(libs.plugins.sample.android.library)
    alias(libs.plugins.sample.buildtype.library)
    alias(libs.plugins.sample.koin.android)
}

android {
    namespace = "nl.pcstet.core.data"
}

dependencies {
    implementation(libs.androidx.appcompat)
}