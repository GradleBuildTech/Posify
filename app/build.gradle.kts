plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

val configProperties = BuildConfig.projectConfigurations(project)

android {
    namespace = Android.applicationId
    compileSdk = Android.compileSdk

    defaultConfig {
        applicationId = Android.applicationId
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk
        versionCode = Android.versionCode
        versionName = Android.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        configProperties.forEach { key, value ->
            buildConfigField("String", key.toString(), "\"${value}\"")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            isMinifyEnabled = false
            isDebuggable = true
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
        compose = true
        buildConfig = true
    }

    flavorDimensions += "env"
    productFlavors {
        repeat(Flavor.LIST_FLAVOR.size) {
            create(Flavor.LIST_FLAVOR[it]) {
                dimension = "env"
                applicationIdSuffix = ".${Flavor.LIST_FLAVOR[it]}"
                versionNameSuffix = "-${Flavor.LIST_FLAVOR[it]}"
                buildConfigField("String", "BASE_URL", "\"${configProperties["BASE_URL"]}\"")
            }
        }
    }
}

dependencies {
    //🎉 Module
    implementation(project(":client"))

    // 🧱 Core AndroidX & Lifecycle
    implementation(Dependencies.androidxCoreKtx)
    implementation(Dependencies.lifecycleRuntimeKtx)

    // 🧑‍🎨 Jetpack Compose UI
    implementation(Dependencies.activityCompose)
    implementation(platform(Dependencies.composeBom)) // Compose BOM để đồng bộ version

    implementation(Dependencies.composeUi)
    implementation(Dependencies.composeUiGraphics)
    implementation(Dependencies.composeUiToolingPreview)
    implementation(Dependencies.composeMaterial3)

    // 🧪 Unit Testing
    testImplementation(Dependencies.junit)

    // 🧪 Instrumented Testing (UI test, Espresso, etc.)
    androidTestImplementation(Dependencies.androidxJunit)
    androidTestImplementation(Dependencies.espressoCore)
    androidTestImplementation(platform(Dependencies.composeBom))
    androidTestImplementation(Dependencies.composeUiTestJunit4)

    // 🐞 Debug-only tools (UI Preview & Test Manifest)
    debugImplementation(Dependencies.composeUiTooling)
    debugImplementation(Dependencies.composeUiTestManifest)

    // 🎉 Hilt
//    ksp()
}