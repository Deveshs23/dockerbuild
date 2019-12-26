def scan(String tag)
{
    try{
            echo "Scanning Docker Image: "
            aquaMicroscanner imageName: "${tag}:${currentBuild.number}", notCompliesCmd: '', onDisallowed: 'fail', outputFormat: 'html'
        }
        catch(err){
            echo "Docker has high severity vulnerabilities are found"
            throw err
        }
    }

