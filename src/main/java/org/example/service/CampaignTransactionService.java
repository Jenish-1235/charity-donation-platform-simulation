package org.example.service;

import org.example.dao.CampaignTransactionDao;
import org.example.model.CampaignTransaction;

import java.util.List;

public class CampaignTransactionService {
    private final CampaignTransactionDao campaignTransactionDao;

    public CampaignTransactionService(CampaignTransactionDao campaignTransactionDao) {
        this.campaignTransactionDao = campaignTransactionDao;
    }

    public boolean donateToCampaign(int donorId, int campaignId, double amount) {
        // Receipt info can be mocked initially
        String receiptStatus = "PENDING";
        String receiptUrl = "https://receipt.placeholder/" + System.currentTimeMillis();
        String ackUrl = "https://thanks.placeholder/" + System.currentTimeMillis();

        return campaignTransactionDao.insertDonation(donorId, campaignId, amount, receiptStatus, receiptUrl, ackUrl);
    }

    public List<CampaignTransaction> getDonorDonations(int donorId) {
        return campaignTransactionDao.getDonationsByDonor(donorId);
    }
}
