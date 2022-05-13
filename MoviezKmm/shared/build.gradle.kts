plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.6.21"
    id("com.android.library")
    id("com.squareup.sqldelight")
}

kotlin {
    android()

    listOf(
        iosX64(),
        iosArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val ktorVersion = "2.0.1"
        val serializationVersion = "1.3.2"
        val koinVersion = "3.1.6"
        val sqldVersion = "1.5.3"

        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.insert-koin:koin-core:$koinVersion")
                implementation("com.squareup.sqldelight:runtime:$sqldVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
                implementation("io.insert-koin:koin-android:$koinVersion")
                implementation("io.insert-koin:koin-androidx-compose:$koinVersion")
                implementation("com.squareup.sqldelight:android-driver:$sqldVersion")
                implementation("com.squareup.sqldelight:coroutines-extensions-jvm:$sqldVersion")
            }
        }
        val androidTest by getting
        val iosMain by creating {
            dependsOn(commonMain)
            dependencies {
                implementation("com.squareup.sqldelight:native-driver:$sqldVersion")
            }
        }
        val iosTest by creating {
            dependsOn(commonTest)
        }
    }
}

android {
    compileSdk = 32
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 23
        targetSdk = 32
    }
}

sqldelight {
    this.database("MoviezDb") {
        packageName = "com.robertlevonyan.demo.moviezkmm.sqldelight"
    }
}