-- This file gets executed during the spring boot process and sets up the tables.

-- TIP: Here is where all the schema creation of the DB happens
-- including creation of tables.
CREATE TABLE IF NOT EXISTS CarStatusTable (
    carId VARCHAR(100) NOT NULL PRIMARY KEY,
    carType INTEGER NOT NULL,
    carAvailability INTEGER NOT NULL,
    latitude DOUBLE,
    longitude DOUBLE);
