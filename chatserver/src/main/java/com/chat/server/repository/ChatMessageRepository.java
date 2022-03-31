package com.chat.server.repository;

import java.util.List;
import com.chat.server.model.ChatMessage;
import com.chat.server.model.Client;
public interface ChatMessageRepository{
  public List<ChatMessage> queryRecords(String nickname,int top,String containText,String messageDirection);
  public void saveChatMessage(ChatMessage chatMessage);
}
