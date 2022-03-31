# ClientServer

Chat application is developed by using Spring Boot, Hibernate and Rest API. 
Embedded H2 database and Tomcat server is integrated into the chat application.  Java must be only installed in your workstation.
Firstly,the user can run “ java -jar server-0.0.1-SNAPSHOT.jar  9090 “ command.The tcp port is changed with desired value. 
After  the “Server is listening on port 9090” is  printed console, then the client applications can be started.
The client application is started by using “java -jar client-0.0.1-SNAPSHOT.jar  client1   127.0.0.1  9090  --server.port=8080”. “client1” is the nickname, “127.0.0.1”  is ipaddress and “8080” is the server port. You must enter te different nickname and server port between client application.The several  command examples are in Readme.txt .
Enter “localhost:8080” in Chrome, then the web interface of the related client is shown.


1. The client information is printed in the web interface.
2. This panel is message panel, box.Then the client can be select the other client to send message.
3. The client can be filter in database records.

The server application has the embedded database server called as H2.You can test some queries in its console panel. Enter “ http://localhost:3000/h2-console”  in Chrome, then the below panel is opened.
