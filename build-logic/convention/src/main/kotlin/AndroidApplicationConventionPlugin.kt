import com.android.build.api.dsl.ApplicationExtension
import com.jnasser.convention.ExtensionType
import com.jnasser.convention.configureBuildTypes
import com.jnasser.convention.configureKotlinAndroid
import com.jnasser.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * Gradle convention plugin for Android application modules.
 * Applies common Android and Kotlin plugins and sets up default configuration.
 */
class AndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                // Apply Android and Kotlin plugins
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<ApplicationExtension> {
                defaultConfig {
                    // Set up core app configuration from version catalog
                    applicationId = libs.findVersion("projectApplicationId").get().toString()
                    targetSdk = libs.findVersion("projectTargetSdkVersion").get().toString().toInt()
                    versionCode = libs.findVersion("projectVersionCode").get().toString().toInt()
                    versionName = libs.findVersion("projectVersionName").get().toString()
                }

                // Apply shared Kotlin Android configuration
                configureKotlinAndroid(this)

                configureBuildTypes(
                    commonExtension = this,
                    extensionType = ExtensionType.APPLICATION
                )
            }
        }
    }
}
