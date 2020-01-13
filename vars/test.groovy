def readCustumerName(String file){
    File file = new File("${workspace}/${file}")
    def line = "test"
    while((line = read.readLine()) != null){
    file.eachLine {String read ->
        println("${read}")
    }
   }
}
