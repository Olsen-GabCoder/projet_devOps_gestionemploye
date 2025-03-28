pipeline {
	agent any

    tools {
		maven 'Maven 3.9.9'
    }

    environment {
		PATH = "C:\\Program Files\\Git\\bin;${env.PATH};C:\\Program Files\\Docker\\Docker\\resources\\bin"
        FRONTEND_IMAGE = 'projet_devops_gestionemploye_frontend'
        BACKEND_IMAGE = 'projet_devops_gestionemploye_backend'
        VERSION = '1.5'
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

        stage('Deploy') {
			steps {
				script {
					if (isUnix()) {
						sh 'docker-compose -f docker-compose.yml down --remove-orphans'
                        sh 'docker-compose -f docker-compose.yml up -d'
                    } else {
						bat 'docker-compose -f docker-compose.yml down --remove-orphans'
                        bat 'docker-compose -f docker-compose.yml up -d'
                    }
                }
            }
        }
    }
}