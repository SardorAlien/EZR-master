-- drop table if exists users;
-- drop table if exists roles;
-- drop table if exists user_role;
-- drop table if exists authorities;
-- drop table if exists role_authority;
--
-- create table if not exists users
-- (
--     id                      bigint not null
--         primary key,
--     account_non_expired     boolean,
--     account_non_locked      boolean,
--     credentials_non_expired boolean,
--     email                   varchar(255),
--     enabled                 boolean,
--     firstname               varchar(255),
--     lastname                varchar(255),
--     password                varchar(255),
--     username                varchar(255)
-- );
-- create table if not exists roles
-- (
--     id   bigint not null
--         primary key,
--     name varchar(255)
-- );
--
-- create table if not exists user_role
-- (
--     user_id bigint not null
--         constraint fkj345gk1bovqvfame88rcx7yyx
--             references users,
--     role_id bigint not null
--         constraint fkt7e7djp752sqn6w22i6ocqy6q
--             references roles,
--     primary key (user_id, role_id)
-- );
--
-- create table if not exists authorities
-- (
--     id         bigint not null
--         primary key,
--     permission varchar(255)
-- );
--
-- create table if not exists role_authority
-- (
--     role_id      bigint not null
--         constraint fk78r7yh1uqg30liv2n75ay99j
--             references roles,
--     authority_id bigint not null
--         constraint fkpduid6tx7e38l03s86446514r
--             references authorities,
--     primary key (role_id, authority_id)
-- );


-- Work Around: Works when you change the file name to schema.sql from scheme.sql or explicitly
-- run the file
CREATE OR REPLACE FUNCTION track_deck_visits()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$function$
DECLARE
    streak_id integer;
BEGIN
    IF NEW.last_visited_at <> OLD.last_visited_at THEN
        INSERT INTO decks_visits(visits, deck_id)
        VALUES (NEW.last_visited_at, NEW.id);

        SELECT streaks.id
        INTO streak_id
        FROM streaks
        WHERE streaks.user_id = OLD.user_id
        ORDER BY streaks.last_streak_date_time DESC
        LIMIT 1;

        raise notice 'streak_id: %s', streak_id;

        IF streak_id IS NULL THEN
            INSERT INTO streaks(current_streak, user_id, last_streak_date_time)
            VALUES (1, OLD.user_id, now());
        END IF;

        IF streak_id IS NOT NULL THEN
            IF extract('day' FROM NEW.last_visited_at) - extract('day' FROM OLD.last_visited_at) = 1 THEN
                UPDATE streaks SET current_streak = current_streak + 1 WHERE streaks.id = streak_id;
            END IF;
            IF extract('day' FROM NEW.last_visited_at) - extract('day' FROM OLD.last_visited_at) > 1 THEN
                INSERT INTO streaks(current_streak, user_id, last_streak_date_time)
                VALUES (1, OLD.user_id, now());
            END IF;

            raise notice 'extracting the day: %s', extract('day' FROM new.last_visited_at);
        END IF;
    END IF;
    RETURN NEW;
END;
$function$;

CREATE TRIGGER deck_visit_changes
    BEFORE UPDATE
    ON decks
    FOR EACH ROW
EXECUTE PROCEDURE track_deck_visits();