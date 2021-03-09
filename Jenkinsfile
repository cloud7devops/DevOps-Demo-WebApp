properties([pipelineTriggers([githubPush()])])
pipeline { 
  agent any
    tools { 
        maven 'maven3.6.3' 
        jdk 'JDK' 
       }
    stages {
	   stage('Checkout SCM') {
		steps {
		checkout([
		$class: 'GitSCM',
		branches: [[name: 'master']],
		userRemoteConfigs: [[
        url: 'https://github.com/cloud7devops/DevOps-Demo-WebApp.git',
        credentialsId: '',
      ]]
     ])
   }
}
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
	   stage('Deploy to QA') {
        steps {
          deploy adapters: [tomcat8(credentialsId: 'tomcat', path: '', url: 'http://3.139.85.79:8080/')], contextPath: '/QAWebapp', war: '**/*.war'
        }
		post {
		 always {
			  jiraSendDeploymentInfo environmentId: 'QA', environmentName: 'QATesting', environmentType: 'testing', site: 'team-1614647321700.atlassian.net'
              }
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
		stage ('Run UI Tests') {
           steps {
                sh 'mvn -f functionaltest/pom.xml  test' 
				publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: '\\functionaltest\\target\\surefire-reports', reportFiles: 'index.html', reportName: 'UI Test Report', reportTitles: ''])
            }
		}
		
		stage ('Run Performance Test') {
			steps{
				blazeMeterTest credentialsId: 'Blazemeter', testId: '9147147.taurus', workspaceId: '779207'
			}
		}
		stage('Deploy to prod') {
        steps {
          deploy adapters: [tomcat8(credentialsId: 'tomcat', path: '', url: 'http://3.133.136.246:8080/')], contextPath: '/ProdWebapp', war: '**/*.war'
        }
		post {
		 always {
			 jiraSendDeploymentInfo environmentId: 'Prod', environmentName: 'ProdTesting', environmentType: 'production', site: 'team-1614647321700.atlassian.net'
			 }
          }
      }
	  
	 stage ('Run Acceptance Tests') {
           steps {
                sh 'mvn -f Acceptancetest/pom.xml  test' 
				publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: '\\Acceptancetest\\target\\surefire-reports', reportFiles: 'index.html', reportName: 'Sanity Test Report', reportTitles: ''])
            }
		}			
	}
	post {
    success {
      slackSend (color: '#00FF00', message: "SUCCESSFUL: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
    }
    failure {
      slackSend (color: '#FF0000', message: "FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
    }
  }
    }
