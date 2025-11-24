package nl.pcstet.sample.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure

private val javaVersion = JavaVersion.VERSION_11

// For Android modules, e.g. DataStore
internal fun Project.configureJavaAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    commonExtension.compileOptions {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }
}

// For pure Kotlin modules, e.g. models
internal fun Project.configureJavaJvm() {
    extensions.configure<JavaPluginExtension> {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }
}
