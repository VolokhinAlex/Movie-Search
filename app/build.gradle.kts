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
    implementation(project(":core:uikit"))
    implementation(project(":core:utils"))

    implementation(project(":feature:home"))
    implementation(project(":feature:details"))
    implementation(project(":feature:search"))
    implementation(project(":feature:favorites"))
    implementation(project(":feature:actor"))
    implementation(project(":feature:movie_category"))

    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.messaging)

    // Compose
    implementation(libs.androidx.compose.navigation)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.material)
    implementation(libs.compose.ui)
    implementation(libs.compose.activity)

    implementation(libs.koin.core)
    implementation(libs.koin.android)

    implementation(libs.androidx.core)

    implementation(libs.threetenabp)

    implementation(libs.google.material)
}