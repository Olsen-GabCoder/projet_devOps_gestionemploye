pipeline {
	agent any

    tools {
		maven 'Maven 3.9.9'  // Configuration de Maven
        nodejs 'NodeJS'  // version stable et sans echec de mon pipeline
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

        stage('SonarQube Analysis') {
			steps {
				script {
					def backendDir = "${WORKSPACE}/BACKEND"
                    withSonarQubeEnv('SonarQube') {
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
					def frontendDir = "${WORKSPACE}/frontend"

                    // Utilisation de npm après l'installation de Node.js dans Jenkins
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
