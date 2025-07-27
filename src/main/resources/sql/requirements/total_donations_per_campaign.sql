    SELECT
        c.campaign_id,
        c.title,
        cc.total_donations,
        cc.donor_count
    FROM
        campaign c
            JOIN
        campaign_cache cc ON c.campaign_id = cc.campaign_id
    ORDER BY
        cc.total_donations DESC;
