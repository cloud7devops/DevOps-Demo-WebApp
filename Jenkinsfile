pipeline { 
  agent any
    tools { 
        maven 'maven3.6.3' 
        jdk 'JDK' 
       }
    stages {
      stage('static code analysis'{
        steps {
          sh 'mvn validate'
        }
       }
      stage('build') {
        step {
          sh 'mvn clean install'
        }
      }
      stage('package creation') {
        step {
          sh 'mvn package'
        }
      }
    }
}
