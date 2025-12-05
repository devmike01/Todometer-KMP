plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlinMultiplatform)
    alias(libs.plugins.jetbrains.composeCompiler)
    alias(libs.plugins.sergiobelda.gradle.common.library.android)
    alias(libs.plugins.sergiobelda.gradle.common.ui)
    alias(libs.plugins.sergiobelda.gradle.dependencyGraphGenerator)
    alias(libs.plugins.sergiobelda.gradle.lint)
}

kotlin{
    androidTarget()
    jvm("desktop")
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets{
        commonMain.dependencies {
            implementation(project(":common:reminder"))
            implementation(projects.appCommon.ui)
            implementation(projects.appCommon.uiTooling)
        }
        all {
            languageSettings.optIn("kotlin.Required")
        }
    }
}

android {
    namespace = "dev.sergiobelda.todometer.common.reminder"

}

//dependencies {
//    implementation(libs.androidx.coreKtx)
//    implementation(libs.androidx.appcompat)
//    implementation(libs.material)
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.test.junit)
//    androidTestImplementation(libs.androidx.test.espresso.core)
//}