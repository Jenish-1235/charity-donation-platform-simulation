package org.example.service;

import org.example.dao.CharityCategoryDao;
import org.example.model.CharityCategory;

import java.util.List;

public class CharityCategoryService {
    private final CharityCategoryDao dao;

    public CharityCategoryService(CharityCategoryDao dao) {
        this.dao = dao;
    }

    public List<CharityCategory> getAllCategories() {
        return dao.getAll();
    }
}
