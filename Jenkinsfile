pipeline {
    agent any

    tools {
        maven 'Maven 3.9.9'
    }

    environment {
        PATH = "C:\\Program Files\\Git\\bin;${env.PATH};C:\\Program Files\\Docker\\Docker\\resources\\bin"
        FRONTEND_IMAGE = 'projet_devops_gestionemploye_frontend'
        BACKEND_IMAGE = 'projet_devops_gestionemploye_backend'
        VERSION = '1.5'
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    echo '📥 Clonage du repository Git...'
                }
                git branch: 'master', url: 'https://github.com/Olsen-GabCoder/projet_devOps_gestionemploye.git'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    echo '🔍 Analyse de la qualité du code avec SonarQube...'
                    def backendDir = "${WORKSPACE}/BACKEND"
                    withSonarQubeEnv('SonarQube') {
                        if (isUnix()) {
                            sh "cd ${backendDir} && mvn clean verify sonar:sonar -Dsonar.projectKey=projet_devops_gestionemploye -Dsonar.host.url=http://localhost:9000"
                        } else {
                            bat "cd ${backendDir} && mvn clean verify sonar:sonar -Dsonar.projectKey=projet_devops_gestionemploye -Dsonar.host.url=http://localhost:9000"
                        }
                    }
                }
            }
        }

        stage('Quality Gate') {
            steps {
                script {
                    echo '✅ Attente du Quality Gate...'
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
                    echo '🚀 Construction du frontend...'
                    def frontendDir = "${WORKSPACE}/frontend"
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
                    echo '🔧 Construction du backend...'
                    def backendDir = "${WORKSPACE}/BACKEND"
                    if (isUnix()) {
                        sh "cd ${backendDir} && mvn clean install package"
                    } else {
                        bat "cd ${backendDir} && mvn clean install package"
                    }
                }
            }
        }

        stage('Build Docker Images') {
            steps {
                script {
                    echo '🐳 Construction des images Docker...'
                    if (isUnix()) {
                        sh """
                            docker build -t ${BACKEND_IMAGE}:${VERSION} ./BACKEND
                            docker build -t ${FRONTEND_IMAGE}:${VERSION} ./frontend
                        """
                    } else {
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
                    echo '🚢 Déploiement des services...'
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

    post {
        success {
            echo '✅ Le pipeline a réussi !'
        }
        failure {
            echo '❌ Le pipeline a échoué.'
        }
    }
}
