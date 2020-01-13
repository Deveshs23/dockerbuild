
def readCustumerName(String file){
    new File("${workspace}/${file}").withReader('UTF-8') { reader ->
    def line
        while ((line = reader.readLine()) != null) { 
            println "${line}"
        }
   }
}
