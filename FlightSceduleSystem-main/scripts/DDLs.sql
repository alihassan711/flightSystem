CREATE TABLE
    airport
    (
        airport_id SERIAL NOT NULL,
        airport_name VARCHAR(60),
        airport_location VARCHAR(60),
        airport_code VARCHAR(60) NOT NULL,
        PRIMARY KEY (airport_id, airport_code) CONSTRAINT pm_airport
    );
    
    
CREATE TABLE
    menus
    (
        menu_id INTEGER NOT NULL,
        menu_name VARCHAR(60),
        menu_order VARCHAR(60),
        menu_type VARCHAR(60),
        PRIMARY KEY (menu_id, menu_type) CONSTRAINT pm_menu_id
    );
    
CREATE TABLE flight (
    flight_number VARCHAR(60) NOT NULL,
    departure_time VARCHAR(60),
    arrival_time VARCHAR(60),
    origin_airport VARCHAR(60),
    destination_airport VARCHAR(60),
    maximum_capacity VARCHAR(15),
    sr_no SERIAL NOT NULL,
    PRIMARY KEY (flight_number)
    FOREIGN KEY (origin_airport) REFERENCES airport (airport_code)
   
);    
        