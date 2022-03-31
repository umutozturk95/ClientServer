package com.chat.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.chat.client.service.ClientService;


@SpringBootApplication
public class ChatclientApplication {

	public static void main(String[] args) {
		 ApplicationContext applicationContext=SpringApplication.run(ChatclientApplication.class, args);
		 if(args.length==4) {
		  ClientService service= applicationContext.getBean(ClientService.class);
		  service.setArgs(args);
		  service.init();
		 }
		 else {
			 System.out.println("Incorrect input!");
		 }
	}

}
