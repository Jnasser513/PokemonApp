import com.android.build.api.dsl.ApplicationExtension
import com.jnasser.convention.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

/**
 * Gradle convention plugin to configure Jetpack Compose support
 * for Android application modules.
 */
class AndroidApplicationComposeConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                // Apply base Android application convention and Compose
                apply("pokemonapp.android.application")
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            // Configure Compose settings
            val extension = extensions.getByType<ApplicationExtension>()
            configureAndroidCompose(extension)
        }
    }
}
