package com.zhuk.controller;

import com.zhuk.domain.application.Application;
import com.zhuk.domain.room.Room;
import com.zhuk.domain.room.RoomType;
import com.zhuk.service.ApplicationService;
import com.zhuk.service.RoomService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    private final ApplicationService applicationService;
    private final RoomService roomService;

    public AdminController(ApplicationService applicationService, RoomService roomService) {
        this.applicationService = applicationService;
        this.roomService = roomService;
    }

    @GetMapping("/applications")
    public String adminApplications(Map<String, Object> model,
                                    @RequestParam(defaultValue = "id") String sort,
                                    @RequestParam(defaultValue = "0") Integer page) {
        PageRequest pageRequest;

        if (sort.contains("Desc"))
            pageRequest = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, sort.substring(0, sort.length() - 4)));
        else pageRequest = PageRequest.of(page, 5, Sort.by(Sort.Direction.ASC, sort));

        Page<Application> applicationPage = applicationService.findAll(pageRequest);

        model.put("page", applicationPage);
        model.put("sort", sort);
        model.put("url", "/admin/applications");

        return "admin_applications";
    }

    @GetMapping("/applications/rooms")
    public String selectRoom(@RequestParam String checkin,
                             @RequestParam String checkout,
                             @RequestParam Integer id,
                             @RequestParam(defaultValue = "id") String sort,
                             @RequestParam(defaultValue = "0") Integer page,
                             Map<String, Object> model) {

        LocalDate checkinDate = LocalDate.parse(checkin);
        LocalDate checkoutDate = LocalDate.parse(checkout);

        List<Room> excluded = roomService.findAllByDates(checkinDate, checkoutDate);
        PageRequest pageRequest;

        if (sort.contains("Desc"))
            pageRequest = PageRequest.of(page, 6, Sort.by(Sort.Direction.DESC, sort.substring(0, sort.length() - 4)));
        else pageRequest = PageRequest.of(page, 6, Sort.by(Sort.Direction.ASC, sort));

        Page<Room> roomPage = new PageImpl<>(roomService.updateStatus("admin", excluded, roomService.findAll(pageRequest)), pageRequest, 6);

        model.put("page", roomPage);
        model.put("id", id);
        model.put("sort", sort);
        model.put("totalElements", roomService.countAll());
        model.put("url", "/admin/applications/rooms");
        model.put("checkin", checkinDate);
        model.put("checkout", checkoutDate);

        return "rooms";
    }

    @PostMapping("/applications/rooms")
    public String insertRoom(@RequestParam Long roomId,
                             @RequestParam Long applicationId) {

        Application application = applicationService.getOne(applicationId);
        application.setRoom(roomService.findFirstById(roomId));
        applicationService.save(application);

        return "redirect:/admin/applications";
    }

    @PostMapping("/applications/delete")
    public String deleteApplication(@RequestParam Long applicationId) {
        applicationService.deleteApplicationById(applicationId);
        return "redirect:/admin/applications";
    }

    @GetMapping("/edit/rooms")
    public String editRoomPage(@RequestParam(defaultValue = "id") String sort,
                               @RequestParam(defaultValue = "0") Integer page,
                               Map<String, Object> model) {

        PageRequest pageRequest;

        if (sort.contains("Desc"))
            pageRequest = PageRequest.of(page, 6, Sort.by(Sort.Direction.DESC, sort.substring(0, sort.length() - 4)));
        else pageRequest = PageRequest.of(page, 6, Sort.by(Sort.Direction.ASC, sort));

        Page<Room> roomPage = roomService.findAll(pageRequest);

        model.put("page", roomPage);
        model.put("sort", sort);
        model.put("url", "/admin/edit/rooms");
        return "admin_rooms";
    }

    @PostMapping("/edit/rooms")
    public String editRoom(Room formRoom) {

        Room room = roomService.getOne(formRoom.getId());
        room.setCapacity(formRoom.getCapacity());
        room.setImgName(formRoom.getImgName());
        room.setPrice(formRoom.getPrice());
        room.setRoomtype(formRoom.getRoomtype());
        roomService.save(room);

        return "redirect:/admin/edit/rooms";
    }

    @PostMapping("/edit/rooms/delete")
    public String deleteRoom(@RequestParam Long roomId) {
        roomService.deleteRoomById(roomId);
        return "redirect:/admin/edit/rooms";
    }

    @GetMapping("/create/rooms")
    public String editRoomPage(Map<String, Object> model) {
        model.put("roomtypes", RoomType.values());
        return "admin_create_room";
    }

    @PostMapping("/create/rooms")
    public String editRoomPage(Room room) {
        roomService.save(room);
        return "redirect:/admin/edit/rooms";
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public String sqlError(SQLIntegrityConstraintViolationException ex,
                           RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("alert", "Cannot delete room that has been already booked");
        return "redirect:/admin/edit/rooms";
    }
}
