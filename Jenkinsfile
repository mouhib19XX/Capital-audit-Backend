pipeline {
    agent any
    tools {
        gradle "Gradle 8.7-rc-2"
    }
    environment {
        SCANNER_HOME = tool "SonarQube-Scanner"        
        APP_NAME = "java-capital-audit"
        RELEASE = "1.0.0"
        DOCKER_USER = "mouhib543"
        DOCKER_PASS = 'dockerhub'
        IMAGE_NAME = "${DOCKER_USER}/${APP_NAME}"
        IMAGE_TAG = "${RELEASE}-${BUILD_NUMBER}"
    }
    stages {
        stage('clean workspace') {
            steps {
                cleanWs()
            }
        }
        
        stage('Checkout from Git') {
            steps {
                git branch: 'main', url: 'https://github.com/mouhib19XX/Capital-audit-Backend.git'
            }
        }
        
        stage ('build Project') {
            steps {
                sh "chmod +x gradlew"
                sh "./gradlew build"
            }
        }

        //stage('SonarQube analysis') {
            //steps {
                //withSonarQubeEnv('SonarQube-Server') { 
                    //sh './gradlew sonarqube'
                //}
            //}    
        //}
    
        //stage("Quality Gate") {
            //steps {
                //script {
                    //waitForQualityGate abortPipeline: false, credentialsId: 'SonarQube-Token'
                //}
            //}
        //}
        
        stage ('Test Package') {
            steps {
                sh "./gradlew test"
            }
        }
        
        //stage("Performance Test with Apache JMeter") {
            //steps {
                //script {
                    // Add your Apache JMeter test execution commands here
                    //sh "jmeter -n -t testplan.jmx -l results.jtl"
                //}
            //}
        //}
        
        
        stage ('Artifactory configuration') {
            steps {
                rtServer (
                    id: "jfrog-server",
                    url: "http://localhost:8082/artifactory",
                    credentialsId: "jfrog"
                )

                rtGradleDeployer (
                    id: "GRADLE_DEPLOYER",
                    serverId: "jfrog-server",
                    repo: "Admin"
                )

                rtGradleResolver (
                    id: "GRADLE_RESOLVER",
                    serverId: "jfrog-server",
                    repo: "Admin"
                )
            }
        }

        stage ('Deploy Artifacts') {
            steps {
                rtGradleRun (
                    buildName: 'app',
                    tool: "Gradle 8.7-rc-2", 
                    tasks: 'clean build',
                    deployerId: "GRADLE_DEPLOYER",
                    resolverId: "GRADLE_RESOLVER"
                )
            }
        }
        
        stage ('Publish build info') {
            steps {
                rtPublishBuildInfo (
                    serverId: "jfrog-server"
                )
            }
        }
        
        stage("Manage release") {
            steps {
                script {
                    //Clone the repository containing the Python script
                    git url: 'https://github.com/mouhib19XX/Delivery-tool.git'
                    // Execute the Python script
                    sh "python script.py"
                }
            }
        }
        
        stage("Build & Push Docker Image") {
            steps {
                script {
                    docker.withRegistry('',DOCKER_PASS) {
                        docker_image = docker.build "${IMAGE_NAME}"
                    }
                    docker.withRegistry('',DOCKER_PASS) {
                        docker_image.push("${IMAGE_TAG}")
                        docker_image.push('latest')
                    }
                }
            }
        }
        
        stage("Trivy Image Scan") {
            steps {
                script {
                    sh ''
                }
            }
        }
        
        stage ('Cleanup Artifacts') {
            steps {
                script {
                    sh "docker rmi ${IMAGE_NAME}:${IMAGE_TAG}"
                    sh "docker rmi ${IMAGE_NAME}:latest"
                }
            }
        }
    }        
}
