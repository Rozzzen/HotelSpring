package com.zhuk.controller;

import com.zhuk.domain.user.User;
import com.zhuk.repo.UserRepo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Map;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UserRepo userRepo;

    public RegisterController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping
    public String register() {
        return "register";
    }

    @PostMapping
    public String addUser(User user, Map<String, Object> model) {

        user.setBirthDate(validateDate(user.getDay(), user.getMonth(), user.getYear()));

        if (userRepo.findByEmail(user.getEmail()) != null) {
            model.put("alert", "User with this email already exists");
            return "register";
        }

        userRepo.save(user);
        return "redirect:/login";
    }


    public LocalDate validateDate(int day, int month, int year) throws DateTimeParseException{

        String dateStr = year + "-";
        if (month < 10) dateStr += "0" + month;
        else dateStr += month;
        dateStr += "-" + day;
        LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("uuuu-M-d").withResolverStyle(ResolverStyle.STRICT));
        return LocalDate.of(year, month, day);

    }

    @ExceptionHandler(DateTimeParseException.class)
    public String sqlError(DateTimeParseException ex,
                           RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("alert", "Date validation error");
        return "redirect:/register";
    }
}
