import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("android-library-convention")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
    id("org.jetbrains.kotlin.android")
}

android {

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro")
        }
    }

    useLibrary("android.test.mock")

    buildFeatures {
        dataBinding = true
        viewBinding = true
        compose = true
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

    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.4.0")

    implementation(Dep.Dagger.Hilt.android)
    kapt(Dep.Dagger.Hilt.compiler)

    // kotlin
    implementation(Dep.Kotlin.coroutines.core)
    implementation(Dep.Kotlin.coroutines.android)

    implementation(Dep.timber)

    testImplementation(Dep.Test.junit)
    testImplementation(Dep.Test.assertJ)
    testImplementation(Dep.Test.mockito)

    implementation(Dep.SquareUp.core)
    implementation(Dep.SquareUp.loggingInterceptor)
    implementation(Dep.SquareUp.urlconnection)
    implementation(Dep.SquareUp.retrofit)
    implementation(Dep.SquareUp.retrofit_gson)

}

kapt {
    useBuildCache = true
}