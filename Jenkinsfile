pipeline {
	agent any

    tools {
		maven 'Maven 3.9.9'
    }

    environment {
		PATH = "C:\\Program Files\\Git\\bin;${env.PATH};C:\\Program Files\\Docker\\Docker\\resources\\bin"
        REGISTRY = 'm1ra'
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

        stage('Build & Push Docker Images') {
			environment {
				DOCKER_CONFIG = credentials('docker-hub-creds')
            }
            steps {
				script {
					if (isUnix()) {
						sh """
                            docker build -t $REGISTRY/$BACKEND_IMAGE:$VERSION ./BACKEND
                            docker login -u $DOCKER_CONFIG_USR -p $DOCKER_CONFIG_PSW
                            docker push $REGISTRY/$BACKEND_IMAGE:$VERSION

                            docker build -t $REGISTRY/$FRONTEND_IMAGE:$VERSION ./frontend
                            docker push $REGISTRY/$FRONTEND_IMAGE:$VERSION
                        """
                    } else {
						bat """
                            docker build -t %REGISTRY%/%BACKEND_IMAGE%:%VERSION% ./BACKEND
                            docker login -u %DOCKER_CONFIG_USR% -p %DOCKER_CONFIG_PSW%
                            docker push %REGISTRY%/%BACKEND_IMAGE%:%VERSION%

                            docker build -t %REGISTRY%/%FRONTEND_IMAGE%:%VERSION% ./frontend
                            docker push %REGISTRY%/%FRONTEND_IMAGE%:%VERSION%
                        """
                    }
                }
            }
        }

        stage('Deploy') {
			steps {
				script {
					if (isUnix()) {
						sh 'docker-compose -f docker-compose.prod.yml down --remove-orphans'
                        sh 'docker-compose -f docker-compose.prod.yml up -d'
                    } else {
						bat 'docker-compose -f docker-compose.prod.yml down --remove-orphans'
                        bat 'docker-compose -f docker-compose.prod.yml up -d'
                    }
                }
            }
        }
    }
}