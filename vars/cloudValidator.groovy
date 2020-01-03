def terraform(){
    sh "terraform init"
    def statusCode = sh script:"terraform plan --detailed-exitcode", returnStatus:true
    try {
        if ("${statusCode}" == "0"){
            currentBuild.result = "SUCCESS"
            return
        }
        else{
            error("State Not Match")
        }
    }
    catch(err){
        throw(err)
    }
}
