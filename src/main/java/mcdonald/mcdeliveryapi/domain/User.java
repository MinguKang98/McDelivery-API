package mcdonald.mcdeliveryapi.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter(value = AccessLevel.PRIVATE)
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

    /* 생성메서드 */
    public static User createUser(String name, String nickname, String email, String password, Address address) {
        User user = new User();
        user.setName(name);
        user.setNickname(nickname);
        user.setEmail(email);
        user.setPassword(password);
        user.setAddress(address);

        return user;
    }
}
