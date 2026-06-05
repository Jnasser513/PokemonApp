plugins {
    alias(libs.plugins.pokedex.android.library)
}

android {
    namespace = "com.jnasser.core.data"
}

dependencies {
    implementation(libs.timber)
    implementation(libs.bundles.koin)
    implementation(libs.androidx.datastore.preference)
    implementation(libs.bundles.retrofit)

    implementation(projects.core.domain)
    implementation(projects.core.database)
    testImplementation(projects.core.testFixtures)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.turbine)
    testImplementation(libs.coroutines.test)
}
