pipeline { 
  agent any
    tools { 
        maven 'maven3.6.3' 
        jdk 'JDK' 
       }
    stages {
      stage ('static code analysis') {
           steps {
                sh 'mvn validate'
                withSonarQubeEnv('sonarqube') {
                    sh "mvn ${SONAR_MAVEN_GOAL} 
					-Dsonar.host.url=${SONAR_HOST_URL}  
					-Dsonar.projectKey=WEBPOC:AVNCommunication 
					-Dsonar.sources=. -Dsonar.tests=. 
					-Dsonar.inclusions=**/test/java/servlet/createpage_junit.java 
					-Dsonar.test.exclusions=**/test/java/servlet/createpage_junit.java 
					-Dsonar.login=admin -Dsonar.password=sonar"
                }
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
