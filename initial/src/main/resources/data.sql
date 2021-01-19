DROP TABLE IF EXISTS gamers;
DROP TABLE IF EXISTS games;
DROP TABLE IF EXISTS geography;
DROP TABLE IF EXISTS levels;
DROP TABLE IF EXISTS gender;
DROP TABLE IF EXISTS gamer_levels;
DROP TABLE IF EXISTS gamer_credit;

CREATE TABLE levels (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(250) NOT NULL UNIQUE
);

CREATE TABLE games (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(250) NOT NULL UNIQUE
);

CREATE TABLE gender (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(250) NOT NULL UNIQUE
);

CREATE TABLE geography (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(250) NOT NULL UNIQUE
);

CREATE TABLE gamers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(250) NOT NULL,
    gender_id INT NOT NULL,
    nickname VARCHAR(250) NOT NULL UNIQUE,
    geography_id INT NOT NULL,
    FOREIGN KEY (gender_id) REFERENCES gender(id),
    FOREIGN KEY (geography_id) REFERENCES geography(id)
);

CREATE TABLE gamer_levels (
    id INT AUTO_INCREMENT PRIMARY KEY,
    gamer_id INT NOT NULL,
    game_id INT NOT NULL,
    level_id INT NOT NULL,
    FOREIGN KEY (gamer_id) REFERENCES gamers(id),
    FOREIGN KEY (game_id) REFERENCES games(id),
    FOREIGN KEY (level_id) REFERENCES levels(id),
    CONSTRAINT gamer_level_info UNIQUE(gamer_id, game_id)
);

CREATE TABLE gamer_credit (
    id INT AUTO_INCREMENT PRIMARY KEY,
    gamer_id INT NOT NULL,
    game_id INT NOT NULL,
    credit INT NOT NULL,
    FOREIGN KEY (gamer_id) REFERENCES gamers(id),
    FOREIGN KEY (game_id) REFERENCES games(id)
);

INSERT INTO levels(name) VALUES 
    ('noob'), 
    ('pro'), 
    ('invincible');

INSERT INTO games(name) VALUES
    ('Fortnite'),
    ('Call of Duty Warzone'),
    ('Dota'),
    ('Among Us'),
    ('Fall Guys');

INSERT INTO gender(name) VALUES
    ('Male'),
    ('Female'),
    ('Other');

INSERT INTO geography(name) VALUES
    ('Europe'),
    ('Asia'),
    ('America');