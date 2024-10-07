plugins {
    kotlin("jvm") version "1.8.0"
    id("org.springframework.boot") version "3.1.0"
    id("io.spring.dependency-management") version "1.1.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.postgresql:postgresql:42.6.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")
    implementation("javax.servlet:javax.servlet-api:4.0.1")
    implementation("org.postgresql:postgresql")
    testImplementation("com.fasterxml.jackson.module:jackson-module-kotlin")

}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17" // Burada JVM hedef versiyonu 11 olarak ayarlandı
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17)) // Java hedef versiyonunu da 11 yapıyoruz
    }
}

