package com.chat.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.chat.server.config.AppConfiguration;
import com.chat.server.service.TCPServer;

@SpringBootApplication
public class ChatserverApplication {

	public static void main(String[] args) {
	   
	     
		 ApplicationContext applicationContext=SpringApplication.run(ChatserverApplication.class, args);
		 if(args.length==1) {
			int tcp_port=Integer.valueOf(args[0]);
		    TCPServer  tcpServer= (TCPServer)applicationContext.getBean("tcpService",tcp_port);
	        tcpServer.setTCPPort(tcp_port);
	        tcpServer.startCommunication();    
		 }
		 else {
			 System.out.println("Incorrect input!");
		 }
		
	}

}
