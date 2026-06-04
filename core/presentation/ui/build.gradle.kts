plugins {
    alias(libs.plugins.pokedex.android.library.compose)
}

android {
    namespace = "com.jnasser.core.presentation.ui"
}

dependencies {
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Modules
    implementation(projects.core.domain)
    implementation(projects.core.presentation.designsystem)
}