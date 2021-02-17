package com.zhuk.controller;

import com.zhuk.domain.room.Booking;
import com.zhuk.domain.room.Room;
import com.zhuk.domain.user.User;
import com.zhuk.service.BookingService;
import com.zhuk.service.RoomService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static java.time.temporal.ChronoUnit.DAYS;

@Controller
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;
    private final BookingService bookingService;

    public RoomController(RoomService roomService, BookingService bookingService) {
        this.roomService = roomService;
        this.bookingService = bookingService;
    }

    @GetMapping
    public String rooms(@RequestParam String checkin,
                        @RequestParam String checkout,
                        @RequestParam(defaultValue = "id") String sort,
                        @RequestParam(defaultValue = "0") Integer page,
                        RedirectAttributes redirectAttributes,
                        Principal principal,
                        Map<String, Object> model) {

        String userEmail = "NONE";
        if(principal != null) userEmail = principal.getName();

        LocalDate checkinDate = LocalDate.parse(checkin);
        LocalDate checkoutDate = LocalDate.parse(checkout);

        if(checkinDate.isBefore(LocalDate.now())) {
            redirectAttributes.addFlashAttribute("alert", "Checkin date is incorrect");
            return "redirect:/";
        }

        List<Room> excluded = roomService.findAllByDates(checkinDate, checkoutDate);
        PageRequest pageRequest;

        if(sort.contains("Desc")) pageRequest = PageRequest.of(page, 6, Sort.by(Sort.Direction.DESC, sort.substring(0, sort.length() - 4)));
        else pageRequest = PageRequest.of(page, 6, Sort.by(Sort.Direction.ASC, sort));

        Page<Room> roomPage = new PageImpl<>(roomService.updateStatus(userEmail, excluded, roomService.findAll(pageRequest)), pageRequest, 6);

        model.put("page", roomPage);
        model.put("sort", sort);
        model.put("totalElements", roomService.countAll());
        model.put("url", "/rooms");
        model.put("checkin", checkinDate);
        model.put("checkout", checkoutDate);

        return "rooms";
    }

    @PostMapping("/payment")
    @PreAuthorize("hasAuthority('USER')")
    public String confirmationPage(@RequestParam Long roomId,
                                   @RequestParam String checkin,
                                   @RequestParam String checkout,
                                   Map<String, Object> model) {

        Room room = roomService.findFirstById(roomId);

        LocalDate checkinDate = LocalDate.parse(checkin);
        LocalDate checkoutDate = LocalDate.parse(checkout);

        model.put("days", DAYS.between(checkinDate, checkoutDate));
        model.put("room", room);
        model.put("checkin", checkinDate);
        model.put("checkout", checkoutDate);
        model.put("url", "/rooms/payment");
        return "confirmation";
    }

    @PostMapping("/payment/confirm")
    @PreAuthorize("hasAuthority('USER')")
    public String confirmApplication(@RequestParam Long roomId,
                                     @RequestParam String checkin,
                                     @RequestParam String checkout) {

        LocalDate checkinDate = LocalDate.parse(checkin);
        LocalDate checkoutDate = LocalDate.parse(checkout);

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Room room = roomService.findFirstById(roomId);
        Booking booking = new Booking();
        booking.setRoom(room);
        booking.setUser(user);
        booking.setUntil(checkoutDate);
        booking.setSince(checkinDate);
        bookingService.saveBooking(booking);

        return "redirect:/";
    }

}
