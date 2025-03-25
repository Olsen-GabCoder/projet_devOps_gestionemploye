pipeline {
	agent any

    tools {
		maven 'Maven 3.9.9'
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
				sh '"C:\\Program Files\\nodejs\\npm.cmd" install'
                 sh '"C:\\Program Files\\nodejs\\npm.cmd" run build --prod'
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