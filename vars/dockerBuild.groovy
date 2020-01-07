def build(String tag, String filename, String docker_dir)
{
    myDockerImage = docker.build("${tag}:${currentBuild.number}", "--file ${filename} ${docker_dir}")
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
