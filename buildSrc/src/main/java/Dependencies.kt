import org.gradle.api.JavaVersion

object Versions {
    // Paging 3
    const val pagingRuntime = "3.1.1"
    const val pagingCommon = "3.1.1"

    // Firebase
    const val firebaseMessaging = "23.1.2"
    const val firebaseCrashlytics = "18.3.6"
    const val firebaseAnalytics = "21.2.1"

    // Compose
    const val pagingCompose = "1.0.0-alpha18"
    const val composeBom = "2022.12.00"
    const val activityCompose = "1.7.0"
    const val composeViewModel = "2.6.1"
    const val composeNavigation = "2.5.3"
    const val composeCoil = "2.3.0"

    // Room
    const val roomKtx = "2.5.1"
    const val roomCompiler = "2.5.1"
    const val roomRuntime = "2.5.1"

    // Koin
    const val koinCore = "3.3.3"
    const val androidKoin = "3.3.3"
    const val compatAndroidKoin = "3.3.3"
    const val composeKoin = "3.4.3"
    const val koinTest = "3.3.3"

    // Retrofit
    const val retrofit = "2.9.0"
    const val converterGson = "2.9.0"
    const val loggingOkHttp = "4.9.3"

    // Android Ktx
    const val ktx = "1.7.0"

    // Design
    const val appcompat = "1.6.1"
    const val material = "1.8.0"
    const val constraintLayout = "1.8.0"

    // Lifecycle
    const val lifecycleLivedata = "2.6.1"
    const val lifecycleViewModel = "2.6.1"

    // Tests
    const val junit = "4.13.2"
    const val extJunit = "1.1.5"
    const val espressoCore = "3.5.1"
}

object Config {
    const val applicationId = "com.example.java.android1.movie_search"
    const val minSdk = 21
    const val targetSdk = 33
    const val compileSdk = 33
    val javaVersion = JavaVersion.VERSION_1_8
}

object Releases {
    const val versionCode = 1
    const val versionName = "1.0"
}

object Paging {
    const val pagingRuntime = "androidx.paging:paging-runtime-ktx:${Versions.pagingRuntime}"
    const val pagingCommon = "androidx.paging:paging-common-ktx:${Versions.pagingCommon}"
}

object Firebase {
    const val firebaseMessaging =
        "com.google.firebase:firebase-messaging-ktx:${Versions.firebaseMessaging}"
    const val firebaseCrashlytics =
        "com.google.firebase:firebase-crashlytics-ktx:${Versions.firebaseCrashlytics}"
    const val firebaseAnalytics =
        "com.google.firebase:firebase-analytics-ktx:${Versions.firebaseAnalytics}"
}

object Compose {
    const val composeBom = "androidx.compose:compose-bom:${Versions.composeBom}"
    const val composePaging = "androidx.paging:paging-compose:${Versions.pagingCompose}"
    const val composeMaterial3 = "androidx.compose.material3:material3"
    const val composeUi = "androidx.compose.ui:ui"
    const val composeUiTooling = "androidx.compose.ui:ui-tooling-preview"
    const val composeActivity = "androidx.activity:activity-compose:${Versions.activityCompose}"
    const val composeViewModel =
        "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.composeViewModel}"
    const val composeLiveData = "androidx.compose.runtime:runtime-livedata"
    const val composeNavigation =
        "androidx.navigation:navigation-compose:${Versions.composeNavigation}"
    const val composeCoil = "io.coil-kt:coil-compose:${Versions.composeCoil}"
}

object Room {
    const val roomRuntime = "androidx.room:room-runtime:${Versions.roomRuntime}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.roomCompiler}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.roomKtx}"
}

object Koin {
    const val koinCore = "io.insert-koin:koin-core:${Versions.koinCore}"
    const val koinAndroid = "io.insert-koin:koin-android:${Versions.androidKoin}"
    const val koinAndroidCompat = "io.insert-koin:koin-android-compat:${Versions.compatAndroidKoin}"
    const val koinTest = "io.insert-koin:koin-test:${Versions.koinTest}"
    const val composeKoin = "io.insert-koin:koin-androidx-compose:${Versions.composeKoin}"
}

object Lifecycle {
    const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycleLivedata}"
    const val viewModel =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleViewModel}"
}

object Retrofit {
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val converterGson = "com.squareup.retrofit2:converter-gson:${Versions.converterGson}"
    const val loggingOkHttp = "com.squareup.okhttp3:logging-interceptor:${Versions.loggingOkHttp}"
}

object Android {
    const val androidCore = "androidx.core:core-ktx:${Versions.ktx}"
}

object Design {
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constrainLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
}

object Tests {
    const val junit = "junit:junit:${Versions.junit}"
    const val extJunit = "androidx.test.ext:junit:${Versions.extJunit}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
}