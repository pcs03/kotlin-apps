import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.sample.android.application.compose)
    alias(libs.plugins.sample.android.application)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.devtools.ksp)
}

android {
    namespace = "nl.pcstet.sample"
    defaultConfig {
        applicationId = "nl.pcstet.sample"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}
dependencies {
    implementation(projects.core.common)


    // Compose
    implementation(libs.bundles.compose.main)
//
//    /// Koin
//    implementation(libs.koin.core)
//    implementation(libs.koin.android)
//    implementation(libs.koin.compose)
//    implementation(libs.koin.compose.viewmodel)
//    implementation(libs.koin.compose.viewmodel.navigation)
//
//    // Datastore
//    implementation(libs.androidx.datastore)
//    implementation(libs.androidx.datastore.preferences)
//
//    // Ktor
//    implementation(libs.bundles.ktor)
//    implementation(libs.ktor.client.okhttp)
//
//    // Test
//    testImplementation(libs.junit)
//
//    // Android Test
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
//    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
//
//    // Debug
//    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
