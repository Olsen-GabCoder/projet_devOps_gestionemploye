# Utiliser une image Maven pour la construction
FROM maven:3.9.4-eclipse-temurin-17 AS build

WORKDIR /app

# Copier tout le contenu du dossier BackEnd
COPY . . 

# Exécuter Maven pour la construction de l'application
RUN mvn clean install

# Utiliser une image Java comme base
FROM openjdk:17-alpine

# Définir le répertoire de travail
WORKDIR /app

# Copier le fichier JAR depuis le répertoire cible généré par Maven
COPY --from=build /app/target/*.jar app.jar

# Exposer le port 8081
EXPOSE 8081

# Lancer l'application Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
