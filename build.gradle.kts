plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.jetbrainsCompose) apply false
    alias(libs.plugins.compose.compiler) apply false // Aquí es compose.compiler
    alias(libs.plugins.sqldelight) apply false
}

allprojects {
    configurations.all {
        resolutionStrategy {
            force("org.jetbrains.androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")
            force("org.jetbrains.androidx.lifecycle:lifecycle-runtime-compose:2.8.4")
            force("org.jetbrains.androidx.savedstate:savedstate:1.2.1")
        }
    }
}