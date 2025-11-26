plugins {
    alias(libs.plugins.android.library)
    kotlin("android")
    alias(libs.plugins.sergiobelda.gradle.common.library.android)
    alias(libs.plugins.sergiobelda.gradle.dependencyGraphGenerator)
    alias(libs.plugins.sergiobelda.gradle.lint)
}

kotlin {
    jvmToolchain(17)
}

android {
    namespace = "dev.sergiobelda.todometer.common.android"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()
}

dependencies {
    implementation(libs.androidx.coreKtx)
    implementation(libs.androidx.appcompat)
}
