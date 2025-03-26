pipeline {
	agent any

    stages {
		stage('Checkout') {
			steps {
				git branch: 'master', url: 'https://github.com/Olsen-GabCoder/projet_devOps_gestionemploye.git'
            }
        }

        stage('Build Backend') {
			steps {
				dir('BACKEND') {
					sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Build Frontend') {
			steps {
				dir('frontend') {
					sh 'npm install'
                    sh 'npm run build --prod'
                }
            }
        }

        stage('Deploy') {
			steps {
				sshagent(['ssh-agent']) {
					sh '''
                        scp -o StrictHostKeyChecking=no -r docker-compose.yml BACKEND/target/*.jar frontend/dist/* ubuntu@13.53.207.181:/home/ubuntu/As-Salam
                        ssh -o StrictHostKeyChecking=no ubuntu@13.53.207.181 "
                            cd /home/ubuntu/As-Salam
                            docker-compose down
                            docker-compose up -d --build
                        "
                    '''
                }
            }
        }
    }

    post {
		always {
			sh 'docker system prune -f'
            cleanWs()
        }
    }
}