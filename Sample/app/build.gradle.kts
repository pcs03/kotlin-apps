plugins {
    alias(libs.plugins.sample.android.application)
    alias(libs.plugins.sample.compose.application)
    alias(libs.plugins.sample.buildtype.application)
    alias(libs.plugins.sample.koin.android)
}

android {
    namespace = "nl.pcstet.sample"
    defaultConfig {
        applicationId = "nl.pcstet.sample"
        versionCode = 1
        versionName = "1.0"
    }
}
dependencies {
    implementation(projects.core.common)


    // Compose
//    implementation(libs.bundles.compose.main)
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
