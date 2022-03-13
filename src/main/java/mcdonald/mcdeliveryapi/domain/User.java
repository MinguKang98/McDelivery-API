package mcdonald.mcdeliveryapi.domain;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String name;

    private String nickname;

    private String email;

    private String password;

    @Embedded
    private Address address;

}
