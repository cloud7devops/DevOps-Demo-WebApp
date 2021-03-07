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
