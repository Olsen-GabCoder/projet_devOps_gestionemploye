pipeline {
	agent any

    tools {
		maven 'Maven 3.9.9'
        nodejs 'NodeJS'  
    }

    environment {
		SERVER = 'ubuntu@13.53.207.181'  // Votre serveur AWS
        SSH_KEY_PATH = '/home/ubuntu/.ssh/id_rsa'  // Chemin de votre clé SSH privée
        FRONTEND_IMAGE = 'projet_devops_gestionemploye_frontend'
        BACKEND_IMAGE = 'projet_devops_gestionemploye_backend'
        VERSION = '1.1'
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

        stage('Build Docker Images') {
			steps {
				script {
					if (isUnix()) {
						sh """
                            docker build -t ${BACKEND_IMAGE}:${VERSION} ./BACKEND
                            docker build -t ${FRONTEND_IMAGE}:${VERSION} ./frontend
                        """
                    } else {
						bat """
                            docker build -t %BACKEND_IMAGE%:%VERSION% ./BACKEND
                            docker build -t %FRONTEND_IMAGE%:%VERSION% ./frontend
                        """
                    }
                }
            }
        }

        stage('Deploy to AWS') {
			steps {
				script {
					// Copier le fichier docker-compose.yml sur le serveur distant AWS
                    sh """
                        scp -i ${SSH_KEY_PATH} ./docker-compose.yml ${SERVER}:/home/ubuntu/projet_devOps/
                    """

                    // Exécuter les commandes Docker Compose sur le serveur AWS via SSH
                    sh """
                        ssh -i ${SSH_KEY_PATH} ${SERVER} 'docker-compose -f /home/ubuntu/projet_devOps/docker-compose.yml down --remove-orphans'
                        ssh -i ${SSH_KEY_PATH} ${SERVER} 'docker-compose -f /home/ubuntu/projet_devOps/docker-compose.yml up -d'
                    """
                }
            }
        }
    }
}
