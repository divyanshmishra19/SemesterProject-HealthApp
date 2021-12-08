# -----------------------------------------------
# SQL script to create the HealthTechDB database
# tables and populate the Recipe, Workout tables.
# Created by Team HealthTech
# -----------------------------------------------

DROP TABLE IF EXISTS NutritionalPlan, UserPhoto, Recipe, UserRecipeConsumed, UserRecipe, UserWorkoutDone, UserWorkout, Workout, User;


CREATE TABLE User
(
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL,
    username VARCHAR(32) NOT NULL,
    password VARCHAR(256) NOT NULL,          /* To store Salted and Hashed Password Parts */
    first_name VARCHAR(32) NOT NULL,
    middle_name VARCHAR(32),
    last_name VARCHAR(32) NOT NULL,
    address1 VARCHAR(128) NOT NULL,
    address2 VARCHAR(128),
    city VARCHAR(64) NOT NULL,
    state VARCHAR(2) NOT NULL,
    zipcode VARCHAR(10) NOT NULL,            /* e.g., 24060-1804 */
    security_question_number INT NOT NULL,   /* Refers to the number of the selected security question */
    security_answer VARCHAR(128) NOT NULL,
    email VARCHAR(128) NOT NULL,
	daily_calorie_intake DECIMAL(8,4) NOT NULL,
	daily_calorie_burn DECIMAL(8,4) NOT NULL
);

CREATE TABLE UserPhoto
(
       id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL,
       extension ENUM('jpeg', 'jpg', 'png', 'gif') NOT NULL,
       user_id INT UNSIGNED,
       FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE
);


/* The Recipe table contains attributes of interest of a recipe. */
CREATE TABLE Recipe
(
 	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL,
	name VARCHAR(256) NOT NULL,
	calories DECIMAL(8,4) NOT NULL,
	fat_total DECIMAL(8,4) NOT NULL,
	fat_sat DECIMAL(8,4) NOT NULL,
	fat_trans DECIMAL(8,4) NOT NULL,
	fat_mono DECIMAL(8,4) NOT NULL,
	fat_poly DECIMAL(8,4) NOT NULL,
	carbs DECIMAL(8,4) NOT NULL,
	protein DECIMAL(8,4) NOT NULL,
	fat_cal DECIMAL(8,4) NOT NULL,
	carb_cal DECIMAL(8,4) NOT NULL,
	protein_cal DECIMAL(8,4) NOT NULL,
	sodium DECIMAL(8,4) NOT NULL,
	calcium DECIMAL(8,4) NOT NULL,
	magnesium DECIMAL(8,4) NOT NULL,
	potassium DECIMAL(8,4) NOT NULL,
	iron DECIMAL(8,4) NOT NULL,
	zinc DECIMAL(8,4) NOT NULL,
	diet_labels VARCHAR(2048) NOT NULL,
	ingredients VARCHAR(2048) NOT NULL
);


/* The Recipe table contains attributes of interest of a recipe. */
CREATE TABLE UserRecipe
(
 	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL,
	user_id INT UNSIGNED,
	name VARCHAR(256) NOT NULL,
	calories DECIMAL(8,4) NOT NULL,
	fat_total DECIMAL(8,4) NOT NULL,
	fat_sat DECIMAL(8,4) NOT NULL,
	fat_trans DECIMAL(8,4) NOT NULL,
	fat_mono DECIMAL(8,4) NOT NULL,
	fat_poly DECIMAL(8,4) NOT NULL,
	carbs DECIMAL(8,4) NOT NULL,
	protein DECIMAL(8,4) NOT NULL,
	fat_cal DECIMAL(8,4) NOT NULL,
	carb_cal DECIMAL(8,4) NOT NULL,
	protein_cal DECIMAL(8,4) NOT NULL,
	sodium DECIMAL(8,4) NOT NULL,
	calcium DECIMAL(8,4) NOT NULL,
	magnesium DECIMAL(8,4) NOT NULL,
	potassium DECIMAL(8,4) NOT NULL,
	iron DECIMAL(8,4) NOT NULL,
	zinc DECIMAL(8,4) NOT NULL,
	diet_labels VARCHAR(2048) NOT NULL,
	ingredients VARCHAR(2048) NOT NULL,
	FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE
);


/* The Recipe table contains attributes of interest of a recipe. */
CREATE TABLE UserRecipeConsumed
(
 	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL,
	user_recipe_id INT UNSIGNED,
	date DATE NOT NULL,
	FOREIGN KEY (user_recipe_id) REFERENCES UserRecipe(id) ON DELETE CASCADE
);

/* The Recipe table contains attributes of interest of a recipe. */
CREATE TABLE Workout
(
 	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL,
	name VARCHAR(256) NOT NULL,
	category VARCHAR(64) NOT NULL,
	youtube_tutorial_video_id VARCHAR(64) NOT NULL,
	burn_rate DECIMAL(8,4) NOT NULL
);


/* The Recipe table contains attributes of interest of a recipe. */
CREATE TABLE UserWorkout
(
 	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL,
	user_id INT UNSIGNED,
	name VARCHAR(256) NOT NULL,
	category VARCHAR(64) NOT NULL,
	youtube_tutorial_video_id VARCHAR(64) NOT NULL,
	burn_rate DECIMAL(8,4) NOT NULL,
	FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE
);


/* The Recipe table contains attributes of interest of a recipe. */
CREATE TABLE UserWorkoutDone
(
 	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL,
	user_workout_id INT UNSIGNED,
	duration INT NOT NULL,
	calories INT NOT NULL,
	date DATE NOT NULL,
	FOREIGN KEY (user_workout_id) REFERENCES UserWorkout(id) ON DELETE CASCADE
);

CREATE TABLE NutritionalPlan
(
    id  				 int auto_increment primary key not null,
    name                 varchar(256)  not null,
    daily_calorie_intake decimal(8,4) not null,
    daily_calorie_burn   decimal(8,4) not null,
    recipe_names         varchar(512)  not null,
    workout_names        varchar(512)  not null,
    recipe_ids           varchar(64)   not null,
    workout_ids          varchar(64)   not null,
    description          varchar(1024)  not null
);


/* If a value to be inserted has a single quote, e.g. Rick's, enter single quote twice as Rick''s */

INSERT INTO Recipe (name, calories, fat_total, fat_sat, fat_trans, fat_mono, fat_poly, carbs, protein, fat_cal, carb_cal, protein_cal, sodium, calcium, magnesium, potassium, iron, zinc, diet_labels, ingredients) VALUES
('Pappardelle With Beef', 3.4, 3.4, 3.4, 3.4, 3.4, 3.4, 3.4, 3.4, 3.4, 3.4, 3.4, 3.4, 3.4, 3.4, 3.4, 3.4, 3.4, 'diet', 'ingredient'),
('Pappardelle With Beef', 3.4, 3.4, 3.4, 3.4, 3.4, 3.4, 3.4, 3.4, 3.4, 3.4, 3.4, 3.4, 3.4, 3.4, 3.4, 3.4, 3.4, 'diet', 'ingredient'),
('Pappardelle With Beef', 3.4, 3.4, 3.4, 3.4, 3.4, 3.4, 3.4, 3.4, 3.4, 3.4, 3.4, 3.4, 3.4, 3.4, 3.4, 3.4, 3.4, 'diet', 'ingredient');


INSERT INTO Workout (name, category, youtube_tutorial_video_id, burn_rate) VALUES
('Walking-Lunges', 'Strength', 'qLyj6xo3j5c', 3.5),
('Dumbbell row', 'Strength', 'roCP6wCXPqo', 5.1),
('Tricep dips', 'Strength', '6kALZikXxLc', 2.8),
('Squats', 'Strength', 'aclHkVaku9U', 7.2),
('Glute Bridge', 'Strength', 'wPM8icPu6H8', 4.1),
('Running', 'Cardio', 'kVnyY17VS9Y', 2.5),
('Swimming', 'Cardio', 'gh5mAtmeR3Y', 4.9),
('Burpees', 'Cardio', 'qLBImHhCXSw', 8.1),
('Bear Crawls', 'Cardio', '6muIdIDEE2E', 4.2),
('Water Aerobics', 'Cardio', 'p-Vi854oZac', 3.8),
('Plyo Push-Ups', 'HIIT', 'FRo3b_Pfw3M', 8.3),
('Russian Twists', 'HIIT', '9V9csctSKj0', 8.9),
('Lateral Lunges with Hops', 'HIIT', 'JO9NkIo2-0M', 9.1),
('Tuck-Ups', 'HIIT', '_kd7k3t3IcY', 10.2),
('Mountain Climbers', 'HIIT', 'cnyTQDSE884', 11.8),
('Pull-Ups', 'Calisthenics', 'jFmCrA6fo78', 5.4),
('Chin-Ups', 'Calisthenics', 'pspXcUhoygA', 3.9),
('Dips', 'Calisthenics', 'l41SoWZiowI', 4.1),
('Crunches', 'Calisthenics', '5ER5Of4MOPI', 6.1),
('Jump Squats', 'Calisthenics', 'A-cFYWvaHr0', 5.8);

INSERT INTO NutritionalPlan (name, daily_calorie_burn, daily_calorie_intake, recipe_names, workout_names, recipe_ids, workout_ids, description) VALUES
('Plan1', '30','30','Pappardelle With Beef, Pappardelle With Beef','Running, Sleeping','1, 2','1, 2', 'This is a low calorie diet plan'),
('Plan2', '60','60','Pappardelle With Beef, Pappardelle With Beef, , Pappardelle With Beef','Running, Sleeping','1, 2','1, 2', 'This is a high calorie diet plan')
