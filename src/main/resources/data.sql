CREATE TABLE IF NOT EXISTS t_user (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(255),
    draw_count BIGINT,
    available_draw_count BIGINT
);

CREATE TABLE IF NOT EXISTS prize (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    prize_name VARCHAR(255),
    quantity BIGINT,
    hit_probability BIGINT
);

CREATE TABLE IF NOT EXISTS user_prize (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    prize_id BIGINT,
    user_id BIGINT
);

-- Insert predefined data into the user table
INSERT INTO t_user (user_id, user_name, draw_count, available_draw_count) VALUES
(1, 'Bryan', 0, 5),
(2, 'Lim', 0, 0),
(3, 'Zhou', 0, 2),
(4, 'Wen', 0, 3),
(5, 'World', 0, 0);

INSERT INTO prize (id, prize_name, quantity, hit_probability) VALUES
(1, 'iPhone', 5, 5),
(2, 'iPad', 3, 8),
(3, 'Amazon Gift Card', 10, 20),
(4, 'Samsung Galaxy', 2, 15),
(5, 'PlayStation 5', 1, 2),
(6, 'Aeon Gift Card', 10, 50);