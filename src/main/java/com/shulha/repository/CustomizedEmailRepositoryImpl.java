package com.shulha.repository;

import com.shulha.model.Message;
import com.shulha.types.MessageStatus;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Optional;

public class CustomizedEmailRepositoryImpl implements CustomizedEmailRepository<Message, String> {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public Optional<Message> markAsDeletedById(final String id) {
        final Query query =
                entityManager.createNativeQuery("update user_messages set message_status = ? where id = ?");
        query.setParameter(1, MessageStatus.DELETED.toString())
                .setParameter(2, id)
                .executeUpdate();
        final Message message = entityManager.createQuery("select m from Message m where m.id = :id", Message.class)
                .setParameter("id", id)
                .getSingleResult();

        return Optional.ofNullable(message);
    }
}
