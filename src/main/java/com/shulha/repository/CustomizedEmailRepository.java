package com.shulha.repository;

import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface CustomizedEmailRepository<T, ID> {
    @Modifying
    Optional<T> markAsDeletedById(final ID id);
}
