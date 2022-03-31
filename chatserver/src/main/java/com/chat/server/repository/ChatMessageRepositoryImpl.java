package com.chat.server.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.chat.server.model.ChatMessage;

@Repository
public class ChatMessageRepositoryImpl implements ChatMessageRepository {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public List<ChatMessage> queryRecords(String nickname, int top, String containText, String messageDirection) {
		try {
			String filterSQL = "Select c from ChatMessage c where ";
			if (messageDirection.equals("send_by_me")) {
				filterSQL += " c.senderNickname ='" + nickname + "' ";
			} else if (messageDirection.equals("send_to_me")) {
				filterSQL += " c.receiverNickname ='" + nickname + "' ";
			}

			if (!containText.isEmpty()) {

				filterSQL += " and c.message like '%" + containText + "%' ";
			}

			Query query = null;
			if (top > 0) {
				filterSQL += " order by c.id desc ";
				query = entityManager.createQuery(filterSQL);
				query.setMaxResults(top);
			} else {
				query = entityManager.createQuery(filterSQL);
			}

			List<ChatMessage> list = query.getResultList();
			return list;
		} catch (NoResultException ex) {
			System.out.println("Exception queryRecords: " + ex.getMessage());
			return null;
		}

	}

	@Override
	@Transactional
	public void saveChatMessage(ChatMessage chatMessage) {
		// TODO Auto-generated method stub
		try {
			entityManager.persist(chatMessage);
		} catch (NoResultException ex) {
			System.out.println("Exception save: " + ex.getMessage());
		}
	}

}
