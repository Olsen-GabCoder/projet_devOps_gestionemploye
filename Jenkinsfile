pipeline {
	agent any

    tools {
		maven 'Maven 3.9.9' // Assurez-vous que Maven est bien configuré dans Jenkins
        nodejs 'NodeJS' // Assurez-vous d'avoir configuré NodeJS dans Jenkins
    }

    environment {
		PATH = "C:\\Program Files\\Git\\bin;C:\\Program Files\\Docker\\Docker\\resources\\bin;C:\\Program Files\\nodejs;${env.PATH}" // Ajout de Node.js dans le PATH
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

        stage('SonarQube Analysis') {
			steps {
				script {
					// Obtient le chemin absolu du répertoire BACKEND
                    def backendDir = "${WORKSPACE}/BACKEND"

                    withSonarQubeEnv('SonarQube') { // Remplace 'SonarQube' par le nom de ton installation SonarQube dans Jenkins
                        if (isUnix()) {
						sh "cd ${backendDir} && mvn clean verify sonar:sonar -Dsonar.projectKey=projet_devops_gestionemploye -Dsonar.host.url=http://localhost:9000"
                        } else {
						bat "cd ${backendDir} && mvn clean verify sonar:sonar -Dsonar.projectKey=projet_devops_gestionemploye -Dsonar.host.url=http://localhost:9000"
                        }
                    }
                }
            }
        }

        stage('Quality Gate') {
			steps {
				script {
					timeout(time: 5, unit: 'MINUTES') {
						def qg = waitForQualityGate()
                        if (qg.status != 'OK') {
							error "L'analyse SonarQube a échoué avec le statut : ${qg.status}"
                        }
                    }
                }
            }
        }

        stage('Build Frontend') {
			steps {
				script {
					// Obtient le chemin absolu du répertoire frontend
                    def frontendDir = "${WORKSPACE}/frontend"

                    if (isUnix()) {
						sh "cd ${frontendDir} && npm install && npm run build --prod"
                    } else {
						bat "cd ${frontendDir} && npm install && npm run build --prod"
                    }
                }
            }
        }

        stage('Build Backend') {
			steps {
				script {
					// Obtient le chemin absolu du répertoire BACKEND
                    def backendDir = "${WORKSPACE}/BACKEND"

                    if (isUnix()) {
						sh "cd ${backendDir} && mvn clean install package"
                    } else {
						bat "cd ${backendDir} && mvn clean install package"
                    }
                }
            }
        }

        stage('Tests Unitaires') {
			steps {
				script {
					// Obtient le chemin absolu du répertoire BACKEND
                    def backendDir = "${WORKSPACE}/BACKEND"

                    if (isUnix()) {
						sh "cd ${backendDir} && mvn test"
                    } else {
						bat "cd ${backendDir} && mvn test"
                    }
                }
            }
            post {
				always {
					archiveArtifacts artifacts: 'BACKEND/target/surefire-reports/*.xml', allowEmptyArchive: true
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
