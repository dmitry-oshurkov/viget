buildscript {
    repositories { maven("https://jitpack.io") }
}

plugins {
    kotlin("multiplatform") version "1.5.20"
    id("org.mrlem.gnome.glade") version "0.1.8"
}

group = "name.oshurkov"
version = "5"

repositories {
    mavenCentral()
    maven(url = "https://oss.sonatype.org/content/repositories/snapshots/")
}

kotlin {
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    nativeTarget.apply {

        val mingwPath = File(System.getenv("MINGW64_DIR") ?: "C:/msys64/mingw64")

        binaries {
            executable {
                entryPoint = "name.oshurkov.viget.main"

                if (isMingwX64) {
                    linkerOpts("-L${mingwPath.resolve("lib")}")
                    runTask?.environment("PATH" to mingwPath.resolve("bin"))
                }
            }
        }

        compilations["main"].cinterops {
            val gtk3 by creating {
                when (preset) {
                    presets["macosX64"], presets["linuxX64"] -> {
                        listOf("/opt/local/include", "/usr/include", "/usr/local/include").forEach {
                            includeDirs(
                                "$it/atk-1.0",
                                "$it/gdk-pixbuf-2.0",
                                "$it/cairo",
                                "$it/harfbuzz",
                                "$it/pango-1.0",
                                "$it/gtk-3.0",
                                "$it/glib-2.0"
                            )
                        }

                        includeDirs(
                            "/opt/local/lib/glib-2.0/include",
                            "/usr/lib/x86_64-linux-gnu/glib-2.0/include",
                            "/usr/local/lib/glib-2.0/include"
                        )
                    }
                    presets["mingwX64"] -> {
                        listOf(
                            "include/atk-1.0",
                            "include/gdk-pixbuf-2.0",
                            "include/cairo",
                            "include/pango-1.0",
                            "include/gtk-3.0",
                            "include/glib-2.0",
                            "lib/glib-2.0/include"
                        ).forEach { includeDirs(mingwPath.resolve(it)) }
                    }
                }
            }
        }
    }

    sourceSets {
        val nativeMain by getting {
            dependencies {
                implementation("org.mrlem.gnome:gtk-binding:0.2.1-SNAPSHOT")
            }
        }
        val nativeTest by getting

        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
            }
        }
    }
}

tasks {

    val reflect by registering {

        file("$buildDir/generated/glade/nativeMain/binding/VigetUI.kt").apply {
            val text = readLines().toMutableList()
            if (text.any { it.contains("val builderNew") }.not())
                text.add(text.size - 1, "val builderNew get() = BuilderFactory.new().apply { addFromString(source) }")
            writeText(text.joinToString("\n"))
        }
    }

    startGladeWatcher.get().finalizedBy(reflect)
}