import com.android.build.api.dsl.ApplicationExtension
import nl.pcstet.sample.convention.configureCompose
import nl.pcstet.sample.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class ComposeApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "org.jetbrains.kotlin.plugin.compose")

            val extension = extensions.getByType<ApplicationExtension>()
            configureCompose(extension)

            dependencies {
                add("implementation", libs.findLibrary("androidx.activity.compose").get())
            }
        }
    }
}