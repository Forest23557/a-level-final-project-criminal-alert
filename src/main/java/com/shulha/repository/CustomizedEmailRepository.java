package com.shulha.repository;

import com.shulha.dto.MessageDTO;
import com.shulha.types.EmailSubject;
import com.shulha.types.MessageStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CustomizedEmailRepository<T, ID> {

    @Transactional
    @Modifying
    @Query("update Message m set m.messageStatus = ?1 where m.id = ?2")
    void changeMessageStatusById(final MessageStatus messageStatus, final ID id);

    @Transactional
    @Modifying
    @Query("update Message m set m.messageStatus = ?1, m.subject = ?2, m.body = ?3 where m.id = ?4")
    void changeMessageFromDtoById(final MessageStatus messageStatus, final EmailSubject subject,
                                  final String body, final ID id);

    @Query("select new com.shulha.dto.MessageDTO(m.id, m.messageStatus, m.subject, m.body) " +
            "from Message m where m.id = ?1")
    MessageDTO getMessageDtoById(final String id);

    @Query("select m from User u inner join u.messages m where u.id = ?1")
    List<T> findByUserId(final ID id);

    @Query("from Message m where m.messageStatus = ?1")
    List<T> findByMessageStatus(final MessageStatus messageStatus);

    @Transactional
    @Modifying
    @Query("update Message m set m.messageStatus = ?1 where user_id = ?2")
    void changeMessageStatusByUserId(final MessageStatus messageStatus, final ID id);
}
