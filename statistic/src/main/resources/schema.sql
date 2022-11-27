drop table if exists stats;
create table stats
(
    id              serial
        primary key,
    stats_app       varchar,
    stats_uri       varchar,
    stats_ip        varchar,
    stats_timestamp timestamp
);

alter table stats
    owner to postgres;



