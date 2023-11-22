pipeline {

    agent any

    tools { 
        maven 'my-maven' 
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
                }
            }
        }

        stage('Deploy Spring Boot to DEV') {
            steps {
                echo 'Deploying and cleaning'
                sh 'kubectl apply -f deloyment.yaml' 
            }
        }
    }
    post {
        always {
            cleanWs()
        }
    }
}