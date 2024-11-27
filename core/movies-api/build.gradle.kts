import com.github.gmazzo.buildconfig.generators.BuildConfigKotlinGenerator
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeSpec
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.android.library)
    alias(libs.plugins.buildConfig)
}

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "core_movies_api"
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.model)

            implementation(libs.ktor.client.core)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.logging)
            implementation(libs.koin.core)
        }

        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }

    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }
    }
}

android {
    namespace = "com.volokhinaleksey.movie_club.movies_api"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

buildConfig {
    generator = object : BuildConfigKotlinGenerator() {
        override fun adaptSpec(spec: TypeSpec) = spec.toBuilder()
            .addAnnotation(AnnotationSpec.builder(ClassName.bestGuess("kotlin.js.JsName"))
                .addMember("name = %S", spec.name!!)
                .build())
            .build()
    }
    val properties = Properties().apply {
        load(project.rootProject.file("apikey.properties").inputStream())
    }
    buildConfigField("MOVIE_API_KEY", properties.getProperty("movie_tmdb_api_key", ""))
}