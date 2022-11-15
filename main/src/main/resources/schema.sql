drop table if exists request;
drop table if exists compilation;
drop table if exists event;
drop table if exists "user";
drop table if exists category;
create sequence if not exists event_new_column_seq
    as integer;

alter sequence event_new_column_seq owner to postgres;

create sequence if not exists "limit"
    minvalue 0;

alter sequence "limit" owner to postgres;

create table category
(
    id            serial
        primary key
        unique,
    category_name varchar
);

alter table category
    owner to postgres;

create table "user"
(
    id         serial
        primary key
        unique,
    user_email varchar not null,
    user_name  varchar
);

alter table "user"
    owner to postgres;

create table event
(
    event_annotation         varchar                                                   not null,
    event_category_id        integer                                                   not null
        constraint event_category_id_fk
            references category,
    event_confirmed_requests integer,
    event_date               timestamp                                                 not null,
    id                       integer default nextval('event_new_column_seq'::regclass) not null
        primary key,
    event_initiator_id       integer                                                   not null
        constraint event_user_id_fk
            references "user",
    event_paid               boolean,
    event_title              varchar                                                   not null,
    event_views              bigint,
    event_created_on         timestamp                                                 not null,
    event_description        varchar                                                   not null,
    event_participant_limit  integer                                                   not null,
    event_request_moderation boolean,
    event_state              varchar                                                   not null,
    event_location_lon       double precision                                          not null,
    event_location_lat       double precision                                          not null,
    event_published_on       timestamp
);

alter table event
    owner to postgres;

alter sequence event_new_column_seq owned by event.id;

create table compilation
(
    compilation_event_id integer
        constraint compilation_event_id_fk
            references event,
    id                   serial
        primary key
        unique,
    compilation_pinned   boolean,
    compilation_title    varchar
);

alter table compilation
    owner to postgres;

create table request
(
    request_created      timestamp not null,
    request_event_id     integer
        constraint request_event_id_fk
            references event
            on update cascade on delete cascade,
    request_requester_id integer
        constraint request_user_id_fk
            references "user"
            on update cascade on delete cascade,
    request_status       varchar,
    id                   serial
        primary key
        unique
);

alter table request
    owner to postgres;

