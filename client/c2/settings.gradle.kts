
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
        maven("https://plugins.gradle.org/m2/")
        maven("https://jitpack.io")
        maven("https://devrepo.kakao.com/nexus/content/groups/public/")
    }
}
rootProject.name = "free"
include(":app")
include(":shared")
include(":core")
