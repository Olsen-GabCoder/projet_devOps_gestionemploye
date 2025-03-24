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