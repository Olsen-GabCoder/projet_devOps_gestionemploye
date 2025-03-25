# Utiliser une image Node.js comme base pour la construction
FROM node:18-alpine AS builder

# Définir le répertoire de travail
WORKDIR /app

# Copier les fichiers package.json et package-lock.json
COPY package*.json ./

# Installer les dépendances
RUN npm install

# Copier les fichiers source de l'application
COPY . .

# Compiler l'application Angular en mode production
RUN npm run build --prod

# Utiliser une image Nginx pour servir l'application
FROM nginx:alpine

# Supprimer la configuration Nginx par défaut
RUN rm -rf /etc/nginx/conf.d/default.conf

# Copier la configuration Nginx personnalisée (si vous en avez une)
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Copier les fichiers compilés depuis l'étape de construction
COPY --from=builder /app/dist/frontend /usr/share/nginx/html

# Exposer le port 80
EXPOSE 80


CMD ["nginx", "-g", "daemon off;"]
