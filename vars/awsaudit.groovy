def call(){

    def userInput = input(id: 'userInput',    
					message: 'Please specify environment',    
					submitterParameter: "whoIsSubmitter",
            parameters: [
                password(defaultValue: '',
                description: '', name: 'aws_access_key', trim: false),
                password(defaultValue: '',
                description: '', name: 'aws_secret_key', trim: false),
                choice(choices: 
                ['us-east-2', 'us-east-1', 'us-west-1',
                'us-west-2', 'ap-east-1', 'ap-south-1', 
                'ap-northeast-3', 'ap-northeast-2', 
                'ap-southeast-1', 'ap-southeast-2',
                'ap-northeast-1', 'ca-central-1', 
                'cn-north-1', 'cn-northwest-1', 
                'eu-central-1', 'eu-west-1', 
                'eu-west-2', 'eu-west-3', 
                'eu-north-1', 'me-south-1', 
                'sa-east-1', 'us-gov-east-1', 
                'us-gov-west-1'],
                description: '', name: 'region'),
                string(name: 'report_name', defaultValue: '', description: 'enter report_name'),
                string(name: 'elastic', defaultValue: '', description: 'enter elasticsearch ip'),
                string(name: 'elastic_port', defaultValue: '', description: 'enter elasticsearch port'),
                string(name: 'kibana', defaultValue: '', description: 'enter kibana ip'),
                string(name: 'kibana_port', defaultValue: '', description: 'enter kibana port')
            ]
        )

        wrap([
         $class: 'MaskPasswordsBuildWrapper',
          varPasswordPairs: [[password: "${userInput.aws_access_key}",
           var: 'aws_access_key'], [password: "${userInput.aws_secret_key}",
           var: 'aws_secret_key']]
           ]){
               scout_suite(
                   "${userInput.aws_access_key}",
                   "${userInput.aws_secret_key}",
                   "${userInput.region}",
                   "${userInput.report_name}"
               )
                // send_json_data(
                //     "${elastic}",
                //     "${port}"
                // )
                report_publish(
                    "${userInput.report_name}"
                )

           }
}
def scout_suite(String aws_access_key, String aws_secret_key, String region, String report){
    
    try {
        stage('Start Scout'){
            sh "docker run --memory 2048m  -v `pwd`/scoutsuite-results:/opt/scoutsuite-results scoutsuite aws --access-key-id ${aws_access_key} --secret-access-key ${aws_secret_key} -r ${region} --report-name ${report}"
        }
    }
    catch (Exception e){
        println("Not able to build image.")
        sendNotification(
            "danger",
            "Job failed at scoute build docker fail",
            "${environment}"
        )
        echo e.toString()
        throw e
    }
}

def sendNotification(status, message) {
    slackSend channel: 'build-status', color: "${status}", message: " ${message}.\n Message:- Please go to ${env.BUILD_URL} for console output"
}
def report_publish(report_name){
    stage('Report Publish')
    {
 //       def html_report = sh(script: """ cd scoutreport/; ls scoutsuite_results_aws-* """, returnStdout: true).trim()

        try{
            System.setProperty("hudson.model.DirectoryBrowserSupport.CSP", "")
            publishHTML([
                allowMissing: false,
                alwaysLinkToLastBuild: false,
                keepAll: true,
                reportDir: "scoutreport/",
                reportFiles: "${report_name}",
                reportName: 'HTML Report',
                reportTitles: ''])
        }
        catch(Exception e){
            println("Not able to build image.")
            sendNotification(
                "danger",
                "Job failed at scoute build docker fail",
                "${environment}"
            )
            echo e.toString()
            throw e
        }
    }  
}

// def send_json_data(elastic, port){
//     stage("Send Data to elasticsearch"){
//         try{
//             def json_report = sh(script: """ cd scoutreport/scoutsuite-results/; ls scoutsuite_results_aws-*""", returnStdout: true).trim()
//             sh " sed '1d' scoutsuite_results_aws-* > result.json " 
//             httpRequest acceptType: 'APPLICATION_JSON', contentType: 'APPLICATION_JSON', httpMode: 'POST', responseHandle: 'NONE', url: "${elastic}:${port}"
//         }
//         catch (Exception e){
//             println("Not able to build image.")
//             sendNotification(
//                 "danger",
//                 "Job failed at send data to elasticsearch",
//                 "${environment}"
//             )
//             echo e.toString()
//             throw e
        
//         }
//     }
// }
// def parse_json(){
//     def jsonSlurper = new JsonSlurper()
//     def json_report = sh(script: """ cd scoutreport/scoutsuite-results/; ls scoutsuite_results_aws-*""", returnStdout: true).trim()
//     def rootDir = pwd()
//     def props = readJSON file: "${rootDir}@scoutreport/scoutsuite-results/${json_report}"
// }
