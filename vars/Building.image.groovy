def call() {
container("aws-cont"){
    stage("retrieve ecr token"){
            sh """
                aws ecr get-login-password --region ap-south-1 > /home/jenkins/agent/ecr_login.sh
            """
				}
        }
   
    stage("build and push image"){
			container("buildah-cont"){
				sh 'buildah --version'
				println "======================================== \n     Building the Dockerfile... \n========================================"
				timestamps {

                sh "buildah login -u AWS --password-stdin < /home/jenkins/agent/ecr_login.sh $repo_uri"
      
				sh "buildah bud -t $repo_uri/$reponame:$tagname"  
				//sh "buildah bud -t docker.io/desynova/$reponame:$tagname"
				}
				
				println "============================================= \n      Pushing the image with tagname... \n============================================="
				sh "buildah push $repo_uri/$reponame:$tagname"
				//sh "buildah push docker.io/desynova/$reponame:$tagname"
				
			   /*  println "============================================= \n      Pushing the image with latest tag... \n============================================="
				
				sh "buildah push docker.io/desynova/$reponame:$tagname docker.io/desynova/$env/$reponame:$tagname" */
				sh 'buildah images'

        println "===============================================\n Scanning the image... \n======================================================="
      container("trivy-cont"){
             sh "trivy -v"
             sh "trivy image --no-progress $repo_uri/$reponame:$tagname > Vulnerability_report.txt"
              // sh 'trivy image --format template --template "@/contrib/html.tpl"  --input ./$reponame:$tagname --output trivy_report.html'
             stage('Send Email with Vulnerability Report') {
             emailext attachmentsPattern: 'Vulnerability_report.txt', subject: "URGENT: Image Scan Report for ${reponame}:${tagname}",
                  mimeType: 'text/html',
                  body: """<p>Vulenerabilty scan results for <strong>${reponame}:${tagname}</strong>.</p>
                           <p>Please find the attached scan report for ${reponame}:${tagname}.</p>
                           <p>Kindly review the attached vulnerability scan report and resolve the identified vulnerabilities by implementing the necessary changes.</p>""",               
                  to :'mohit.sh@planetc.net,nidhi@planetc.net,cc:suryanarayana@planetc.net,cc:mounikakondeti@planetc.net,cc:pravat@planetc.net'
             } 			    
			}

				sh 'rm -rf *'
				println "======================================== \n     Build Completed Successfully... \n========================================"
        }
    }
