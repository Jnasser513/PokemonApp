plugins {
    alias(libs.plugins.pokedex.jvm.library)
}

dependencies {
    implementation(libs.junit)
    implementation(libs.coroutines.test)
    implementation(projects.core.domain)
}
