drop table if exists compilation_event;
drop table if exists request;
drop table if exists compilation;
drop table if exists event;
drop table if exists users;
drop table if exists category;
create sequence if not exists limits
    minvalue 0;

alter sequence limits owner to postgres;

create sequence event_new_column_seq
    as integer;

alter sequence event_new_column_seq owner to postgres;

create table category
(
    id            serial
        primary key,
    category_name varchar
        unique
);

alter table category
    owner to postgres;

create table users
(
    id         serial
        primary key,
    user_email varchar not null,
    user_name  varchar
        unique
);

alter table users
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
            references users,
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
    event_published_on       timestamp,
    event_only_available     boolean
);

alter table event
    owner to postgres;

alter sequence event_new_column_seq owned by event.id;

create table compilation
(
    id                 serial
        primary key,
    compilation_pinned boolean,
    compilation_title  varchar
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
            references users
            on update cascade on delete cascade,
    request_status       varchar,
    id                   serial
        primary key
);

alter table request
    owner to postgres;

create table compilation_event
(
    id             serial
        primary key,
    compilation_id integer
        constraint compilation_event_compilation_id_fk
            references compilation,
    event_id       integer
        constraint compilation_event_event_id_fk
            references event
);

alter table compilation_event
    owner to postgres;