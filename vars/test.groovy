def readCustumerName(String file){
     def path = "${workspace}/${file}"
        sh """
            while IFS= read -r line
            do
                echo "$line"
            done < "${path}"
        """
        
    }
}
