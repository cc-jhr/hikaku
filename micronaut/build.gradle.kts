plugins {
  kotlin("jvm") version "1.9.23"
  `maven-publish`
  `java-library`
  jacoco
}

val moduleName = "hikaku-micronaut"
val githubUsername: String by rootProject.extra
val githubReleaseToken: String by rootProject.extra
version = rootProject.version

dependencies {
  implementation(platform(kotlin("bom")))
  api(kotlin("stdlib"))
  api(kotlin("reflect"))
  api(kotlin("test"))
  api(project(":core"))
  api("io.micronaut:micronaut-http:4.3.10")

  testImplementation("org.junit.platform:junit-platform-launcher:1.10.2")
  testImplementation("org.junit.jupiter:junit-jupiter-engine:5.10.2")
  testImplementation("io.github.ccjhr:nagare:3.1.1")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
  kotlinOptions {
    jvmTarget = "21"
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
  reports.html.required.set(false)
  reports.junitXml.required.set(false)
  maxParallelForks = Runtime.getRuntime().availableProcessors()
}

val sourcesJar by tasks.registering(Jar::class) {
  archiveClassifier.set("sources")
  from(sourceSets.main.get().allSource)
}

val javaDoc by tasks.registering(Jar::class) {
  archiveClassifier.set("javadoc")
  from(sourceSets.main.get().allSource)
}

publishing {
  repositories {
    maven {
      name = moduleName
      url = uri("https://maven.pkg.github.com/$githubUsername/${rootProject.name}")
      credentials {
        username = githubUsername
        password = githubReleaseToken
      }
    }
  }
  publications {
    create<MavenPublication>("maven") {
      groupId = project.group.toString()
      artifactId = moduleName
      version = project.version.toString()

      from(components["java"])
      artifact(sourcesJar.get())
      artifact(javaDoc.get())

      pom {
        packaging = "jar"
        name.set(moduleName)
        description.set("A library that tests if the implementation of a REST-API meets its specification. This module contains a converter for micronaut implementations.")
        url.set("https://github.com/$githubUsername/${rootProject.name}")

        licenses {
          license {
            name.set("Apache License 2.0")
            url.set("https://www.apache.org/licenses/LICENSE-2.0")
          }
        }

        scm {
          connection.set("scm:git@github.com:$githubUsername/${rootProject.name}.git")
          developerConnection.set("scm:git:ssh://github.com:$githubUsername/${rootProject.name}.git")
          url.set("https://github.com/$githubUsername/${rootProject.name}")
        }
      }
    }
  }
}