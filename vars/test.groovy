def readCustumerName(String file){

  def list_of_custumer = ['linux', 'windows'] as String[]
    list_of_custumer.each {arch ->
        println("${arch}")
  }
}
