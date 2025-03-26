pipeline {
	agent any

    tools {
		maven 'Maven 3.9.9'  // Assurez-vous que la version est bien installée dans Jenkins
    }

    environment {
		PATH = "C:\\Program Files\\Git\\bin;${env.PATH}"
        REGISTRY = 'm1ra'
        FRONTEND_IMAGE = 'projet_devops_gestionemploye-frontend'
        BACKEND_IMAGE = 'projet_devops_gestionemploye-backend'
        VERSION = '1.1'  // On incrémente la version
    }

    stages {
		stage('Checkout') {
			steps {
				git branch: 'master', url: 'https://github.com/Olsen-GabCoder/projet_devOps_gestionemploye.git'
            }
        }

        stage('Build Frontend') {
			steps {
				script {
					if (isUnix()) {
						sh 'cd frontend && npm install && npm run build --prod'
                    } else {
						bat 'cd frontend && npm install && npm run build --prod'
                    }
                }
            }
        }

        stage('Build Backend') {
			steps {
				script {
					if (isUnix()) {
						sh 'cd BACKEND && mvn clean install package'
                    } else {
						bat 'cd BACKEND && mvn clean install package'
                    }
                }
            }
        }

        stage('Build & Push Docker Images') {
			steps {
				script {
					// Assurez-vous que la commande Docker utilise le bon contexte
                    sh "docker build -t $REGISTRY/$BACKEND_IMAGE:$VERSION ./BACKEND"
                    sh "docker push $REGISTRY/$BACKEND_IMAGE:$VERSION"

                    sh "docker build -t $REGISTRY/$FRONTEND_IMAGE:$VERSION ./frontend"
                    sh "docker push $REGISTRY/$FRONTEND_IMAGE:$VERSION"
                }
            }
        }

        stage('Deploy') {
			steps {
				script {
					// Déployer avec docker-compose
                    sh 'docker-compose down'
                    sh 'docker-compose pull'
                    sh 'docker-compose up -d'
                }
            }
        }
    }
}
