plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id(BuildPlugins.kspId)
    id(BuildPlugins.daggerHiltPlugin)
}

android {
    namespace = Android.dataNameSpace
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

    buildFeatures {
        buildConfig = true
    }

}

dependencies {
    // 🎉Module
    implementation(project(":client"))
    implementation(project(":domain"))
    implementation(project(":core"))

    // 🛜 Networking
    implementation(Dependencies.retrofitCore)
    implementation(Dependencies.retrofitMoshiConverter)

    // 🙈 Adaptors
    ksp(Dependencies.moshiCodegen)
    implementation(Dependencies.moshiKotlin)
    implementation(Dependencies.moshi)
    implementation(Dependencies.moshiAdapters)

    // 🎉 Hilt
    ksp(Dependencies.hiltCompiler)
    implementation(Dependencies.hiltCore)
}