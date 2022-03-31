package com.chat.client.thread;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.chat.client.model.Client;
public abstract class ClientEvent extends Thread{
    protected Client client;
    protected Socket socket;
	public ClientEvent(Socket socket,Client client) {
		// TODO Auto-generated constructor stub
		this.socket=socket;
		this.client=client;
	}
  
	public abstract void run();
	public abstract void init();
	
	public void SetSocket(Socket socket) {
		this.socket=socket;
	}
	public Socket getSocket() {
		return this.socket;
	}
	
}
