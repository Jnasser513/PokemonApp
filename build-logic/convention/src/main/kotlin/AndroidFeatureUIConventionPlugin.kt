import com.jnasser.convention.addUiLayerDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Gradle convention plugin for feature UI modules.
 * Applies Compose library convention and adds UI layer dependencies.
 */
class AndroidFeatureUIConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                // Apply compose library convention
                apply("pokemonapp.android.library.compose")
            }

            // Add common UI layer dependencies
            dependencies {
                addUiLayerDependencies(target)
            }
        }
    }
}
