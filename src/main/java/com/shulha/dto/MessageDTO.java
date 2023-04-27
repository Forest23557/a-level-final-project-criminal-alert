package com.shulha.dto;

import com.shulha.types.EmailSubject;
import com.shulha.types.MessageStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MessageDTO {

    private String id;

    private MessageStatus messageStatus;

    private EmailSubject subject;

    private String body;

    public MessageDTO(final String id, final MessageStatus messageStatus,
                      final EmailSubject subject, final String body) {
        this.id = id;
        this.messageStatus = messageStatus;
        this.subject = subject;
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageDTO that = (MessageDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
