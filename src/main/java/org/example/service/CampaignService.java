package org.example.service;

import org.example.dao.CampaignDao;
import org.example.model.Campaign;

import java.time.LocalDate;
import java.util.List;

public class CampaignService {
    private final CampaignDao campaignDao;

    public CampaignService(CampaignDao campaignDao) {
        this.campaignDao = campaignDao;
    }

    public boolean create(int charityId, int categoryId, String title, String description,
                          String recUrl, String ackUrl, LocalDate start, LocalDate end) {
        return campaignDao.createCampaign(charityId, categoryId, title, description,
                recUrl, ackUrl, start, end);
    }

    public List<Campaign> getAllActiveCampaigns() {
        return campaignDao.getActiveCampaigns();
    }

    public List<Campaign> getCampaignsForCharity(int charityId) {
        return campaignDao.getCampaignsByCharity(charityId);
    }
}
