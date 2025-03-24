pipeline {
	agent any

    stages {
		stage('Checkout') {
			steps {
				git 'https://github.com/Olsen-GabCoder/projet_devOps_gestionemploye.git'
            }
        }

        stage('Build Frontend') {
			agent { docker { image 'node:18-alpine' } }
            steps {
				sh 'cd frontend && npm install && npm run build --prod'
            }
        }

        stage('Build Backend') {
			agent { docker { image 'maven:3.8.5-jdk-17' } }
            steps {
				sh 'cd backend && mvn clean install'
            }
        }

        stage('Test') {
			steps {
				echo "Test stage: Not implemented yet"
            }
        }

        stage('Build and Push Docker Images') {
			steps {
				withCredentials([string(credentialsId: 'dockerhub-username', variable: 'DOCKER_USERNAME'),
                                password(credentialsId: 'dockerhub-password', variable: 'DOCKER_PASSWORD')]) {
					sh 'docker-compose build'
                    sh 'docker login -u "$DOCKER_USERNAME" -p "$DOCKER_PASSWORD" docker.io'
                    sh 'docker-compose push'
                }
            }
        }

        stage('Deploy') {
			steps {
				sshPublisher(publishers: [
                    sshPublisherDesc(configName: 'mon-serveur-ssh',
                        transfers: [
                            sshTransfer(cleanRemote: false,
                                remoteDirectory: '/opt/docker',
                                sourceFiles: 'docker-compose.yml',
                                excludes: '.git,.gitignore',
                                makeEmptyDirs: false,
                                noDefaultExcludes: false,
                                removePrefix: '',
                                sourceSeparator: ',')
                        ],
                        usePromotionTimestamp: false,
                        useWorkspaceInPromotion: false,
                        verbose: false)
                ])
                sshCommand remoteCommand: 'cd /opt/docker && docker-compose down && docker-compose pull && docker-compose up -d', //Adaptez le chemin si nécessaire
                                                sshPublisherName: 'mon-serveur-ssh'
            }
        }
    }
}