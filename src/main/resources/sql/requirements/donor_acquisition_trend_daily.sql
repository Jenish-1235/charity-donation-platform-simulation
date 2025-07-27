SELECT
    DATE(created_at) AS signup_day,
    COUNT(*) AS new_donors
FROM
    donor
GROUP BY
    signup_day
ORDER BY
    signup_day ASC;
