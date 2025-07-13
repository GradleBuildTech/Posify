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

    val kspId ="com.google.devtools.ksp"
    val daggerHiltPlugin = "com.google.dagger.hilt.android"
}

// ✅ Android app configuration constants (used in app module's build.gradle.kts)
object Android {
    const val applicationId = "com.example.posNativeApp"
    const val dataNameSpace = "com.example.data"
    const val managerNameSpace = "com.example.manager"
    const val coreNameSpace = "com.example.core"
    const val clientNameSpace = "com.example.client"
    const val componentNameSpace = "com.example.component"
    const val domainNameSpace = "com.example.domain"
    const val offlineNameSpace = "com.example.offline"
    const val navigationNameSpace = "com.example.navigation"
    const val authNameSpace = "com.example.auth"

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


    //Networking
    val retrofitCore = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitMoshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"

    // Navigation
    val composeNavigation = "androidx.navigation:navigation-compose:${Versions.navigation}"

    //Adapter
    val moshi = "com.squareup.moshi:moshi:${Versions.moshi}"
    val moshiKotlin = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
    val moshiAdapters = "com.squareup.moshi:moshi-adapters:${Versions.moshi}"
    val moshiCodegen = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"

    // Hilt
    val hiltCore = "com.google.dagger:hilt-android:${Versions.hilt}"
    val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"

    // OkHttp
    val okhttpCore = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    val okhttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"

    // Security
    val securityCrypto = "androidx.security:security-crypto:${Versions.security}"

    // Room
    val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    val roomKtx = "androidx.room:room-ktx:${Versions.room}"
}
