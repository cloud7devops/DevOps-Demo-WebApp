pipeline { 
  agent any
    tools { 
        maven 'maven3.6.3' 
        jdk 'JDK' 
       }
    stages {
      stage ("Build"){
        steps {
          sh "mvn clean install" 
        }
       }
    }
}
