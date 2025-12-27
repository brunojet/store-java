plugins {
	java
	id("org.springframework.boot") version "4.0.1"
	id("io.spring.dependency-management") version "1.1.7"
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
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-webmvc")

	// common shared module
	implementation(project(":common"))
	// Standard Spring Boot test starter
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	// legacy/extra test starters (kept if project expects them)
	testImplementation("org.springframework.boot:spring-boot-starter-data-jpa-test")
	testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
	// In-memory database for tests
	// NOTE: H2 is pinned due to regressions observed with H2 2.4.240 under Gradle/JDK 25
	// (spurious failures like "Check constraint invalid" / "The database has been closed" on valid INSERTs).
	// Related upstream reports:
	// - https://github.com/h2database/h2database/issues/4308
	// - https://github.com/h2database/h2database/issues/4292
	testRuntimeOnly("com.h2database:h2:2.2.224")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

configurations.testRuntimeClasspath {
	resolutionStrategy {
		force("com.h2database:h2:2.2.224")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
