	stage('Image deployment to Kubernetes cluster') {
         container("k8s"){
					echo "Deploying the image..."
					sh "hostname"
					// sh "kubectl set image deployment/$imageName $imageName=$orgName/$imageName:$BUILD_ID -n contido-transcoder-dev"
					withKubeConfig([credentialsId: 'jenkins-kubeconfig']) {
				// 	    sh 'apt update && apt install -y curl'
				// 		sh 'curl -LO "https://storage.googleapis.com/kubernetes-release/release/$(curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt)/bin/linux/amd64/kubectl"'
				// 		sh 'chmod u+x ./kubectl'
						sh "kubectl get nodes"
						sh "pwd"
						sh "ls -la"
						/*slackSend color: '#4CAF50', message: "New version of ${serviceName}:${gitCommit} pushed to ECR!"*/
						sh "kubectl set image deployment/$djangoDeployment $djangoContainername=$repo_uri/$reponame:$tagname -n $namespace"
					//	slackSend color: '#4CAF50', message: "Deployment ${djangoDeployment} has been updated with Version: ${tagname} for image: desynova/${reponame} in contido k8s cluster!"
						sh "kubectl set image deployment/$cronDeployment $cronContainername=$repo_uri/$reponame:$tagname -n $namespace"
					//	slackSend color: '#4CAF50', message: "Deployment ${cronDeployment} has been updated with Version: ${tagname} for image: desynova/${reponame} in contido k8s cluster!"
						
					}
         }
	}
  }
