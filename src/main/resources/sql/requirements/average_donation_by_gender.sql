SELECT
    d.gender,
    s.donation_type,
    AVG(s.total_amount) AS avg_donation,
    SUM(s.total_amount) AS total_donation,
    COUNT(*) AS donor_count
FROM
    donor_donation_summary s
        JOIN
    donor d ON d.id = s.donor_id
GROUP BY
    d.gender, s.donation_type
ORDER BY
    d.gender, s.donation_type;
