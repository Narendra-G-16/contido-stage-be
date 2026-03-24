def call() {

    properties([
        parameters([
            string(
                description: 'Not Default branch??,Then Enter branch name',
                name: 'Branch',
                trim: true
            )
        ])
    ])

    pipeline {
        agent any

        stages {

            stage('Init') {
                steps {
                    echo "Pipeline Started"
                }
            }

            stage('Print Branch') {
                steps {
                    script {
                        if (params.Branch) {
                            echo "Building branch: ${params.Branch}"
                        } else {
                            echo "Using default branch"
                        }
                    }
                }
            }

            stage('Run Basic Commands') {
                steps {
                    sh '''
                    echo "Running basic commands"
                    uname -a
                    date
                    '''
                }
            }

        }
    }
}
