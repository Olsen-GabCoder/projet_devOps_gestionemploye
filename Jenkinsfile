pipeline {
	agent any

    tools {
		maven 'Maven 3.9.9'
        cmd 'Cmd'  // Utilisez le nom que vous avez donné à votre outil Cmd
    }
    environment {
		PATH = "\"C:\\Program Files\\Git\\bin;\${env.PATH}\""
   }

    stages {
		stage('Checkout') {
			steps {
				git branch: 'master',
                    url: 'https://github.com/Olsen-GabCoder/projet_devOps_gestionemploye.git'
            }
        }

        stage('Build Frontend') {
			steps {
				bat 'cd frontend && npm install && npm run build --prod'
            }
        }

        stage('Build Backend') {
			steps {
				bat 'cd backend && mvn clean install'
            }
        }

        stage('Test') {
			steps {
				echo "Test stage: Not implemented yet"
            }
        }
    }
}