SELECT
    referrer_source,
    COUNT(*) AS donor_count
FROM
    donor
GROUP BY
    referrer_source
ORDER BY
    donor_count DESC;
