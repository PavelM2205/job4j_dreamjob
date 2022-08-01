package ru.job4j.dreamjob.dream.control;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.dreamjob.dream.model.User;
import ru.job4j.dreamjob.dream.service.UserService;

@ThreadSafe
@Controller
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/registration")
    public String registration(Model model, @ModelAttribute User user) {
        User regUser = service.add(user);
        if (regUser.getId() == 0) {
            model.addAttribute("message",
                    "Пользователь с такой почтой уже существует!");
            return "loginFail";
        } else {
            return "loginSuccess";
        }
    }
}
