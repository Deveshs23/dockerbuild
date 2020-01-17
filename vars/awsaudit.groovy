#!/usr/bin/groovy

// import groovy.json.JsonSlurper

def call(){

        wrap([
         $class: 'MaskPasswordsBuildWrapper',
          varPasswordPairs: [[password: "${aws_access_key}",
           var: 'aws_access_key'], [password: "${aws_secret_key}",
           var: 'aws_secret_key']]
           ]){
               scout_suite(
                   "${aws_access_key}",
                   "${aws_secret_key}",
                   "${region}",
                   "${report_name}"
               )
                send_json_data(
                    "${elastic}",
                    "${port}"
                )
                report_publish()

           }
}
def scout_suite(String aws_secrete_key, String aws_access_key, String region, String report){
    
    try {
        stage('Start Scout'){
            
            sh "docker run -v `pwd`/:/opt/scoutsuite-results scoutsuite aws --access-key-id ${aws_access_key} --secret-access-key ${aws_secret_key} -r ${region} --report-name ${report}"
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

