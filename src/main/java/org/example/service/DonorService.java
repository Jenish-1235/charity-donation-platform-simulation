package org.example.service;

import org.example.dao.DonorDao;
import org.example.model.Donor;

public class DonorService {
    private final DonorDao donorDao;

    public DonorService(DonorDao donorDao) {
        this.donorDao = donorDao;
    }

    public boolean register(String name, String email, String password, String city, String state,
                            String country, int age, String gender, String incomeRange, String referrerSource) {
        return donorDao.registerDonor(name, email, password, city, state, country, age, gender, incomeRange, referrerSource);
    }

    public Donor login(String email, String password) {
        return donorDao.getDonorByEmailAndPassword(email, password);
    }

    public Donor getById(int id) {
        return donorDao.getDonorById(id);
    }
}
