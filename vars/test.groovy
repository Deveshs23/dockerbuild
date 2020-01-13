def readCustumerName(String file){
     new File("${workspace}/${file}").split('\n').each  {String line ->
  //        while("${line}" != null){
            println("${line}")
             
//    }
}
}
