pipeline { 
  agent any
    tools { 
        maven 'maven3.6.3' 
        jdk 'JDK' 
       }
    stages {
      stage ('validate"'{
        steps {
          
        }
       }
      stage ('compile') {
        step {
          sh 'mvn clean compile'
        }
      }
      stage ('package') {
        step {
          sh 'mvn package'
        }
      }
    }
}
