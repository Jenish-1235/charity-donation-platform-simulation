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
        DirectDonation d = new DirectDonation();
        d.setDonorId(donorId);
        d.setCharityId(charityId);
        d.setAmount(amount);
        d.setReceiptStatus("PENDING");
        dao.create(d);

        // TODO: Push to donor_donation_summary for type = "DIRECT"
    }
}
