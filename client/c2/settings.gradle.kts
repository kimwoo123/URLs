pluginManagement {
    val agpVersion = "7.0.3"
    val kotlinVersion = "1.5.31"
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    plugins {
        id("com.android.application") version agpVersion
        id("com.android.library") version agpVersion
        id("org.jetbrains.kotlin.android") version kotlinVersion
        id("org.jetbrains.kotlin.jvm") version kotlinVersion
        id("org.jetbrains.kotlin.kapt") version kotlinVersion
        id("com.google.devtools.ksp") version "${kotlinVersion}-1.0.0"
    }
    resolutionStrategy {
        eachPlugin {
            when (val id = requested.id.id) {
                "dagger.hilt.android.plugin" -> useModule("com.google.dagger:hilt-android-gradle-plugin:2.39.1")
                "org.jetbrains.kotlin.plugin.serialization" -> useModule("${id}:${id}.gradle.plugin:${kotlinVersion}")
            }
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://plugins.gradle.org/m2/") {
            content {
                includeGroup("org.jlleitschuh.gradle")
            }
        }
    }
}
rootProject.name = "free"
include(":app")
include(":shared")
include(":core")