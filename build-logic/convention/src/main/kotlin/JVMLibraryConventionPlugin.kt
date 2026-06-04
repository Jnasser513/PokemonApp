import com.jnasser.convention.configureKotlinJvm
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Gradle convention plugin for Kotlin JVM libraries.
 * Applies the Kotlin JVM plugin and configures Java 11 compatibility.
 */
class JVMLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            // Apply Kotlin JVM plugin
            pluginManager.apply("org.jetbrains.kotlin.jvm")

            // Configure Kotlin and Java compatibility settings
            configureKotlinJvm()
        }
    }
}
