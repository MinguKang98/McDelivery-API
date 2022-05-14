package mcdonald.mcdeliveryapi.controller;

import lombok.Getter;
import lombok.Setter;
import mcdonald.mcdeliveryapi.domain.Address;
import mcdonald.mcdeliveryapi.domain.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class UserDTO {

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @NotBlank(message = "닉네입은 필수 입력 값입니다.")
    private String nickname;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{8,20}",
        message = "비밀번호는 영문 대,소문자와 숫자가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password;

    @NotBlank(message = "도시는 필수 입력 값입니다.")
    private String city;
    @NotBlank(message = "도로명은 필수 입력 값입니다.")
    private String street;
    @NotBlank(message = "우편번호는 필수 입력 값입니다.")
    private String zipcode;

    // UserForm to Entity
    public User toUser() {
        Address address = new Address(this.getCity(), this.getStreet(), this.getZipcode());
        User user = User.createUser(this.getName(), this.getNickname(), this.getEmail(), this.getPassword(), address);
        return user;
    }
}
