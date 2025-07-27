package org.example.service;

import org.example.dao.CategoryDao;
import org.example.model.Category;

import java.util.List;

public class CategoryService {
    private final CategoryDao dao;

    public CategoryService(CategoryDao dao) {
        this.dao = dao;
    }

    public List<Category> getAllCategories() {
        return dao.getAll();
    }
}
