plugins {
    alias(libs.plugins.sample.android.library)
    alias(libs.plugins.sample.compose.library)
    alias(libs.plugins.sample.buildtype.library)
    alias(libs.plugins.sample.koin.compose)
}

android {
    namespace = "nl.pcstet.core.ui"
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
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
}