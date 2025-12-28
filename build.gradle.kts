plugins {
	java
	id("org.springframework.boot") version "4.0.1"
	id("io.spring.dependency-management") version "1.1.7"
	jacoco
}

group = "com.personal"
version = "0.0.1-SNAPSHOT"
description = "Demo project for Spring Boot"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(25)
	}
}

springBoot {
    mainClass.set("com.personal.store.StoreApplication")
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-webmvc")

	// common shared module
	implementation(project(":common")) {
		exclude(group = "org.springframework.boot", module = "spring-boot-starter-data-jpa")
		exclude(group = "org.springframework.boot", module = "spring-boot-starter-jdbc")
	}
	// Standard Spring Boot test starter
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

jacoco {
	toolVersion = "0.8.14"
}

tasks.jacocoTestReport {
	dependsOn(tasks.test)
	reports {
		xml.required.set(true)
		html.required.set(true)
	}
}

// Aggregated report across all modules (e.g., includes :common tests/coverage).
tasks.register<JacocoReport>("jacocoRootReport") {
	group = "verification"
	description = "Generates an aggregated JaCoCo coverage report for all subprojects."

	val allTestTasks = listOf(tasks.withType<Test>()) + subprojects.map { it.tasks.withType<Test>() }
	dependsOn(allTestTasks)

	executionData.from(
		files(
			fileTree(layout.buildDirectory).include("jacoco/test*.exec"),
			subprojects.map { sp -> sp.fileTree(sp.layout.buildDirectory).include("jacoco/test*.exec") }
		)
	)

	val allMainSourceSets = (listOf(project) + subprojects).mapNotNull { p ->
		p.extensions.findByType(org.gradle.api.tasks.SourceSetContainer::class.java)?.findByName("main")
	}

	sourceDirectories.from(allMainSourceSets.map { it.allSource.srcDirs })
	classDirectories.from(allMainSourceSets.map { it.output })

	reports {
		xml.required.set(true)
		html.required.set(true)
	}
}
