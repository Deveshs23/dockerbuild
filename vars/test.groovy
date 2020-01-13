def readCustumerName(String file){
//     def path = "${workspace}/${file}"
        sh(script: """
            path="file.txt" 
            while IFS= read -r line
            do
                echo "$line"
            done < "${path}"
        """, returnStdout: true)
        
    
}
