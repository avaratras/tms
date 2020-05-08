package com.hcm.tms.service;

import com.hcm.tms.entity.Subject;
import com.hcm.tms.entity.User;
import com.hcm.tms.repository.SubjectRepository;
import com.hcm.tms.validation.FieldValueExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface SubjectService extends FieldValueExists {

    Page<Subject> getAll(String title, Integer pageNo);

    List<Subject> getSubjects();

    void save(Subject subject);

    Optional<Subject> findByTitle(String title);

    Optional<Subject> findById(Long id);

    List<Integer> getPages(Page<Subject> pages);

    void deleteSubject(Long id);

    Page<Subject> findAll(Integer page, Integer pageSize);
}