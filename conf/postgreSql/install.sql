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

ALTER TABLE ONLY public.moves DROP CONSTRAINT valutation_criterias_foreign_key;
ALTER TABLE ONLY public.users_criteria_points DROP CONSTRAINT valutation_criterias_foreign_key;
ALTER TABLE ONLY public.users_points DROP CONSTRAINT users_foreign_key;
ALTER TABLE ONLY public.judge_moves DROP CONSTRAINT users_foreign_key;
ALTER TABLE ONLY public.users_criteria_points DROP CONSTRAINT users_foreign_key;
ALTER TABLE ONLY public.users_profiles DROP CONSTRAINT users_foreign_key;
ALTER TABLE ONLY public.notifications DROP CONSTRAINT users_foreign_key;
ALTER TABLE ONLY public.moves DROP CONSTRAINT third_requirements_foreign_key;
ALTER TABLE ONLY public.moves DROP CONSTRAINT second_users_foreign_key;
ALTER TABLE ONLY public.moves DROP CONSTRAINT second_requirements_foreign_key;
ALTER TABLE ONLY public.judge_moves DROP CONSTRAINT second_arguments_foreign_key;
ALTER TABLE ONLY public.judge_moves DROP CONSTRAINT requirements_foreign_key;
ALTER TABLE ONLY public.users_profiles DROP CONSTRAINT profiles_foreign_key;
ALTER TABLE ONLY public.judge_moves DROP CONSTRAINT moves_foreign_key;
ALTER TABLE ONLY public.moves DROP CONSTRAINT fourth_requirements_foreign_key;
ALTER TABLE ONLY public.moves DROP CONSTRAINT first_users_foreign_key;
ALTER TABLE ONLY public.moves DROP CONSTRAINT first_requirements_foreign_key;
ALTER TABLE ONLY public.judge_moves DROP CONSTRAINT first_arguments_foreign_key;
ALTER TABLE ONLY public.moves DROP CONSTRAINT fifth_requirements_foreign_key;
DROP INDEX public.fki_valutation_criterias_foreign_key;
DROP INDEX public.fki_users_foreign_key;
DROP INDEX public.fki_second_users_foreign_key;
DROP INDEX public.fki_second_requirements_foreign_key;
DROP INDEX public.fki_first_users_foreign_key;
DROP INDEX public.fki_first_requirements_foreign_key;
ALTER TABLE ONLY public.valutation_criterias DROP CONSTRAINT valutation_criterias_primary_key;
ALTER TABLE ONLY public.users_profiles DROP CONSTRAINT users_profiles_primary_key;
ALTER TABLE ONLY public.users DROP CONSTRAINT users_primary_key;
ALTER TABLE ONLY public.users_points DROP CONSTRAINT users_points_primary_key;
ALTER TABLE ONLY public.users_criteria_points DROP CONSTRAINT users_criteria_points_primary_key;
ALTER TABLE ONLY public.profiles DROP CONSTRAINT unique_name;
ALTER TABLE ONLY public.users DROP CONSTRAINT unique_email;
ALTER TABLE ONLY public.requirements DROP CONSTRAINT requirements_primary_key;
ALTER TABLE ONLY public.profiles DROP CONSTRAINT profiles_primary_key;
ALTER TABLE ONLY public.points DROP CONSTRAINT points_primary_key;
ALTER TABLE ONLY public.notifications DROP CONSTRAINT notifications_primary_key;
ALTER TABLE ONLY public.moves DROP CONSTRAINT moves_primary_key;
ALTER TABLE ONLY public.judge_moves DROP CONSTRAINT judge_moves_primary_key;
ALTER TABLE ONLY public.arguments DROP CONSTRAINT arguments_primary_key;
DROP TABLE public.valutation_criterias;
DROP TABLE public.users_profiles;
DROP TABLE public.users_points;
DROP TABLE public.users_criteria_points;
DROP TABLE public.users;
DROP TABLE public.requirements_choices;
DROP TABLE public.requirements;
DROP TABLE public.profiles;
DROP TABLE public.points;
DROP TABLE public.notifications;
DROP TABLE public.moves;
DROP TABLE public.judge_moves;
DROP SEQUENCE public.hibernate_sequence;
DROP TABLE public.arguments;
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

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: arguments; Type: TABLE; Schema: public; Owner: testDB; Tablespace: 
--

CREATE TABLE arguments (
    argument_id bigint NOT NULL,
    content text NOT NULL
);


ALTER TABLE arguments OWNER TO "testDB";

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

--
-- Name: judge_moves; Type: TABLE; Schema: public; Owner: testDB; Tablespace: 
--

CREATE TABLE judge_moves (
    judge_move_id bigint NOT NULL,
    move_id bigint NOT NULL,
    judge_id bigint,
    need_arguments boolean DEFAULT false NOT NULL,
    gave_opinion boolean DEFAULT false NOT NULL,
    opinion bigint,
    first_argument_id bigint,
    second_argument_id bigint,
    finish boolean DEFAULT false NOT NULL,
    notification_sent boolean NOT NULL,
    to_judge boolean DEFAULT true NOT NULL,
    to_solve boolean DEFAULT false NOT NULL
);


ALTER TABLE judge_moves OWNER TO "testDB";

--
-- Name: moves; Type: TABLE; Schema: public; Owner: testDB; Tablespace: 
--

CREATE TABLE moves (
    move_id bigint NOT NULL,
    name text NOT NULL,
    start_time timestamp with time zone NOT NULL,
    timer bigint NOT NULL,
    finish boolean NOT NULL,
    first_requirement_id bigint NOT NULL,
    second_requirement_id bigint NOT NULL,
    first_player_id bigint NOT NULL,
    second_player_id bigint NOT NULL,
    criteria_id bigint NOT NULL,
    first_player_requirement bigint,
    second_player_requirement bigint,
    notification_sent boolean DEFAULT false NOT NULL,
    to_play boolean DEFAULT true NOT NULL,
    to_argue boolean DEFAULT false NOT NULL,
    winner_requirement bigint
);


ALTER TABLE moves OWNER TO "testDB";

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
-- Name: points; Type: TABLE; Schema: public; Owner: testDB; Tablespace: 
--

CREATE TABLE points (
    point_id bigint NOT NULL,
    description text NOT NULL,
    global_points bigint DEFAULT 0 NOT NULL,
    criteria_points bigint DEFAULT 0 NOT NULL
);


ALTER TABLE points OWNER TO "testDB";

--
-- Name: profiles; Type: TABLE; Schema: public; Owner: testDB; Tablespace: 
--

CREATE TABLE profiles (
    profile_id bigint NOT NULL,
    name text NOT NULL
);


ALTER TABLE profiles OWNER TO "testDB";

--
-- Name: requirements; Type: TABLE; Schema: public; Owner: testDB; Tablespace: 
--

CREATE TABLE requirements (
    requirement_id bigint NOT NULL,
    name text NOT NULL,
    description text
);


ALTER TABLE requirements OWNER TO "testDB";

--
-- Name: requirements_choices; Type: TABLE; Schema: public; Owner: testDB; Tablespace: 
--

CREATE TABLE requirements_choices (
    requirements_choice_id bigint NOT NULL,
    description text,
    value bigint
);


ALTER TABLE requirements_choices OWNER TO "testDB";

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
-- Name: users_criteria_points; Type: TABLE; Schema: public; Owner: testDB; Tablespace: 
--

CREATE TABLE users_criteria_points (
    user_id bigint NOT NULL,
    criteria_id bigint NOT NULL,
    points bigint DEFAULT 0 NOT NULL,
    user_criteria_points_id bigint NOT NULL
);


ALTER TABLE users_criteria_points OWNER TO "testDB";

--
-- Name: users_points; Type: TABLE; Schema: public; Owner: testDB; Tablespace: 
--

CREATE TABLE users_points (
    user_id bigint NOT NULL,
    user_points bigint DEFAULT 0 NOT NULL,
    users_points_id bigint NOT NULL
);


ALTER TABLE users_points OWNER TO "testDB";

--
-- Name: users_profiles; Type: TABLE; Schema: public; Owner: testDB; Tablespace: 
--

CREATE TABLE users_profiles (
    user_id bigint NOT NULL,
    profile_id bigint NOT NULL
);


ALTER TABLE users_profiles OWNER TO "testDB";

--
-- Name: valutation_criterias; Type: TABLE; Schema: public; Owner: testDB; Tablespace: 
--

CREATE TABLE valutation_criterias (
    criteria_id bigint NOT NULL,
    name text NOT NULL
);


ALTER TABLE valutation_criterias OWNER TO "testDB";

--
-- Data for Name: arguments; Type: TABLE DATA; Schema: public; Owner: testDB
--

COPY arguments (argument_id, content) FROM stdin;
209	E' la scelta più sensata.
210	Più veloce e funzionale!
313	Più economico sotto tutti i punti di vista.
422	E' la scelta più saggio ed ecologica.
445	Sicuramente superiore da un punto di vista ambientale e sociale.
\.


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: testDB
--

SELECT pg_catalog.setval('hibernate_sequence', 521, true);


--
-- Data for Name: judge_moves; Type: TABLE DATA; Schema: public; Owner: testDB
--

COPY judge_moves (judge_move_id, move_id, judge_id, need_arguments, gave_opinion, opinion, first_argument_id, second_argument_id, finish, notification_sent, to_judge, to_solve) FROM stdin;
449	446	127	t	f	\N	209	210	t	t	f	t
457	454	127	t	f	\N	313	445	t	t	f	t
465	462	127	f	t	55	\N	\N	t	f	t	f
470	467	127	f	t	56	\N	\N	t	f	t	f
475	472	127	t	f	\N	313	445	t	t	f	t
484	481	127	t	f	\N	209	445	t	t	f	t
489	486	127	t	f	\N	422	422	t	t	f	t
506	503	501	t	f	\N	210	422	t	t	f	t
\.


--
-- Data for Name: moves; Type: TABLE DATA; Schema: public; Owner: testDB
--

COPY moves (move_id, name, start_time, timer, finish, first_requirement_id, second_requirement_id, first_player_id, second_player_id, criteria_id, first_player_requirement, second_player_requirement, notification_sent, to_play, to_argue, winner_requirement) FROM stdin;
446	test	2015-12-15 08:29:23.32+00	3600	t	54	60	35	117	67	54	60	t	f	t	60
454	test	2015-12-15 14:55:37.703+00	3600	t	58	60	35	117	67	58	60	t	f	t	60
462	test	2015-12-15 15:03:09.129+00	3600	t	55	57	29	117	67	55	57	t	t	f	55
481	test	2015-12-18 10:28:23.995+00	3600	t	55	59	35	117	67	55	59	t	f	t	55
467	test	2015-12-17 10:41:21.194+00	3600	t	56	58	35	117	67	56	58	t	t	f	56
486	test	2015-12-18 14:04:14.494+00	3600	t	55	59	35	117	65	59	55	t	f	t	55
497	test	2016-01-11 10:20:45.387+00	3600	f	60	53	35	117	65	\N	\N	f	t	f	\N
472	test	2015-12-18 09:54:18.155+00	3600	t	55	57	35	117	66	55	57	t	f	t	55
503	test	2016-01-12 08:36:33.416+00	3600	t	52	53	500	502	63	53	52	t	f	t	53
515	test	2016-01-12 09:26:00.342+00	3600	f	53	54	502	500	64	\N	\N	f	t	f	\N
\.


--
-- Data for Name: notifications; Type: TABLE DATA; Schema: public; Owner: testDB
--

COPY notifications (notification_id, message, user_id, read, email_sent, creation_time, link) FROM stdin;
463	New move 462	29	t	f	2015-12-15 15:03:09.169+00	game-requirements/user_moves
498	New move 497	35	t	f	2016-01-11 10:20:45.427+00	game-requirements/user_moves
499	New move 497	117	t	t	2016-01-11 10:20:45.435+00	game-requirements/user_moves
505	New move 503	502	t	f	2016-01-12 08:36:33.481+00	game-requirements/user_moves
504	New move 503	500	t	t	2016-01-12 08:36:33.469+00	game-requirements/user_moves
508	New conflict in move 503	501	t	f	2016-01-12 08:38:08.148+00	game-requirements/judge_moves
507	New conflict in move 503	127	f	t	2016-01-12 08:38:08.132+00	game-requirements/judge_moves
510	Need argument for move 503	502	t	f	2016-01-12 09:15:11.865+00	game-requirements/user_moves
509	Need argument for move 503	500	t	f	2016-01-12 09:15:11.843+00	game-requirements/user_moves
512	There are two arguments in move 506	501	t	f	2016-01-12 09:16:04.263+00	game-requirements/judge_moves
511	There are two arguments in move 506	127	f	t	2016-01-12 09:16:04.251+00	game-requirements/judge_moves
516	New move 515	502	t	f	2016-01-12 09:26:00.358+00	game-requirements/user_moves
517	New move 515	500	t	t	2016-01-12 09:26:00.374+00	game-requirements/user_moves
\.


--
-- Data for Name: points; Type: TABLE DATA; Schema: public; Owner: testDB
--

COPY points (point_id, description, global_points, criteria_points) FROM stdin;
-1	voted	1	0
-2	both_players_agree	5	1
-3	judge opinion	5	0
-4	winner argument	10	2
-5	winner with new argument	15	2
-6	loser argument	0	2
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
-- Data for Name: requirements; Type: TABLE DATA; Schema: public; Owner: testDB
--

COPY requirements (requirement_id, name, description) FROM stdin;
52	Requirement 0	Requirement 0 description. Here there are some information about this requirement.
53	Requirement 1	Requirement 1 description. Here there are some information about this requirement.
54	Requirement 2	Requirement 2 description. Here there are some information about this requirement.
55	Requirement 3	Requirement 3 description. Here there are some information about this requirement.
56	Requirement 4	Requirement 4 description. Here there are some information about this requirement.
57	Requirement 5	Requirement 5 description. Here there are some information about this requirement.
58	Requirement 6	Requirement 6 description. Here there are some information about this requirement.
59	Requirement 7	Requirement 7 description. Here there are some information about this requirement.
60	Requirement 8	Requirement 8 description. Here there are some information about this requirement.
61	Requirement 9	Requirement 9 description. Here there are some information about this requirement.
\.


--
-- Data for Name: requirements_choices; Type: TABLE DATA; Schema: public; Owner: testDB
--

COPY requirements_choices (requirements_choice_id, description, value) FROM stdin;
1	Primo	1
2	Secondo	2
3	Nessuno dei due	3
4	Meglio il primo del secondo	4
5	Meglio il secondo del primo	5
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: testDB
--

COPY users (user_id, name, email, password, locale) FROM stdin;
-1	admin	wp_admin	$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.	\N
117	Andrea	sosi.andrea@gmail.com	$2a$10$RmDX5VdjobYISkur0Zid/eyc.kY724LnaM0upK4COi.wzWKQy7QtC	\N
127	Pippo	pippo	$2a$10$R0nNYkSlUkz94OHGK43LJuumfBpYE7.wBVlVtGHsuRqpwquoavm/i	\N
35	testAdmin	testAdmin	$2a$10$ZxpruWIrRvQCsZzMupGeguwzyvSjwx7paGX4ZDxO4o20mi54TYZ52	en
500	Player	supersede.test+player@gmail.com	$2a$10$r7IcUeIW3SnnHC5pAuTEeujIir2A1rvwOhQKPrVr4MCjqhQgWnWsC	\N
501	Judge	supersede.test+judge@gmail.com	$2a$10$OytDvVWkrgCi.WqfSVCWUuRcqgYNfMqy1LOcQ0CvoOmTMbrWoWOG2	\N
502	Game Master	supersede.test+gm@gmail.com	$2a$10$7HkFzeGuGtgcpDIbiubWOe3BakBgdz7G2hD2JJyg5JFKraSvd9dXa	\N
29	Michele	lunelli.michele@gmail.com	$2a$10$/6ttaYzflQlQXKcSsf277OvPwL4pCXoVJ28MvCwP15RkXLOq7fv1m	en
\.


--
-- Data for Name: users_criteria_points; Type: TABLE DATA; Schema: public; Owner: testDB
--

COPY users_criteria_points (user_id, criteria_id, points, user_criteria_points_id) FROM stdin;
117	63	1	389
35	63	1	390
117	64	4	380
35	64	3	399
35	66	2	480
117	66	4	373
35	67	9	385
117	67	9	384
117	65	3	394
35	65	3	395
500	63	2	513
502	63	2	514
\.


--
-- Data for Name: users_points; Type: TABLE DATA; Schema: public; Owner: testDB
--

COPY users_points (user_id, user_points, users_points_id) FROM stdin;
-1	0	-1
35	130	-3
117	103	-4
29	6	-2
127	15	-5
501	0	-7
502	1	-8
500	11	-6
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
500	1
501	2
502	0
502	1
\.


--
-- Data for Name: valutation_criterias; Type: TABLE DATA; Schema: public; Owner: testDB
--

COPY valutation_criterias (criteria_id, name) FROM stdin;
63	Marketing
64	User Interface
65	Design
66	Management
67	Programming
\.


--
-- Name: arguments_primary_key; Type: CONSTRAINT; Schema: public; Owner: testDB; Tablespace: 
--

ALTER TABLE ONLY arguments
    ADD CONSTRAINT arguments_primary_key PRIMARY KEY (argument_id);


--
-- Name: judge_moves_primary_key; Type: CONSTRAINT; Schema: public; Owner: testDB; Tablespace: 
--

ALTER TABLE ONLY judge_moves
    ADD CONSTRAINT judge_moves_primary_key PRIMARY KEY (judge_move_id);


--
-- Name: moves_primary_key; Type: CONSTRAINT; Schema: public; Owner: testDB; Tablespace: 
--

ALTER TABLE ONLY moves
    ADD CONSTRAINT moves_primary_key PRIMARY KEY (move_id);


--
-- Name: notifications_primary_key; Type: CONSTRAINT; Schema: public; Owner: testDB; Tablespace: 
--

ALTER TABLE ONLY notifications
    ADD CONSTRAINT notifications_primary_key PRIMARY KEY (notification_id);


--
-- Name: points_primary_key; Type: CONSTRAINT; Schema: public; Owner: testDB; Tablespace: 
--

ALTER TABLE ONLY points
    ADD CONSTRAINT points_primary_key PRIMARY KEY (point_id);


--
-- Name: profiles_primary_key; Type: CONSTRAINT; Schema: public; Owner: testDB; Tablespace: 
--

ALTER TABLE ONLY profiles
    ADD CONSTRAINT profiles_primary_key PRIMARY KEY (profile_id);


--
-- Name: requirements_primary_key; Type: CONSTRAINT; Schema: public; Owner: testDB; Tablespace: 
--

ALTER TABLE ONLY requirements
    ADD CONSTRAINT requirements_primary_key PRIMARY KEY (requirement_id);


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
-- Name: users_criteria_points_primary_key; Type: CONSTRAINT; Schema: public; Owner: testDB; Tablespace: 
--

ALTER TABLE ONLY users_criteria_points
    ADD CONSTRAINT users_criteria_points_primary_key PRIMARY KEY (user_criteria_points_id);


--
-- Name: users_points_primary_key; Type: CONSTRAINT; Schema: public; Owner: testDB; Tablespace: 
--

ALTER TABLE ONLY users_points
    ADD CONSTRAINT users_points_primary_key PRIMARY KEY (users_points_id);


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
-- Name: valutation_criterias_primary_key; Type: CONSTRAINT; Schema: public; Owner: testDB; Tablespace: 
--

ALTER TABLE ONLY valutation_criterias
    ADD CONSTRAINT valutation_criterias_primary_key PRIMARY KEY (criteria_id);


--
-- Name: fki_first_requirements_foreign_key; Type: INDEX; Schema: public; Owner: testDB; Tablespace: 
--

CREATE INDEX fki_first_requirements_foreign_key ON moves USING btree (first_requirement_id);


--
-- Name: fki_first_users_foreign_key; Type: INDEX; Schema: public; Owner: testDB; Tablespace: 
--

CREATE INDEX fki_first_users_foreign_key ON moves USING btree (first_player_id);


--
-- Name: fki_second_requirements_foreign_key; Type: INDEX; Schema: public; Owner: testDB; Tablespace: 
--

CREATE INDEX fki_second_requirements_foreign_key ON moves USING btree (second_requirement_id);


--
-- Name: fki_second_users_foreign_key; Type: INDEX; Schema: public; Owner: testDB; Tablespace: 
--

CREATE INDEX fki_second_users_foreign_key ON moves USING btree (second_player_id);


--
-- Name: fki_users_foreign_key; Type: INDEX; Schema: public; Owner: testDB; Tablespace: 
--

CREATE INDEX fki_users_foreign_key ON notifications USING btree (user_id);


--
-- Name: fki_valutation_criterias_foreign_key; Type: INDEX; Schema: public; Owner: testDB; Tablespace: 
--

CREATE INDEX fki_valutation_criterias_foreign_key ON users_criteria_points USING btree (criteria_id);


--
-- Name: fifth_requirements_foreign_key; Type: FK CONSTRAINT; Schema: public; Owner: testDB
--

ALTER TABLE ONLY moves
    ADD CONSTRAINT fifth_requirements_foreign_key FOREIGN KEY (winner_requirement) REFERENCES requirements(requirement_id);


--
-- Name: first_arguments_foreign_key; Type: FK CONSTRAINT; Schema: public; Owner: testDB
--

ALTER TABLE ONLY judge_moves
    ADD CONSTRAINT first_arguments_foreign_key FOREIGN KEY (first_argument_id) REFERENCES arguments(argument_id);


--
-- Name: first_requirements_foreign_key; Type: FK CONSTRAINT; Schema: public; Owner: testDB
--

ALTER TABLE ONLY moves
    ADD CONSTRAINT first_requirements_foreign_key FOREIGN KEY (first_requirement_id) REFERENCES requirements(requirement_id);


--
-- Name: first_users_foreign_key; Type: FK CONSTRAINT; Schema: public; Owner: testDB
--

ALTER TABLE ONLY moves
    ADD CONSTRAINT first_users_foreign_key FOREIGN KEY (first_player_id) REFERENCES users(user_id);


--
-- Name: fourth_requirements_foreign_key; Type: FK CONSTRAINT; Schema: public; Owner: testDB
--

ALTER TABLE ONLY moves
    ADD CONSTRAINT fourth_requirements_foreign_key FOREIGN KEY (second_player_requirement) REFERENCES requirements(requirement_id);


--
-- Name: moves_foreign_key; Type: FK CONSTRAINT; Schema: public; Owner: testDB
--

ALTER TABLE ONLY judge_moves
    ADD CONSTRAINT moves_foreign_key FOREIGN KEY (move_id) REFERENCES moves(move_id);


--
-- Name: profiles_foreign_key; Type: FK CONSTRAINT; Schema: public; Owner: testDB
--

ALTER TABLE ONLY users_profiles
    ADD CONSTRAINT profiles_foreign_key FOREIGN KEY (profile_id) REFERENCES profiles(profile_id);


--
-- Name: requirements_foreign_key; Type: FK CONSTRAINT; Schema: public; Owner: testDB
--

ALTER TABLE ONLY judge_moves
    ADD CONSTRAINT requirements_foreign_key FOREIGN KEY (opinion) REFERENCES requirements(requirement_id);


--
-- Name: second_arguments_foreign_key; Type: FK CONSTRAINT; Schema: public; Owner: testDB
--

ALTER TABLE ONLY judge_moves
    ADD CONSTRAINT second_arguments_foreign_key FOREIGN KEY (second_argument_id) REFERENCES arguments(argument_id);


--
-- Name: second_requirements_foreign_key; Type: FK CONSTRAINT; Schema: public; Owner: testDB
--

ALTER TABLE ONLY moves
    ADD CONSTRAINT second_requirements_foreign_key FOREIGN KEY (second_requirement_id) REFERENCES requirements(requirement_id);


--
-- Name: second_users_foreign_key; Type: FK CONSTRAINT; Schema: public; Owner: testDB
--

ALTER TABLE ONLY moves
    ADD CONSTRAINT second_users_foreign_key FOREIGN KEY (second_player_id) REFERENCES users(user_id);


--
-- Name: third_requirements_foreign_key; Type: FK CONSTRAINT; Schema: public; Owner: testDB
--

ALTER TABLE ONLY moves
    ADD CONSTRAINT third_requirements_foreign_key FOREIGN KEY (first_player_requirement) REFERENCES requirements(requirement_id);


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
-- Name: users_foreign_key; Type: FK CONSTRAINT; Schema: public; Owner: testDB
--

ALTER TABLE ONLY users_criteria_points
    ADD CONSTRAINT users_foreign_key FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- Name: users_foreign_key; Type: FK CONSTRAINT; Schema: public; Owner: testDB
--

ALTER TABLE ONLY judge_moves
    ADD CONSTRAINT users_foreign_key FOREIGN KEY (judge_id) REFERENCES users(user_id);


--
-- Name: users_foreign_key; Type: FK CONSTRAINT; Schema: public; Owner: testDB
--

ALTER TABLE ONLY users_points
    ADD CONSTRAINT users_foreign_key FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- Name: valutation_criterias_foreign_key; Type: FK CONSTRAINT; Schema: public; Owner: testDB
--

ALTER TABLE ONLY users_criteria_points
    ADD CONSTRAINT valutation_criterias_foreign_key FOREIGN KEY (criteria_id) REFERENCES valutation_criterias(criteria_id);


--
-- Name: valutation_criterias_foreign_key; Type: FK CONSTRAINT; Schema: public; Owner: testDB
--

ALTER TABLE ONLY moves
    ADD CONSTRAINT valutation_criterias_foreign_key FOREIGN KEY (criteria_id) REFERENCES valutation_criterias(criteria_id);


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

