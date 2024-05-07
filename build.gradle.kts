import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    id("java")
    id("io.freefair.lombok") version "8.6"
    id("io.qameta.allure") version "2.11.2"
}

group = "otcuda.zvuk"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("io.qameta.allure:allure-rest-assured:2.20.1")
    testImplementation("com.github.javafaker:javafaker:1.0.2")
    testImplementation("org.aeonbits.owner:owner:1.0.4")
    testImplementation("io.rest-assured:rest-assured:5.3.0")
    testImplementation("org.assertj:assertj-core:3.25.3")

    testImplementation("org.seleniumhq.selenium:selenium-java:4.19.1")
    testImplementation("io.github.bonigarcia:webdrivermanager:5.7.0")

    testImplementation("com.codeborne:selenide:7.2.3")
    testImplementation("com.codeborne:pdf-test:1.8.1")
    testImplementation("org.apache.poi:poi:5.2.5")
    testImplementation("org.apache.poi:poi-ooxml:5.2.5")
    testImplementation("ru.yandex.qatools.ashot:ashot:1.5.4")
}

tasks.test {
    testLogging { events(TestLogEvent.FAILED,
            TestLogEvent.PASSED,
            TestLogEvent.SKIPPED)
        exceptionFormat = TestExceptionFormat.FULL }

    useJUnitPlatform()
}

tasks.register<Test>("testsWithMyTags") {
    val fullTags = System.getProperty("customTags")
    if (fullTags != null){
        val tags = fullTags.split(",")
        useJUnitPlatform {
            tags.forEach{
                includeTags.add(it)
            }
        }
        testLogging { events(TestLogEvent.FAILED,
                TestLogEvent.PASSED,
                TestLogEvent.SKIPPED)
            exceptionFormat = TestExceptionFormat.FULL
        }
    }
}