package com.campusfin.repository;

import com.campusfin.model.StudentFinancialProfile;
import com.campusfin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentFinancialProfileRepository
        extends JpaRepository<StudentFinancialProfile, Long> {

    Optional<StudentFinancialProfile> findByUser(User user);
}