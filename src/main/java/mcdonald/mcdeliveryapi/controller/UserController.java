package mcdonald.mcdeliveryapi.controller;

import lombok.RequiredArgsConstructor;
import mcdonald.mcdeliveryapi.domain.Address;
import mcdonald.mcdeliveryapi.domain.User;
import mcdonald.mcdeliveryapi.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users/new")
    public String createUser(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "/users/createUserForm";
    }

    @PostMapping("/users/new")
    public String create(@Valid UserDTO userDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "/users/createUserForm"; //valid는 thymeleaf에서 처리
        }
        User user = userDTO.toUser();
        userService.join(user);
        return "redirect:/";
    }

    @GetMapping("/users")
    public String list(Model model) {
        List<User> users = userService.findAllUser();
        model.addAttribute("users", users);
        return "/users/userList";
    }

    @GetMapping("/users/{userId}/update")
    public String updateUser(@PathVariable("userId") Long userId, Model model) {
        User findUser = userService.findUserById(userId);
        UserDTO userDTO = findUser.toUserDTO();
        model.addAttribute("userDTO", userDTO);
        return "/users/updateUserForm";
    }

    @PostMapping("/users/{userId}/update")
    public String update(@PathVariable("userId") Long userId, @Valid UserDTO userDTO, BindingResult result) {
        if(result.hasErrors()){
            return "/users/updateUserForm";
        }
        User newUser = userDTO.toUser();
        userService.update(userId, newUser);
        return "redirect:/users";
    }

    @GetMapping("/users/{userId}/delete")
    public String delete(@PathVariable("userId") Long userId) {
        userService.withdrawal(userId);
        return "redirect:/users";
    }

}
