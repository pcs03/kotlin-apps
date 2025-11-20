import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.TestExtension
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

class AndroidTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.android.test")
            apply(plugin = "org.jetbrains.kotlin.android")

            extensions.configure<TestExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 36
            }

            dependencies {
                add("androidTestImplementation", libs.findLibrary("kotlin.test").get())
                add("androidTestImplementation", libs.findLibrary("compose.ui.test.junit4.android").get())
                add("androidTestImplementation", libs.findLibrary("androidx.junit").get())
                add("androidTestImplementation", libs.findLibrary("androidx.espresso.core").get())
                add("androidTestImplementation", libs.findLibrary("androidx.core.ktx").get())
                add("debugImplementation", libs.findLibrary("compose.ui.test.manifest").get())
            }
        }
    }
}