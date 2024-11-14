plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.gms)
    alias(libs.plugins.compose.compiler)
    id("kotlin-parcelize")
}

android {
    namespace = "com.volokhinaleksey.movie_club"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.volokhinaleksey.movie_club"
        versionCode = 1
        versionName = "1.0"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        vectorDrawables.useSupportLibrary = true
    }

    buildFeatures.compose = true
    buildFeatures.buildConfig = true

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
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
    implementation(project(":core:model"))
    implementation(project(":core:movies-api"))
    implementation(project(":core:database"))
    implementation(project(":core:uikit"))
    implementation(project(":core:data"))
    implementation(project(":core:domain"))

    implementation(project(":feature:home"))
    implementation(project(":feature:details"))
    implementation(project(":feature:search"))

    implementation(libs.androidx.work.runtime.ktx)

    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.common)

    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.messaging)

    // Compose
    implementation(libs.androidx.compose.navigation)
    implementation(libs.androidx.compose.paging)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.material)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling)
    implementation(libs.compose.activity)
    implementation(libs.compose.viewmodel)
    implementation(libs.compose.livedata)
    implementation(libs.compose.coil)
    implementation(libs.compose.material)

    implementation(libs.androidx.appcompat)

    implementation(libs.androidx.livedata)
    implementation(libs.androidx.viewmodel)

    implementation(libs.androidx.room.core)
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)

    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.android.compat)
    implementation(libs.compose.koin)

    implementation(libs.squareup.retrofit)
    implementation(libs.squareup.retrofit.converter.gson)
    implementation(libs.squareup.logging.interceptor)

    implementation(libs.google.material)

    implementation(libs.androidx.core)
}