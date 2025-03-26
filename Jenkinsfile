pipeline {
	agent any
    environment {
		SERVER = 'ubuntu@13.53.207.181'
    }

    stages {
		stage('Checkout') {
			steps {
				git branch: 'master', url: 'https://github.com/Olsen-GabCoder/projet_devOps_gestionemploye.git'
            }
        }

        stage('Build Backend') {
			steps {
				sh 'cd BACKEND && mvn clean package -DskipTests'
            }
        }

        stage('Deploy to Server') {
			steps {
				sshagent(['ssh-agent']) {
					sh '''
                        ssh -o StrictHostKeyChecking=no $SERVER "mkdir -p /home/ubuntu/As-Salam"
                        scp -o StrictHostKeyChecking=no -r * $SERVER:/home/ubuntu/As-Salam
                        ssh -o StrictHostKeyChecking=no $SERVER "cd /home/ubuntu/As-Salam && ls"
                        ssh -o StrictHostKeyChecking=no $SERVER "cd /home/ubuntu/As-Salam && docker-compose up -d"
                    '''
                }
            }
        }
    }
}
