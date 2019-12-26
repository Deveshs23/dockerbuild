def push(String tag, String credential){
    try{
            echo "Pushing Image into Repo: "
            withDockerRegistry(credentialsId: "${credential}", url: '')
            {
                myDockerImage.push('latest')
            }    
        }
        catch(err){
            echo "Fail To push Docker Image: "
            throw err 
        }
}