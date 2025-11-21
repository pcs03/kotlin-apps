package nl.pcstet.sample.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal fun Project.configureKotlin()  {
    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    dependencies {
        add("implementation", libs.findLibrary("koin-core").get())

        add("testImplementation", libs.findLibrary("kotlin.test").get())
        add("testImplementation", libs.findLibrary("kotlin.test.annotations.common").get())
        add("testImplementation", libs.findLibrary("assertk").get())
        add("testImplementation", libs.findLibrary("kotlinx.coroutines.test").get())

        add("testImplementation", libs.findLibrary("koin-test").get())
        add("testImplementation", libs.findLibrary("koin-test").get())
        add("testImplementation", libs.findLibrary("koin-test-junit4").get())
    }
}