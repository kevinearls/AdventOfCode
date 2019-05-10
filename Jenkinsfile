pipeline {
    agent { label 'mvn' }
    tools {
        jdk 'jdk9'
    }
    stages {
        stage('Build'){
            steps {
                sh '''
                    #env | sort
                    checkout scm
                     mvn --fail-never clean test

                    find . -name "TEST*.xml"
                '''
            }
        }
    }
    post {
        always {
            junit allowEmptyResults: true, testResults: '**/target/surefire-reports/**'
        }
    }
}
