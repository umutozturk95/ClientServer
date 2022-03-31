package com.chat.client.model;


public class Client {
    
	private String nickname;
	private String ipAddress;
    private int port;
    private String message;
	public Client() {
		
	}
	public Client(String nickname,String message) {
		// TODO Auto-generated constructor stub
		this.nickname=nickname;
	//	this.ipAddress=ipAddress;
		//this.port=port;
		this.message=message;
		
	}
	public void setNickname(String nickname) {
		this.nickname=nickname;
	}
	public String getNickname() {
		return this.nickname;
	}
	
	public void setIpAddress(String ipAddress) {
		this.ipAddress=ipAddress;
	}
	public String getIpAddress() {
		return this.ipAddress;
	}
	public void setPort(int port) {
		this.port=port;
	}
	public int getPort() {
		return this.port;
	}
	public String getMessage() {
		return this.message;
	}
	public void setMessage(String message) {
		this.message=message;
	}
	
	public String toString() {
		return "Nickname:"+nickname+" IpAddress: "+ipAddress+" Port:"+port+" Message:"+message;
		
	}

}
