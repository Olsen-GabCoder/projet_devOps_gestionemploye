pipeline {
	agent any

    tools {
		maven 'Maven 3.9.9'
    }

    environment {
		PATH = "C:\\Program Files\\Git\\bin;${env.PATH}"
        SHELL = "C:\\Program Files\\Git\\bin\\sh.exe"
        DOCKER_HUB_USER = 'm1ra'  // Remplace par ton identifiant Docker Hub
        DOCKER_HUB_PASSWORD = credentials('docker-hub-credentials')  // Stocke tes credentials dans Jenkins
    }

    stages {
		stage('Checkout') {
			steps {
				git branch: 'master',
                    url: 'https://github.com/Olsen-GabCoder/projet_devOps_gestionemploye.git'
            }
        }

        stage('Build Frontend') {
			steps {
				sh """
                cd frontend
                npm install
                npm run build --prod
                """
            }
        }

        stage('Build Backend') {
			steps {
				sh """
                cd BACKEND
                mvn clean install
                """
            }
        }

        stage('Build & Push Docker Images') {
			steps {
				script {
					withDockerRegistry([credentialsId: 'docker-hub-credentials', url: '']) {
						// Build frontend
                        sh """
                        docker build -t m1ra/projet_devops_gestionemploye-frontend:1.0 ./frontend
                        docker push m1ra/projet_devops_gestionemploye-frontend:1.0
                        """

                        // Build backend
                        sh """
                        docker build -t m1ra/projet_devops_gestionemploye-backend:1.0 ./BACKEND
                        docker push m1ra/projet_devops_gestionemploye-backend:1.0
                        """
                    }
                }
            }
        }

        stage('Deploy') {
			steps {
				sh """
                docker-compose down
                docker-compose pull
                docker-compose up -d
                """
            }
        }
    }
}
