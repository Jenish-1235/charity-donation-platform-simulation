SELECT
    dd.charity_id,
    c.name AS charity_name,
    dd.receipt_status,
    COUNT(*) AS count
FROM
    direct_donations dd
        JOIN
    charity c ON dd.charity_id = c.charity_id
GROUP BY
    dd.charity_id, dd.receipt_status
ORDER BY
    dd.charity_id, dd.receipt_status;
