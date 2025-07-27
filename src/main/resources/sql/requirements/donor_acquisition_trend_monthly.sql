SELECT
    DATE_FORMAT(created_at, '%Y-%m') AS month,
    COUNT(*) AS new_donors
FROM
    donor
GROUP BY
    month
ORDER BY
    month ASC;
