CREATE VIEW v_sra_last_updated AS
SELECT
    MAX(
            tbl.last_updated
        ) AS last_updated
FROM
    (
        SELECT
            MAX(updated) AS last_updated
        FROM
            t_sra_submission
        UNION
        SELECT
            MAX(updated) AS last_updated
        FROM
            t_sra_experiment
        UNION
        SELECT
            MAX(updated) AS last_updated
        FROM
            t_sra_analysis
        UNION
        SELECT
            MAX(updated) AS last_updated
        FROM
            t_sra_run
        UNION
        SELECT
            MAX(updated) AS last_updated
        FROM
            t_sra_analysis
        UNION
        SELECT
            MAX(updated) AS last_updated
        FROM
            t_sra_study
        UNION
        SELECT
            MAX(updated) AS last_updated
        FROM
            t_sra_analysis
        UNION
        SELECT
            MAX(updated) AS last_updated
        FROM
            t_sra_sample
    ) AS tbl
;
