SELECT
    f.fundraiser_id,
    f.title,
    f.start_date,
    f.end_date,
    f.goal_amount,
    fc.total_amount AS raised_amount,
    ROUND(fc.total_amount / f.goal_amount * 100, 2) AS progress_percent
FROM
    fundraiser f
        JOIN
    fundraiser_cache fc ON f.fundraiser_id = fc.fundraiser_id
WHERE
    f.is_active = TRUE
  AND f.goal_amount IS NOT NULL
  AND fc.total_amount >= 0.8 * f.goal_amount
ORDER BY
    progress_percent DESC;
