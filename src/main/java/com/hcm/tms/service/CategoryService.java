package com.hcm.tms.service;

import com.hcm.tms.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> getAll();
    void addCategory(Category category);
    Optional<Category> getCategoryById(Long id);
    Optional<Category> getCategoryByName(String name);
}
