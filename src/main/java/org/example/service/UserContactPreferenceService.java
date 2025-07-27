package org.example.service;

import org.example.dao.UserContactPreferenceDao;
import org.example.model.UserContactPreference;

import java.util.List;

public class UserContactPreferenceService {
    private final UserContactPreferenceDao dao;

    public UserContactPreferenceService(UserContactPreferenceDao dao) {
        this.dao = dao;
    }

    public boolean update(int donorId, int methodId, boolean isEnabled, String language) {
        return dao.setPreference(donorId, methodId, isEnabled, language);
    }

    public List<UserContactPreference> getForDonor(int donorId) {
        return dao.getPreferencesByDonor(donorId);
    }
}
