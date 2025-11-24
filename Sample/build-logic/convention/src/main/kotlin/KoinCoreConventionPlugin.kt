import nl.pcstet.sample.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class KoinCoreConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                val koinBom = libs.findLibrary("koin-bom").get()
                add("implementation", platform(koinBom))

                add("implementation", libs.findLibrary("koin-core").get())

                add("testImplementation", libs.findLibrary("koin-test-junit4").get())
                add("testImplementation", libs.findLibrary("koin-test").get())
            }
        }
    }
}
