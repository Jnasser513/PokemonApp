import com.jnasser.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Gradle convention plugin for Kotlin JVM projects using Retrofit.
 * Applies Kotlin serialization plugin and adds Retrofit dependencies.
 */
class JVMRetrofitConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            // Apply Kotlin serialization plugin
            pluginManager.apply("org.jetbrains.kotlin.plugin.serialization")

            // Add Retrofit dependency bundle
            dependencies {
                "implementation"(libs.findBundle("retrofit").get())
            }
        }
    }
}
