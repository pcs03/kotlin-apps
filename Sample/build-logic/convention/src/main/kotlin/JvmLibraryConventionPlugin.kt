import nl.pcstet.sample.convention.configureJavaJvm
import nl.pcstet.sample.convention.configureKotlin
import nl.pcstet.sample.convention.configureKotlinCommon
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

class JvmLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "org.jetbrains.kotlin.jvm")

            configureJavaJvm()
            configureKotlin<KotlinJvmProjectExtension>()
            configureKotlinCommon()
        }
    }
}