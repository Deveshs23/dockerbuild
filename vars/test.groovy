def readCustumerName(String file){
     new File("${workspace}/${file}").withReader {String line ->
        while("${line}" != null)
        println("${line}")
        line++
    }
}
