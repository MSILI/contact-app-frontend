pipeline {
    agent any

    tools {
       maven 'maven-3.9.4'
       jdk 'openJdk11'
    }

    stages {
       stage("Init & Checkout") {
           steps {
               git url: "https://github.com/ssbostan/neptune.git",
                   branch: "master",
                   changelog: true,
                   poll: true,
                   credentialsId: 'GITHUB_CREDENTIALS'

           }
       }

       stage("Build") {
           steps {
              sh """
                echo 'Build project'
                mvn -B -DskipTests clean package
              """

           }
       }

       stage("Test") {
           steps {
              sh """
                  echo 'Tests execution'
                  mvn test
              """
           }
       }

    }
    
}