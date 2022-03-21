package mcdonald.mcdeliveryapi.service;

import mcdonald.mcdeliveryapi.domain.Address;
import mcdonald.mcdeliveryapi.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    public void 회원가입() throws Exception {
        //given
        User user = User.createUser("kang", "mintgreen", "kang@naver.com", "1234",
                new Address("서울시", "한천로37길 33", "101-101"));

        ///when
        Long saveId = userService.join(user);

        //then
        User findUser = userService.findUserById(saveId);
        assertThat(findUser).isEqualTo(user);
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        //given
        User user1 = User.createUser("kang", "mintgreen", "kang@naver.com", "1234",
                new Address("서울시", "한천로37길 33", "101-101"));
        User user2 = User.createUser("kang", "mintgreen", "kang@naver.com", "1234",
                new Address("서울시", "한천로37길 33", "101-101"));

        ///when
        Long saveId1 = userService.join(user1);

        //then
        Assertions.assertThrows(IllegalStateException.class, () -> {
            userService.join(user2);
        });
    }

    @Test
    public void 회원탈퇴() throws Exception {
        //given
        User user = User.createUser("kang", "mintgreen", "kang@naver.com", "1234",
                new Address("서울시", "한천로37길 33", "101-101"));

        ///when
        Long saveId = userService.join(user);
        userService.withdrawal(saveId);

        //then
        assertThat(userService.findUserById(saveId)).isNull();
    }

    @Test
    public void 회원_정보_수정() throws Exception {
        //given
        User user1 = User.createUser("kang", "mintgreen", "kang@naver.com", "1234",
                new Address("서울시", "한천로37길 33", "101-101"));
        User user2 = User.createUser("kim", "mintgreen", "kang@naver.com", "5678",
                new Address("서울시", "한천로37길 33", "101-101"));

        ///when
        Long saveId = userService.join(user1);
        userService.update(saveId, user2);

        //then
        User findUser = userService.findUserById(saveId);
        assertThat(findUser.getName()).isEqualTo(user2.getName());
        assertThat(findUser.getPassword()).isEqualTo(user2.getPassword());
    }

}