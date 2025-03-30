pipeline {
	agent any

    environment {
		PATH = "C:\\Program Files\\Git\\bin;${env.PATH};C:\\Program Files\\Docker\\Docker\\resources\\bin"
        FRONTEND_IMAGE = 'projet_devops_gestionemploye_frontend'
        BACKEND_IMAGE = 'projet_devops_gestionemploye_backend'
        VERSION = '1.5'
        // NE PAS METTRE LE TOKEN ICI .!
        // SONAR_TOKEN = "votre_token_sonarqube"  <- Mauvaise pratique !
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
                    def frontendDir = "${WORKSPACE}/frontend"

                    // Analyse du Backend (Maven)
                    bat "cd ${backendDir} && mvn clean verify sonar:sonar -Dsonar.projectKey=projet_devops_gestionemploye -Dsonar.host.url=http://localhost:9000"

                    // Analyse du Frontend (SonarQube Scanner)
                    bat "cd ${frontendDir} && sonar-scanner -Dsonar.projectKey=projet_devops_gestionemploye_frontend -Dsonar.sources=. -Dsonar.host.url=http://localhost:9000"
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
                    bat "cd ${frontendDir} && \"C:\\Program Files\\nodejs\\npm.cmd\" install"
                    bat "cd ${frontendDir} && \"C:\\Program Files\\nodejs\\npm.cmd\" run build --prod"
                }
            }
        }

        stage('Build Backend') {
			steps {
				script {
					def backendDir = "${WORKSPACE}/BACKEND"
                    bat "cd ${backendDir} && mvn clean install package"
                }
            }
        }

        stage('Tests Unitaires') {
			steps {
				script {
					def backendDir = "${WORKSPACE}/BACKEND"
                    bat "cd ${backendDir} && mvn test"
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
					def backendDir = "${WORKSPACE}/BACKEND"
                    def frontendDir = "${WORKSPACE}/frontend"
                    bat """
                        docker build -t %BACKEND_IMAGE%:%VERSION% ${backendDir}
                        docker build -t %FRONTEND_IMAGE%:%VERSION% ${frontendDir}
                    """
                }
            }
        }

        stage('Deploy') {
			steps {
				script {
					bat 'docker-compose -f docker-compose.yml down --remove-orphans'
                    bat 'docker-compose -f docker-compose.yml up -d'
                }
            }
        }
    }
}