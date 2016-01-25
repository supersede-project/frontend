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

ALTER TABLE ONLY public.requirements_matrices DROP CONSTRAINT valutation_criterias_foreign_key;
ALTER TABLE ONLY public.game_criterias DROP CONSTRAINT valutation_criterias_foreign_key;
ALTER TABLE ONLY public.moves DROP CONSTRAINT valutation_criterias_foreign_key;
ALTER TABLE ONLY public.users_criteria_points DROP CONSTRAINT valutation_criterias_foreign_key;
ALTER TABLE ONLY public.judge_acts DROP CONSTRAINT users_foreign_key;
ALTER TABLE ONLY public.player_moves DROP CONSTRAINT users_foreign_key;
ALTER TABLE ONLY public.game_players DROP CONSTRAINT users_foreign_key;
ALTER TABLE ONLY public.users_points DROP CONSTRAINT users_foreign_key;
ALTER TABLE ONLY public.judge_moves DROP CONSTRAINT users_foreign_key;
ALTER TABLE ONLY public.users_criteria_points DROP CONSTRAINT users_foreign_key;
ALTER TABLE ONLY public.users_profiles DROP CONSTRAINT users_foreign_key;
ALTER TABLE ONLY public.notifications DROP CONSTRAINT users_foreign_key;
ALTER TABLE ONLY public.moves DROP CONSTRAINT third_requirements_foreign_key;
ALTER TABLE ONLY public.criterias_matrices_data DROP CONSTRAINT second_valutation_criterias_foreign_key;
ALTER TABLE ONLY public.moves DROP CONSTRAINT second_users_foreign_key;
ALTER TABLE ONLY public.requirements_matrices_data DROP CONSTRAINT second_requirements_foreign_key;
ALTER TABLE ONLY public.moves DROP CONSTRAINT second_requirements_foreign_key;
ALTER TABLE ONLY public.judge_moves DROP CONSTRAINT second_arguments_foreign_key;
ALTER TABLE ONLY public.requirements_matrices_data DROP CONSTRAINT requirements_matrices_foreign_key;
ALTER TABLE ONLY public.judge_acts DROP CONSTRAINT requirements_matrices_data_foreign_key;
ALTER TABLE ONLY public.player_moves DROP CONSTRAINT requirements_matrices_data_foreign_key;
ALTER TABLE ONLY public.game_requirements DROP CONSTRAINT requirements_foreign_key;
ALTER TABLE ONLY public.judge_moves DROP CONSTRAINT requirements_foreign_key;
ALTER TABLE ONLY public.users_profiles DROP CONSTRAINT profiles_foreign_key;
ALTER TABLE ONLY public.judge_moves DROP CONSTRAINT moves_foreign_key;
ALTER TABLE ONLY public.requirements_matrices DROP CONSTRAINT games_foreign_key;
ALTER TABLE ONLY public.criterias_matrices DROP CONSTRAINT games_foreign_key;
ALTER TABLE ONLY public.game_requirements DROP CONSTRAINT games_foreign_key;
ALTER TABLE ONLY public.game_players DROP CONSTRAINT games_foreign_key;
ALTER TABLE ONLY public.game_criterias DROP CONSTRAINT games_foreign_key;
ALTER TABLE ONLY public.moves DROP CONSTRAINT fourth_requirements_foreign_key;
ALTER TABLE ONLY public.criterias_matrices_data DROP CONSTRAINT first_valutation_criterias_foreign_key;
ALTER TABLE ONLY public.moves DROP CONSTRAINT first_users_foreign_key;
ALTER TABLE ONLY public.requirements_matrices_data DROP CONSTRAINT first_requirements_foreign_key;
ALTER TABLE ONLY public.moves DROP CONSTRAINT first_requirements_foreign_key;
ALTER TABLE ONLY public.judge_moves DROP CONSTRAINT first_arguments_foreign_key;
ALTER TABLE ONLY public.moves DROP CONSTRAINT fifth_requirements_foreign_key;
ALTER TABLE ONLY public.criterias_matrices_data DROP CONSTRAINT criterias_matrices_foreign_key;
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
ALTER TABLE ONLY public.requirements_matrices DROP CONSTRAINT requirements_matrices_primary_key;
ALTER TABLE ONLY public.requirements_matrices_data DROP CONSTRAINT requirements_matrices_data_primary_key;
ALTER TABLE ONLY public.profiles DROP CONSTRAINT profiles_primary_key;
ALTER TABLE ONLY public.points DROP CONSTRAINT points_primary_key;
ALTER TABLE ONLY public.player_moves DROP CONSTRAINT player_moves_primary_key;
ALTER TABLE ONLY public.notifications DROP CONSTRAINT notifications_primary_key;
ALTER TABLE ONLY public.moves DROP CONSTRAINT moves_primary_key;
ALTER TABLE ONLY public.judge_moves DROP CONSTRAINT judge_moves_primary_key;
ALTER TABLE ONLY public.judge_acts DROP CONSTRAINT judge_acts_primary_key;
ALTER TABLE ONLY public.games DROP CONSTRAINT games_primary_key;
ALTER TABLE ONLY public.game_requirements DROP CONSTRAINT game_requirements_primary_key;
ALTER TABLE ONLY public.game_players DROP CONSTRAINT game_players_primary_key;
ALTER TABLE ONLY public.game_criterias DROP CONSTRAINT game_criterias_primary_key;
ALTER TABLE ONLY public.criterias_matrices DROP CONSTRAINT criterias_matrices_primary_key;
ALTER TABLE ONLY public.criterias_matrices_data DROP CONSTRAINT criterias_matrices_data_primary_key;
ALTER TABLE ONLY public.arguments DROP CONSTRAINT arguments_primary_key;
DROP TABLE public.valutation_criterias;
DROP TABLE public.users_profiles;
DROP TABLE public.users_points;
DROP TABLE public.users_criteria_points;
DROP TABLE public.users;
DROP TABLE public.requirements_matrices_data;
DROP TABLE public.requirements_matrices;
DROP TABLE public.requirements_choices;
DROP TABLE public.requirements;
DROP TABLE public.profiles;
DROP TABLE public.points;
DROP TABLE public.player_moves;
DROP TABLE public.notifications;
DROP TABLE public.moves;
DROP TABLE public.judge_moves;
DROP TABLE public.judge_acts;
DROP SEQUENCE public.hibernate_sequence;
DROP TABLE public.games;
DROP TABLE public.game_requirements;
DROP TABLE public.game_players;
DROP TABLE public.game_criterias;
DROP TABLE public.criterias_matrices_data;
DROP TABLE public.criterias_matrices;
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
-- Name: criterias_matrices; Type: TABLE; Schema: public; Owner: testDB; Tablespace: 
--

CREATE TABLE criterias_matrices (
    criterias_matrix_id bigint NOT NULL,
    game_id bigint NOT NULL
);


ALTER TABLE criterias_matrices OWNER TO "testDB";

--
-- Name: criterias_matrices_data; Type: TABLE; Schema: public; Owner: testDB; Tablespace: 
--

CREATE TABLE criterias_matrices_data (
    criterias_matrix_data_id bigint NOT NULL,
    criterias_matrix_id bigint NOT NULL,
    "row" bigint NOT NULL,
    "column" bigint NOT NULL,
    value bigint
);


ALTER TABLE criterias_matrices_data OWNER TO "testDB";

--
-- Name: game_criterias; Type: TABLE; Schema: public; Owner: testDB; Tablespace: 
--

CREATE TABLE game_criterias (
    game_criteria_id bigint NOT NULL,
    game_id bigint NOT NULL,
    criteria_id bigint NOT NULL
);


ALTER TABLE game_criterias OWNER TO "testDB";

--
-- Name: game_players; Type: TABLE; Schema: public; Owner: testDB; Tablespace: 
--

CREATE TABLE game_players (
    game_player_id bigint NOT NULL,
    game_id bigint NOT NULL,
    player_id bigint NOT NULL
);


ALTER TABLE game_players OWNER TO "testDB";

--
-- Name: game_requirements; Type: TABLE; Schema: public; Owner: testDB; Tablespace: 
--

CREATE TABLE game_requirements (
    game_requirement_id bigint NOT NULL,
    game_id bigint NOT NULL,
    requirement_id bigint NOT NULL
);


ALTER TABLE game_requirements OWNER TO "testDB";

--
-- Name: games; Type: TABLE; Schema: public; Owner: testDB; Tablespace: 
--

CREATE TABLE games (
    game_id bigint NOT NULL,
    start_time timestamp with time zone NOT NULL
);


ALTER TABLE games OWNER TO "testDB";

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
-- Name: judge_acts; Type: TABLE; Schema: public; Owner: testDB; Tablespace: 
--

CREATE TABLE judge_acts (
    judge_act_id bigint NOT NULL,
    requirements_matrix_data_id bigint NOT NULL,
    judge_id bigint,
    voted boolean DEFAULT false NOT NULL
);


ALTER TABLE judge_acts OWNER TO "testDB";

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
-- Name: player_moves; Type: TABLE; Schema: public; Owner: testDB; Tablespace: 
--

CREATE TABLE player_moves (
    player_move_id bigint NOT NULL,
    requirements_matrix_data_id bigint NOT NULL,
    player_id bigint NOT NULL,
    value bigint,
    played boolean DEFAULT false NOT NULL
);


ALTER TABLE player_moves OWNER TO "testDB";

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
-- Name: requirements_matrices; Type: TABLE; Schema: public; Owner: testDB; Tablespace: 
--

CREATE TABLE requirements_matrices (
    requirements_matrix_id bigint NOT NULL,
    game_id bigint NOT NULL,
    criteria_id bigint NOT NULL
);


ALTER TABLE requirements_matrices OWNER TO "testDB";

--
-- Name: requirements_matrices_data; Type: TABLE; Schema: public; Owner: testDB; Tablespace: 
--

CREATE TABLE requirements_matrices_data (
    requirements_matrix_data_id bigint NOT NULL,
    requirements_matrix_id bigint NOT NULL,
    "row" bigint NOT NULL,
    "column" bigint NOT NULL,
    value bigint
);


ALTER TABLE requirements_matrices_data OWNER TO "testDB";

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
-- Data for Name: criterias_matrices; Type: TABLE DATA; Schema: public; Owner: testDB
--

COPY criterias_matrices (criterias_matrix_id, game_id) FROM stdin;
518	505
519	506
\.


--
-- Data for Name: criterias_matrices_data; Type: TABLE DATA; Schema: public; Owner: testDB
--

COPY criterias_matrices_data (criterias_matrix_data_id, criterias_matrix_id, "row", "column", value) FROM stdin;
520	518	64	65	0
521	518	64	67	0
522	518	65	67	0
\.


--
-- Data for Name: game_criterias; Type: TABLE DATA; Schema: public; Owner: testDB
--

COPY game_criterias (game_criteria_id, game_id, criteria_id) FROM stdin;
507	505	64
508	505	65
509	505	67
\.


--
-- Data for Name: game_players; Type: TABLE DATA; Schema: public; Owner: testDB
--

COPY game_players (game_player_id, game_id, player_id) FROM stdin;
514	505	117
515	505	127
516	505	29
517	505	35
\.


--
-- Data for Name: game_requirements; Type: TABLE DATA; Schema: public; Owner: testDB
--

COPY game_requirements (game_requirement_id, game_id, requirement_id) FROM stdin;
510	505	55
511	505	57
512	505	58
513	505	60
\.


--
-- Data for Name: games; Type: TABLE DATA; Schema: public; Owner: testDB
--

COPY games (game_id, start_time) FROM stdin;
505	2016-01-15 10:18:09.916+00
506	2016-01-15 10:19:49.332+00
\.


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: testDB
--

SELECT pg_catalog.setval('hibernate_sequence', 642, true);


--
-- Data for Name: judge_acts; Type: TABLE DATA; Schema: public; Owner: testDB
--

COPY judge_acts (judge_act_id, requirements_matrix_data_id, judge_id, voted) FROM stdin;
625	526	\N	f
626	527	\N	f
627	528	\N	f
628	529	\N	f
629	530	\N	f
630	531	\N	f
631	532	\N	f
632	533	\N	f
633	534	\N	f
634	535	\N	f
635	536	\N	f
636	537	\N	f
637	538	\N	f
638	539	\N	f
639	540	\N	f
640	541	\N	f
641	542	\N	f
642	543	\N	f
\.


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
500	497	127	t	f	\N	209	422	t	t	f	t
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
472	test	2015-12-18 09:54:18.155+00	3600	t	55	57	35	117	66	55	57	t	f	t	55
497	test	2016-01-11 10:20:45.387+00	3600	t	60	53	35	117	65	60	53	t	f	t	60
\.


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
-- Data for Name: player_moves; Type: TABLE DATA; Schema: public; Owner: testDB
--

COPY player_moves (player_move_id, requirements_matrix_data_id, player_id, value, played) FROM stdin;
544	526	117	-1	f
545	526	127	-1	f
546	526	29	-1	f
548	527	117	-1	f
549	527	127	-1	f
550	527	29	-1	f
552	528	117	-1	f
553	528	127	-1	f
554	528	29	-1	f
555	528	35	-1	f
556	529	117	-1	f
557	529	127	-1	f
558	529	29	-1	f
559	529	35	-1	f
560	530	117	-1	f
561	530	127	-1	f
562	530	29	-1	f
563	530	35	-1	f
564	531	117	-1	f
565	531	127	-1	f
566	531	29	-1	f
567	531	35	-1	f
568	532	117	-1	f
569	532	127	-1	f
570	532	29	-1	f
571	532	35	-1	f
572	533	117	-1	f
573	533	127	-1	f
574	533	29	-1	f
575	533	35	-1	f
576	534	117	-1	f
577	534	127	-1	f
578	534	29	-1	f
579	534	35	-1	f
580	535	117	-1	f
581	535	127	-1	f
582	535	29	-1	f
583	535	35	-1	f
584	536	117	-1	f
585	536	127	-1	f
586	536	29	-1	f
587	536	35	-1	f
588	537	117	-1	f
589	537	127	-1	f
590	537	29	-1	f
591	537	35	-1	f
592	538	117	-1	f
593	538	127	-1	f
594	538	29	-1	f
595	538	35	-1	f
596	539	117	-1	f
597	539	127	-1	f
598	539	29	-1	f
599	539	35	-1	f
600	540	117	-1	f
601	540	127	-1	f
602	540	29	-1	f
603	540	35	-1	f
604	541	117	-1	f
605	541	127	-1	f
606	541	29	-1	f
607	541	35	-1	f
608	542	117	-1	f
609	542	127	-1	f
610	542	29	-1	f
611	542	35	-1	f
612	543	117	-1	f
613	543	127	-1	f
614	543	29	-1	f
615	543	35	-1	f
547	526	35	7	t
551	527	35	8	t
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
616	Choose 0	0
617	Choose 1	1
618	Choose 2	2
619	Choose 3	3
620	Choose 4	4
621	Choose 5	5
622	Choose 6	6
623	Choose 7	7
624	Choose 8	8
\.


--
-- Data for Name: requirements_matrices; Type: TABLE DATA; Schema: public; Owner: testDB
--

COPY requirements_matrices (requirements_matrix_id, game_id, criteria_id) FROM stdin;
523	505	64
524	505	65
525	505	67
\.


--
-- Data for Name: requirements_matrices_data; Type: TABLE DATA; Schema: public; Owner: testDB
--

COPY requirements_matrices_data (requirements_matrix_data_id, requirements_matrix_id, "row", "column", value) FROM stdin;
526	523	57	55	-1
527	523	58	55	-1
528	523	58	57	-1
529	523	60	55	-1
530	523	60	57	-1
531	523	60	58	-1
532	524	57	55	-1
533	524	58	55	-1
534	524	58	57	-1
535	524	60	55	-1
536	524	60	57	-1
537	524	60	58	-1
538	525	57	55	-1
539	525	58	55	-1
540	525	58	57	-1
541	525	60	55	-1
542	525	60	57	-1
543	525	60	58	-1
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
35	65	5	395
117	65	5	394
\.


--
-- Data for Name: users_points; Type: TABLE DATA; Schema: public; Owner: testDB
--

COPY users_points (user_id, user_points, users_points_id) FROM stdin;
-1	0	-1
117	104	-4
35	141	-3
29	6	-2
127	15	-5
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
-- Name: criterias_matrices_data_primary_key; Type: CONSTRAINT; Schema: public; Owner: testDB; Tablespace: 
--

ALTER TABLE ONLY criterias_matrices_data
    ADD CONSTRAINT criterias_matrices_data_primary_key PRIMARY KEY (criterias_matrix_data_id);


--
-- Name: criterias_matrices_primary_key; Type: CONSTRAINT; Schema: public; Owner: testDB; Tablespace: 
--

ALTER TABLE ONLY criterias_matrices
    ADD CONSTRAINT criterias_matrices_primary_key PRIMARY KEY (criterias_matrix_id);


--
-- Name: game_criterias_primary_key; Type: CONSTRAINT; Schema: public; Owner: testDB; Tablespace: 
--

ALTER TABLE ONLY game_criterias
    ADD CONSTRAINT game_criterias_primary_key PRIMARY KEY (game_criteria_id);


--
-- Name: game_players_primary_key; Type: CONSTRAINT; Schema: public; Owner: testDB; Tablespace: 
--

ALTER TABLE ONLY game_players
    ADD CONSTRAINT game_players_primary_key PRIMARY KEY (game_player_id);


--
-- Name: game_requirements_primary_key; Type: CONSTRAINT; Schema: public; Owner: testDB; Tablespace: 
--

ALTER TABLE ONLY game_requirements
    ADD CONSTRAINT game_requirements_primary_key PRIMARY KEY (game_requirement_id);


--
-- Name: games_primary_key; Type: CONSTRAINT; Schema: public; Owner: testDB; Tablespace: 
--

ALTER TABLE ONLY games
    ADD CONSTRAINT games_primary_key PRIMARY KEY (game_id);


--
-- Name: judge_acts_primary_key; Type: CONSTRAINT; Schema: public; Owner: testDB; Tablespace: 
--

ALTER TABLE ONLY judge_acts
    ADD CONSTRAINT judge_acts_primary_key PRIMARY KEY (judge_act_id);


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
-- Name: player_moves_primary_key; Type: CONSTRAINT; Schema: public; Owner: testDB; Tablespace: 
--

ALTER TABLE ONLY player_moves
    ADD CONSTRAINT player_moves_primary_key PRIMARY KEY (player_move_id);


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
-- Name: requirements_matrices_data_primary_key; Type: CONSTRAINT; Schema: public; Owner: testDB; Tablespace: 
--

ALTER TABLE ONLY requirements_matrices_data
    ADD CONSTRAINT requirements_matrices_data_primary_key PRIMARY KEY (requirements_matrix_data_id);


--
-- Name: requirements_matrices_primary_key; Type: CONSTRAINT; Schema: public; Owner: testDB; Tablespace: 
--

ALTER TABLE ONLY requirements_matrices
    ADD CONSTRAINT requirements_matrices_primary_key PRIMARY KEY (requirements_matrix_id);


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
-- Name: criterias_matrices_foreign_key; Type: FK CONSTRAINT; Schema: public; Owner: testDB
--

ALTER TABLE ONLY criterias_matrices_data
    ADD CONSTRAINT criterias_matrices_foreign_key FOREIGN KEY (criterias_matrix_id) REFERENCES criterias_matrices(criterias_matrix_id);


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
-- Name: first_requirements_foreign_key; Type: FK CONSTRAINT; Schema: public; Owner: testDB
--

ALTER TABLE ONLY requirements_matrices_data
    ADD CONSTRAINT first_requirements_foreign_key FOREIGN KEY ("row") REFERENCES requirements(requirement_id);


--
-- Name: first_users_foreign_key; Type: FK CONSTRAINT; Schema: public; Owner: testDB
--

ALTER TABLE ONLY moves
    ADD CONSTRAINT first_users_foreign_key FOREIGN KEY (first_player_id) REFERENCES users(user_id);


--
-- Name: first_valutation_criterias_foreign_key; Type: FK CONSTRAINT; Schema: public; Owner: testDB
--

ALTER TABLE ONLY criterias_matrices_data
    ADD CONSTRAINT first_valutation_criterias_foreign_key FOREIGN KEY ("row") REFERENCES valutation_criterias(criteria_id);


--
-- Name: fourth_requirements_foreign_key; Type: FK CONSTRAINT; Schema: public; Owner: testDB
--

ALTER TABLE ONLY moves
    ADD CONSTRAINT fourth_requirements_foreign_key FOREIGN KEY (second_player_requirement) REFERENCES requirements(requirement_id);


--
-- Name: games_foreign_key; Type: FK CONSTRAINT; Schema: public; Owner: testDB
--

ALTER TABLE ONLY game_criterias
    ADD CONSTRAINT games_foreign_key FOREIGN KEY (game_id) REFERENCES games(game_id);


--
-- Name: games_foreign_key; Type: FK CONSTRAINT; Schema: public; Owner: testDB
--

ALTER TABLE ONLY game_players
    ADD CONSTRAINT games_foreign_key FOREIGN KEY (game_id) REFERENCES games(game_id);


--
-- Name: games_foreign_key; Type: FK CONSTRAINT; Schema: public; Owner: testDB
--

ALTER TABLE ONLY game_requirements
    ADD CONSTRAINT games_foreign_key FOREIGN KEY (game_id) REFERENCES games(game_id);


--
-- Name: games_foreign_key; Type: FK CONSTRAINT; Schema: public; Owner: testDB
--

ALTER TABLE ONLY criterias_matrices
    ADD CONSTRAINT games_foreign_key FOREIGN KEY (game_id) REFERENCES games(game_id);


--
-- Name: games_foreign_key; Type: FK CONSTRAINT; Schema: public; Owner: testDB
--

ALTER TABLE ONLY requirements_matrices
    ADD CONSTRAINT games_foreign_key FOREIGN KEY (game_id) REFERENCES games(game_id);


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
-- Name: requirements_foreign_key; Type: FK CONSTRAINT; Schema: public; Owner: testDB
--

ALTER TABLE ONLY game_requirements
    ADD CONSTRAINT requirements_foreign_key FOREIGN KEY (requirement_id) REFERENCES requirements(requirement_id);


--
-- Name: requirements_matrices_data_foreign_key; Type: FK CONSTRAINT; Schema: public; Owner: testDB
--

ALTER TABLE ONLY player_moves
    ADD CONSTRAINT requirements_matrices_data_foreign_key FOREIGN KEY (requirements_matrix_data_id) REFERENCES requirements_matrices_data(requirements_matrix_data_id);


--
-- Name: requirements_matrices_data_foreign_key; Type: FK CONSTRAINT; Schema: public; Owner: testDB
--

ALTER TABLE ONLY judge_acts
    ADD CONSTRAINT requirements_matrices_data_foreign_key FOREIGN KEY (requirements_matrix_data_id) REFERENCES requirements_matrices_data(requirements_matrix_data_id);


--
-- Name: requirements_matrices_foreign_key; Type: FK CONSTRAINT; Schema: public; Owner: testDB
--

ALTER TABLE ONLY requirements_matrices_data
    ADD CONSTRAINT requirements_matrices_foreign_key FOREIGN KEY (requirements_matrix_id) REFERENCES requirements_matrices(requirements_matrix_id);


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
-- Name: second_requirements_foreign_key; Type: FK CONSTRAINT; Schema: public; Owner: testDB
--

ALTER TABLE ONLY requirements_matrices_data
    ADD CONSTRAINT second_requirements_foreign_key FOREIGN KEY ("column") REFERENCES requirements(requirement_id);


--
-- Name: second_users_foreign_key; Type: FK CONSTRAINT; Schema: public; Owner: testDB
--

ALTER TABLE ONLY moves
    ADD CONSTRAINT second_users_foreign_key FOREIGN KEY (second_player_id) REFERENCES users(user_id);


--
-- Name: second_valutation_criterias_foreign_key; Type: FK CONSTRAINT; Schema: public; Owner: testDB
--

ALTER TABLE ONLY criterias_matrices_data
    ADD CONSTRAINT second_valutation_criterias_foreign_key FOREIGN KEY ("column") REFERENCES valutation_criterias(criteria_id);


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
-- Name: users_foreign_key; Type: FK CONSTRAINT; Schema: public; Owner: testDB
--

ALTER TABLE ONLY game_players
    ADD CONSTRAINT users_foreign_key FOREIGN KEY (player_id) REFERENCES users(user_id);


--
-- Name: users_foreign_key; Type: FK CONSTRAINT; Schema: public; Owner: testDB
--

ALTER TABLE ONLY player_moves
    ADD CONSTRAINT users_foreign_key FOREIGN KEY (player_id) REFERENCES users(user_id);


--
-- Name: users_foreign_key; Type: FK CONSTRAINT; Schema: public; Owner: testDB
--

ALTER TABLE ONLY judge_acts
    ADD CONSTRAINT users_foreign_key FOREIGN KEY (judge_id) REFERENCES users(user_id);


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
-- Name: valutation_criterias_foreign_key; Type: FK CONSTRAINT; Schema: public; Owner: testDB
--

ALTER TABLE ONLY game_criterias
    ADD CONSTRAINT valutation_criterias_foreign_key FOREIGN KEY (criteria_id) REFERENCES valutation_criterias(criteria_id);


--
-- Name: valutation_criterias_foreign_key; Type: FK CONSTRAINT; Schema: public; Owner: testDB
--

ALTER TABLE ONLY requirements_matrices
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

