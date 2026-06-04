plugins {
    alias(libs.plugins.pokedex.jvm.library)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)

    // Modules
    implementation(projects.core.domain)
}
