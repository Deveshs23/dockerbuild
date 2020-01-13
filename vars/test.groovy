def readCustumerName(String file){
    new File("${workspace}/${file}").eachLine {String line ->
        println("${line}")
    }
}
