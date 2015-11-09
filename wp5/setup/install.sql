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
       LC_COLLATE = 'Italian_Italy.1252'
       LC_CTYPE = 'Italian_Italy.1252'
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

  

-- INSERT TEST DATA

INSERT INTO users VALUES (0, 'Test', 'test@test.com', 'test', 'admin');