package com.chat.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import com.chat.server.service.TCPServer;
@Configuration
@ComponentScan({"com.chat.server.service","com.chat.server.dao"})
public class AppConfiguration {

}
