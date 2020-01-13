def readCustumerName(String file){
    File file = new File("${workspace}/${file}")
    def line = "test"
    while((line != null))
    file.eachLine {String line ->
        println("${line}")
    }
}
