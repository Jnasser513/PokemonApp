plugins {
    alias(libs.plugins.pokedex.android.library.compose)
}

android {
    namespace = "com.jnasser.core.presentation.designsystem"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.constraintlayout.compose)
    api(libs.androidx.material3)
    implementation(libs.coil.compose)

    // Modules
    implementation(projects.core.domain)
}