package com.hcm.tms.repository;

import com.hcm.tms.entity.Category;
import com.hcm.tms.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    Optional<Category> findByCategoryName(String name);
}
