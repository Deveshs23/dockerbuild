def readCustumerName(String file){
    File sfile = new File("${workspace}/${file}")
    def line = "test"
    while((line = read.readLine()) != null){
    sfile.eachLine {String read ->
        println("${read}")
    }
   }
}
