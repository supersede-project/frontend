-- Table: profiles_labels

-- DROP TABLE profiles_labels;

CREATE TABLE profiles_labels
(
  profile_id bigint,
  lang text NOT NULL,
  label text NOT NULL,
  profile_label_id bigint NOT NULL,
  CONSTRAINT profiles_labels_pk PRIMARY KEY (profile_label_id),
  CONSTRAINT profiles_profiles_labels_fk FOREIGN KEY (profile_id)
      REFERENCES profiles (profile_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT profiles_labels_profile_id_lang_key UNIQUE (profile_id, lang)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE profiles_labels
  OWNER TO "testDB";

-- Index: fki_profiles_profiles_labels_fk

-- DROP INDEX fki_profiles_profiles_labels_fk;

CREATE INDEX fki_profiles_profiles_labels_fk
  ON profiles_labels
  USING btree
  (profile_id);

