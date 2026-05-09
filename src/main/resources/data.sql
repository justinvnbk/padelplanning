INSERT INTO player (name, is_admin, gender, birth_date, self_evaluation, p_ranking, telephone, email, preferred_playside, profile_picture_url, has_unseen_notifications)
VALUES
('Liam Carter', false, 'M', '1998-05-12', 'Intermediate', 500, '111111111', 'liam@example.com', 'RIGHT', '',false),
('Noah Schmidt', false, 'M', '1995-03-22', 'Advanced', 700, '111111112', 'noah@example.com', 'LEFT', '',false),
('Emma Novak', false, 'F', '2000-07-10', 'Beginner', 300, '111111113', 'emma@example.com', 'NONE', '',false),
('Olivia Dubois', false, 'F', '1999-01-15', 'Intermediate', 500, '111111114', 'olivia@example.com', 'RIGHT', '',false),
('Lucas Silva', false, 'M', '1997-11-02', 'Advanced', 800, '111111115', 'lucas@example.com', 'LEFT', '',false),
('Sofia Ivanova', false, 'F', '1996-09-09', 'Intermediate', 600, '111111116', 'sofia@example.com', 'RIGHT', '',false),
('Ethan Brown', false, 'M', '1994-06-18', 'Advanced', 700, '111111117', 'ethan@example.com', 'LEFT', '',false),
('Mia Andersson', false, 'F', '2001-02-25', 'Beginner', 200, '111111118', 'mia@example.com', 'NONE', '',false),
('Daniel Kim', false, 'M', '1993-12-30', 'Intermediate', 600, '111111119', 'daniel@example.com', 'RIGHT', '',false),
('Chloe Martin', false, 'F', '1998-08-08', 'Intermediate', 500, '111111120', 'chloe@example.com', 'LEFT', '',false),
('Alex Johnson', true, 'M', '1992-04-14', 'Advanced', 900, '111111121', 'alex@example.com', 'RIGHT', '',false),
('Sara Khan', false, 'F', '2002-10-05', 'Beginner', 300, '111111122', 'sara@example.com', 'NONE', '',false);

INSERT INTO field (name, is_outside)
VALUES
('A',true),
('B',false),
('C', true),
('D',false),
('1',true),
('2',true),
('3',false),
('4',false);

-- Liam Carter
INSERT INTO USERS (USERNAME, PASSWORD, ENABLED)
VALUES ('liam@example.com', '$2a$10$CB8dz.jJdSTQvmtUa45Yfuixhwh/nKLXFjVsDrU1gxqPDRKAaPCqO', true);
-- password: abcd1234
INSERT INTO AUTHORITIES (USERNAME, AUTHORITY)
VALUES ('liam@example.com', 'USER');

-- Noah Schmidt
INSERT INTO USERS (USERNAME, PASSWORD, ENABLED)
VALUES ('noah@example.com', '$2a$10$CB8dz.jJdSTQvmtUa45Yfuixhwh/nKLXFjVsDrU1gxqPDRKAaPCqO', true);
-- password: abcd1234
INSERT INTO AUTHORITIES (USERNAME, AUTHORITY)
VALUES ('noah@example.com', 'USER');

-- Emma Novak
INSERT INTO USERS (USERNAME, PASSWORD, ENABLED)
VALUES ('emma@example.com', '$2a$10$CB8dz.jJdSTQvmtUa45Yfuixhwh/nKLXFjVsDrU1gxqPDRKAaPCqO', true);
-- password: abcd1234
INSERT INTO AUTHORITIES (USERNAME, AUTHORITY)
VALUES ('emma@example.com', 'USER');

-- Olivia Dubois
INSERT INTO USERS (USERNAME, PASSWORD, ENABLED)
VALUES ('olivia@example.com', '$2a$10$CB8dz.jJdSTQvmtUa45Yfuixhwh/nKLXFjVsDrU1gxqPDRKAaPCqO', true);
-- password: abcd1234
INSERT INTO AUTHORITIES (USERNAME, AUTHORITY)
VALUES ('olivia@example.com', 'USER');

-- Lucas Silva
INSERT INTO USERS (USERNAME, PASSWORD, ENABLED)
VALUES ('lucas@example.com', '$2a$10$CB8dz.jJdSTQvmtUa45Yfuixhwh/nKLXFjVsDrU1gxqPDRKAaPCqO', true);
-- password: abcd1234
INSERT INTO AUTHORITIES (USERNAME, AUTHORITY)
VALUES ('lucas@example.com', 'USER');

-- Sofia Ivanova
INSERT INTO USERS (USERNAME, PASSWORD, ENABLED)
VALUES ('sofia@example.com', '$2a$10$CB8dz.jJdSTQvmtUa45Yfuixhwh/nKLXFjVsDrU1gxqPDRKAaPCqO', true);
-- password: abcd1234
INSERT INTO AUTHORITIES (USERNAME, AUTHORITY)
VALUES ('sofia@example.com', 'USER');

-- Ethan Brown
INSERT INTO USERS (USERNAME, PASSWORD, ENABLED)
VALUES ('ethan@example.com', '$2a$10$CB8dz.jJdSTQvmtUa45Yfuixhwh/nKLXFjVsDrU1gxqPDRKAaPCqO', true);
-- password: abcd1234
INSERT INTO AUTHORITIES (USERNAME, AUTHORITY)
VALUES ('ethan@example.com', 'USER');

-- Mia Andersson
INSERT INTO USERS (USERNAME, PASSWORD, ENABLED)
VALUES ('mia@example.com', '$2a$10$CB8dz.jJdSTQvmtUa45Yfuixhwh/nKLXFjVsDrU1gxqPDRKAaPCqO', true);
-- password: abcd1234
INSERT INTO AUTHORITIES (USERNAME, AUTHORITY)
VALUES ('mia@example.com', 'USER');

-- Daniel Kim
INSERT INTO USERS (USERNAME, PASSWORD, ENABLED)
VALUES ('daniel@example.com', '$2a$10$CB8dz.jJdSTQvmtUa45Yfuixhwh/nKLXFjVsDrU1gxqPDRKAaPCqO', true);
-- password: abcd1234
INSERT INTO AUTHORITIES (USERNAME, AUTHORITY)
VALUES ('daniel@example.com', 'USER');

-- Chloe Martin
INSERT INTO USERS (USERNAME, PASSWORD, ENABLED)
VALUES ('chloe@example.com', '$2a$10$CB8dz.jJdSTQvmtUa45Yfuixhwh/nKLXFjVsDrU1gxqPDRKAaPCqO', true);
-- password: abcd1234
INSERT INTO AUTHORITIES (USERNAME, AUTHORITY)
VALUES ('chloe@example.com', 'USER');

-- Alex Johnson (Admin)
INSERT INTO USERS (USERNAME, PASSWORD, ENABLED)
VALUES ('alex@example.com', '$2a$10$CB8dz.jJdSTQvmtUa45Yfuixhwh/nKLXFjVsDrU1gxqPDRKAaPCqO', true);
-- password: abcd1234
INSERT INTO AUTHORITIES (USERNAME, AUTHORITY)
VALUES ('alex@example.com', 'ADMIN');

-- Sara Khan
INSERT INTO USERS (USERNAME, PASSWORD, ENABLED)
VALUES ('sara@example.com', '$2a$10$CB8dz.jJdSTQvmtUa45Yfuixhwh/nKLXFjVsDrU1gxqPDRKAaPCqO', true);
-- password: abcd1234
INSERT INTO AUTHORITIES (USERNAME, AUTHORITY)
VALUES ('sara@example.com', 'USER');


-- Test padeldag met inschijvingen:
INSERT INTO padel_day (date, number_of_matches)
VALUES
('2026-07-06 18:00:00', 3);
--
INSERT INTO PADEL_DAY_FIELDS (FIELDS_ID,PADEL_DAYS_ID)
VALUES
    ('1','1'),
    ('2','1'),
    ('3','1');
--
INSERT INTO PADEL_DAY_RESERVED_PLAYERS (PADEL_DAY_ID, RESERVED_PLAYERS_ID)
VALUES
    ('1','1'),
    ('1','2'),
    ('1','3');

INSERT INTO PADEL_DAY_SIGNED_UP_PLAYERS (PADEL_DAY_ID, SIGNED_UP_PLAYERS_ID)
VALUES
    ('1','4'),
    ('1','5'),
    ('1','6'),
    ('1','7');


--TEST DATA FOR NOTIFICATIONS
INSERT INTO NOTIFICATION (DATE_TIME, MESSAGE, TITLE)
VALUES
    ('2026-07-06 18:00:00', 'TEST MESSAGE', 'TEST1'),
    ('2026-08-06 18:00:00', 'TEST MESSAGE', 'TEST2'),
    ('2026-09-06 18:00:00', 'TEST MESSAGE', 'TEST3'),
    ('2026-10-06 18:00:00', 'TEST MESSAGE', 'TEST4'),
    ('2026-11-06 18:00:00', 'TEST MESSAGE', 'TEST5'),
    ('2026-12-06 18:00:00', 'TEST MESSAGE', 'TEST6'),
    ('2026-05-06 18:00:00', 'TEST MESSAGE', 'TEST7'),
    ('2026-04-06 18:00:00', 'TEST MESSAGE', 'TEST8'),
    ('2026-03-06 18:00:00', 'TEST MESSAGE', 'TEST9'),
    ('2026-02-06 18:00:00', 'TEST MESSAGE', 'TEST10'),
    ('2026-01-06 18:00:00', 'TEST MESSAGE', 'TEST11');

INSERT INTO NOTIFICATION_RECIPIENTS (NOTIFICATIONS_ID, RECIPIENTS_ID)
VALUES
    (1,11),
    (2,11),
    (3,11),
    (4,11),
    (5,11),
    (6,11),
    (7,11),
    (8,11),
    (9,11),
    (10,11),
    (11,11);