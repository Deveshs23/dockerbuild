def readCustumerName(){
   //  def path = "${workspace}/${file}"
        sh """
            path='file.txt' 
            while IFS= read -r line
            do
                echo $line
            done < $path
        """
 }
