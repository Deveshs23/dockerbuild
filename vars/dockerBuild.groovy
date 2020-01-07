def build(String tag, String filename)
{
    myDockerImage = docker.build("${tag}:${currentBuild.number}", "--file ${filename} .")
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
