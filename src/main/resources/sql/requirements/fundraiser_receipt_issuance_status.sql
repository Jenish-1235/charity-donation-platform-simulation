SELECT
    f.title,
    ft.receipt_status,
    COUNT(*) AS count
FROM
    fundraiser_transactions ft
        JOIN
    fundraiser f ON ft.fundraiser_id = f.fundraiser_id
GROUP BY
    f.title, ft.receipt_status
ORDER BY
    f.title;
