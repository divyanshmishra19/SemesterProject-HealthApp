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
('Pizza', 2048.3746, 61.1374, 23.4619, 1.1790, 23.8932, 6.5538, 330.1054, 71.2790, 523.0000, 1255.0000, 271.0000, 799.5652, 875.2126, 598.2600, 3014.0026, 15.2212, 15.0341, 'BALANCED,HIGH_FIBER,LOW_SODIUM,', '100g Cheese, 400g Wheat, 4 tomatoes, 1 onion, 5 olives, 20ml olive oil '),
('Steak', 1052.8929, 88.7609, 31.8264, 4.4992, 46.2591, 6.4997, 0.0000, 64.3650, 796.0000, 0.0000, 257.0000, 857.9529, 28.4229, 66.5176, 784.1410, 6.3408, 16.4868, 'LOW_CARB,', '1 Beef rib-eye steak, 20ml Vegetable oil, 5g Salt'),
('Stir fry Tofu', 939.8030, 79.7596, 8.3777, 0.1820, 43.8752, 24.5391, 35.0804, 32.9155, 682.0000, 133.0000, 125.0000, 48.9100, 497.3800, 198.3100, 1373.6000, 6.6163, 4.0141, 'LOW_CARB,LOW_SODIUM,', '200g Tofu, 50ml Oil, 1 onion, 1 capsicum, 1 tomato, 50g peanuts'),
('Almond Ice Cream', 697.0720, 43.7306, 12.8651, 0.0075, 21.4491, 6.9565, 70.2612, 18.9519, 366.0000, 261.0000, 70.0000, 94.2942, 430.6801, 204.6266, 806.7205, 3.5949, 3.0647, 'LOW_SODIUM,', '200ml Milk, 20g Sugar, 50g Almonds, 5g cinnamon, 40g chocolate'),
('Sheet-Pan Chicken', 1203.4140, 59.6205, 10.7223, 0.1827, 31.8555, 12.4876, 124.9552, 53.7961, 516.0000, 481.0000, 207.0000, 2072.6600, 278.9300, 219.8900, 3262.6900, 6.9901, 4.4735, 'BALANCED,', '1 chicken breasts, 1 tablespoon sesame oil,2 tablespoons soy sauce, 2 tablespoons honey, 2  red bell peppers, 2 yellow bell peppers, 3 carrots, 1 broccoli, 2 red onions, 2 tablespoons extra-virgin olive oil'),
('Roasted Butternut Squash & Brussels Sprouts with Crispy Bacon', 2680.8695, 196.1422, 52.8930, 1.7260, 93.6993, 35.5943, 185.8625, 80.5795, 1672.0000, 704.0000, 305.0000, 1845.8804, 1588.0634, 534.6110, 5249.4066, 15.4692, 14.3674, '', '1 pound brussels sprouts, 5 cups butternut squash, 2 tablespoons olive oil, 2 tablespoons pure maple syrup, 3 garlic cloves, 1 teaspoon cayenne pepper, 1 teaspoon cinnamon, 4-5 oz bacon, 1 cup cranberries, 1 cup pecans, 1 cup cheese'),
('Peanut Cookie', 2332.9600, 123.1122, 46.5247, 1.7044, 46.3853, 21.6754, 251.9584, 70.5882, 1078.0000, 980.0000, 275.0000, 277.7400, 246.8200, 291.1400, 1351.8600, 11.5300, 7.7488, 'LOW_SODIUM,', '4 eggs, 50g sugar, 100g peanuts, 50g butter, 50g chocolate, 200g flour'),
('Almond Cookie', 2661.4400, 131.9808, 46.7361, 1.7520, 56.6561, 20.0899, 307.9876, 76.7398, 1159.0000, 1202.0000, 300.0000, 383.3600, 472.4800, 403.4600, 1499.5400, 12.1900, 8.7132, 'BALANCED,LOW_SODIUM,', '6 eggs, 100g sugar, 100g almonds, 50g butter, 50g chocolate, 200g flour'),
('Cashew Cookie', 3786.9400, 225.3058, 93.0591, 3.3760, 88.1926, 25.4554, 378.7976, 94.5548, 1958.0000, 1463.0000, 365.0000, 417.3600, 305.4800, 775.9600, 2281.0400, 23.4150, 18.0082, 'LOW_SODIUM,', '6 eggs, 100g sugar, 200g cashew, 100g butter, 100g chocolate, 200g flour'),
('Beans on Toast', 1230.8400, 57.9988, 31.5442, 1.8949, 14.0604, 5.7453, 128.2196, 52.7404, 516.0000, 507.0000, 208.0000, 1836.7600, 1062.3600, 181.8000, 913.4400, 10.3208, 6.6888, 'BALANCED,', '200g beans, 20g butter, 100g cheese, 8 slices bread'),
('Grilled Cheese', 1334.0400, 60.6629, 31.9328, 1.8949, 16.0949, 5.8548, 147.1666, 53.5718, 540.0000, 582.0000, 212.0000, 2027.0000, 1111.8280, 163.8240, 954.7280, 10.0386, 6.9685, 'BALANCED,', '2 onions, 8 olives, 1 garlic, 20g butter, 100g cheese, 8 slices bread'),
('Dal Fry with Rice', 1716.5600, 29.4700, 12.1748, 0.6556, 9.8400, 5.1420, 296.5524, 69.3364, 263.0000, 1178.0000, 275.0000, 23.8800, 154.6400, 204.0000, 1768.0400, 15.9480, 9.8104, 'LOW_SODIUM,', '200g Lentil, 200g rice, 1 garlic, 20g butter, 20g peanuts'),
('Arrabiata Pasta ', 3293.1346, 155.4974, 54.3373, 1.5420, 64.9859, 22.3836, 342.3198, 127.4054, 1406.0000, 1375.0000, 512.0000, 2213.6452, 915.6526, 356.2600, 2999.2426, 9.7092, 14.1025, 'BALANCED,', '100g Cheese, 400g Pasta, 4 tomatoes, 1 onion, 1 garlic, 200g bacon, 100g chicken, 5 olives, 20ml olive oil '),
('Mushroom Gravy', 2270.6151, 195.6237, 52.6916, 1.7260, 93.5459, 35.3321, 78.2231, 72.2641, 1692.0000, 301.0000, 278.0000, 1727.1619, 1075.1624, 233.1081, 2463.3560, 6.4868, 13.7710, 'LOW_CARB,', '1 pound mushrooms, 2 tablespoons olive oil, 2 tablespoons pure maple syrup, 3 garlic cloves, 1 teaspoon cayenne pepper, 1 teaspoon cinnamon, 4-5 oz bacon, 1 cup cranberries, 1 cup pecans, 1 cup cheese'),
('Butter Chicken', 2533.8736, 174.6777, 74.9196, 3.7180, 62.1935, 22.7296, 142.9399, 114.7583, 1530.0000, 557.0000, 447.0000, 3673.3105, 432.2251, 505.1126, 3740.9929, 26.1332, 13.4610, 'LOW_CARB,', '1 pound chicken, 100g butter, 5 tomatoes, 2 onions, 20 cumin seeds, 10g salt, 100g wheat, 40g cashew '),
('Khow Suey', 5114.6207, 341.5882, 142.2380, 0.9578, 124.4807, 52.1749, 350.8520, 161.9408, 3068.0000, 1400.0000, 646.0000, 4164.1415, 403.9628, 524.0128, 3491.4336, 28.2071, 19.3736, '', '200g rice, 5 eggs, 300ml coconut milk, 20oz bacon, 200g noodles, 50g peanuts, 10g cilantro, 2 onions, 1 clove garlic '),
('Edamame Sushi', 2666.8098, 126.4221, 25.4150, 0.1074, 40.3371, 33.5942, 169.4807, 188.7058, 1180.0000, 703.0000, 783.0000, 2848.2304, 177.7873, 379.1235, 3721.2610, 7.0993, 6.8901, '', '200g rice, 100g edamame beans, 40ml soy sauce, 1 cup vinegar, 1 tablespoon vegetable oil, 2 salmon'),
('Avocado on Toast', 1302.0300, 84.3988, 20.7010, 0.6904, 46.4862, 10.2567, 114.9924, 37.8362, 721.0000, 437.0000, 144.0000, 1142.8700, 656.9000, 225.0100, 2976.7300, 7.9867, 6.5835, 'HIGH_FIBER,', '2 Avocado, 4 slices bread, 2 slices of cheese, 20g olives, 2 tomatoes, 1 onion, 1 clove garlic');



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
('Weight Loose 1', '400','2500','Avocado on Toast, Grilled Cheese','Dips, Tricep dips, Running','18, 11','18, 3, 6', 'This plan includes low calorie meals & medium intensity workouts'),
('Weight Loose 2', '500','2700','Dal Fry with Rice, Steak','Dumbbell row, Burpees, Squats','2, 12','2, 8, 4', 'This plan includes medium to low calorie meals & high intensity workouts'),
('High Intensity', '300','3500','Stir fry Tofu, Edamame Sushi','Walking-Lunges, Tricep Dips, Running','3, 17','1, 3, 6', 'This plan includes medium calorie meals & low intensity workouts'),
('Weight Gain', '600','4000','Arrabiata Pasta, Almond Ice Cream','Squats, Burpees, Mountain Climbers','13, 4','4, 8, 15', 'This plan includes high calorie meals & high intensity workouts'),
('Muscle Gain', '400','2400','Sheet-Pan Chicken, Beans on Toast','Dumbbell row, Glute Bridge','2, 10','2, 8', 'This plan includes low calorie meals & medium intensity workouts'),
('Toning', '300','8000','Khow Suey, Cashew Cookie','Tricep dips, Running','16, 9','3, 6', 'This plan includes high calorie meals & low intensity workouts')
