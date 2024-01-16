plugins {
    kotlin("jvm") version "1.9.22"
    jacoco
    `jacoco-report-aggregation`
    id("com.github.nbaztec.coveralls-jacoco") version "1.2.18"
}

val githubUsername by extra { System.getenv("GH_USERNAME") ?: project.findProperty("GH_USERNAME") as String? ?: "cc-jhr" }
val githubReleaseToken by extra { System.getenv("GH_PACKAGES_RELEASE_TOKEN") ?: project.findProperty("GH_PACKAGES_RELEASE_TOKEN") as String? ?: "NOT_SET" }
version = project.findProperty("release.version") as String? ?: ""

allprojects {
    repositories {
        mavenCentral()
        maven {
            name = "nagare"
            url = uri("https://maven.pkg.github.com/cc-jhr/nagare")
            credentials {
                username = System.getenv("GH_USERNAME") ?: project.findProperty("GH_USERNAME") as String? ?: "cc-jhr"
                password = System.getenv("GH_PACKAGES_READ_TOKEN") ?: project.findProperty("GH_PACKAGES_READ_TOKEN") as String? ?: "NOT_SET"
            }
        }
    }
    group = "io.github.ccjhr.hikaku"
}

val sources: List<File> =
    subprojects
        .flatMap { project ->
            file("${project.projectDir}/src/")
                .walkBottomUp()
                .maxDepth(2)
                .filter { it.path.contains("kotlin", ignoreCase = true) }
                .filter { it.path.contains("main", ignoreCase = true) }
                .toSet()
                .toList()
        }

coverallsJacoco {
    reportSourceSets = sources
    reportPath = "${layout.buildDirectory}/reports/jacoco/test/jacocoFullReport.xml"
}

tasks.jacocoTestReport {
    subprojects {
        this@subprojects.plugins.withType<JacocoPlugin>().configureEach {
            this@subprojects.tasks.matching {
                it.extensions.findByType<JacocoTaskExtension>() != null }
                .configureEach {
                    sourceSets(this@subprojects.the<SourceSetContainer>().named("main").get())
                    executionData(this)
                }
        }
    }

    reports {
        html.required.set(false)
        xml.required.set(true)
        xml.outputLocation.set(file("${layout.buildDirectory}/reports/jacoco/test/jacocoFullReport.xml"))
    }
    dependsOn(allprojects.map { it.tasks.named<Test>("test") })
}