pipeline {
	agent any

   environment {
		PATH = "C:\\Program Files\\Git\\bin;\${env.PATH}"
   }

    stages {
		stage('Checkout') {
			steps {
				git branch: 'master', url: 'https://github.com/Olsen-GabCoder/projet_devOps_gestionemploye.git'
            }
        }

        stage('Build Frontend') {
			steps {
				sh 'cd frontend && npm install && npm run build --prod'
            }
        }

        stage('Build Backend') {
			steps {
				sh 'cd BACKEND && mvn clean install'
            }
        }

        stage('Test') {
			steps {
				echo "Test stage: Not implemented yet"
            }
        }
    }
}