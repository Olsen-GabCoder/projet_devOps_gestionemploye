pipeline {
	agent any

    environment {
		FRONTEND_DIR = './frontend'
        BACKEND_DIR = './backend'
        DOCKER_COMPOSE_PATH = './docker-compose.yml'
    }

    stages {
		stage('Checkout Code') {
			steps {
				script {
					// Cloner le code source depuis le dépôt Git
                    checkout scm
                }
            }
        }

        stage('Build Backend (Maven)') {
			steps {
				script {
					// Construire l'application backend avec Maven
                    dir(BACKEND_DIR) {
						sh 'mvn clean install package'
                    }
                }
            }
        }

        stage('Build Frontend (Angular)') {
			steps {
				script {
					// Construire l'application frontend avec Angular
                    dir(FRONTEND_DIR) {
						sh 'npm install'
                        sh 'npm run build --prod'
                    }
                }
            }
        }

        stage('Build Docker Images') {
			steps {
				script {
					// Construire les images Docker pour le frontend et le backend
                    sh 'docker-compose -f ${DOCKER_COMPOSE_PATH} build'
                }
            }
        }

        stage('Run Docker Compose Up') {
			steps {
				script {
					// Démarrer les services Docker via docker-compose
                    sh 'docker-compose -f ${DOCKER_COMPOSE_PATH} up -d'
                }
            }
        }

        stage('Run Tests (Optional)') {
			steps {
				script {
					// Optionnel : Ajouter ici les tests unitaires ou d'intégration
                    echo 'Running tests...'
                }
            }
        }

        stage('Clean Up') {
			steps {
				script {
					// Nettoyer les images Docker non utilisées après le déploiement
                    sh 'docker system prune -f'
                }
            }
        }
    }

    post {
		success {
			echo 'Pipeline finished successfully.'
        }

        failure {
			echo 'Pipeline failed. Please check the logs for errors.'
        }

        always {
			// Effectuer un nettoyage général si nécessaire, en particulier sur les conteneurs
            sh 'docker-compose down'
        }
    }
}
