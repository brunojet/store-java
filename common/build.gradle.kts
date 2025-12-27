
plugins {
    java
}

group = "com.personal.store"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // This module doesn't apply the Spring Boot / dependency-management plugins,
    // so we import the Spring Boot BOM explicitly to allow versionless deps.
    implementation(platform("org.springframework.boot:spring-boot-dependencies:4.0.1"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("jakarta.persistence:jakarta.persistence-api")

    // Lombok is used by common domain classes; pin to a version compatible with Java 25.
    compileOnly("org.projectlombok:lombok:1.18.42")
    annotationProcessor("org.projectlombok:lombok:1.18.42")

    testImplementation(platform("org.springframework.boot:spring-boot-dependencies:4.0.1"))
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-data-jpa-test")
    // Some IDE/JUnit runners don't include testRuntimeOnly on the classpath.
    // Keeping the JDBC driver as testImplementation avoids "Cannot load driver class".
    testRuntimeOnly("com.mysql:mysql-connector-j:8.1.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testRuntimeOnly("com.h2database:h2")
}

tasks.test {
    useJUnitPlatform()
}
