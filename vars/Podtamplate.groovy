def call() {
podTemplate (label: podtemplatename, // podRetention: Never,      
        nodeSelector: "name=contido-jenkins-agent-node",
        containers: [
                containerTemplate(name: 'aws-cont', image: '909463554763.dkr.ecr.ap-south-1.amazonaws.com/org-tools:awscli-1.0', command: 'cat', ttyEnabled: true, privileged: true, workingDir: '/home/jenkins/agent',  resourceLimitCpu: '250m',resourceLimitMemory:'250Mi',resourceRequestCpu :'250m',resourceRequestMemory :'250Mi'),
			        	containerTemplate(name: 'buildah-cont', image: '909463554763.dkr.ecr.ap-south-1.amazonaws.com/org-tools:buildah-1.0', command: 'sleep 99999', ttyEnabled: true, privileged: true, workingDir: '/home/jenkins/agent',  resourceLimitCpu: '2500m',resourceLimitMemory:'3500Mi',resourceRequestCpu :'2500m',resourceRequestMemory :'3500Mi'),
                containerTemplate(name: 'k8s', image: '909463554763.dkr.ecr.ap-south-1.amazonaws.com/org-tools:k8s-image', command: 'cat', ttyEnabled: true, privileged: true, workingDir: '/home/jenkins/agent' ,  resourceLimitCpu: '250m',resourceLimitMemory:'250Mi',resourceRequestCpu :'250m',resourceRequestMemory :'250Mi'),
                containerTemplate(name: 'trivy-cont', image: '909463554763.dkr.ecr.ap-south-1.amazonaws.com/org-tools:trivy', command: 'sleep 99999', ttyEnabled: true, privileged: true, workingDir: '/home/jenkins/agent')

            ],
            volumes: [
                emptyDirVolume(mountPath: '/home/jenkins/agent', memory: false),
                //secretVolume(mountPath: '/.docker', secretName: 'kaniko-secret'),
            ],
        )
