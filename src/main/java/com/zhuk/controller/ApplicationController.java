package com.zhuk.controller;

import com.zhuk.domain.application.Application;
import com.zhuk.domain.room.Booking;
import com.zhuk.domain.room.Room;
import com.zhuk.domain.user.User;
import com.zhuk.service.ApplicationService;
import com.zhuk.service.BookingService;
import com.zhuk.service.RoomService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Map;

import static java.time.temporal.ChronoUnit.DAYS;

@Controller
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService applicationService;
    private final RoomService roomService;
    private final BookingService bookingService;

    public ApplicationController(ApplicationService applicationService, RoomService roomService, BookingService bookingService) {
        this.applicationService = applicationService;
        this.roomService = roomService;
        this.bookingService = bookingService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    public String userApplications(Map<String, Object> model,
                                   @RequestParam(defaultValue = "id") String sort,
                                   @RequestParam(defaultValue = "0") Integer page)
    {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        PageRequest pageRequest;

        if(sort.contains("Desc")) pageRequest = PageRequest.of(page, 4, Sort.by(Sort.Direction.DESC, sort.substring(0, sort.length() - 4)));
        else pageRequest = PageRequest.of(page, 4, Sort.by(Sort.Direction.ASC, sort));

        Page<Application> applicationPage = applicationService.findAllByUserEquals(user, pageRequest);

        model.put("page", applicationPage);
        model.put("sort", sort);
        model.put("url", "/applications");

        return "applications";
    }

    @PostMapping("/reject")
    @PreAuthorize("hasAuthority('USER')")
    public String rejectRoom(@RequestParam Long applicationId) {
        Application application = applicationService.getOne(applicationId);
        application.setRoom(null);
        applicationService.save(application);
        return "redirect:/applications";
    }

    @PostMapping("/confirm")
    @PreAuthorize("hasAuthority('USER')")
    public String confirmRoom(@RequestParam Long applicationId) {
        Application application = applicationService.getOne(applicationId);
        application.setBooked(true);
        applicationService.save(application);

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Booking booking = new Booking();
        booking.setRoom(application.getRoom());
        booking.setSince(application.getCheckin());
        booking.setUntil(application.getCheckout());
        booking.setUser(user);
        bookingService.saveBooking(booking);
        applicationService.createPaymentEvent(applicationId);
        return "redirect:/applications";
    }

    @PostMapping("/payment")
    @PreAuthorize("hasAuthority('USER')")
    public String confirmationPage(@RequestParam Long applicationId,
                                     Map<String, Object> model) {

        Application application = applicationService.getOne(applicationId);
        Room room = application.getRoom();

        LocalDate checkin = application.getCheckin();
        LocalDate checkout = application.getCheckout();

        model.put("days", DAYS.between(checkin, checkout));
        model.put("room", room);
        model.put("checkin", checkin);
        model.put("checkout", checkout);
        model.put("applicationId", applicationId);
        model.put("url", "/applications/payment");
        return "confirmation";
    }

    @PostMapping("/payment/confirm")
    @PreAuthorize("hasAuthority('USER')")
    public String confirmApplication(@RequestParam Long applicationId) {

        Application application = applicationService.getOne(applicationId);
        application.setPaid(true);
        applicationService.save(application);

        return "redirect:/applications";
    }
}
