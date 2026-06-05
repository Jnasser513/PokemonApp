plugins {
    alias(libs.plugins.pokedex.android.library)
    alias(libs.plugins.pokedex.android.room)
}

android {
    namespace = "com.jnasser.core.database"
}

dependencies {
    implementation(libs.bundles.koin)

    // Modules
    implementation(projects.core.domain)
}