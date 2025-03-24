pipeline {
	agent any

    stages {
		stage('Checkout') {
			steps {
				git branch: 'master',
                    credentialsId: 'github-token', // Remplacez par l'ID de vos informations d'identification GitHub
                    url: 'https://github.com/Olsen-GabCoder/projet_devOps_gestionemploye.git'
            }
        }

        stage('Build Frontend') {
			steps {
				bat 'cd frontend && npm install && npm run build --prod'  // Utilisez 'bat' au lieu de 'sh'
            }
        }

        stage('Build Backend') {
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