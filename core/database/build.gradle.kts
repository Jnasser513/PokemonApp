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
    testImplementation(projects.core.testFixtures)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.turbine)
    testImplementation(libs.coroutines.test)
}
