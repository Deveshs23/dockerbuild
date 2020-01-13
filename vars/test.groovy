def readCustumerName(String file){
    File sfile = new File("${workspace}/${file}")
    def read = "test"
    while((read = line.readLine()) != null){
    sfile.eachLine {String line ->
        println("${line}")
    }
   }
}
