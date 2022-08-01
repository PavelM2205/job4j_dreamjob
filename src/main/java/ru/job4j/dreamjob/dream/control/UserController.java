package ru.job4j.dreamjob.dream.control;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.dreamjob.dream.model.User;
import ru.job4j.dreamjob.dream.service.UserService;

import java.util.Optional;

@ThreadSafe
@Controller
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/registrationPage")
    public String registrationPage() {
        return "registration";
    }

    @PostMapping("/registration")
        public String registration(@ModelAttribute User user, Model model) {
            Optional<User> regUser = service.add(user);
            if (regUser.isEmpty()) {
                model.addAttribute("message",
                        "Пользователь с такой почтой уже существует");
                return "registrationFail";
            }
            return "registrationSuccess";
    }

    @GetMapping("/loginPage")
    public String login(Model model,
                        @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "login";
    }

    @PostMapping("/login")
    public String registration(@ModelAttribute User user) {
        Optional<User> regUser = service.findUserByEmailAndPwd(
                user.getEmail(), user.getPassword());
        if (regUser.isEmpty()) {
            return "redirect:/loginPage?fail=true";
        }
        return "redirect:/index";
    }
}
