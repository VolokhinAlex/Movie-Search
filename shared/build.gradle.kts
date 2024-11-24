//plugins {
//    alias(libs.plugins.jetbrains.kotlin.multiplatform)
//    alias(libs.plugins.jetbrains.compose.multiplatform)
//    alias(libs.plugins.compose.compiler)
//    alias(libs.plugins.jetbrains.kotlin.serialization)
//}
//
//kotlin {
//    listOf(
//        iosX64(),
//        iosArm64(),
//        iosSimulatorArm64()
//    ).forEach { iosTarget ->
//        iosTarget.binaries.framework {
//            baseName = "shared"
//            isStatic = true
//        }
//    }
//
//    sourceSets {
//        commonMain.dependencies {
//            implementation(project(":composeApp"))
//            // Необходимо явно добавить все зависимости для работы
//            // приложения на iOS, чтобы они попали в XCFramework
//            implementation(compose.runtime)
//            implementation(compose.ui)
//
//            implementation(libs.kotlinx.coroutines.core)
//
//            implementation(libs.koin.core)
//        }
//    }
//}