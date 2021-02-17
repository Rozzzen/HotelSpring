package com.zhuk.repo;

import com.zhuk.domain.application.Application;
import com.zhuk.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ApplicationRepo extends JpaRepository<Application, Long>, CrudRepository<Application, Long> {

    Page<Application> findAllByUserEquals(User user, Pageable pageable);

    Page<Application> findAll(Pageable pageable);

    @Override
    Application getOne(Long aLong);

//    @Transactional
//    @Modifying
//    @Query(value = "CREATE EVENT applicationEvent \n" +
//            "    ON SCHEDULE AT CURDATE() + INTERVAL 2 DAY\n" +
//            "    DO\n" +
//            "    DELETE rb\n" +
//            "    FROM room_bookings rb\n" +
//            "             INNER JOIN applications ua ON\n" +
//            "            ua.user_id = rb.user_id AND ua.checkin = rb.since AND\n" +
//            "            ua.checkout = rb.until AND ua.room_id = rb.room_id\n" +
//            "    WHERE ua.paid = false\n" +
//            "      AND ua.id = :id", nativeQuery = true)
//    void createEvent(Long id);
}