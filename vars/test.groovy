def readCustumerName(String file){
    new File("${workspace}/${file}").eachLine { line ->
        println("${line}")
    }
}
