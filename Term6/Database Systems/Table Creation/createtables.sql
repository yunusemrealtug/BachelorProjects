-- Create Tables:
# A user has following attributes.
CREATE TABLE Users (
	username VARCHAR (50) NOT NULL,
    user_name VARCHAR (50) NOT NULL,
    user_surname VARCHAR (50) NOT NULL,
    user_password VARCHAR (20) NOT NULL,
    PRIMARY KEY (username)

);
# A Platform has following attributes.
CREATE TABLE Platform (
	platform_name VARCHAR (50) NOT NULL UNIQUE,
    platform_id INT,
    PRIMARY KEY (platform_id)
);

# Director connects user with foreign key username. Therefore, there cannot be director without user information. We implemented it with ON DELETE CASCADE.
# Platform and director have one-to-many relationship. Therefore, it is implemented with a foreign key instead of new table.
# Since a director may not have a platform, we used on delete set null.
CREATE TABLE Director (
	username VARCHAR(50),
	nation VARCHAR(50) NOT NULL,
    platform_id INT, 
    PRIMARY KEY (username),
	FOREIGN KEY (username) REFERENCES Users (username) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (platform_id) REFERENCES Platform (platform_id) ON DELETE SET NULL ON UPDATE CASCADE
	
);

# Audience has two lists. These are implemented with relationship in er digram and with new tables in MySQL.
# Audience connects user with foreign key username. Therefore, there cannot be audience without user information. We implemented it with ON DELETE CASCADE.
CREATE TABLE Audience (
	username VARCHAR(50),
	PRIMARY KEY (username),
	FOREIGN KEY (username) REFERENCES Users (username) ON DELETE CASCADE ON UPDATE CASCADE
);
# Because session_date and slot are not unique by themselves, we made composite key. It will connect to session and theatre with new table.
CREATE TABLE Times (
	session_date Date NOT NULL,
    slot INT NOT NULL,
    PRIMARY KEY (session_date, slot)
);
# genre_name is a candidate key.
CREATE TABLE Genre(
	genre_name VARCHAR (50) UNIQUE NOT NULL,
    genre_id INT,
    PRIMARY KEY (genre_id)
);

#Since movie name, duration, average rating, director username solely depends on theatre ID, it is implemented in a distinct table.    
# Platform_id of director and his/her movies must be same, so we referenced platform_id and director_username from the same row in the director table.
# Movie may not have a rating platform or an average_rating. Therefore these attributes may be NULL in the table. 

CREATE TABLE Movie (
	movie_id INT,
    movie_name VARCHAR (100) NOT NULL,
    duration INT NOT NULL,
    average_rating FLOAT,
    platform_id INT, 
    director_username VARCHAR(50) NOT NULL,
    PRIMARY KEY (movie_id),
    FOREIGN KEY (platform_id,director_username) REFERENCES Director (platform_id,username) ON DELETE CASCADE ON UPDATE CASCADE
    
);

#Since theatre name, district and capacity solely depends on theatre ID, it is implemented in a distinct table.    
CREATE TABLE Theatre (
	theatre_id INT,
    theatre_name VARCHAR (100) NOT NULL,
    capacity INT NOT NULL,
    district VARCHAR (50) NOT NULL,
    PRIMARY KEY (theatre_id)
);
#Since movie and theatre have own attributes depend on their ids, we moved attributes into other tables.
CREATE TABLE Session (
	session_id INT,
    movie_id INT NOT NULL,
    PRIMARY KEY (session_id),
    FOREIGN KEY (movie_id) REFERENCES Movie (movie_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Db_manager(
	db_name VARCHAR (50),
    user_password VARCHAR (20) NOT NULL,
    PRIMARY KEY (db_name)
    );

# Since audience and platform have many-to-many relationship while subscribing, we created new table.
CREATE TABLE Subscribes (
	platform_id INT,
    username VARCHAR (50),
    FOREIGN KEY (username) REFERENCES Audience (username) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (platform_id) REFERENCES Platform (platform_id) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (username, platform_id)
);

# Since movie and audience have many-to-many relationship while rating, we created new table.

CREATE TABLE Rates (
	movie_id INT,
    username VARCHAR (50),
    rating FLOAT NOT NULL,
    FOREIGN KEY (username) REFERENCES Audience (username) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (movie_id) REFERENCES Movie (movie_id) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (username, movie_id)
);

# Since movie and genre have many-to-many relationship while rating, we created new table.
# We did not guarantee at least one genre for each movie constraint.
CREATE TABLE Has_Genre (
	movie_id INT,
    genre_id INT,
    FOREIGN KEY (movie_id) REFERENCES Movie (movie_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (genre_id) REFERENCES Genre (genre_id) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (movie_id, genre_id)
);
# A movie succeeds or preceeds one or more than one movie. Therefore this is many-to-many relationship.

CREATE TABLE Succeeds (
	predecessor_id INT,
    successor_id INT,
    FOREIGN KEY (predecessor_id) REFERENCES Movie (movie_id) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (successor_id) REFERENCES Movie (movie_id) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (predecessor_id, successor_id)
);

CREATE TABLE Buys_Ticket (
	username VARCHAR (50),
    session_id INT,
    FOREIGN KEY (username) REFERENCES Audience (username) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (session_id) REFERENCES Session (session_id) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (session_id, username)
);
# This is a ternary relationship, so we took the primary keys as foreign keys from three tables.
# Since, no two movie sessions can overlap in terms of theatre and the time it's screened. We defined the uniqueness by date and slot and theatre_id.
CREATE TABLE Plays(
	session_date Date NOT NULL,
    slot INT NOT NULL,
    theatre_id INT NOT NULL,
    session_id INT NOT NULL,
    FOREIGN KEY (session_id) REFERENCES Session (session_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (theatre_id) REFERENCES Theatre (theatre_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (session_date, slot) REFERENCES Times (session_date, slot) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (session_date, slot, theatre_id) 
);
