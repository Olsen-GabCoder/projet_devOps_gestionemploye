pipeline {
    agent any

    /*tools {
	maven 'Maven 3.9.9'
    }

    environment {
	PATH = "C:\\Program Files\\Git\\bin;${env.PATH}"
    }*/

    environment {
		SERVER = 'ubuntu@13.53.207.181'  // Définir la variable ici
    }

     stages {
	    stage('Checkout') {
		    steps {
				 git branch: 'master', url: 'https://github.com/Olsen-GabCoder/projet_devOps_gestionemploye.git'
            }
        }

        stage('build'){
			steps {
				sshagent(['ssh-key']) {
					sh '''
                      # Créer le répertoire cible si inexistant
                        ssh -o StrictHostKeyChecking=no $SERVER "mkdir -p /home/ubuntu/As-Salam"

                        # Copier les fichiers vers la machine distante
                        scp -o StrictHostKeyChecking=no -r * $SERVER:/home/ubuntu/As-Salam

                        # Se déplacer dans le répertoire As-Salam sur la machine distante et lister les fichiers
                        ssh -o StrictHostKeyChecking=no $SERVER "cd /home/ubuntu/As-Salam && ls"

                        # Créer et démarrer les conteneurs dans le répertoire /home/ubuntu/As-Salam
                        ssh -o StrictHostKeyChecking=no $SERVER "cd /home/ubuntu/As-Salam && docker-compose up -d"

                  '''
                }
            }
        }

        /*stage('Build Frontend') {
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
        }*/
    }
}