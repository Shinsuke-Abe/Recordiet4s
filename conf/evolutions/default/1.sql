# User schema

# --- !Ups

CREATE SEQUENCE user_id_seq;
CREATE TABLE recordiet_user(
  id integer NOT NULL DEFAULT nextval('user_id_seq'),
  mail_address varchar(255),
  password varchar(255),
  display_name varchar(255)
);

# --- !Downs

DROP TABLE recordiet_user;
DROP SEQUENCE user_id_seq;