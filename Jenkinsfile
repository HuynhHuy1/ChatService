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

        stage('Packaging/Pushing imagae') {

            steps {
                withDockerRegistry(credentialsId: 'dockerhub', url: 'https://index.docker.io/v1/') {
                    sh 'docker build -t huy21it490/chatservice:latest .'
                    sh 'docker push huy21it490/chatservice:latest'
                    sh 'docker rmi huy21it490/chatservice:latest'
                }
            }
        }

        stage('Deploy Spring Boot to DEV') {
            steps {
                    sh "ls -la"
                    sh "kubectl create ns test"
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