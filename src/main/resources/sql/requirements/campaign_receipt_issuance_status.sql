SELECT
    c.title,
    ct.receipt_status,
    COUNT(*) AS count
FROM
    campaign_transactions ct
JOIN
    campaign c ON ct.campaign_id = c.campaign_id
GROUP BY
    c.title, ct.receipt_status
ORDER BY
    c.title;
