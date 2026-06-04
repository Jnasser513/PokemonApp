plugins {
    `kotlin-dsl`
}

group = "com.jnasser.convention"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplicationCompose") {
            id = "pokemonapp.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidApplication") {
            id = "pokemonapp.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidFeatureUi") {
            id = "pokemonapp.android.feature.ui"
            implementationClass = "AndroidFeatureUIConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "pokemonapp.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "pokemonapp.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidRoom") {
            id = "pokemonapp.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
        register("jvmRetrofit") {
            id = "pokemonapp.jvm.retrofit"
            implementationClass = "JVMRetrofitConventionPlugin"
        }
        register("jvmLibrary") {
            id = "pokemonapp.jvm.library"
            implementationClass = "JVMLibraryConventionPlugin"
        }
    }
}
