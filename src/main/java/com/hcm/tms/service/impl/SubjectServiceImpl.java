package com.hcm.tms.service.impl;

import com.hcm.tms.entity.Subject;
import com.hcm.tms.entity.User;
import com.hcm.tms.repository.SubjectRepository;
import com.hcm.tms.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public Page<Subject> getAll(String title, Integer pageNo) {

        int pageSize = 4;

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.asc("subjectId")));

        //return subjectRepository.findAllByIsEnable(paging,true);
        if(title.length() <1)
            return subjectRepository.findAllByIsEnable(paging, true);
        else
            return subjectRepository.findByTitleAndIsEnable(paging,title, true);
    }

    @Override
    public List<Subject> getSubjects() {
        List<Subject> subjects = (List<Subject>) subjectRepository.findAll();
        return subjects;
    }

    @Override
    public void save(Subject subject) {
        subjectRepository.save(subject);
    }

    @Override
    public Optional<Subject> findByTitle(String title) {

        Optional<Subject> subject = subjectRepository.findByTitle(title);
        return subject;
    }

    @Override
    public Optional<Subject> findById(Long id) {
        return subjectRepository.findById(id);
    }

    @Override
    public List<Integer> getPages(Page<Subject> pages) {
        Long totalPages = Math.round((double)pages.getTotalPages()) ;
        List<Integer> listPageNumbers = new ArrayList<>();

        if(totalPages > 0) {
            listPageNumbers = IntStream.rangeClosed(1, Math.toIntExact(totalPages)).boxed().collect(Collectors.toList());
        }
        return listPageNumbers;
    }

    @Override
    public void deleteSubject(Long id) {
        Optional<Subject> subject = subjectRepository.findById(id);
        subject.ifPresent( obj -> {
            obj.setEnable(false);
            subjectRepository.save(obj);
        });
    }

    @Override
    public Page<Subject> findAll(Integer page, Integer pageSize) {
        if(page == null) {
            page = 0;
        } else if(page <= 1) {
            page = 0;
        } else {
            page = page - 1;
        }
        if(pageSize == null || pageSize <= 0) {
            pageSize = 5;
        };
        Pageable pageable = PageRequest.of(page,pageSize);
        Page<Subject> subjects = subjectRepository.findAllByIsEnable(pageable,true);
        return subjects;
    }

    @Override
    public boolean fieldValueExists(Object value, String fieldName) throws UnsupportedOperationException {

        Subject subject = (Subject) value;

        if (!fieldName.equals("title")) {
            throw new UnsupportedOperationException("Field name not supported");
        }

        if (value == null) {
            return false;
        }

        Optional<Subject> subjectByTitle = subjectRepository.findByTitle(subject.getTitle());
        boolean a = subjectByTitle.isPresent() && !subjectByTitle.get().getSubjectId().equals(subject.getSubjectId());
        return (a);
    }
}