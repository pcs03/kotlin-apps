import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import nl.pcstet.sample.convention.configureAndroid
import nl.pcstet.sample.convention.configureBuildType
import nl.pcstet.sample.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class KoinAndroidConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {

            dependencies {
                val koinBom = libs.findLibrary("koin-bom").get()
                add("implementation", platform(koinBom))

                add("implementation", libs.findLibrary("koin.android").get())

                add("testImplementation", libs.findLibrary("koin-test-junit4").get())
                add("testImplementation", libs.findLibrary("koin-android-test").get())
            }
        }
    }
}
