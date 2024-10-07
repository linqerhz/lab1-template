# Temel image olarak OpenJDK 17 kullanıyoruz
FROM openjdk:17-jdk-slim

# Uygulamanın çalışacağı dizini belirliyoruz
WORKDIR /app

# Gradle build ile oluşturulan jar dosyasını container içine kopyalıyoruz
COPY build/libs/lab1-template.jar app.jar

# Uygulamanın hangi portu dinleyeceğini belirliyoruz
EXPOSE 8080

# Uygulamanın çalıştırılma komutu
ENTRYPOINT ["java", "-jar", "app.jar"]

