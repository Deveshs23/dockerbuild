def readCustumerName(String file){
     new File("${workspace}/${file}").eachLine {String line ->
        while("${line}" != null)
        println("${line}")
        line++
    }
}
