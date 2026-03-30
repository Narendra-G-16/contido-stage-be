def call {
  catch(e) {
      println "======================================== \n        Build failed... \n========================================"
      // If there was an exception thrown, the build failed
			  currentBuild.result = "FAILED"
              throw e
  }
  finally {
	     // Success or failure, always send notifications
	  notifyBuild(currentBuild.result)
	}
 }
