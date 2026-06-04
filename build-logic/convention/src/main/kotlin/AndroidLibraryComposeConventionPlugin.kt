import com.android.build.api.dsl.LibraryExtension
import com.jnasser.convention.ExtensionType
import com.jnasser.convention.configureAndroidCompose
import com.jnasser.convention.configureBuildTypes
import com.jnasser.convention.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.kotlin

/**
 * Gradle convention plugin for Android library modules using Jetpack Compose.
 * Applies shared Compose configuration for library modules.
 */
class AndroidLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                // Apply base Android library convention and Compose
                apply("pokemonapp.android.library")
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            // Configure Compose settings
            val extension = extensions.getByType<LibraryExtension>()
            configureAndroidCompose(extension)
        }
    }
}
