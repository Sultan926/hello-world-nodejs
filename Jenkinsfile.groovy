pipeline {
    agent any
    environment {
        DOCKER_IMAGE = 'hello-world-nodejs' // Docker image name
        DOCKER_REGISTRY = '' // If you are using a Docker registry, specify it here
    }
    stages {
        stage('Clone Repository') {
            steps {
                git branch: 'main', url: 'https://github.com/sultan926/hello-world-nodejs'
            }
        }
        stage('Build') {
            steps {
                script {
                    docker.image('node:14').inside('-u root:root') {
                        // Build Docker image
                        sh 'docker build -t $DOCKER_IMAGE .'
                    }
                }
            }
        }
        stage('Test') {
            steps {
                script {
                    docker.image('node:14').inside('-u root:root') {
                        // Add test commands here, e.g., npm test
                        echo 'Running tests...'
                    }
                }
            }
        }
        stage('Deploy') {
            steps {
                script {
                    docker.image('node:14').inside('-u root:root') {
                        // Run Docker container
                        sh 'docker run -d -p 3000:3000 --name hello-world-nodejs $DOCKER_IMAGE'
                    }
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
