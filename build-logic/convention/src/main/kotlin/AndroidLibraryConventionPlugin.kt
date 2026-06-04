import com.android.build.api.dsl.LibraryExtension
import com.jnasser.convention.ExtensionType
import com.jnasser.convention.configureBuildTypes
import com.jnasser.convention.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

/**
 * Gradle convention plugin for Android library modules.
 * Applies common Android and Kotlin plugins and sets up default configuration.
 */
class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                // Apply Android and Kotlin plugins
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                // Apply shared Kotlin Android configuration
                configureKotlinAndroid(this)

                // Configure default build types for libraries
                configureBuildTypes(
                    commonExtension = this,
                    extensionType = ExtensionType.LIBRARY
                )

                defaultConfig {
                    // Set test runner and consumer ProGuard rules
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    consumerProguardFiles("consumer-rules.pro")
                }

                // Add test dependencies
                dependencies {
                    "testImplementation"(kotlin("test"))
                }
            }
        }
    }
}
