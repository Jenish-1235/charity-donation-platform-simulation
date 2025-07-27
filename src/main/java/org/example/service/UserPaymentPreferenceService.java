package org.example.service;

import org.example.dao.UserPaymentPreferenceDao;
import org.example.model.UserPaymentPreference;

import java.util.List;

public class UserPaymentPreferenceService {
    private final UserPaymentPreferenceDao dao;

    public UserPaymentPreferenceService(UserPaymentPreferenceDao dao) {
        this.dao = dao;
    }

    public boolean add(int donorId, int methodId, String accountLast4, String providerToken, boolean isPrimary) {
        return dao.addPaymentMethod(donorId, methodId, accountLast4, providerToken, isPrimary);
    }

    public List<UserPaymentPreference> getByDonor(int donorId) {
        return dao.getMethodsByDonor(donorId);
    }

    public UserPaymentPreference getPrimary(int donorId) {
        return dao.getPrimaryMethod(donorId);
    }
}
