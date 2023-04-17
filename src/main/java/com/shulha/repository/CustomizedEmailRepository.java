package com.shulha.repository;

import com.shulha.types.MessageStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CustomizedEmailRepository<T, ID> {

    @Transactional
    @Modifying
    @Query("update Message m set m.messageStatus = ?1 where m.id = ?2")
    void changeMessageStatusById(final MessageStatus messageStatus, final ID id);

    @Query("select m from User u inner join u.messages m where u.id = ?1")
    Iterable<T> findByUserId(final ID id);

    @Transactional
    @Modifying
    @Query("update Message m set m.messageStatus = ?1 where user_id = ?2")
    void changeMessageStatusByUserId(final MessageStatus messageStatus, final ID id);
}
