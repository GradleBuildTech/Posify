plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id(BuildPlugins.kspId)
    id(BuildPlugins.daggerHiltPlugin)

}

android {
    namespace = Android.onboardingNameSpace
    compileSdk = Android.compileSdk

    defaultConfig {
        minSdk = Android.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    // 🎉Module
    implementation(project(":components"))
    implementation(project(":core"))
    implementation(project(":manager"))
    implementation(project(":client"))
    implementation(project(":offline"))

    // 🧱 Core AndroidX & Lifecycle
    implementation(Dependencies.androidxCoreKtx)
    implementation(Dependencies.androidxAppCompat)
    implementation(Dependencies.material)

    // 🏫 Compose
    implementation(Dependencies.lifecycleRuntimeKtx)
    implementation(Dependencies.activityCompose)
    implementation(platform(Dependencies.composeBom))
    implementation(Dependencies.composeUi)
    implementation(Dependencies.composeMaterial3)

    // 🎉 Hilt
    ksp(Dependencies.hiltCompiler)
    implementation(Dependencies.hiltCore)
    implementation(Dependencies.hiltNavigationCompose)
}