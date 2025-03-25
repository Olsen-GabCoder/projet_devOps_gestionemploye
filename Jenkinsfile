pipeline {
	agent any

    tools {
		maven 'Maven 3.9.9'
    }
    environment {
		PATH = "C:\\Program Files\\Git\\bin;\${env.PATH}"
        SHELL = "C:\\Program Files\\Git\\bin\\sh.exe"
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
				// Ajout du shebang pour spécifier l'interpréteur de commandes
                sh "#!C:\\Program Files\\Git\\bin\\sh.exe\n cd frontend && npm install && npm run build --prod"
            }
        }

        stage('Build Backend') {
			steps {
				// Ajout du shebang pour spécifier l'interpréteur de commandes
                sh "#!C:\\Program Files\\Git\\bin\\sh.exe\n cd BACKEND && mvn clean install"
            }
        }

        stage('Test') {
			steps {
				echo "Test stage: Not implemented yet"
            }
        }
    }
}