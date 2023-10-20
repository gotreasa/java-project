plugins {
    java
    application
    alias(libs.plugins.test.logger)
    jacoco
    checkstyle
    alias(libs.plugins.sonar)
    alias(libs.plugins.snyk)
    alias(libs.plugins.dotenv)
}

group = "com.example"
version = "0.1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

application {
    mainClass.set("com.example.javaproject.App")
}

testing {
    suites {
        withType<JvmTestSuite>().configureEach {
            useJUnitJupiter()

            dependencies {
                implementation(project())
                implementation(libs.junit.jupiter)
                implementation(libs.assertj.core)
                implementation(libs.mockito.core)
            }
        }

        getByName<JvmTestSuite>("test") {
            dependencies {}
        }

        register<JvmTestSuite>("integrationTest") {
            testType.set(TestSuiteType.INTEGRATION_TEST)

            dependencies {}

            sources {
                java {
                    setSrcDirs(listOf("src/it/java"))
                }
            }

            targets {
                all {
                    testTask.configure {
                        shouldRunAfter(tasks.test)
                    }
                }
            }
        }
    }
}

tasks.withType<Test> {
    finalizedBy("jacocoTestReport")
}

tasks.check {
    dependsOn(
        tasks.named("integrationTest"),
        tasks.named("jacocoTestReport")
    )
}

tasks.withType<JacocoReport> {
    dependsOn(tasks.named("integrationTest"))
    executionData.setFrom(fileTree(layout.buildDirectory.asFile).include("/jacoco/*.exec"))

    reports {
        html.required.set(true)
        xml.required.set(true)
    }
}

checkstyle {
    isShowViolations = true
    toolVersion = libs.versions.checkstyle.get()
}

tasks.sonar {
    dependsOn("check")
}

sonar {
    properties {
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.token", env.fetch("SONAR_TOKEN"))
        property("sonar.links.scm", "https://github.com/gotreasa/java-project")
        property("sonar.projectKey", "gotreasa_java-project")
        property("sonar.organization", "gotreasa")
        property("sonar.projectName", "java-project")
        property("sonar.sources", "src/main")
        property("sonar.tests", "src/test,src/it")
        property("sonar.junit.reportPaths", "${layout.buildDirectory.dir("test-results/test").get()},${layout.buildDirectory.dir("test-results/integrationTest").get()}")
        property("sonar.coverage.jacoco.xmlReportPaths", "${layout.buildDirectory.dir("reports/jacoco/test/jacocoTestReport.xml").get()}")
        property("sonar.qualitygate.wait", "true")
    }
}

snyk {
    setSeverity("low")
    setApi(env.fetch("SNYK_TOKEN", ""))
    setArguments("--configuration-matching='^.*([cC]ompile|[iI]mplementation|[cC]lasspath|[aA]nnotationProcessor|[rR]untime).*$'")
}

tasks.wrapper {
    gradleVersion = "8.4"
}
