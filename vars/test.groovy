
def readCustumerName(String file){
    new File("${workspace}/${file}").eachline { reader ->
    def line
        while ((line = reader.readLine()) != null) { 
            println "${line}"
        }
   }
}
