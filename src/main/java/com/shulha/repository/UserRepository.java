package com.shulha.repository;

import com.shulha.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, String>,
        CustomizedUserRepository<User, String> {

    Optional<User> findUserByEmailAddressOrUsername(final String emailAddress, final String username);
}
