package com.zhuk.service;

import com.zhuk.domain.application.Application;
import com.zhuk.domain.user.User;
import com.zhuk.repo.ApplicationRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Random;

@Service
public class ApplicationService {

    @PersistenceContext
    EntityManager entityManager;

    private final ApplicationRepo applicationRepo;

    public ApplicationService(ApplicationRepo applicationRepo) {
        this.applicationRepo = applicationRepo;
    }

    public void save(Application s) {
        applicationRepo.save(s);
    }

    public Page<Application> findAllByUserEquals(User user, Pageable pageable) {
        return applicationRepo.findAllByUserEquals(user, pageable);
    }

    public Page<Application> findAll(Pageable pageable) {
        return applicationRepo.findAll(pageable);
    }

    public Application getOne(Long aLong) {
        return applicationRepo.getOne(aLong);
    }

    public void deleteApplicationById(Long id) {
        applicationRepo.deleteById(id);
    }

    @Transactional
    public void createPaymentEvent(Long id) {

        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int length = 7;

        for(int i = 0; i < length; i++) {
            int index = random.nextInt(alphabet.length());
            sb.append(alphabet.charAt(index));
        }

        entityManager.joinTransaction();
        entityManager.createNativeQuery("CREATE EVENT " + sb.toString() +
                "    ON SCHEDULE AT CURDATE() + INTERVAL 2 DAY" +
                "    DO" +
                "    DELETE rb" +
                "    FROM room_bookings rb" +
                "             INNER JOIN applications ua ON" +
                "            ua.user_id = rb.user_id AND ua.checkin = rb.since AND" +
                "            ua.checkout = rb.until AND ua.room_id = rb.room_id" +
                "    WHERE ua.paid = false" +
                "      AND ua.id = ?").setParameter(1, id).executeUpdate();
    }
}