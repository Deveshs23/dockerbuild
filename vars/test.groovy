def readCustumerName(String file){
     File tFile = new File("${workspace}/${file}").each.split("\n")
     
     tFile.each {String line ->
  //        while("${line}" != null){
            println("${line}")
             
//    }
}
}
