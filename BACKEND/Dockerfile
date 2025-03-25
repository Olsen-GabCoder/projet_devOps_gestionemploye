FROM maven:3.9.4-eclipse-temurin-17 AS build

WORKDIR /app

# Copier tout le contenu du dossier BackEnd
COPY . .
# Construire le projet Maven sans les tests
# RUN mvn clean install package
RUN mvn clean install package
# Utiliser une image Java comme base
FROM openjdk:17-alpine

# Définir le répertoire de travail
WORKDIR /app

# Copier le fichier JAR
COPY target/*.jar app.jar

# Exposer le port 8081
EXPOSE 8081

# Lancer l'application Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]