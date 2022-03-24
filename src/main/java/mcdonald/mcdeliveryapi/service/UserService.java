package mcdonald.mcdeliveryapi.service;

import lombok.RequiredArgsConstructor;
import mcdonald.mcdeliveryapi.domain.User;
import mcdonald.mcdeliveryapi.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long join(User user) {
        validateDuplicateUserByEmail(user);
        userRepository.save(user);
        return user.getId();
    }

    private void validateDuplicateUserByEmail(User user) {
        Optional<User> findUser = userRepository.findByEmail(user.getEmail());
        if (findUser.isPresent()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

    }

    public User findUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    @Transactional
    public void withdrawal(Long id) {
        User findUser = userRepository.findById(id);
        userRepository.delete(findUser);
    }

    @Transactional
    public void update(Long id, User user) {
        User findUser = userRepository.findById(id);
        findUser.changeUserValues(user);
    }
}
