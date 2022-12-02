DROP TABLE IF EXISTS stats;

CREATE TABLE stats
(id SERIAL PRIMARY KEY,
stats_app VARCHAR,
stats_uri VARCHAR,
stats_ip VARCHAR,
stats_timestamp TIMESTAMP);

ALTER TABLE stats OWNER TO postgres;



