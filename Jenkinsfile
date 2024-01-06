pipeline {

    agent any

    tools { 
        maven 'maven-tool' 
    }
    // environment {
    //     MYSQL_ROOT_LOGIN = credentials('dockerhub')
    // }
    stages {

        stage('Build with Maven') {
            steps {
                sh 'mvn --version'
                sh 'java -version'
                sh 'mvn clean package'
            }
        }
        
        stage('Test with Sonarqube') {
            steps {
                script {
                    def mvn = tool 'maven-tool';
                    withSonarQubeEnv('sonarqube') {
                    sh "${mvn}/bin/mvn clean verify sonar:sonar -Dsonar.projectKey=firstDemo -Dsonar.projectName='firstDemo'"
                    }
                }
            }
        }


        // stage('Test with Sonarqube') {
        //     steps {
        //         script {
        //             def scannerHome = tool 'SonarQubeScanner'
        //             echo "SonarScanner Home: ${scannerHome}"  // In ra giá trị của biến scannerHome
        //              withSonarQubeEnv('sonarqube') {
        //                 sh "${scannerHome}/bin/sonar-scanner -X"
        //             }
        //         }
        //     }
        // }



        stage('Packaging/Pushing imagae') {

            steps {
                withDockerRegistry(credentialsId: 'dockerhub', url: 'https://index.docker.io/v1/') {
                    sh 'docker build -t huy21it490/chatservice7:latest --platform linux/amd64 .'
                    sh 'docker push huy21it490/chatservice7:latest'
                    sh 'docker rmi -f huy21it490/chatservice7:latest'
                }
            }
        }

        stage('Deploy Spring Boot to DEV') {
            steps {
                    sh "ls -la"
                    sh "kubectl rollout restart deployment app-and-db -n chat-app"
                    sh "kubectl rollout restart deployment app-and-db-2 -n chat-app"
            }
        }
    }
    post {
        always {
            cleanWs()
        }
    }
}