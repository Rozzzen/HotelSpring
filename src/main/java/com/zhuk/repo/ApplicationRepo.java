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
}