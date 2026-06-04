plugins {
    alias(libs.plugins.pokedex.android.feature.ui)
}

android {
    namespace = "com.jnasser.pokemon.presentation"
}

dependencies {
    implementation(libs.google.maps.android.compose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.timber)
    implementation(libs.androidx.storage)
    implementation(libs.coil.compose)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.androidx.navigation.common.android)
    implementation(libs.koin.androidx.compose)

    // Modules
    implementation(projects.pokemon.domain)
    implementation(projects.core.domain)
}