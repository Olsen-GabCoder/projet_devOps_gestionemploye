pipeline {
    agent any

    tools {
        maven 'Maven 3.9.9'
    }
    
    environment {
        PATH = "C:\\Program Files\\Git\\bin;${env.PATH}"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master', url: 'https://github.com/Olsen-GabCoder/projet_devOps_gestionemploye.git'
            }
        }

        stage('Build Frontend') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'cd frontend && npm install && npm run build --prod'
                    } else {
                        bat 'cd frontend && npm install && npm run build --prod'
                    }
                }
            }
        }

        stage('Build Backend') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'cd BACKEND && mvn clean install'
                    } else {
                        bat 'cd BACKEND && mvn clean install'
                    }
                }
            }
        }

        stage('Test') {
            steps {
                echo "Test stage: Not implemented yet"
            }
        }
    }
}
