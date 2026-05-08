INSERT INTO player (name, is_approved, gender, birth_date, self_evaluation, p_ranking, telephone, email, preferred_playside, profile_picture_url)
VALUES
('Liam Carter', true, 'M', '1998-05-12', 'GEMIDDELD', 500, '111111111', 'liam@example.com', 'RECHTS', ''),
('Noah Schmidt', true, 'M', '1995-03-22', 'GEVORDERD', 700, '111111112', 'noah@example.com', 'LINKS', ''),
('Emma Novak', true, 'F', '2000-07-10', 'BEGINNER', 300, '111111113', 'emma@example.com', 'GEEN', ''),
('Olivia Dubois', true, 'F', '1999-01-15', 'GEMIDDELD', 500, '111111114', 'olivia@example.com', 'RECHTS', ''),
('Lucas Silva', true, 'M', '1997-11-02', 'PRO', 1000, '111111115', 'lucas@example.com', 'LINKS', ''),
('Sofia Ivanova', true, 'F', '1996-09-09', 'GEMIDDELD', 500, '111111116', 'sofia@example.com', 'RECHTS', ''),
('Ethan Brown', true, 'M', '1994-06-18', 'GEVORDERD', 700, '111111117', 'ethan@example.com', 'LINKS', ''),
('Mia Andersson', true, 'F', '2001-02-25', 'BEGINNER', 50, '111111118', 'mia@example.com', 'GEEN', ''),
('Daniel Kim', true, 'M', '1993-12-30', 'GEMIDDELD', 400, '111111119', 'daniel@example.com', 'RECHTS', ''),
('Chloe Martin', true, 'F', '1998-08-08', 'GEMIDDELD', 500, '111111120', 'chloe@example.com', 'LINKS', ''),
('Alex Johnson', true, 'M', '1992-04-14', 'PRO', 1000, '111111121', 'alex@example.com', 'RECHTS', ''),
('Sara Khan', true, 'F', '2002-10-05', 'BEGINNER', 200, '111111122', 'sara@example.com', 'GEEN', '');

INSERT INTO field (name, is_outside)
VALUES
('1A',true),
('1B',false),
('5', true);

-- INSERT INTO team (averagepranking)
-- VALUES
-- (6.0),
-- (4.0),
-- (7.5),
-- (5.5),
-- (6.5),
-- (3.0);

-- INSERT INTO match (p_ranking_difference, time_slot)
-- VALUES
-- (1.0, '18:00:00'),
-- (2.0, '18:00:00'),
-- (1.5, '18:00:00'),
--
-- (0.5, '18:40:00'),
-- (1.2, '18:40:00'),
-- (2.5, '18:40:00'),
--
-- (1.0, '19:20:00'),
-- (0.8, '19:20:00'),
-- (1.7, '19:20:00');

-- INSERT INTO padel_day (date, number_of_matches)
-- VALUES
-- ('2026-04-25 18:00:00', 9);

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