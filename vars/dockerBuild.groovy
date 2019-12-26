def build(String tag)
{
    myDockerImage = docker.build("${tag}:${currentBuild.number}", "--file Dockerfile .")
        try{
            myDockerImage.inside {
                echo "Hello Inside the container"
    
            }
        }
        catch(err){
            echo "Can not login in to container"
            throw err
            }
}
