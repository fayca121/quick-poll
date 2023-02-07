pipeline {
    agent any
    triggers {
        pollSCM('H/5 * * * *')
        }
    stages {
        stage("Compile") {
            steps {
                sh 'chmod 777 mvnw'
                sh './mvnw clean compile'
            }
        }

        stage("Unit Tests") {
            steps {
                sh './mvnw test'
            }
        }

        stage("Code coverage"){
            steps {
                sh './mvnw verify'
                publishHTML (target: [
                    reportDir: 'target/site/jacoco/',
                    reportFiles: 'index.html',
                    reportName: 'JaCoCo Report'
                    ])
                }
            }

        stage("Build") {
            steps {
                sh './mvnw -DskipTests package'
            }
        }
    }

}