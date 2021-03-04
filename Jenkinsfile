pipeline { 
  agent any
    tools { 
        maven 'maven3.6.3' 
        jdk 'JDK' 
       }
    stages {
      stage('static code analysis'){
        steps {
	      withSonarQubeEnv('sonar')
          sh 'mvn validate'
        }
       }
      stage('Build') {
        steps {
          sh 'mvn clean install'
        }
      }
      stage('package creation') {
        steps {
          sh 'mvn package'
        }
      }
	   stage('Deploy') {
        steps {
          deploy adapters: [tomcat8(credentialsId: 'tomcat', path: '', url: 'http://52.15.79.81:8080/')], contextPath: '/QAWebapp', war: '**/*.war'
        }
      }
	  
    }
}
