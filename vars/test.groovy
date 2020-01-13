def readCustumerName(String file){
     new File("${workspace}/${file}").reader {String line ->
        while("${line}" != null)
        println("${line}")
        line++
    }
}
