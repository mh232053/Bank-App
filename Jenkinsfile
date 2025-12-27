pipeline {
    agent any

    tools {
        maven 'Maven 3.8'
        jdk 'JDK 17'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying to Tomcat...'
                // Example deployment command (adjust path as needed)
                // sh 'cp target/bank-webapp.war /usr/local/tomcat/webapps/'
            }
        }
    }

    post {
        always {
            junit 'target/surefire-reports/*.xml'
        }
    }
}
