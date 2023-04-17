package com.shulha.repository;

import com.shulha.types.MessageStatus;
import com.shulha.types.PersonStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CustomizedUserRepository<T, ID> {

    @Transactional
    @Modifying
    @Query("update User u set u.personStatus = ?1 where u.id = ?2")
    void changePersonStatusById(final PersonStatus personStatus, final ID id);

    @Transactional
    @Modifying
    @Query("update Person p set p.personStatus = ?1")
    void changeAllPersonStatuses(final PersonStatus personStatus);
}
