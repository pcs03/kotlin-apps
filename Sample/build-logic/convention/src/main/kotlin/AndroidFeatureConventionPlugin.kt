import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import nl.pcstet.sample.convention.configureAndroidCompose
import nl.pcstet.sample.convention.configureKotlinAndroid
import nl.pcstet.sample.convention.configureKotlinJvm
import nl.pcstet.sample.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.android.library")

            dependencies {
                // TODO: Project dependencies for core

                add("implementation", libs.findLibrary("koin.compose.viewmodel").get())
                add("implementation", libs.findLibrary("koin.compose.viewmodel.navigation").get())

                add("implementation", libs.findLibrary("androidx.lifecycle.runtime.ktx").get())
                add("implementation", libs.findLibrary("androidx.lifecycle.viewmodel.compose").get())
                add("implementation", libs.findLibrary("androidx.navigation.compose").get())
            }
        }
    }
}