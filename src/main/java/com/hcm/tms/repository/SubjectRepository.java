package com.hcm.tms.repository;

import com.hcm.tms.entity.Role;
import com.hcm.tms.entity.Subject;
import com.hcm.tms.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends PagingAndSortingRepository<Subject,Long> {

    //Page<Subject> findAll(Pageable paging);
    Optional<Subject> findByTitle(String title);
    Page<Subject> findAllByIsEnable(Pageable paging, boolean isEnable);

    Page<Subject> findByTitleAndIsEnable(Pageable paging, String title, boolean isEnable);
}