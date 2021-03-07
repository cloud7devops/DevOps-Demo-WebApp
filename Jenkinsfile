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
                //withSonarQubeEnv('sonarqube') {
                   // sh "mvn ${SONAR_MAVEN_GOAL} -Dsonar.host.url=${SONAR_HOST_URL}  -Dsonar.projectKey=WEBPOC:AVNCommunication -Dsonar.sources=. -Dsonar.tests=. -Dsonar.inclusions=**/test/java/servlet/createpage_junit.java -Dsonar.test.exclusions=**/test/java/servlet/createpage_junit.java -Dsonar.login=admin -Dsonar.password=sonar"
                }
            }
        }
      stage('Build') {
        steps {
          sh 'mvn clean install'
        }
		post {
		 always {
              jiraSendBuildInfo site: 'team-1614647321700.atlassian.net'
              }
          }
      }
	   stage('Deploy') {
        steps {
          deploy adapters: [tomcat8(credentialsId: 'tomcat', path: '', url: 'http://3.139.82.90:8080/')], contextPath: '/QAWebapp', war: '**/*.war'
        }
      }
	  
	   stage ('Publish to artifactory') {
			steps {
				script {
				def server = Artifactory.server('artifactory')
				def rtMaven = Artifactory.newMavenBuild()
				rtMaven.resolver server: server, releaseRepo: 'libs-release', snapshotRepo: 'libs-snapshot'
				rtMaven.deployer server: server, releaseRepo: 'libs-release-local', snapshotRepo: 'libs-snapshot-local'
				rtMaven.deployer.artifactDeploymentPatterns.addInclude("*.war")
				def buildInfo = rtMaven.run pom: 'pom.xml', goals: 'install'
				server.publishBuildInfo buildInfo
				}
			}
		} 
    }
}
