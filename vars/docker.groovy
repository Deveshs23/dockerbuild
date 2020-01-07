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

def push(name, credential){
    stage('docker push'){
        try{
                echo "Pushing Image into Repo: "
                withDockerRegistry(credentialsId: "${credential}", url: '')
                {
                    def myDockerImage = docker.build("${name}:${currentBuild.number}")
                    myDockerImage.push("${currentBuild.number}")
                }    
            }
            catch(err){
                echo "Fail To push Docker Image: "
                throw err 
            }
    }
}
def scan(name){
    stage(scan){
            try{
                echo "Scanning Docker Image: "
                aquaMicroscanner imageName: "${name}:${currentBuild.number}", notCompliesCmd: '', onDisallowed: 'fail', outputFormat: 'html'
            }
            catch(err){
                echo "Docker has high severity vulnerabilities are found"
                throw err
            }
        }
    }
}

