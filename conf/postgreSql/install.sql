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

ALTER TABLE ONLY public.users_profiles DROP CONSTRAINT users_foreign_key;
ALTER TABLE ONLY public.notifications DROP CONSTRAINT users_foreign_key;
ALTER TABLE ONLY public.users_profiles DROP CONSTRAINT profiles_foreign_key;
DROP INDEX public.fki_users_foreign_key;
ALTER TABLE ONLY public.users_profiles DROP CONSTRAINT users_profiles_primary_key;
ALTER TABLE ONLY public.users DROP CONSTRAINT users_primary_key;
ALTER TABLE ONLY public.profiles DROP CONSTRAINT unique_name;
ALTER TABLE ONLY public.users DROP CONSTRAINT unique_email;
ALTER TABLE ONLY public.profiles DROP CONSTRAINT profiles_primary_key;
ALTER TABLE ONLY public.notifications DROP CONSTRAINT notifications_primary_key;
DROP TABLE public.users_profiles;
DROP TABLE public.users;
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
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: testDB
--

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hibernate_sequence OWNER TO "testDB";

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: notifications; Type: TABLE; Schema: public; Owner: testDB; Tablespace: 
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


ALTER TABLE notifications OWNER TO "testDB";

--
-- Name: profiles; Type: TABLE; Schema: public; Owner: testDB; Tablespace: 
--

CREATE TABLE profiles (
    profile_id bigint NOT NULL,
    name text NOT NULL
);


ALTER TABLE profiles OWNER TO "testDB";

--
-- Name: users; Type: TABLE; Schema: public; Owner: testDB; Tablespace: 
--

CREATE TABLE users (
    user_id bigint NOT NULL,
    name text NOT NULL,
    email text NOT NULL,
    password text NOT NULL,
    locale text
);


ALTER TABLE users OWNER TO "testDB";

--
-- Name: users_profiles; Type: TABLE; Schema: public; Owner: testDB; Tablespace: 
--

CREATE TABLE users_profiles (
    user_id bigint NOT NULL,
    profile_id bigint NOT NULL
);


ALTER TABLE users_profiles OWNER TO "testDB";

--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: testDB
--

SELECT pg_catalog.setval('hibernate_sequence', 642, true);


--
-- Data for Name: notifications; Type: TABLE DATA; Schema: public; Owner: testDB
--

COPY notifications (notification_id, message, user_id, read, email_sent, creation_time, link) FROM stdin;
463	New move 462	29	t	f	2015-12-15 15:03:09.169+00	game-requirements/user_moves
498	New move 497	35	t	f	2016-01-11 10:20:45.427+00	game-requirements/user_moves
499	New move 497	117	t	t	2016-01-11 10:20:45.435+00	game-requirements/user_moves
501	New conflict in move 497	127	t	f	2016-01-12 15:01:42.366+00	game-requirements/judge_moves
502	Need argument for move 497	35	t	f	2016-01-13 08:55:57.345+00	game-requirements/user_moves
503	Need argument for move 497	117	t	f	2016-01-13 08:55:57.366+00	game-requirements/user_moves
504	There are two arguments in move 500	127	t	f	2016-01-13 08:56:37.89+00	game-requirements/judge_moves
\.


--
-- Data for Name: profiles; Type: TABLE DATA; Schema: public; Owner: testDB
--

COPY profiles (profile_id, name) FROM stdin;
0	ADMIN
1	PLAYER
2	JUDGE
3	USER
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: testDB
--

COPY users (user_id, name, email, password, locale) FROM stdin;
-1	admin	wp_admin	$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.	\N
29	Michele	lunelli.michele@gmail.com	$2a$10$/6ttaYzflQlQXKcSsf277OvPwL4pCXoVJ28MvCwP15RkXLOq7fv1m	\N
127	Pippo	pippo	$2a$10$R0nNYkSlUkz94OHGK43LJuumfBpYE7.wBVlVtGHsuRqpwquoavm/i	\N
117	Andrea	sosi.andrea@gmail.com	$2a$10$RmDX5VdjobYISkur0Zid/eyc.kY724LnaM0upK4COi.wzWKQy7QtC	
35	testAdmin	testAdmin	$2a$10$ZxpruWIrRvQCsZzMupGeguwzyvSjwx7paGX4ZDxO4o20mi54TYZ52	
\.


--
-- Data for Name: users_profiles; Type: TABLE DATA; Schema: public; Owner: testDB
--

COPY users_profiles (user_id, profile_id) FROM stdin;
-1	0
-1	1
29	1
29	0
35	0
35	1
117	1
127	2
127	1
\.


--
-- Name: notifications_primary_key; Type: CONSTRAINT; Schema: public; Owner: testDB; Tablespace: 
--

ALTER TABLE ONLY notifications
    ADD CONSTRAINT notifications_primary_key PRIMARY KEY (notification_id);


--
-- Name: profiles_primary_key; Type: CONSTRAINT; Schema: public; Owner: testDB; Tablespace: 
--

ALTER TABLE ONLY profiles
    ADD CONSTRAINT profiles_primary_key PRIMARY KEY (profile_id);


--
-- Name: unique_email; Type: CONSTRAINT; Schema: public; Owner: testDB; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT unique_email UNIQUE (email);


--
-- Name: unique_name; Type: CONSTRAINT; Schema: public; Owner: testDB; Tablespace: 
--

ALTER TABLE ONLY profiles
    ADD CONSTRAINT unique_name UNIQUE (name);


--
-- Name: users_primary_key; Type: CONSTRAINT; Schema: public; Owner: testDB; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_primary_key PRIMARY KEY (user_id);


--
-- Name: users_profiles_primary_key; Type: CONSTRAINT; Schema: public; Owner: testDB; Tablespace: 
--

ALTER TABLE ONLY users_profiles
    ADD CONSTRAINT users_profiles_primary_key PRIMARY KEY (user_id, profile_id);


--
-- Name: fki_users_foreign_key; Type: INDEX; Schema: public; Owner: testDB; Tablespace: 
--

CREATE INDEX fki_users_foreign_key ON notifications USING btree (user_id);


--
-- Name: profiles_foreign_key; Type: FK CONSTRAINT; Schema: public; Owner: testDB
--

ALTER TABLE ONLY users_profiles
    ADD CONSTRAINT profiles_foreign_key FOREIGN KEY (profile_id) REFERENCES profiles(profile_id);


--
-- Name: users_foreign_key; Type: FK CONSTRAINT; Schema: public; Owner: testDB
--

ALTER TABLE ONLY notifications
    ADD CONSTRAINT users_foreign_key FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- Name: users_foreign_key; Type: FK CONSTRAINT; Schema: public; Owner: testDB
--

ALTER TABLE ONLY users_profiles
    ADD CONSTRAINT users_foreign_key FOREIGN KEY (user_id) REFERENCES users(user_id);


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
