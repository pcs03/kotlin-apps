import com.android.build.api.dsl.ApplicationExtension
import nl.pcstet.sample.convention.configureAndroidCompose
import nl.pcstet.sample.convention.configureKotlinJvm
import nl.pcstet.sample.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class JvmLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "org.jetbrains.kotlin.jvm")

            configureKotlinJvm()

            dependencies {
                add("implementation", libs.findLibrary("koin-core").get())

                add("testImplementation", libs.findLibrary("kotlin.test").get())
                add("testImplementation", libs.findLibrary("kotlin.test.annotations.common").get())
                add("testImplementation", libs.findLibrary("assertk").get())
                add("testImplementation", libs.findLibrary("kotlinx-coroutines-test").get())
            }
        }
    }
}