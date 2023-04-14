package com.shulha.model;

import com.shulha.types.RoleName;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Role extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private RoleName name;
}
