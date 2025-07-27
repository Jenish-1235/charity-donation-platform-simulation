package org.example.service;

import org.example.dao.FundraiserTransactionDao;
import org.example.model.FundraiserTransaction;

import java.util.List;

public class FundraiserTransactionService {
    private final FundraiserTransactionDao fundraiserTransactionDao;

    public FundraiserTransactionService(FundraiserTransactionDao dao) {
        this.fundraiserTransactionDao = dao;
    }

    public boolean donateToFundraiser(int donorId, int fundraiserId, double amount) {
        String receiptStatus = "PENDING";
        String receiptUrl = "https://receipt.fundraiser/" + System.currentTimeMillis();
        String ackUrl = "https://thanks.fundraiser/" + System.currentTimeMillis();

        return fundraiserTransactionDao.insertDonation(donorId, fundraiserId, amount, receiptStatus, receiptUrl, ackUrl);
    }

    public List<FundraiserTransaction> getDonationsByDonor(int donorId) {
        return fundraiserTransactionDao.getDonationsByDonor(donorId);
    }
}
