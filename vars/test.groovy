def readCustumerName(String file){
    new File("${workspace}/${file}").eachLine { line ->
        sh "helm upgrade  -f values.yaml ${line} ./ --namespace producer --install"
    }
}
