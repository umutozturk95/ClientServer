package com.chat.client.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Semaphore;

import com.chat.client.model.Client;

public class ListSendMesagesToServer {

	private Semaphore mutex;
	private List<String> messages;
	private ConcurrentHashMap<String, Integer> checkAlreadySendMessage;

	public ListSendMesagesToServer() {
		// TODO Auto-generated constructor stub

		messages = new CopyOnWriteArrayList<>();
		checkAlreadySendMessage = new ConcurrentHashMap<>();
		mutex = new Semaphore(1);
	}

	public void addSendMessage(String header, String json_message) {

		messages.add(json_message);

	}

	public List<String> getSendMessagesToServer() {
		List<String> allMessages = null;
		allMessages = new ArrayList<String>(messages);
		return allMessages;
	}

	public void removeAllMessages() {
		messages.clear();

	}
}
