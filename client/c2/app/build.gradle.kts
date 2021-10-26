plugins {
    id("android-application-convention")
    id("android-compose-convention")
    kotlin("kapt")
    id("com.google.firebase.crashlytics")
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    defaultConfig {
        applicationId = "com.keelim.free"
        versionCode = 1
        versionName = "0.0.1"
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        getByName(BuildType.DEBUG) {

        }

        getByName(BuildType.RELEASE) {
            isMinifyEnabled = BuildTypeRelease.isMinifyEnabled
            proguardFiles(getDefaultProguardFile("proguard-android.txt"))
            proguardFiles(file("proguard-rules.pro"))
        }
    }
    useLibrary("android.test.mock")

    buildFeatures{
        dataBinding = true
        viewBinding = true
        compose = true
    }
}

dependencies {
    implementation(project(":core"))
    implementation(Dep.Kotlin.stdlibJvm)

    implementation(Dep.AndroidX.activity.ktx)
    implementation(Dep.AndroidX.lifecycle.viewModelKtx)
    implementation(Dep.AndroidX.lifecycle.runtime)

    // Android UI
    implementation(Dep.AndroidX.UI.browser)
    implementation(Dep.AndroidX.UI.material)

    // Compose
    implementation(Dep.AndroidX.Compose.ui)
    implementation(Dep.AndroidX.Compose.material)
    implementation(Dep.AndroidX.Compose.tooling)

    // OkHttp
    implementation(Dep.OkHttp.loggingInterceptor)

    // Hilt
    implementation(Dep.Dagger.Hilt.android)
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    kapt(Dep.Dagger.Hilt.compiler)

    // kotlin
    implementation(Dep.Kotlin.coroutines.core)
    implementation(Dep.Kotlin.coroutines.android)

    implementation(Dep.timber)
    debugImplementation(Dep.leakCanary)
    implementation(Dep.Coil.core)


    testImplementation(Dep.Test.junit)
    testImplementation(Dep.Test.assertJ)
    testImplementation(Dep.Test.mockito)

    // startup
    implementation(Dep.AndroidX.startup)
    // firebase

    implementation(platform("com.google.firebase:firebase-bom:28.4.1"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-crashlytics")
    implementation("com.google.firebase:firebase-messaging")

    implementation(Dep.AndroidX.datastore.datastore)
    implementation(Dep.AndroidX.datastore.preferences)

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.20.0")

    implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.5")
}

kapt {
    useBuildCache = true
}
