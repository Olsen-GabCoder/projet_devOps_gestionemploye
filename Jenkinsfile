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
				sh 'cd frontend && npm install && npm run build --prod'
            }
        }

        stage('Build Backend') {
			steps {
				sh 'cd backend && mvn clean install'
            }
        }

        stage('Test') {
			steps {
				echo "Test stage: Not implemented yet"
            }
        }
    }
}