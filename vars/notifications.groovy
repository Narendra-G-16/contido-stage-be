// vars/notifications.groovy

def call(String buildStatus = 'STARTED') {
    // Default build status if null
    buildStatus = buildStatus ?: 'SUCCESSFUL'

    def jobUserId, jobUserName

    // Get user id and name of the build started user
    wrap([$class: 'BuildUser']) {
        jobUserId = "${BUILD_USER_ID}"
        jobUserName = "${BUILD_USER}"
    }

    println("Started By: ${jobUserName}")

    // Default notification color
    def color = 'RED'
    def colorCode = '#FF0000'

    // Prepare summary
    def summary = "${buildStatus}: `${env.JOB_NAME}` #${env.BUILD_NUMBER}:\n${env.BUILD_URL} env: `stage` triggered by: `${jobUserId}`"

    // Override color based on build status
    if (buildStatus == 'STARTED') {
        color = 'YELLOW'
        colorCode = '#FFFF00'
    } else if (buildStatus == 'SUCCESSFUL') {
        color = 'GREEN'
        colorCode = '#00FF00'
    }

    // Send Slack notification
    slackSend(color: colorCode, message: summary)

    // Send email notification
    emailext(
        subject: "$buildStatus : Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
        mimeType: 'text/html',
        body: """<p>$buildStatus : Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'</p>
                 <p>Check console output at <a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a></p>""",
        to: 'narendra@planetc.net,suryanarayana@planetc.net,mounikakondeti@planetc.net'
    )
}
