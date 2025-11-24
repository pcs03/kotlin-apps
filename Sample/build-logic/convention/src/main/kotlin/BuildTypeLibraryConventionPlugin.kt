import com.android.build.api.dsl.LibraryExtension
import nl.pcstet.sample.convention.configureBuildType
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class BuildTypeLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val extension = extensions.getByType<LibraryExtension>()
            configureBuildType(extension)
        }
    }
}