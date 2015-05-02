# postback-service-example
Code sample for listening Callfire API notifications.

Files:
 - com.callfire.postbackservice.Application.java - Spring boot javaconfig class
 - com.callfire.postbackservice.PostbackController.java - Rest controller for listening Callfire API
 - application.properties - properties file with API credentials and callback URI
 
How to start:
 - $ gradle build
 - $ java -jar build/libs/postback-service-example-1.0.jar

Project dependencies:
 - spring boot 1.2.3
 - unirest 1.4.5