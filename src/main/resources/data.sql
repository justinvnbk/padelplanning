--PLAYERS
INSERT INTO player (name, is_approved, gender, birth_date, self_evaluation, p_ranking, telephone, email, preferred_playside, profile_picture_url, has_unseen_notifications)
VALUES
    ('Liam Carter', true, 'M', '1998-05-12', 'GEMIDDELD', 500, '+32111111111', 'liam@example.com', 'RECHTS', '', false),
    ('Noah Schmidt', true, 'M', '1995-03-22', 'GEVORDERD', 700, '+32111111112', 'noah@example.com', 'LINKS', '', false),
    ('Emma Novak', true, 'F', '2000-07-10', 'BEGINNER', 300, '+32111111113', 'emma@example.com', 'GEEN', '', false),
    ('Olivia Dubois', true, 'F', '1999-01-15', 'GEMIDDELD', 500, '+32111111114', 'olivia@example.com', 'RECHTS', '', false),
    ('Lucas Silva', true, 'M', '1997-11-02', 'PRO', 1000, '+32111111115', 'lucas@example.com', 'LINKS', '', false),
    ('Sofia Ivanova', true, 'F', '1996-09-09', 'GEMIDDELD', 500, '+32111111116', 'sofia@example.com', 'RECHTS', '', false),
    ('Ethan Brown', true, 'M', '1994-06-18', 'GEVORDERD', 700, '+32111111117', 'ethan@example.com', 'LINKS', '', false),
    ('Mia Andersson', true, 'F', '2001-02-25', 'BEGINNER', 50, '+32111111118', 'mia@example.com', 'GEEN', '', false),
    ('Daniel Kim', true, 'M', '1993-12-30', 'GEMIDDELD', 400, '+32111111119', 'daniel@example.com', 'RECHTS', '', false),
    ('Chloe Martin', true, 'F', '1998-08-08', 'GEMIDDELD', 500, '+32111111120', 'chloe@example.com', 'LINKS', '', false),
    ('Tom Van Den Broeck', true, 'M', '1992-04-14', 'PRO', 1000, '+32111111121', 'tomvdb@example.com', 'RECHTS', '', false),
    ('Max', true, 'M', '1992-04-14', 'PRO', 1000, '+32111111121', 'max@example.com', 'RECHTS', '', false),
    ('Sara Khan', true, 'F', '2002-10-05', 'BEGINNER', 200, '+32111111122', 'sara@example.com', 'GEEN', '', false),
    ('Amelie Lefebvre', true, 'F', '1994-11-22', 'GEMIDDELD', 550, '+32111111124', 'amelie@example.com', 'RECHTS', '', false),
    ('Lukas Weber', true, 'M', '1988-06-05', 'PRO', 1100, '+32111111125', 'lukasw@example.com', 'LINKS', '', false),
    ('Elena Rossi', true, 'F', '1996-01-19', 'BEGINNER', 250, '+32111111126', 'elena@example.com', 'GEEN', '', false),
    ('William Jones', true, 'M', '1992-08-14', 'GEMIDDELD', 450, '+32111111127', 'william@example.com', 'RECHTS', '', false),
    ('Isabella Martinez', true, 'F', '2000-12-01', 'GEVORDERD', 650, '+32111111128', 'isabella@example.com', 'LINKS', '', false),
    ('Arjun Singh', true, 'M', '1995-05-30', 'GEMIDDELD', 500, '+32111111129', 'arjun@example.com', 'RECHTS', '', false),
    ('Zoe Chen', true, 'F', '1997-09-12', 'PRO', 950, '+32111111130', 'zoe@example.com', 'LINKS', '', false),
    ('Matteo Conti', true, 'M', '1991-04-25', 'BEGINNER', 150, '+32111111131', 'matteo@example.com', 'GEEN', '', false),
    ('Sophie Müller', true, 'F', '1993-07-08', 'GEMIDDELD', 520, '+32111111132', 'sophie@example.com', 'RECHTS', '', false),
    ('Hugo Nielsen', true, 'M', '1985-10-10', 'GEVORDERD', 800, '+32111111133', 'hugo@example.com', 'LINKS', '', false),
    ('Clara Vidal', true, 'F', '1999-02-28', 'GEMIDDELD', 480, '+32111111134', 'clara@example.com', 'RECHTS', '', false),
    ('Gabriel Santos', true, 'M', '1996-12-14', 'PRO', 1050, '+32111111135', 'gabriel@example.com', 'LINKS', '', false),
    ('Layla Haddad', true, 'F', '2001-05-05', 'BEGINNER', 100, '+32111111136', 'layla@example.com', 'GEEN', '', false),
    ('Oscar Berg', true, 'M', '1994-08-20', 'GEMIDDELD', 500, '+32111111137', 'oscar@example.com', 'RECHTS', '', false),
    ('Yuki Tanaka', true, 'F', '1998-03-11', 'GEVORDERD', 720, '+32111111138', 'yuki@example.com', 'LINKS', '', false),
    ('Thomas Wright', true, 'M', '1990-11-30', 'GEMIDDELD', 400, '+32111111139', 'thomas@example.com', 'RECHTS', '', false),
    ('Nina Petrova', true, 'F', '1995-06-25', 'PRO', 1200, '+32111111140', 'nina@example.com', 'LINKS', '', false),
    ('Justin Van Beek', false, 'M', '1998-05-12', 'GEMIDDELD', 500, '+32111111111', 'justin@example.com', 'RECHTS', '', false),
    ('Mustafa Akcay', false, 'M', '1995-03-22', 'GEVORDERD', 700, '+32111111112', 'mustafa@example.com', 'LINKS', '', false),
    ('Benjamin Garcia', true, 'M', '1990-03-15', 'GEVORDERD', 750, '+32111111123', 'benjamin@example.com', 'LINKS', '', false);

--USERS (LINKED TO PLAYERS BY EMAIL)
INSERT INTO USERS (USERNAME, PASSWORD, ENABLED)
VALUES
    ('liam@example.com', '$2a$10$CB8dz.jJdSTQvmtUa45Yfuixhwh/nKLXFjVsDrU1gxqPDRKAaPCqO', true),
    ('noah@example.com', '$2a$10$CB8dz.jJdSTQvmtUa45Yfuixhwh/nKLXFjVsDrU1gxqPDRKAaPCqO', true),
    ('emma@example.com', '$2a$10$CB8dz.jJdSTQvmtUa45Yfuixhwh/nKLXFjVsDrU1gxqPDRKAaPCqO', true),
    ('olivia@example.com', '$2a$10$CB8dz.jJdSTQvmtUa45Yfuixhwh/nKLXFjVsDrU1gxqPDRKAaPCqO', true),
    ('lucas@example.com', '$2a$10$CB8dz.jJdSTQvmtUa45Yfuixhwh/nKLXFjVsDrU1gxqPDRKAaPCqO', true),
    ('sofia@example.com', '$2a$10$CB8dz.jJdSTQvmtUa45Yfuixhwh/nKLXFjVsDrU1gxqPDRKAaPCqO', true),
    ('ethan@example.com', '$2a$10$CB8dz.jJdSTQvmtUa45Yfuixhwh/nKLXFjVsDrU1gxqPDRKAaPCqO', true),
    ('mia@example.com', '$2a$10$CB8dz.jJdSTQvmtUa45Yfuixhwh/nKLXFjVsDrU1gxqPDRKAaPCqO', true),
    ('daniel@example.com', '$2a$10$CB8dz.jJdSTQvmtUa45Yfuixhwh/nKLXFjVsDrU1gxqPDRKAaPCqO', true),
    ('chloe@example.com', '$2a$10$CB8dz.jJdSTQvmtUa45Yfuixhwh/nKLXFjVsDrU1gxqPDRKAaPCqO', true),
    ('tomvdb@example.com', '$2a$10$CB8dz.jJdSTQvmtUa45Yfuixhwh/nKLXFjVsDrU1gxqPDRKAaPCqO', true),
    ('max@example.com', '$2a$10$CB8dz.jJdSTQvmtUa45Yfuixhwh/nKLXFjVsDrU1gxqPDRKAaPCqO', true),
    ('sara@example.com', '$2a$10$CB8dz.jJdSTQvmtUa45Yfuixhwh/nKLXFjVsDrU1gxqPDRKAaPCqO', true),
    ('benjamin@example.com', '$2a$10$CB8dz.jJdSTQvmtUa45Yfuixhwh/nKLXFjVsDrU1gxqPDRKAaPCqO', true),
    ('amelie@example.com', '$2a$10$CB8dz.jJdSTQvmtUa45Yfuixhwh/nKLXFjVsDrU1gxqPDRKAaPCqO', true),
    ('lukasw@example.com', '$2a$10$CB8dz.jJdSTQvmtUa45Yfuixhwh/nKLXFjVsDrU1gxqPDRKAaPCqO', true),
    ('elena@example.com', '$2a$10$CB8dz.jJdSTQvmtUa45Yfuixhwh/nKLXFjVsDrU1gxqPDRKAaPCqO', true),
    ('william@example.com', '$2a$10$CB8dz.jJdSTQvmtUa45Yfuixhwh/nKLXFjVsDrU1gxqPDRKAaPCqO', true),
    ('isabella@example.com', '$2a$10$CB8dz.jJdSTQvmtUa45Yfuixhwh/nKLXFjVsDrU1gxqPDRKAaPCqO', true),
    ('arjun@example.com', '$2a$10$CB8dz.jJdSTQvmtUa45Yfuixhwh/nKLXFjVsDrU1gxqPDRKAaPCqO', true),
    ('zoe@example.com', '$2a$10$CB8dz.jJdSTQvmtUa45Yfuixhwh/nKLXFjVsDrU1gxqPDRKAaPCqO', true),
    ('matteo@example.com', '$2a$10$CB8dz.jJdSTQvmtUa45Yfuixhwh/nKLXFjVsDrU1gxqPDRKAaPCqO', true),
    ('sophie@example.com', '$2a$10$CB8dz.jJdSTQvmtUa45Yfuixhwh/nKLXFjVsDrU1gxqPDRKAaPCqO', true),
    ('hugo@example.com', '$2a$10$CB8dz.jJdSTQvmtUa45Yfuixhwh/nKLXFjVsDrU1gxqPDRKAaPCqO', true),
    ('clara@example.com', '$2a$10$CB8dz.jJdSTQvmtUa45Yfuixhwh/nKLXFjVsDrU1gxqPDRKAaPCqO', true),
    ('gabriel@example.com', '$2a$10$CB8dz.jJdSTQvmtUa45Yfuixhwh/nKLXFjVsDrU1gxqPDRKAaPCqO', true),
    ('layla@example.com', '$2a$10$CB8dz.jJdSTQvmtUa45Yfuixhwh/nKLXFjVsDrU1gxqPDRKAaPCqO', true),
    ('oscar@example.com', '$2a$10$CB8dz.jJdSTQvmtUa45Yfuixhwh/nKLXFjVsDrU1gxqPDRKAaPCqO', true),
    ('yuki@example.com', '$2a$10$CB8dz.jJdSTQvmtUa45Yfuixhwh/nKLXFjVsDrU1gxqPDRKAaPCqO', true),
    ('thomas@example.com', '$2a$10$CB8dz.jJdSTQvmtUa45Yfuixhwh/nKLXFjVsDrU1gxqPDRKAaPCqO', true),
    ('nina@example.com', '$2a$10$CB8dz.jJdSTQvmtUa45Yfuixhwh/nKLXFjVsDrU1gxqPDRKAaPCqO', true),
    ('justin@example.com', '$2a$10$CB8dz.jJdSTQvmtUa45Yfuixhwh/nKLXFjVsDrU1gxqPDRKAaPCqO', false),
    ('mustafa@example.com', '$2a$10$CB8dz.jJdSTQvmtUa45Yfuixhwh/nKLXFjVsDrU1gxqPDRKAaPCqO', false);

--AUTHORITIES
INSERT INTO AUTHORITIES (USERNAME, AUTHORITY)
VALUES
    ('liam@example.com', 'USER'),
    ('noah@example.com', 'USER'),
    ('emma@example.com', 'USER'),
    ('olivia@example.com', 'USER'),
    ('lucas@example.com', 'USER'),
    ('sofia@example.com', 'USER'),
    ('ethan@example.com', 'USER'),
    ('mia@example.com', 'USER'),
    ('daniel@example.com', 'USER'),
    ('chloe@example.com', 'USER'),
    ('tomvdb@example.com', 'ADMIN'),
    ('max@example.com', 'ADMIN'),
    ('sara@example.com', 'USER'),
    ('mustafa@example.com', 'USER'),
    ('justin@example.com', 'USER'),
    ('benjamin@example.com', 'USER'),
    ('amelie@example.com', 'USER'),
    ('lukasw@example.com', 'USER'),
    ('elena@example.com', 'USER'),
    ('william@example.com', 'USER'),
    ('isabella@example.com', 'USER'),
    ('arjun@example.com', 'USER'),
    ('zoe@example.com', 'USER'),
    ('matteo@example.com', 'USER'),
    ('sophie@example.com', 'USER'),
    ('hugo@example.com', 'USER'),
    ('clara@example.com', 'USER'),
    ('gabriel@example.com', 'USER'),
    ('layla@example.com', 'USER'),
    ('oscar@example.com', 'USER'),
    ('yuki@example.com', 'USER'),
    ('thomas@example.com', 'USER'),
    ('nina@example.com', 'USER');


--FIELDS
INSERT INTO field (name, is_outside, is_covered)
VALUES
    ('A',false, true),
    ('B',false, true),
    ('C', false, true),
    ('1',true, true),
    ('2',true, true),
    ('3',true, true),
    ('4',true, false),
    ('5',true, false),
    ('6',true, true),
    ('7',true, true),
    ('8',true, true);

--PADELDAYS
INSERT INTO padel_day (date, number_of_matches, is_published)
VALUES
    ('2026-02-22 14:00:00', 3, false),
    ('2026-03-01 14:00:00', 3, false),
    ('2026-03-08 14:00:00', 3, false),
    ('2026-03-15 14:00:00', 3, false),
    ('2026-03-22 14:00:00', 3, false),
    ('2026-03-29 14:00:00', 3, false),
    ('2026-04-05 14:00:00', 3, false),
    ('2026-04-12 14:00:00', 3, false),
    ('2026-04-19 14:00:00', 3, false),
    ('2026-04-26 14:00:00', 3, false),
    ('2026-05-03 14:00:00', 3, false),
    ('2026-05-10 14:00:00', 3, false),
    ('2026-05-31 14:00:00', 3, true),
    (CAST(CURRENT_DATE AS TIMESTAMP) + INTERVAL '1' DAY + INTERVAL '14' HOUR, 3, true),
    (CAST(CURRENT_DATE AS TIMESTAMP) + INTERVAL '7' DAY + INTERVAL '14' HOUR, 3, false),
    (CAST(CURRENT_DATE AS TIMESTAMP) + INTERVAL '14' DAY + INTERVAL '14' HOUR, 3, false),
    (CAST(CURRENT_DATE AS TIMESTAMP) + INTERVAL '21' DAY + INTERVAL '14' HOUR, 3, false),
    (CAST(CURRENT_DATE AS TIMESTAMP) + INTERVAL '28' DAY + INTERVAL '14' HOUR, 3, false);

--AVAILABLE FIELDS FOR PADELDAYS
INSERT INTO PADEL_DAY_FIELDS (FIELDS_ID,PADEL_DAYS_ID)
VALUES
    ('1','1'),('2','1'),('3','1'),('4','1'),('5','1'),('6','1'),('7','1'),('8','1'),
    ('1','2'),('2','2'),('3','2'),('4','2'),('5','2'),('6','2'),('7','2'),('8','2'),
    ('1','3'),('2','3'),('3','3'),('4','3'),('5','3'),('6','3'),('7','3'),('8','3'),
    ('1','4'),('2','4'),('4','4'),('5','4'),('6','4'),('7','4'),('8','4'),
    ('1','5'),('2','5'),('3','5'),('4','5'),('5','5'),('6','5'),('7','5'),('8','5'),
    ('1','6'),('2','6'),('3','6'),('4','6'),('5','6'),('6','6'),('7','6'),('8','6'),
    ('1','7'),('2','7'),('3','7'),('4','7'),('5','7'),('6','7'),('7','7'),('8','7'),
    ('1','8'),('2','8'),('3','8'),('4','8'),('5','8'),('6','8'),('7','8'),('8','8'),
    ('1','9'),('2','9'),('3','9'),('4','9'),('6','9'),('7','9'),('8','9'),
    ('1','10'),('2','10'),('3','10'),('4','10'),('5','10'),('6','10'),('7','10'),('8','10'),
    ('1','11'),('2','11'),('3','11'),('5','11'),('6','11'),('7','11'),('8','11'),
    ('1','12'),('2','12'),('3','12'),('4','12'),('5','12'),('6','12'),('7','12'),('8','12'),
    ('1','13'),('2','13'),('3','13'),('4','13'),('5','13'),('6','13'),('7','13'),('8','13'),
    ('1','14'),('2','14'),('3','14'),('4','14'),('5','14'),('6','14'),('7','14'),('8','14'),
    ('1','15'),('2','15'),('3','15'),('4','15'),('5','15'),('6','15'),('8','15'),
    ('1','16'),('2','16'),('4','16'),('5','16'),('6','16'),('7','16'),('8','16'),
    ('1','17'),('2','17'),('3','17'),('5','17'),('6','17'),
    ('1','18'),('2','18'),('3','18'),('6','18'),('7','18'),('8','18');


-- PADEL_DAY_RESERVED_PLAYERS
INSERT INTO PADEL_DAY_RESERVED_PLAYERS (PADEL_DAY_ID, RESERVED_PLAYERS_ID)
VALUES
    ('1','1'), ('1','2'), ('1','3'),
    ('2','4'), ('2','5'),
    ('5','7'), ('5','8'), ('5','9'),
    ('6','10'), ('6','11'),
    ('9','13'), ('9','14'), ('9','15'),
    ('14','22'), ('14','23'),
    ('15','24');

-- PADEL_DAY_SIGNED_UP_PLAYERS
INSERT INTO PADEL_DAY_SIGNED_UP_PLAYERS (PADEL_DAY_ID, SIGNED_UP_PLAYERS_ID)
VALUES
    ('1','4'),('1','5'),('1','6'),('1','7'),('1','8'),('1','9'),('1','10'),('1','11'),('1','12'),('1','13'),('1','14'),('1','15'),
    ('2','6'),('2','7'),('2','8'),('2','9'),('2','10'),('2','11'),('2','12'),('2','13'),('2','14'),('2','15'),('2','16'),('2','17'),('2','18'),('2','19'),('2','20'),('2','21'),('2','22'),('2','23'),
    ('3','1'),('3','2'),('3','3'),('3','4'),('3','5'),('3','6'),('3','7'),('3','8'),('3','9'),('3','10'),('3','11'),('3','12'),('3','13'),('3','14'),('3','15'),('3','16'),('3','17'),('3','18'),('3','19'),('3','20'),
    ('4','1'),('4','2'),('4','3'),('4','4'),('4','5'),('4','6'),('4','7'),('4','8'),('4','9'),('4','10'),('4','11'),('4','12'),('4','13'),('4','14'),('4','15'),('4','16'),('4','17'),('4','18'),('4','19'),('4','20'),('4','21'),('4','22'),('4','23'),('4','24'),
    ('5','10'),('5','11'),('5','12'),('5','13'),('5','14'),('5','15'),('5','16'),('5','17'),('5','18'),('5','19'),('5','20'),('5','21'),
    ('6','12'),('6','13'),('6','14'),('6','15'),('6','16'),('6','17'),('6','18'),('6','19'),('6','20'),('6','21'),('6','22'),('6','23'),('6','24'),('6','25'),('6','26'),('6','27'),('6','28'),
    ('7','1'),('7','2'),('7','3'),('7','4'),('7','5'),('7','6'),('7','7'),('7','8'),('7','9'),('7','10'),('7','11'),('7','12'),('7','13'),('7','14'),('7','15'),('7','16'),('7','17'),('7','18'),('7','19'),('7','20'),
    ('8','7'),('8','8'),('8','9'),('8','10'),('8','11'),('8','12'),('8','13'),('8','14'),('8','15'),('8','16'),('8','17'),('8','18'),('8','19'),('8','20'),('8','21'),('8','22'),('8','23'),('8','24'),('8','25'),('8','26'),('8','27'),('8','28'),('8','29'),('8','30'),
    ('9','1'),('9','2'),('9','3'),('9','4'),('9','5'),('9','6'),('9','7'),('9','8'),('9','9'),('9','10'),('9','11'),('9','12'),
    ('10','1'),('10','2'),('10','3'),('10','4'),('10','5'),('10','6'),('10','7'),('10','8'),('10','9'),('10','10'),('10','11'),('10','12'),('10','13'),('10','14'),('10','15'),('10','16'),
    ('11','11'),('11','12'),('11','13'),('11','14'),('11','15'),('11','16'),('11','17'),('11','18'),('11','19'),('11','20'),('11','21'),('11','22'),('11','23'),('11','24'),('11','25'),('11','26'),('11','27'),('11','28'),('11','29'),('11','30'),
    ('12','1'),('12','2'),('12','3'),('12','4'),('12','5'),('12','6'),('12','7'),('12','8'),('12','9'),('12','10'),('12','11'),('12','12'),('12','13'),('12','14'),('12','15'),('12','16'),('12','17'),('12','18'),('12','19'),('12','20'),('12','21'),('12','22'),('12','23'),('12','24'),
    ('13','18'),('13','19'),('13','20'),('13','21'),('13','22'),('13','23'),('13','24'),('13','25'),('13','26'),('13','27'),('13','28'),('13','29'),
    ('14','5'),('14','6'),('14','7'),('14','8'),('14','9'),('14','10'),('14','11'),('14','12'),('14','13'),('14','14'),('14','15'),('14','16'),('14','17'),('14','18'),('14','19'),('14','20'),
    ('15','1'),('15','2'),('15','3'),('15','4'),('15','5'),('15','6'),('15','7'),('15','8'),('15','9'),('15','10'),('15','11'),('15','12'),('15','13'),('15','14'),('15','15'),('15','16'),('15','17'),('15','18'),('15','19'),('15','20');


--NOTIFICATIONS
INSERT INTO NOTIFICATION (DATE_TIME, MESSAGE, TITLE)
VALUES
    ('2026-07-06 17:00:00', 'Nieuwe planning', 'Een nieuwe planning is beschikbaar'),
    ('2026-10-06 15:00:00', 'Nieuw speelmoment opgestart', 'Er is een nieuw speelmoment gestart'),
    ('2026-04-06 23:00:00', 'Aanpassing speelmoment', 'Het speelmoment is aangepast'),
    ('2026-08-06 15:00:00', 'Nieuwe planning', 'Een nieuwe planning is beschikbaar'),
    ('2026-02-06 09:00:00', 'Aanpassing speelmoment', 'Het speelmoment is aangepast'),
    ('2026-05-06 15:00:00', 'Aanpassing speelmoment', 'Het speelmoment is aangepast'),
    ('2026-11-06 10:00:00', 'Nieuw speelmoment opgestart', 'Er is een nieuw speelmoment gestart'),
    ('2026-09-06 15:00:00', 'Nieuwe planning', 'Een nieuwe planning is beschikbaar'),
    ('2026-03-06 12:00:00', 'Aanpassing speelmoment', 'Het speelmoment is aangepast'),
    ('2026-12-06 09:00:00', 'Nieuw speelmoment opgestart', 'Er is een nieuw speelmoment gestart'),
    ('2026-01-06 05:00:00', 'Aanpassing speelmoment', 'Het speelmoment is aangepast');

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


--PAYED PLAYERS
INSERT INTO PLAYER_PAYED_PADEL_DAYS (PAYED_PADEL_DAYS_ID, PAYED_PLAYERS_ID)
VALUES
    ('1','4'),('1','5'),('1','6'),('1','7'),('1','8'),('1','9'),('1','10'),('1','11'),('1','12'),('1','13'),('1','14'),('1','15'),
    ('2','6'),('2','7'),('2','8'),('2','9'),('2','10'),('2','11'),('2','12'),('2','13'),('2','14'),('2','15'),('2','16'),('2','17'),('2','18'),('2','19'),('2','20'),('2','21'),
    ('3','1'),('3','2'),('3','3'),('3','4'),('3','5'),('3','6'),('3','7'),('3','8'),('3','9'),('3','10'),('3','11'),('3','12'),('3','13'),('3','14'),('3','15'),('3','16'),('3','17'),('3','18'),('3','19'),('3','20'),
    ('4','1'),('4','2'),('4','3'),('4','4'),('4','5'),('4','6'),('4','7'),('4','8'),('4','9'),('4','10'),('4','11'),('4','12'),('4','13'),('4','14'),('4','15'),('4','16'),('4','17'),('4','18'),('4','19'),('4','20'),('4','21'),('4','22'),('4','23'),('4','24'),
    ('5','10'),('5','11'),('5','12'),('5','13'),('5','14'),('5','15'),('5','16'),('5','17'),('5','18'),('5','19'),('5','20'),('5','21'),
    ('6','12'),('6','13'),('6','14'),('6','15'),('6','16'),('6','17'),('6','18'),('6','19'),('6','20'),('6','21'),('6','22'),('6','23'),('6','24'),('6','25'),('6','26'),('6','27'),
    ('7','1'),('7','2'),('7','3'),('7','4'),('7','5'),('7','6'),('7','7'),('7','8'),('7','9'),('7','10'),('7','11'),('7','12'),('7','13'),('7','14'),('7','15'),('7','16'),('7','17'),('7','18'),('7','19'),('7','20'),
    ('8','7'),('8','8'),('8','9'),('8','10'),('8','11'),('8','12'),('8','13'),('8','14'),('8','15'),('8','16'),('8','17'),('8','18'),('8','19'),('8','20'),('8','21'),('8','22'),('8','23'),('8','24'),('8','25'),('8','26'),('8','27'),('8','28'),('8','29'),('8','30'),
    ('9','1'),('9','2'),('9','3'),('9','4'),('9','5'),('9','6'),('9','7'),('9','8'),('9','9'),('9','10'),('9','11'),('9','12'),
    ('10','1'),('10','2'),('10','3'),('10','4'),('10','5'),('10','6'),('10','7'),('10','8'),('10','9'),('10','10'),('10','11'),('10','12'),('10','13'),('10','14'),('10','15'),('10','16'),
    ('11','11'),('11','12'),('11','13'),('11','14'),('11','15'),('11','16'),('11','17'),('11','18'),('11','19'),('11','20'),('11','21'),('11','22'),('11','23'),('11','24'),('11','25'),('11','26'),('11','27'),('11','28'),('11','29'),('11','30'),
    ('12','1'),('12','4'),('12','5'),('12','6'),('12','7'),('12','8'),('12','9'),('12','10'),('12','11'),('12','12'),('12','13'),('12','14'),('12','15'),('12','16'),('12','17'),('12','18'),('12','19'),('12','20'),('12','21'),('12','22'),('12','23'),('12','24'),
    ('13','18'),('13','19'),('13','20'),('13','23'),('13','24'),('13','25'),('13','26'),('13','27'),('13','28'),('13','29'),
    ('14','5'),('14','6'),('14','11'),('14','12'),('14','13'),('14','14'),('14','15'),('14','16'),('14','17'),('14','18'),('14','19'),('14','20'),
    ('15','1'),('15','2'),('15','3'),('15','4'),('15','5'),('15','6'),('15','7'),('15','8'),('15','9'),('15','10');


--insert Matches and teams
INSERT INTO MATCH(FIELD_ID, P_RANKING_DIFFERENCE, TIME_SLOT)
VALUES
    (1, 50.0, '14:00:00'),
    (2, 40.0, '14:00:00'),
    (3, 40.0, '14:00:00'),
    (1, 525.0, '14:40:00'),
    (2, 460.0, '14:40:00'),
    (3, 410.0, '14:40:00'),
    (1, 75.0, '15:20:00'),
    (2, 90.0, '15:20:00'),
    (3, 425.0, '15:20:00'),
    (1,50.0,'14:00:00'),
    (2,125.0,'14:00:00'),
    (3,75.0,'14:00:00'),
    (4,50.0,'14:00:00'),
    (1,100.0,'14:40:00'),
    (2,175.0,'14:40:00'),
    (3,525.0,'14:40:00'),
    (4,300.0,'14:40:00'),
    (1,150.0,'15:20:00'),
    (2,175.0,'15:20:00'),
    (3,125.0,'15:20:00'),
    (4,100.0,'15:20:00');

INSERT INTO TEAM (AVERAGEPRANKING)
VALUES
    (290.0),
    (275.0),
    (610.0),
    (510.0),
    (1000.0),
    (1000.0),
    (1000.0),
    (325.0),
    (725.0),
    (310.0),
    (835.0),
    (490.0),
    (440.0),
    (775.0),
    (510.0),
    (1075.0),
    (410.0),
    (475.0),
    (275.0),
    (225.0),
    (725.0),
    (600.0),
    (550.0),
    (475.0),
    (1050.0),
    (1000.0),
    (875.0),
    (975.0),
    (525.0),
    (700.0),
    (225.0),
    (750.0),
    (575.0),
    (275.0),
    (625.0),
    (475.0),
    (750.0),
    (575.0),
    (750.0),
    (625.0),
    (600.0),
    (500.0),
    (475.0),
    (150.0),
    (750.0),
    (350.0),
    (775.0),
    (600.0),
    (975.0),
    (825.0);


INSERT INTO TEAM_PLAYERS (PLAYERS_ID, TEAMS_ID)
VALUES
    (25,1),
    (27,1),
    (23,2),
    (21,2),
    (20,3),
    (26,3),
    (28,4),
    (19,4),
    (18,5),
    (24,5),
    (29,6),
    (22,6),
    (21,7),
    (19,7),
    (25,8),
    (23,8),
    (28,9),
    (20,9),
    (22,10),
    (27,10),
    (26,11),
    (18,11),
    (29,12),
    (24,12),
    (23,13),
    (19,13),
    (25,14),
    (29,14),
    (28,15),
    (20,15),
    (27,16),
    (26,16),
    (24,17),
    (22,17),
    (18,18),
    (21,18),
    (8,19),
    (6,19),
    (13,20),
    (16,20),
    (10,21),
    (20,21),
    (14,22),
    (18,22),
    (9,23),
    (7,23),
    (17,24),
    (19,24),
    (11,25),
    (15,25),
    (12,26),
    (5,26),
    (18,27),
    (15,27),
    (11,28),
    (20,28),
    (14,29),
    (19,29),
    (5,30),
    (9,30),
    (13,31),
    (16,31),
    (6,32),
    (12,32),
    (17,33),
    (7,33),
    (8,34),
    (10,34),
    (14,35),
    (7,35),
    (17,36),
    (19,36),
    (9,37),
    (15,37),
    (10,38),
    (18,38),
    (11,39),
    (6,39),
    (12,40),
    (16,40),
    (5,41),
    (13,41),
    (8,42),
    (20,42),
    (17,43),
    (10,43),
    (16,44),
    (8,44),
    (9,45),
    (15,45),
    (6,46),
    (13,46),
    (14,47),
    (5,47),
    (19,48),
    (7,48),
    (12,49),
    (20,49),
    (11,50),
    (18,50);

INSERT INTO MATCH_TEAMS (MATCHES_ID, TEAMS_ID)
VALUES
    (1,	1),
    (1,	2),
    (2,	3),
    (2,	4),
    (3,	5),
    (3,	6),
    (4,	7),
    (4,	8),
    (5,	9),
    (5,	10),
    (6,	11),
    (6,	12),
    (7,	13),
    (7,	14),
    (8,	15),
    (8,	16),
    (9,	17),
    (9,	18),
    (10,19),
    (10,20),
    (11,21),
    (11,22),
    (12,23),
    (12,24),
    (13,25),
    (13,26),
    (14,27),
    (14,28),
    (15,29),
    (15,30),
    (16,31),
    (16,32),
    (17,33),
    (17,34),
    (18,35),
    (18,36),
    (19,37),
    (19,38),
    (20,39),
    (20,40),
    (21,41),
    (21,42);

INSERT INTO PADEL_DAY_MATCHES (MATCHES_ID, PADEL_DAYS_ID)
VALUES
    (1,13),
    (2,13),
    (3,13),
    (4,13),
    (5,13),
    (6,13),
    (7,13),
    (8,13),
    (9,13),
    (10,14),
    (11,14),
    (12,14),
    (13,14),
    (14,14),
    (15,14),
    (16,14),
    (17,14),
    (18,14),
    (19,14),
    (20,14),
    (21,14);

--Event test data
--Published event
INSERT INTO club_event (title,description,location,start_date_time,end_date_time,maximum_participants,published,price)
VALUES
    ('Familiebarbecue','Gezellige familiebarbecue voor alle leden en hun familie.','Padelclub Antwerpen','2026-06-28 15:00:00','2026-06-28 21:00:00',50,TRUE,15.00),
    ('Nationale feestdag', 'Vier de Belgische nationale feestdag met een gezellige uitstap naar Plopsaland De Panne voor leden en hun familie.', 'Plopsaland - De Panne', '2026-07-21 09:30:00', '2026-07-21 18:00:00', null, TRUE, 35.00);

--Unpublished event
INSERT INTO club_event (title,description,location,start_date_time,end_date_time,maximum_participants,published,price)
VALUES
    ('Familie-uitstap','Een gezamenlijke uitstap voor leden en familie.','Planckendael','2026-08-15 09:30:00','2026-08-15 18:00:00',30,FALSE, 30.00);

--Deelnemers toevoegen aan Nationale feestdag
INSERT INTO CLUB_EVENT_PARTICIPANTS (CLUB_EVENT_ID, PARTICIPANTS_ID)
SELECT club_event.id, player.id
FROM club_event CROSS JOIN player
WHERE club_event.title = 'Nationale feestdag'
  AND player.email IN (
                       'liam@example.com',
                       'noah@example.com',
                       'emma@example.com',
                       'olivia@example.com',
                       'lucas@example.com',
                       'sofia@example.com',
                       'ethan@example.com',
                       'mia@example.com'
    );

--Deelnemers toevoegen aan Familiebarbecue
INSERT INTO CLUB_EVENT_PARTICIPANTS (CLUB_EVENT_ID, PARTICIPANTS_ID)
SELECT club_event.id, player.id
FROM club_event CROSS JOIN player
WHERE club_event.title = 'Familiebarbecue'
  AND player.email IN (
                       'daniel@example.com',
                       'chloe@example.com',
                       'tomvdb@example.com',
                       'sara@example.com'
    );