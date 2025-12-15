import com.android.build.api.dsl.androidLibrary

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlinMultiplatform)
    alias(libs.plugins.sergiobelda.gradle.common.library.android)
    alias(libs.plugins.sergiobelda.gradle.dependencyGraphGenerator)
    alias(libs.plugins.sergiobelda.gradle.lint)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.composeCompiler)

}


kotlin {
    androidTarget()
    jvm("desktop")
    iosX64()
    iosArm64()
    iosSimulatorArm64()


    sourceSets {

        commonMain.dependencies {
            implementation("org.jetbrains.compose.components:components-resources:1.10.0-rc01")
            implementation(project(":common:designsystem-resources"))
            implementation(libs.sergiobelda.fonament.diKoin)
            implementation(libs.androidx.datastore.preferences.core)
            implementation(libs.jetbrains.kotlin.coroutines.core)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }
        androidMain.dependencies {

            implementation(libs.androidx.datastore.preferences)
        }
    }
}



android {
    namespace = "dev.sergiobelda.todometer.common.reminder"
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    buildFeatures {
        compose = true
    }
}
