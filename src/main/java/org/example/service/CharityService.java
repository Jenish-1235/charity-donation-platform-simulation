package org.example.service;

import org.example.dao.CharityDao;
import org.example.model.Charity;

public class CharityService {
    private final CharityDao charityDao;

    public CharityService(CharityDao charityDao) {
        this.charityDao = charityDao;
    }

    public boolean register(String name, String email, String password, String description,
                            String websiteUrl, String ackUrl, String receiptUrl, int categoryId) {
        return charityDao.registerCharity(name, email, password, description,
                websiteUrl, ackUrl, receiptUrl, categoryId);
    }

    public Charity login(String email, String password) {
        return charityDao.getCharityByEmailAndPassword(email, password);
    }

    public Charity getById(int id) {
        return charityDao.getCharityById(id);
    }
}
