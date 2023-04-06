package com.shulha.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "message_for_relatives_body")
public class MessageForRelativesBody extends SimpleMessageBody {
    @OneToOne
    private Address address;
}
