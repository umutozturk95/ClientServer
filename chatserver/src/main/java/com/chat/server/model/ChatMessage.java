package com.chat.server.model;

import javax.persistence.Column;  
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;  
import javax.persistence.Table;

@Entity
@Table(name="chatmessage")
public class ChatMessage {

	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;
	@Column(name="sender_nickname")
	private String senderNickname;
	@Column(name="receiver_nickname")
	private String receiverNickname;
	@Column(name="message_content")
    private String message;
    
	public ChatMessage(String senderNickname,String receiverNickname,String message) {
		this.senderNickname=senderNickname;
		this.receiverNickname=receiverNickname;
		this.message=message;
	}
	public ChatMessage() {
		
	}
	public int getId() {
		return id;
	}	
	
	public void setId(int id) {
		this.id=id;
	}
	
	public String getSenderNickname() {
		return senderNickname;
	}	
	
	public void setSenderNickname(String senderNickname) {
		this.senderNickname=senderNickname;
	}
	
	public String getReceiverNickname() {
		return receiverNickname;
	}	
	
	public void setReceivedNickname(String receiverNickname) {
		this.receiverNickname=receiverNickname;
	}


   public void setMessage(String message) {
	   this.message=message;
   }
   public String getMessage() {
	   return this.message;
   }
	
	@Override 
	public String toString() {
		return "Client senderNickname:"+this.senderNickname+" receivedNickname: "+this.receiverNickname+" Message: "+this.message;
	}
    	
}
