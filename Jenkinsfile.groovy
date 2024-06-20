pipeline {
    agent {
        docker {
            image 'node:14' // Use the official Node.js Docker image
            args '-u root:root' // Allow running Docker commands
        }
    }
    environment {
        DOCKER_IMAGE = 'hello-world-nodejs' // Docker image name
        DOCKER_REGISTRY = '' // If you are using a Docker registry, specify it here
    }
    stages {
        stage('Clone Repository') {
            steps {
                git 'https://github.com/sultan926/hello-world-nodejs' // Replace with your repository URL
            }
        }
        stage('Build') {
            steps {
                script {
                    // Build Docker image
                    sh 'docker build -t $DOCKER_IMAGE .'
                }
            }
        }
        stage('Test') {
            steps {
                script {
                    // You can add test commands here, e.g., npm test
                    echo 'Running tests...'
                }
            }
        }
        stage('Deploy') {
            steps {
                script {
                    // Run Docker container
                    sh 'docker run -d -p 3000:3000 $DOCKER_IMAGE'
                }
            }
        }
    }
    post {
        always {
            script {
                // Clean up Docker container
                sh 'docker stop hello-world-nodejs || true'
                sh 'docker rm hello-world-nodejs || true'
            }
        }
    }
}
