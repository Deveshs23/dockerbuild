def readCustumerName(String file){

    sh "echo 'Hello World'"
 
            sh(script: """
            path='file.txt'
            while IFS=read -r line
            do
                echo $line
            done < $path
        """, returnStdout: true)

}
