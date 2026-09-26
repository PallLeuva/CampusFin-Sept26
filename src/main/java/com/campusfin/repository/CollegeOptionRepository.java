package com.campusfin.repository;

import com.campusfin.model.CollegeOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollegeOptionRepository
        extends JpaRepository<CollegeOption, Long> {
}