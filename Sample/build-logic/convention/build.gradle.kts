import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "nl.pcstet.sample.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_11
    }
}

dependencies {
    compileOnly(libs.android.gradle.api.plugin)
    compileOnly(libs.compose.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.android.tools.common)
    implementation(libs.ksp.gradle.plugin)
    implementation(libs.kotlin.serialization.gradle.plugin)
}

gradlePlugin {
    plugins {
        // JVM
        register("jvmLibrary") {
            id = libs.plugins.sample.jvm.library.get().pluginId
            implementationClass = "JvmLibraryConventionPlugin"
        }

        // Android
        register("androidApplication") {
            id = libs.plugins.sample.android.application.get().pluginId
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = libs.plugins.sample.android.library.get().pluginId
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        // Compose
        register("composeApplication") {
            id = libs.plugins.sample.compose.application.get().pluginId
            implementationClass = "ComposeApplicationConventionPlugin"
        }
        register("composeLibrary") {
            id = libs.plugins.sample.compose.library.get().pluginId
            implementationClass = "ComposeLibraryConventionPlugin"
        }

        // Koin
        register("koinCore") {
            id = libs.plugins.sample.koin.core.get().pluginId
            implementationClass = "KoinCoreConventionPlugin"
        }
        register("koinAndroid") {
            id = libs.plugins.sample.koin.android.get().pluginId
            implementationClass = "KoinAndroidConventionPlugin"
        }
        register("koinCompose") {
            id = libs.plugins.sample.koin.compose.get().pluginId
            implementationClass = "KoinComposeConventionPlugin"
        }

        // Build types
        register("buildTypeApplication") {
            id = libs.plugins.sample.buildtype.application.get().pluginId
            implementationClass = "BuildTypeApplicationConventionPlugin"
        }
        register("buildTypeLibrary") {
            id = libs.plugins.sample.buildtype.library.get().pluginId
            implementationClass = "BuildTypeLibraryConventionPlugin"
        }

        // Feature
        register("androidFeature") {
            id = libs.plugins.sample.android.feature.get().pluginId
            implementationClass = "AndroidFeatureConventionPlugin"
        }
    }
}