
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
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:4.0.0")
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
}
