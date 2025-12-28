
import java.io.StringReader
import org.xml.sax.InputSource

plugins {
    java
	jacoco
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
    // Version is managed by the Spring Boot BOM.
    testImplementation("com.mysql:mysql-connector-j")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    // NOTE: H2 is pinned/forced due to instability observed with H2 2.4.240 under Gradle/JDK 25
    // (spurious failures like "Check constraint invalid" / "The database has been closed" on valid INSERTs).
    // Related upstream reports:
    // - https://github.com/h2database/h2database/issues/4308 (Regression in H2 2.4.240: CHECK(IN ...) + DEFAULT IDENTITY fails)
    // - https://github.com/h2database/h2database/issues/4292 (JdbcSQLIntegrityConstraintViolationException: Check constraint invalid)
    // Once an upstream fix is confirmed, we can remove this pin and go back to the BOM-managed version.
    testRuntimeOnly("com.h2database:h2:2.2.224")
}

tasks.test {
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

tasks.register("jacocoDomainsReport") {
    group = "verification"
    description = "Generates a domain-class index to avoid confusion when JaCoCo HTML omits classes with no executable lines."
    dependsOn(tasks.jacocoTestReport)

    doLast {
        val jacocoXml = layout.buildDirectory.file("reports/jacoco/test/jacocoTestReport.xml").get().asFile
        require(jacocoXml.exists()) { "Expected JaCoCo XML report at ${jacocoXml.absolutePath}" }

        val domainSrcDir = projectDir.resolve("src/main/java/com/personal/store/common/domain")
        val domainFiles = if (domainSrcDir.exists()) {
            project.fileTree(domainSrcDir) { include("**/*.java") }.files
        } else {
            emptySet()
        }

        val domainClasses = domainFiles
            .map { file ->
                val rel = file.relativeTo(projectDir.resolve("src/main/java")).invariantSeparatorsPath
                rel.removeSuffix(".java").replace('/', '.')
            }
            .sorted()

        val dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance()
        dbf.isNamespaceAware = false
        // JaCoCo XML references an external DTD (report.dtd). We don't want to resolve it.
        try {
            dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false)
            dbf.setFeature("http://xml.org/sax/features/external-general-entities", false)
            dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false)
        } catch (_: Exception) {
            // Best-effort: different XML parsers support different feature sets.
        }

        val builder = dbf.newDocumentBuilder()
        builder.setEntityResolver { _, _ ->
            InputSource(StringReader(""))
        }
        val doc = builder.parse(jacocoXml)
        doc.documentElement.normalize()

        val classNodes = doc.getElementsByTagName("class")
        val coverageByInternalName = LinkedHashMap<String, Pair<Int, Int>>()
        for (i in 0 until classNodes.length) {
            val node = classNodes.item(i)
            if (node is org.w3c.dom.Element) {
                val name = node.getAttribute("name")
                var lineMissed = 0
                var lineCovered = 0
                val childNodes = node.getElementsByTagName("counter")
                for (j in 0 until childNodes.length) {
                    val c = childNodes.item(j)
                    if (c is org.w3c.dom.Element && c.getAttribute("type") == "LINE") {
                        lineMissed = c.getAttribute("missed").toIntOrNull() ?: 0
                        lineCovered = c.getAttribute("covered").toIntOrNull() ?: 0
                        break
                    }
                }
                coverageByInternalName[name] = lineMissed to lineCovered
            }
        }

        val outDir = layout.buildDirectory.dir("reports/jacoco").get().asFile
        outDir.mkdirs()
        val outFile = outDir.resolve("domains.html")

        fun esc(s: String) = s
            .replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")

        val rows = StringBuilder()
        for (fqcn in domainClasses) {
            val internal = fqcn.replace('.', '/')
            val cov = coverageByInternalName[internal]
            val status: String
            val pct: String
            when {
                cov == null -> {
                    status = "Not analyzed (class not present in JaCoCo XML)"
                    pct = "-"
                }
                (cov.first + cov.second) == 0 -> {
                    status = "No executable lines (e.g., generated/accessors filtered)"
                    pct = "N/A"
                }
                else -> {
                    status = "Analyzed"
                    val total = cov.first + cov.second
                    pct = String.format("%.1f%%", (cov.second.toDouble() / total.toDouble()) * 100.0)
                }
            }

            rows.append("<tr><td><code>")
                .append(esc(fqcn))
                .append("</code></td><td>")
                .append(esc(pct))
                .append("</td><td>")
                .append(esc(status))
                .append("</td></tr>\n")
        }

        outFile.writeText(
            """
            <!doctype html>
            <html lang=\"en\">
            <head>
              <meta charset=\"utf-8\"/>
              <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"/>
              <title>Domain Coverage Index</title>
              <style>
                body { font-family: system-ui, -apple-system, Segoe UI, Roboto, Arial, sans-serif; padding: 16px; }
                table { border-collapse: collapse; width: 100%; }
                th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
                th { background: #f6f6f6; }
                code { font-family: ui-monospace, SFMono-Regular, Menlo, Monaco, Consolas, "Liberation Mono", "Courier New", monospace; }
              </style>
            </head>
            <body>
              <h1>Domain Coverage Index</h1>
              <p>This index lists all classes under <code>com.personal.store.common.domain</code>.
              JaCoCo HTML may omit classes that have <em>no executable lines</em> (e.g., only Lombok-generated accessors).
              </p>
              <p>Source JaCoCo XML: <code>${esc(jacocoXml.invariantSeparatorsPath)}</code></p>
              <table>
                <thead>
                  <tr><th>Class</th><th>Line Coverage</th><th>Status</th></tr>
                </thead>
                <tbody>
                ${rows}
                </tbody>
              </table>
            </body>
            </html>
            """.trimIndent()
        )

        logger.lifecycle("Wrote domain coverage index: ${outFile.absolutePath}")
    }
}

configurations.testRuntimeClasspath {
    resolutionStrategy {
        force("com.h2database:h2:2.2.224")
    }
}
