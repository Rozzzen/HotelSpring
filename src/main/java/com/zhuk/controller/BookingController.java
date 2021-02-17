package com.zhuk.controller;

import com.zhuk.domain.application.Application;
import com.zhuk.domain.room.RoomType;
import com.zhuk.domain.user.User;
import com.zhuk.service.ApplicationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Map;

@Controller
@PreAuthorize("hasAuthority('USER')")
@RequestMapping("/booking")
public class BookingController {

    private ApplicationService applicationService;

    public BookingController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping
    public String booking(Map<String, Object> model) {
        model.put("roomtypes", RoomType.values());
        return "booking";
    }

    @PostMapping
    public String booking(Application application,
                          RedirectAttributes redirectAttributes) {

        if(application.getCheckin() != null && application.getCheckout() != null) {
            if(application.getCheckin().isBefore(LocalDate.now())) {
                redirectAttributes.addFlashAttribute("alert", "Checkin date is incorrect");
                return "redirect:/booking";
            }
        }
        application.setUser((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        applicationService.save(application);
        return "/";
    }
}
