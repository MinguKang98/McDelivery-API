package mcdonald.mcdeliveryapi.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

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

    /* 생성자 메서드 */
    public static User createUser(String name, String nickname, String email, String password, Address address) {
        User user = new User();
        user.setName(name);
        user.setNickname(nickname);
        user.setEmail(email);
        user.setPassword(password);
        user.setAddress(address);

        return user;
    }

    public void changeUserValues(User user) {
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.address = user.getAddress();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getName(), user.getName()) && Objects.equals(getNickname(), user.getNickname()) && Objects.equals(getEmail(),
                user.getEmail()) && Objects.equals(getPassword(), user.getPassword()) && Objects.equals(getAddress(), user.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getNickname(), getEmail(), getPassword(), getAddress());
    }
}
