package com.zhuk.controller;

import com.zhuk.domain.dto.CaptchaResponseDto;
import com.zhuk.domain.user.User;
import com.zhuk.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Collections;
import java.util.Map;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final static String CAPTCHA_URL = 
            "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Value("${recaptcha.secret}")
    private String secretKey;

    private RestTemplate restTemplate;
    private final UserRepo userRepo;

    public RegisterController(UserRepo userRepo, RestTemplate restTemplate) {
        this.userRepo = userRepo;
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public String register() {
        return "register";
    }

    @PostMapping
    public String addUser(User user,
                          Map<String, Object> model,
                          @RequestParam(value = "g-recaptcha-response", defaultValue = "none") String captchaResponse) {

        user.setBirthDate(validateDate(user.getDay(), user.getMonth(), user.getYear()));

        String url = String.format(CAPTCHA_URL, secretKey, captchaResponse);
        CaptchaResponseDto response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);

        if(!response.isSuccess()) model.put("alert", "Please fill captcha!");
        if(userRepo.findByEmail(user.getEmail()) != null) model.put("alert", "User with this email already exists");
        if (userRepo.findByEmail(user.getEmail()) != null || !response.isSuccess()) return "register";

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
