INSERT INTO player (name, is_admin, gender, birth_date, self_evaluation, p_ranking, telephone, email, preferred_playside, profile_picture_url)
VALUES
('Liam Carter', false, 'M', '1998-05-12', 'Intermediate', 500, '111111111', 'liam@example.com', 'RIGHT', ''),
('Noah Schmidt', false, 'M', '1995-03-22', 'Advanced', 700, '111111112', 'noah@example.com', 'LEFT', ''),
('Emma Novak', false, 'F', '2000-07-10', 'Beginner', 300, '111111113', 'emma@example.com', 'NONE', ''),
('Olivia Dubois', false, 'F', '1999-01-15', 'Intermediate', 500, '111111114', 'olivia@example.com', 'RIGHT', ''),
('Lucas Silva', false, 'M', '1997-11-02', 'Advanced', 800, '111111115', 'lucas@example.com', 'LEFT', ''),
('Sofia Ivanova', false, 'F', '1996-09-09', 'Intermediate', 600, '111111116', 'sofia@example.com', 'RIGHT', ''),
('Ethan Brown', false, 'M', '1994-06-18', 'Advanced', 700, '111111117', 'ethan@example.com', 'LEFT', ''),
('Mia Andersson', false, 'F', '2001-02-25', 'Beginner', 200, '111111118', 'mia@example.com', 'NONE', ''),
('Daniel Kim', false, 'M', '1993-12-30', 'Intermediate', 600, '111111119', 'daniel@example.com', 'RIGHT', ''),
('Chloe Martin', false, 'F', '1998-08-08', 'Intermediate', 500, '111111120', 'chloe@example.com', 'LEFT', ''),
('Alex Johnson', true, 'M', '1992-04-14', 'Advanced', 900, '111111121', 'alex@example.com', 'RIGHT', ''),
('Sara Khan', false, 'F', '2002-10-05', 'Beginner', 300, '111111122', 'sara@example.com', 'NONE', '');

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

--  INSERT INTO padel_day (date, number_of_matches)
--  VALUES ('2026-04-25 18:00:00', 3);