pipeline { 
  agent any
    stages {
      tools { 
        maven 'maven3.6.3' 
        jdk 'JDK' 
       }
      stage ("Build"){
        steps {
          sh "mvn clean install" 
        }
       }
    }
}
