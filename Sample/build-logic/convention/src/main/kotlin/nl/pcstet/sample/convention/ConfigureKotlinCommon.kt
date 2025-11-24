package nl.pcstet.sample.convention

import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType

internal fun Project.configureKotlinCommon() {
    apply(plugin = "org.jetbrains.kotlin.plugin.serialization")
    apply(plugin = "com.google.devtools.ksp")

    dependencies {
        add("implementation", libs.findLibrary("kotlinx-serialization-json").get())

        add("testImplementation", libs.findLibrary("kotlin.test").get())
        add("testImplementation", libs.findLibrary("kotlin.test.annotations.common").get())
        add("testImplementation", libs.findLibrary("kotlinx.coroutines.test").get())
        add("testImplementation", libs.findLibrary("kotest.assertions").get())
        add("testImplementation", libs.findLibrary("kotest.property").get())
//
    }
}