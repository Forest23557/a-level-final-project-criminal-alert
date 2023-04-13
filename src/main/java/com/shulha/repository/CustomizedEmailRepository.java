package com.shulha.repository;

import com.shulha.types.MessageStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CustomizedEmailRepository<T, ID> {

    @Transactional
    @Modifying
    @Query("update Message m set m.messageStatus = ?1 where m.id = ?2")
    void markAsDeletedById(final MessageStatus messageStatus, final ID id);
}
