package com.shulha.repository;

import com.shulha.model.Message;
import org.springframework.data.repository.CrudRepository;

public interface EmailRepository extends CrudRepository<Message, String>,
        CustomizedEmailRepository<Message, String> {

}
