import androidx.room.gradle.RoomExtension
import com.jnasser.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

/**
 * Gradle convention plugin for Android modules using Room.
 * Applies Room and KSP plugins, configures schema location, and adds Room dependencies.
 */
class AndroidRoomConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                // Apply Room Gradle plugin and Kotlin Symbol Processing (KSP)
                apply("androidx.room")
                apply("com.google.devtools.ksp")
            }

            // Configure Room schema output directory
            extensions.configure<RoomExtension> {
                schemaDirectory("$projectDir/schemas")
            }

            // Add Room dependencies from version catalog
            dependencies {
                "implementation"(libs.findLibrary("room.runtime").get())
                "implementation"(libs.findLibrary("room.ktx").get())
                "ksp"(libs.findLibrary("room.compiler").get())
            }
        }
    }
}
