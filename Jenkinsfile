pipeline {
	agent any

    tools {
		// --- AJOUT IMPORTANT ---
        // Assurez-vous que 'JDK_11' correspond EXACTEMENT au nom que vous avez
        // configuré dans Administrer Jenkins -> Configuration globale des outils -> JDK
        jdk 'JDK_11'
        // --- FIN DE L'AJOUT ---

        maven 'Maven 3.9.9'  // Configuration de Maven existante
        nodejs 'NodeJS'      // Configuration de NodeJS existante
    }

    environment {
		// Note : Il n'est généralement PAS nécessaire d'ajouter manuellement Git au PATH ici
        // si Git est correctement installé et accessible par l'utilisateur Jenkins.
        // La modification du PATH pour Docker est correcte si Docker n'est pas géré autrement.
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
					// Jenkins définira automatiquement JAVA_HOME grâce à la section 'tools'
                    def backendDir = "${WORKSPACE}/BACKEND"
                    withSonarQubeEnv('SonarQube') {
						if (isUnix()) {
							// Le 'mvn' utilisé ici sera celui trouvé dans le PATH (modifié par l'outil Maven)
                            sh "cd ${backendDir} && mvn clean verify sonar:sonar -Dsonar.projectKey=projet_devops_gestionemploye -Dsonar.host.url=http://localhost:9000"
                        } else {
							// Le 'mvn' utilisé ici sera celui trouvé dans le PATH (modifié par l'outil Maven)
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
						// La fonction waitForQualityGate communique avec SonarQube
                        def qg = waitForQualityGate abortPipeline: true // abortPipeline: true est plus direct
                        /* // Optionnel: vérifier le statut manuellement si abortPipeline: true ne suffit pas
                        if (qg.status != 'OK') {
                           error "L'analyse SonarQube a échoué avec le statut : ${qg.status}"
                        }
                        */
                    }
                }
            }
        }

        stage('Build Frontend') {
			steps {
				script {
					def frontendDir = "${WORKSPACE}/frontend"
                    // Jenkins utilisera le Node.js/npm de l'outil 'NodeJS'
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
					// Jenkins utilisera le JDK et Maven définis dans 'tools'
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
					// Jenkins utilisera le JDK et Maven définis dans 'tools'
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
					// Archive les rapports de test JUnit/Surefire
                    archiveArtifacts artifacts: 'BACKEND/target/surefire-reports/*.xml', allowEmptyArchive: true
                    // Vous pourriez aussi utiliser : junit 'BACKEND/target/surefire-reports/*.xml' pour afficher les résultats des tests dans Jenkins
                }
            }
        }

        stage('Build Docker Images') {
			steps {
				script {
					// Assurez-vous que 'docker' est accessible (via le PATH modifié dans environment ou autrement)
                    if (isUnix()) {
						sh """
                            docker build -t ${env.BACKEND_IMAGE}:${env.VERSION} ./BACKEND
                            docker build -t ${env.FRONTEND_IMAGE}:${env.VERSION} ./frontend
                        """
                    } else {
						// Utilisation de %VAR% pour les variables d'environnement dans bat
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
					// Assurez-vous que 'docker-compose' est accessible
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