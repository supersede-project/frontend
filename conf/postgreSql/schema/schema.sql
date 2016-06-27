--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = public, pg_catalog;

ALTER TABLE ONLY public.users_gadgets DROP CONSTRAINT users_gadgets_users_fk;
ALTER TABLE ONLY public.users_profiles DROP CONSTRAINT users_foreign_key;
ALTER TABLE ONLY public.notifications DROP CONSTRAINT users_foreign_key;
ALTER TABLE ONLY public.users_dashboard DROP CONSTRAINT users_dashboard_users_fk;
ALTER TABLE ONLY public.profiles_labels DROP CONSTRAINT profiles_profiles_labels_fk;
ALTER TABLE ONLY public.users_profiles DROP CONSTRAINT profiles_foreign_key;
DROP INDEX public.fki_users_gadgets_users_fk;
DROP INDEX public.fki_users_foreign_key;
DROP INDEX public.fki_profiles_profiles_labels_fk;
ALTER TABLE ONLY public.users_profiles DROP CONSTRAINT users_profiles_primary_key;
ALTER TABLE ONLY public.users DROP CONSTRAINT users_primary_key;
ALTER TABLE ONLY public.users_gadgets DROP CONSTRAINT users_gadgets_primary_key;
ALTER TABLE ONLY public.users_dashboard DROP CONSTRAINT users_dashboard_primary_key;
ALTER TABLE ONLY public.users DROP CONSTRAINT unique_username;
ALTER TABLE ONLY public.profiles DROP CONSTRAINT unique_name;
ALTER TABLE ONLY public.users DROP CONSTRAINT unique_email;
ALTER TABLE ONLY public.profiles DROP CONSTRAINT profiles_primary_key;
ALTER TABLE ONLY public.profiles_labels DROP CONSTRAINT profiles_labels_profile_id_lang_key;
ALTER TABLE ONLY public.profiles_labels DROP CONSTRAINT profiles_labels_pk;
ALTER TABLE ONLY public.notifications DROP CONSTRAINT notifications_primary_key;
DROP TABLE public.users_profiles;
DROP TABLE public.users_gadgets;
DROP TABLE public.users_dashboard;
DROP TABLE public.users;
DROP TABLE public.profiles_labels;
DROP TABLE public.profiles;
DROP TABLE public.notifications;
DROP SEQUENCE public.hibernate_sequence;
DROP EXTENSION plpgsql;
DROP SCHEMA public;
--
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO postgres;

--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'standard public schema';


--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; 
--

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: notifications; Type: TABLE; Schema: public;  Tablespace: 
--

CREATE TABLE notifications (
    notification_id bigint NOT NULL,
    message text NOT NULL,
    user_id bigint,
    read boolean NOT NULL,
    email_sent boolean NOT NULL,
    creation_time timestamp with time zone NOT NULL,
    link text
);


--
-- Name: profiles; Type: TABLE; Schema: public;  Tablespace: 
--

CREATE TABLE profiles (
    profile_id bigint NOT NULL,
    name text NOT NULL
);


--
-- Name: profiles_labels; Type: TABLE; Schema: public;  Tablespace: 
--

CREATE TABLE profiles_labels (
    profile_id bigint,
    lang text NOT NULL,
    label text NOT NULL,
    profile_label_id bigint NOT NULL
);


--
-- Name: users; Type: TABLE; Schema: public;  Tablespace: 
--

CREATE TABLE users (
    user_id bigint NOT NULL,
    first_name text NOT NULL,
    email text NOT NULL,
    locale text,
    last_name text,
    username text
);


--
-- Name: users_dashboard; Type: TABLE; Schema: public;  Tablespace: 
--

CREATE TABLE users_dashboard (
    user_id bigint NOT NULL,
    panels bigint
);


--
-- Name: users_gadgets; Type: TABLE; Schema: public;  Tablespace: 
--

CREATE TABLE users_gadgets (
    gadget_id bigint NOT NULL,
    user_id bigint NOT NULL,
    application_name text NOT NULL,
    gadget_name text NOT NULL,
    panel bigint
);


--
-- Name: users_profiles; Type: TABLE; Schema: public;  Tablespace: 
--

CREATE TABLE users_profiles (
    user_id bigint NOT NULL,
    profile_id bigint NOT NULL
);


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; 
--

SELECT pg_catalog.setval('hibernate_sequence', 1000, true);


--
-- Data for Name: notifications; Type: TABLE DATA; Schema: public; 
--

COPY notifications (notification_id, message, user_id, read, email_sent, creation_time, link) FROM stdin;
\.


--
-- Data for Name: profiles; Type: TABLE DATA; Schema: public; 
--

COPY profiles (profile_id, name) FROM stdin;
0	ADMIN
1	USER
\.


--
-- Data for Name: profiles_labels; Type: TABLE DATA; Schema: public; 
--

COPY profiles_labels (profile_id, lang, label, profile_label_id) FROM stdin;
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; 
--

COPY users (user_id, first_name, email, locale, last_name, username) FROM stdin;
-1	WP	wp_admin@supersede.eu		Admin	wp_admin
\.


--
-- Data for Name: users_dashboard; Type: TABLE DATA; Schema: public;
--

COPY users_dashboard (user_id, panels) FROM stdin;
-1	2
\.


--
-- Data for Name: users_gadgets; Type: TABLE DATA; Schema: public; 
--

COPY users_gadgets (gadget_id, user_id, application_name, gadget_name, panel) FROM stdin;
0	-1	admin-user-manager-app	list_users	0
\.


--
-- Data for Name: users_profiles; Type: TABLE DATA; Schema: public; 
--

COPY users_profiles (user_id, profile_id) FROM stdin;
-1	0
\.


--
-- Name: notifications_primary_key; Type: CONSTRAINT; Schema: public; Tablespace: 
--

ALTER TABLE ONLY notifications
    ADD CONSTRAINT notifications_primary_key PRIMARY KEY (notification_id);


--
-- Name: profiles_labels_pk; Type: CONSTRAINT; Schema: public;  Tablespace: 
--

ALTER TABLE ONLY profiles_labels
    ADD CONSTRAINT profiles_labels_pk PRIMARY KEY (profile_label_id);


--
-- Name: profiles_labels_profile_id_lang_key; Type: CONSTRAINT; Schema: public;  Tablespace: 
--

ALTER TABLE ONLY profiles_labels
    ADD CONSTRAINT profiles_labels_profile_id_lang_key UNIQUE (profile_id, lang);


--
-- Name: profiles_primary_key; Type: CONSTRAINT; Schema: public;  Tablespace: 
--

ALTER TABLE ONLY profiles
    ADD CONSTRAINT profiles_primary_key PRIMARY KEY (profile_id);


--
-- Name: unique_email; Type: CONSTRAINT; Schema: public;  Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT unique_email UNIQUE (email);


--
-- Name: unique_name; Type: CONSTRAINT; Schema: public;  Tablespace: 
--

ALTER TABLE ONLY profiles
    ADD CONSTRAINT unique_name UNIQUE (name);


--
-- Name: unique_username; Type: CONSTRAINT; Schema: public;  Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT unique_username UNIQUE (username);


--
-- Name: users_dashboard_primary_key; Type: CONSTRAINT; Schema: public;  Tablespace: 
--

ALTER TABLE ONLY users_dashboard
    ADD CONSTRAINT users_dashboard_primary_key PRIMARY KEY (user_id);


--
-- Name: users_gadgets_primary_key; Type: CONSTRAINT; Schema: public;  Tablespace: 
--

ALTER TABLE ONLY users_gadgets
    ADD CONSTRAINT users_gadgets_primary_key PRIMARY KEY (gadget_id, user_id);


--
-- Name: users_primary_key; Type: CONSTRAINT; Schema: public;  Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_primary_key PRIMARY KEY (user_id);


--
-- Name: users_profiles_primary_key; Type: CONSTRAINT; Schema: public;  Tablespace: 
--

ALTER TABLE ONLY users_profiles
    ADD CONSTRAINT users_profiles_primary_key PRIMARY KEY (user_id, profile_id);


--
-- Name: fki_profiles_profiles_labels_fk; Type: INDEX; Schema: public;  Tablespace: 
--

CREATE INDEX fki_profiles_profiles_labels_fk ON profiles_labels USING btree (profile_id);


--
-- Name: fki_users_foreign_key; Type: INDEX; Schema: public;  Tablespace: 
--

CREATE INDEX fki_users_foreign_key ON notifications USING btree (user_id);


--
-- Name: fki_users_gadgets_users_fk; Type: INDEX; Schema: public;  Tablespace: 
--

CREATE INDEX fki_users_gadgets_users_fk ON users_gadgets USING btree (user_id);


--
-- Name: profiles_foreign_key; Type: FK CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY users_profiles
    ADD CONSTRAINT profiles_foreign_key FOREIGN KEY (profile_id) REFERENCES profiles(profile_id);


--
-- Name: profiles_profiles_labels_fk; Type: FK CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY profiles_labels
    ADD CONSTRAINT profiles_profiles_labels_fk FOREIGN KEY (profile_id) REFERENCES profiles(profile_id);


--
-- Name: users_dashboard_users_fk; Type: FK CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY users_dashboard
    ADD CONSTRAINT users_dashboard_users_fk FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- Name: users_foreign_key; Type: FK CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY notifications
    ADD CONSTRAINT users_foreign_key FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- Name: users_foreign_key; Type: FK CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY users_profiles
    ADD CONSTRAINT users_foreign_key FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- Name: users_gadgets_users_fk; Type: FK CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY users_gadgets
    ADD CONSTRAINT users_gadgets_users_fk FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

