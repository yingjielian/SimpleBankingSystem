SELECT
    s.name,
    SUM(CASE WHEN TIMESTAMPDIFF(MINUTE, v.start_dt, v.end_dt) < 15 THEN 1 ELSE 0 END) AS less_than_15_min,
    SUM(CASE WHEN TIMESTAMPDIFF(MINUTE, v.start_dt, v.end_dt) >= 15
        AND TIMESTAMPDIFF(MINUTE, v.start_dt, v.end_dt) < 30 THEN 1 ELSE 0 END) AS 15_to_30_min,
    SUM(CASE WHEN TIMESTAMPDIFF(MINUTE, v.start_dt, v.end_dt) >= 30 THEN 1 ELSE 0 END) AS more_than_30_min,
    COUNT(v.stream_id) AS total_viewers
FROM
    streams s
        LEFT JOIN
    visitors v ON s.id = v.stream_id
GROUP BY
    s.id, s.name
ORDER BY
    s.name ASC;