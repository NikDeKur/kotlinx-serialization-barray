@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
        mavenCentral()
        mavenLocal()
        gradlePluginPortal()
        google()

        maven {
            name = "ndkRepoReleases"
            url = uri("https://repo.nikdekur.uk/releases")
        }

        maven {
            name = "Sonatype Snapshots (Legacy)"
            url = uri("https://oss.sonatype.org/content/repositories/snapshots")
        }

        maven {
            name = "Sonatype Snapshots"
            url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots")
        }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    repositories {
        mavenCentral()
        mavenLocal()
        google()

        maven {
            name = "ndkRepoReleases"
            url = uri("https://repo.nikdekur.uk/releases")
        }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "kotlinx-serialization-barray"