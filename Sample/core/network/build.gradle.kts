plugins {
    alias(libs.plugins.sample.android.library)
    alias(libs.plugins.sample.buildtype.library)
    alias(libs.plugins.sample.koin.android)
}

android {
    namespace = "nl.pcstet.core.network"
}

dependencies {
    implementation(projects.core.common)
    api(libs.bundles.ktor)
    api(libs.ktor.client.okhttp)

    testImplementation(libs.ktor.client.mock)
    testImplementation(libs.ktor.logging.logback.classic)
}