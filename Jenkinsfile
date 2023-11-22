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
                withCredentials([file(credentialsId: 'kubectl', variable: 'KUBECONFIG')]) {
                    sh "cat \$KUBECONFIG"
                    sh "pwd"
                    sh "ls"
                    sh "cp \$KUBECONFIG ~/.kube/config"
                    sh "kubectl apply -f deloyment.yaml"
                }
            }
        }
    }
    post {
        always {
            cleanWs()
        }
    }
}