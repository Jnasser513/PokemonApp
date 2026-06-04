plugins {
    alias(libs.plugins.pokedex.android.library)
    alias(libs.plugins.pokedex.jvm.retrofit)
}

android {
    namespace = "com.jnasser.pokemon.network"
}

dependencies {
    implementation(libs.bundles.koin)

    // Modules
    implementation(projects.core.domain)
    implementation(projects.core.data)
}