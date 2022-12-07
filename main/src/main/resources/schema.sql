DROP TABLE IF EXISTS compilation_event;
DROP TABLE IF EXISTS request;
DROP TABLE IF EXISTS compilation;
DROP TABLE IF EXISTS stats;
DROP TABLE IF EXISTS comment_event;
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS event;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS users;
CREATE SEQUENCE IF NOT EXISTS limits MINVALUE 0;

ALTER SEQUENCE limits OWNER TO postgres;

CREATE SEQUENCE event_new_column_seq AS INTEGER;

ALTER SEQUENCE event_new_column_seq OWNER TO postgres;

CREATE TABLE category
(id SERIAL PRIMARY KEY, category_name VARCHAR unique);

ALTER TABLE category OWNER TO postgres;

CREATE TABLE users
(id SERIAL PRIMARY KEY, user_email VARCHAR NOT NULL, user_name VARCHAR unique);

ALTER TABLE users OWNER TO postgres;

CREATE TABLE event
(event_annotation VARCHAR NOT NULL,
event_category_id INTEGER NOT NULL CONSTRAINT event_category_id_fk REFERENCES category,
event_confirmed_requests INTEGER,
event_date TIMESTAMP NOT NULL,
id INTEGER DEFAULT NEXTVAL('event_new_column_seq'::REGCLASS) NOT NULL PRIMARY KEY,
event_initiator_id INTEGER NOT NULL CONSTRAINT event_user_id_fk REFERENCES users,
event_paid BOOLEAN,
event_title VARCHAR NOT NULL,
event_views  bigint,
event_CREATEd_on TIMESTAMP NOT NULL,
event_description VARCHAR NOT NULL,
event_participant_limit  INTEGER NOT NULL,
event_request_moderation BOOLEAN,
event_state  VARCHAR NOT NULL,
event_location_lon DOUBLE PRECISION  NOT NULL,
event_location_lat DOUBLE PRECISION  NOT NULL,
event_published_on TIMESTAMP,
event_only_available BOOLEAN
);

ALTER TABLE event OWNER TO postgres;

ALTER SEQUENCE event_new_column_seq owned by event.id;

CREATE TABLE compilation
(id SERIAL PRIMARY KEY, compilation_pinned BOOLEAN, compilation_title  VARCHAR);

ALTER TABLE compilation OWNER TO postgres;

CREATE TABLE request
(request_CREATEd  TIMESTAMP NOT NULL,
request_event_id INTEGER CONSTRAINT request_event_id_fk REFERENCES event ON UPDATE CASCADE ON DELETE CASCADE,
request_requester_id INTEGER CONSTRAINT request_user_id_fk REFERENCES users ON UPDATE CASCADE ON DELETE CASCADE,
request_status VARCHAR,
id SERIAL PRIMARY KEY);

ALTER TABLE request OWNER TO postgres;

CREATE TABLE compilation_event
(id SERIAL PRIMARY KEY,
compilation_id INTEGER CONSTRAINT compilation_event_compilation_id_fk REFERENCES compilation,
event_id INTEGER CONSTRAINT compilation_event_event_id_fk REFERENCES event);

ALTER TABLE compilation_event OWNER TO postgres;

CREATE TABLE comment
(id SERIAL PRIMARY KEY UNIQUE,
comment_status VARCHAR NOT NULL ,
comment_text VARCHAR(400) NOT NULL,
comment_published_on TIMESTAMP,
comment_author_id INTEGER NOT NULL
CONSTRAINT comment_users_id_fk
REFERENCES users
ON DELETE CASCADE,
comment_event_id INTEGER
CONSTRAINT comment_event_id_fk
REFERENCES event
ON DELETE CASCADE );

ALTER TABLE comment OWNER TO postgres;

CREATE TABLE comment_event
(id SERIAL PRIMARY KEY UNIQUE ,
event_id INTEGER CONSTRAINT comment_event_event_id_fk
REFERENCES event
ON DELETE CASCADE ,
comment_id INTEGER
CONSTRAINT comment_event_comment_id_fk
REFERENCES comment
ON DELETE CASCADE);

ALTER TABLE comment_event
OWNER TO postgres;
