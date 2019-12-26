def deploy(String tag, String deploy){
    try{
        sh "kubectl create deploy ${deploy} --image=${tag}:${currentBuild.number}"
    }
    catch(err){
        echo "Fail to Deploy"
        throw err
    }

}