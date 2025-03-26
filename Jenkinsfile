pipeline {
	agent any

    environment {
		SERVER = 'ubuntu@13.53.207.181'  // Définir la variable ici
    }

    stages {
		stage('Checkout') {
			steps {
				git branch: 'master', url: 'https://github.com/Olsen-GabCoder/projet_devOps_gestionemploye.git'
            }
        }

        stage('Build Docker Images') {
			steps {
				script {
					// Créer le répertoire cible si inexistant et modifier les permissions
                    sshagent(['ssh-agent']) {
						// Copier les fichiers vers la machine distante
                        sh '''
                            ssh -o StrictHostKeyChecking=no $SERVER "mkdir -p /home/ubuntu/As-Salam && sudo chown -R ubuntu:ubuntu /home/ubuntu/As-Salam"
                            scp -o StrictHostKeyChecking=no -r . $SERVER:/home/ubuntu/As-Salam
                        '''
                    }
                }
            }
        }

        stage('Build and Run Containers') {
			steps {
				script {
					// Construire et démarrer les conteneurs via docker-compose
                    sshagent(['ssh-agent']) {
						sh '''
                            ssh -o StrictHostKeyChecking=no $SERVER "cd /home/ubuntu/As-Salam && docker-compose down"
                            ssh -o StrictHostKeyChecking=no $SERVER "cd /home/ubuntu/As-Salam && docker-compose build"
                            ssh -o StrictHostKeyChecking=no $SERVER "cd /home/ubuntu/As-Salam && docker-compose up -d"
                        '''
                    }
                }
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
						sh 'cd BACKEND && mvn clean install'
                    } else {
						bat 'cd BACKEND && mvn clean install'
                    }
                }
            }
        }

        stage('Test') {
			steps {
				echo "Test stage: Not implemented yet"
            }
        }
    }
}
