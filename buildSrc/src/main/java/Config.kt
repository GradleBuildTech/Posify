// ✅ Plugin definitions (used in root build.gradle.kts or module plugins blocks)
object BuildPlugins {
    // Android Gradle Plugin
    val androidPlugin = "com.android.tools.build:gradle:${Versions.agp}"

    // Kotlin Gradle Plugin
    val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"

    // (Deprecated) Kotlin Android Extensions (if still used, e.g., for parcelize)
    val kotlinAndroidExtensions = "org.jetbrains.kotlin:kotlin-android-extensions:${Versions.kotlin}"

    // Reuse plugin for library modules
    val androidLibrary = "com.android.tools.build:gradle:${Versions.agp}"
}

// ✅ Android app configuration constants (used in app module's build.gradle.kts)
object Android {
    const val applicationId = "com.example.posNativeApp"
    const val compileSdk = 35
    const val minSdk = 24
    const val targetSdk = 35
    const val versionCode = 1
    const val versionName = "1.0"
}
// ✅ Project-wide dependency definitions (used in all modules)
object Dependencies {
    // AndroidX Core & AppCompat
    val androidxCoreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    val androidxAppCompat = "androidx.appcompat:appcompat:${Versions.appcompat}"

    // Material Design Components
    val material = "com.google.android.material:material:${Versions.material}"

    // Lifecycle
    val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntimeKtx}"

    // Compose & UI
    val activityCompose = "androidx.activity:activity-compose:${Versions.activityCompose}"
    val composeBom = "androidx.compose:compose-bom:${Versions.composeBom}"

    val composeUi = "androidx.compose.ui:ui"
    val composeUiGraphics = "androidx.compose.ui:ui-graphics"
    val composeUiToolingPreview = "androidx.compose.ui:ui-tooling-preview"
    val composeUiTooling = "androidx.compose.ui:ui-tooling"
    val composeUiTestManifest = "androidx.compose.ui:ui-test-manifest"
    val composeUiTestJunit4 = "androidx.compose.ui:ui-test-junit4"
    val composeMaterial3 = "androidx.compose.material3:material3"

    // Testing
    val junit = "junit:junit:${Versions.junit}"
    val androidxJunit = "androidx.test.ext:junit:${Versions.junitVersion}"
    val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
}
