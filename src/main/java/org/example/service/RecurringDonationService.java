package org.example.service;

import org.example.dao.RecurringDonationDao;
import org.example.model.RecurringDonation;

import java.time.LocalDate;
import java.util.List;

public class RecurringDonationService {
    private final RecurringDonationDao dao;

    public RecurringDonationService(RecurringDonationDao dao) {
        this.dao = dao;
    }

    public boolean create(int donorId, int entityTypeId, int entityId, int charityId,
                          String recurringRate, double amount,
                          LocalDate startDate, LocalDate endDate,
                          int paymentMethodId) {
        return dao.createRecurringDonation(donorId, entityTypeId, entityId, charityId,
                recurringRate, amount, startDate, endDate, paymentMethodId);
    }

    public List<RecurringDonation> getActiveForDonor(int donorId) {
        return dao.getActiveRecurringByDonor(donorId);
    }
}
