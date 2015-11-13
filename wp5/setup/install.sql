-- Role: testDB

-- DROP ROLE "testDB";

CREATE ROLE "testDB" LOGIN
  ENCRYPTED PASSWORD 'md553ffb125586759e24e875d14486a5b1b'
  NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE NOREPLICATION;

  
  
-- Database: "testDB"

-- DROP DATABASE "testDB";

CREATE DATABASE "testDB"
  WITH OWNER = "testDB"
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'en_US.UTF-8'
       LC_CTYPE = 'en_US.UTF-8'
       CONNECTION LIMIT = -1;

       
       
-- Table: users

-- DROP TABLE users;

CREATE TABLE users
(
  user_id bigint NOT NULL,
  name text NOT NULL,
  email text NOT NULL,
  password text NOT NULL,
  role text,
  CONSTRAINT users_primary_key PRIMARY KEY (user_id),
  CONSTRAINT unique_email UNIQUE (email)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE users
  OWNER TO "testDB";

  
  
-- Table: notifications

-- DROP TABLE notifications;

CREATE TABLE notifications
(
  notification_id bigint NOT NULL,
  message text NOT NULL,
  user_id bigint,
  read boolean NOT NULL,
  email_sent boolean NOT NULL,
  creation_time timestamp with time zone NOT NULL,
  CONSTRAINT notifications_primary_key PRIMARY KEY (notification_id),
  CONSTRAINT users_foreign_key FOREIGN KEY (user_id)
      REFERENCES users (user_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE notifications
  OWNER TO "testDB";

-- Index: fki_users_foreign_key

-- DROP INDEX fki_users_foreign_key;

CREATE INDEX fki_users_foreign_key
  ON notifications
  USING btree
  (user_id);

  

-- INSERT TEST DATA

INSERT INTO users VALUES (0, 'Test', 'test@test.com', 'test', 'admin');
