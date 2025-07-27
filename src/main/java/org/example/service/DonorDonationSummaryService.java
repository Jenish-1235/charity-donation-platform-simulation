package org.example.service;

import org.example.dao.DonorDonationSummaryDao;
import org.example.model.DonorDonationSummary;

import java.util.List;

public class DonorDonationSummaryService {
    private final DonorDonationSummaryDao dao;

    public DonorDonationSummaryService(DonorDonationSummaryDao dao) {
        this.dao = dao;
    }

    public boolean updateSummary(int donorId, String donationType, double amount) {
        return dao.upsertSummary(donorId, donationType, amount);
    }

    public List<DonorDonationSummary> getByType(String donationType) {
        return dao.getByType(donationType);
    }

    public List<DonorDonationSummary> getAll() {
        return dao.getAll();
    }
}
