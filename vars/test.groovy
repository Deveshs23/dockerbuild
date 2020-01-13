def readCustumerName(String filePath) {
    File file = new File("${workspace}/${filePath}")
    def line, noOfLines = 0;
    file.withReader { reader ->
        while ((line = reader.readLine()) != null) {
            println "${line}"
            noOfLines++
        }
    }
}
