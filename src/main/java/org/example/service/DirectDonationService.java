package org.example.service;


import org.example.dao.DirectDonationDao;
import org.example.model.DirectDonation;

import java.math.BigDecimal;
import java.sql.SQLException;

public class DirectDonationService {
    private final DirectDonationDao dao;

    public DirectDonationService(DirectDonationDao dao) {
        this.dao = dao;
    }

    public void donate(int donorId, int charityId, BigDecimal amount) throws SQLException {
        dao.insertDonation(donorId, charityId, amount.doubleValue(), "PENDING", "", "");

        // TODO: Push to donor_donation_summary for type = "DIRECT"
    }
}
