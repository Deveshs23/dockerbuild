def readCustumerName(String file){
     new File("${workspace}/${file}").eachLine.split("\n") {String line ->
        while("${line}" != null)
        println("${line}")
        line++
    }
}
