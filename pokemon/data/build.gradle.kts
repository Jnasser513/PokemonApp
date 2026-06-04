plugins {
    alias(libs.plugins.pokedex.android.library)
    alias(libs.plugins.pokedex.jvm.retrofit)
}

android {
    namespace = "com.jnasser.pokemon.data"
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.google.android.gms.play.services.location)
    implementation(libs.androidx.work)
    implementation(libs.koin.android.workmanager)
    implementation(libs.kotlinx.serialization.json)

    // Modules
    implementation(projects.core.domain)
    implementation(projects.pokemon.domain)
    implementation(projects.core.data)
}