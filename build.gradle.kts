
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    id("maven-publish")
}

group = "dev.nikdekur"
version = "1.1.1"

val authorId: String by project
val authorName: String by project

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_1_8)
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    sourceCompatibility = "1.8"
    targetCompatibility = "1.8"
}


kotlin {
    explicitApi()

    jvm {
        compilerOptions.apply {
            jvmTarget = JvmTarget.JVM_1_8
            freeCompilerArgs.addAll(
                listOf(
                    "-Xopt-in=kotlin.RequiresOptIn",
                    "-Xopt-in=kotlin.time.ExperimentalTime",
                    "-Xopt-in=kotlin.ExperimentalStdlibApi",
                    "-Xno-param-assertions",
                    "-Xno-call-assertions",
                    "-Xno-receiver-assertions"
                )
            )
        }
    }

    // iOS
    // iosX64()
    // iosArm64()
    // iosSimulatorArm64()

    // Desktop
    // mingwX64()
    // linuxX64()
    // linuxArm64()
    // macosX64()
    // macosArm64()

    // Web
    js {
        outputModuleName = project.name
        browser()
        nodejs()
    }

    wasmJs {
        outputModuleName = project.name + "Wasm"
        browser()
        nodejs()
    }

    sourceSets {

        commonMain.dependencies {
            implementation(libs.kotlinx.serialization)
            implementation(libs.kotlinx.io.core)
            implementation(libs.ndkore)
        }


        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

val repoUsernameProp = "NDK_REPO_USERNAME"
val repoPasswordProp = "NDK_REPO_PASSWORD"
val repoUsername = System.getenv(repoUsernameProp)
val repoPassword = System.getenv(repoPasswordProp)

if (repoUsername.isNullOrBlank() || repoPassword.isNullOrBlank()) {
    throw GradleException("Environment variables $repoUsernameProp and $repoPasswordProp must be set.")
}


publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()

            pom {
                developers {
                    developer {
                        id.set(authorId)
                        name.set(authorName)
                    }
                }
            }

            from(components["kotlin"])
        }
    }

    repositories {
        maven {
            name = "ndk-repo"
            url = uri("https://repo.nikdekur.uk/releases")
            credentials {
                username = repoUsername
                password = repoPassword
            }
        }

        mavenLocal()
    }
}