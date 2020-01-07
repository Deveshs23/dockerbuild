def call(String pFile){
 
    def properties = readProperties  file:"${pFile}"
        tag = properties['tag']
        filename = properties['filename']
        docker_dir = properties['Docker_dir']
        credentialID = properties['credentialID']

//        checkout scm
        try {
            build("${tag}", "${filename}", "${docker_dir}")
        }
        catch (err){
            sendNotification("danger", "Docker build Job failed at getting requirements for ${environment}")
            echo err.toString()
            throw err
        }
        try {
            scan("${tag}")
        }
        catch (err){
            sendNotification("danger", "Dcoker Scan Job failed at getting requirements for ${environment}")
            echo err.toString()
            throw err
        }
        try {
            push("${tag}", "${credentialID}")
        }
        catch (err){
            sendNotification("danger", "Docker push Job failed at getting requirements for ${environment}")
            echo err.toString()
            throw err
        }
        

    }


def build(String tag, String filename, String docker_dir){
    stage("Docker Build"){
        myDockerImage = docker.build("${tag}:${currentBuild.number}", "--file ${filename} ${docker_dir}")
            }
}

def scan(String tag)
{
    stage("Docker scan"){
        try{
                echo "Scanning Docker Image: "
                aquaMicroscanner imageName: "${tag}:${currentBuild.number}", notCompliesCmd: '', outputFormat: 'html'
            }
            catch(err){
                echo "Docker has high severity vulnerabilities are found"
                throw err
            }
    }
}

def push(String tag, String credential){
    stage("Docker Push"){
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
}


def sendNotification(status, message) {
    slackSend channel: 'build-status', color: "${status}", message: " ${message}.\n Message:- Please go to ${env.BUILD_URL} for console output"
}
