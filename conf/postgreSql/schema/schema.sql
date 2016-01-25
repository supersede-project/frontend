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

