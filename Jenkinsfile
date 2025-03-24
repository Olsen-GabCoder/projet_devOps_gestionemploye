pipeline {
	agent any

    tools {
		maven 'Maven 3.9.9' // Assurez-vous que le nom correspond à celui que vous avez configuré dans "Global Tool Configuration"
    }

    stages {
		stage('Checkout') {
			steps {
				git branch: 'master',
                    credentialsId: 'github-credentials', // ID des informations d'identification GitHub (Nom et mot de passe)
                    url: 'https://github.com/Olsen-GabCoder/projet_devOps_gestionemploye.git'
            }
        }

        stage('Build Frontend') {
			environment {
				PATH = "\"C:\\Program Files\\Git\\bin;\${env.PATH}\""
            }
            steps {
				bat 'cd frontend && npm install && npm run build --prod'  // Utilisez 'bat' au lieu de 'sh'
            }
        }

        stage('Build Backend') {
			environment {
				PATH = "\"C:\\Program Files\\Git\\bin;\${env.PATH}\""
            }
            steps {
				bat 'cd backend && mvn clean install'  // Utilisez 'bat' au lieu de 'sh'
            }
        }

        stage('Test') {
			steps {
				echo "Test stage: Not implemented yet"
            }
        }
    }
}