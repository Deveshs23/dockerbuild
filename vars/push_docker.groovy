def push(String tag, String credential){
    try{
            echo "Pushing Image into Repo: "
            withDockerRegistry(credentialsId: "${credential}", url: '')
            {
                def myDockerImage = docker.build("${tag}:${currentBuild.number}")
                myDockerImage.push("${currentBuild.number}")
            }    
        }
        catch(err){
            echo "Fail To push Docker Image: "
            throw err 
        }
}
