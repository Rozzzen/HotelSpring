package com.zhuk.service;

import com.zhuk.domain.application.Application;
import com.zhuk.domain.user.User;
import com.zhuk.repo.ApplicationRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ApplicationService {

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

//    public void createPaymentEvent(Long id) {
//        applicationRepo.createEvent(id);
//    }
}