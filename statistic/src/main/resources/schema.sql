drop table if exists stats;
create table stats
(
    id              integer not null,
    stats_app       varchar,
    stats_uri       varchar,
    stats_ip        varchar,
    stats_timestamp timestamp
);

alter table stats
    owner to postgres;

