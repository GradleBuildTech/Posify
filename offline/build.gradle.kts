plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id(BuildPlugins.kspId)
    id(BuildPlugins.daggerHiltPlugin)
}

android {
    namespace = Android.applicationId
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
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // 🧱 Core AndroidX & Lifecycle
    implementation(Dependencies.androidxCoreKtx)
    implementation(Dependencies.androidxAppCompat)

    // 📦 Jetpack Compose UI
    implementation(Dependencies.material)

    // 🧪 Instrumented Testing (UI test, Espresso, etc.)
    testImplementation(Dependencies.junit)
    androidTestImplementation(Dependencies.androidxJunit)
    androidTestImplementation(Dependencies.espressoCore)

    // 🎉 Hilt
    ksp(Dependencies.hiltCompiler)
    implementation(Dependencies.hiltCore)

    // ☢️Security
    implementation(Dependencies.securityCrypto)
}