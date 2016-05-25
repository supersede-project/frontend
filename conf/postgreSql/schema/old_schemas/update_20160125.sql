-- Table: users_gadgets

-- DROP TABLE users_gadgets;

CREATE TABLE users_gadgets
(
  gadget_id bigint NOT NULL,
  user_id bigint NOT NULL,
  application_name text NOT NULL,
  gadget_name text NOT NULL,
  size text,
  CONSTRAINT users_gadgets_primary_key PRIMARY KEY (gadget_id, user_id),
  CONSTRAINT users_gadgets_users_fk FOREIGN KEY (user_id)
      REFERENCES users (user_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE users_gadgets
  OWNER TO "testDB";

-- Index: fki_users_gadgets_users_fk

-- DROP INDEX fki_users_gadgets_users_fk;

CREATE INDEX fki_users_gadgets_users_fk
  ON users_gadgets
  USING btree
  (user_id);
