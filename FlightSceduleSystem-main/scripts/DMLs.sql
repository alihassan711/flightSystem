
insert into airport(airport_id, airport_name, airport_location, airport_code) VALUES ('1','Istanbul Airport', 'Istanbul', 'IST');
insert into airport(airport_id, airport_name, airport_location, airport_code) VALUES ('2','Dubai International Airport', 'Dubai', 'DXB');  
insert into airport(airport_id, airport_name, airport_location, airport_code) VALUES ('3','Muscat Airport', 'Muscat', 'MCT'); 
insert into airport(airport_id, airport_name, airport_location, airport_code) VALUES ('4','Bodrum Milas Airport', 'Milas', 'BJV'); 
insert into airport(airport_id, airport_name, airport_location, airport_code) VALUES ('4','Bahrain Airport', 'Bahrain', 'BAH'); 

INSERT INTO ik_menu (menu_id, menu_name, menu_order, menu_type) VALUES (1, 'Sign Up', '1', 'home');
INSERT INTO ik_menu (menu_id, menu_name, menu_order, menu_type) VALUES (2, 'Sign In', '2', 'home');
INSERT INTO ik_menu (menu_id, menu_name, menu_order, menu_type) VALUES (1, 'View Flight details', '1', 'admin');
INSERT INTO ik_menu (menu_id, menu_name, menu_order, menu_type) VALUES (2, 'Add Flight', '2', 'admin');
INSERT INTO ik_menu (menu_id, menu_name, menu_order, menu_type) VALUES (3, 'Delete Flight', '3', 'admin');
INSERT INTO ik_menu (menu_id, menu_name, menu_order, menu_type) VALUES (4, 'Exit', '4', 'admin');

INSERT INTO flight (flight_number, departure_time, arrival_time, origin_airport, destination_airport, maximum_capacity) VALUES ('PK747', TO_DATE('2023-06-04 15:59:19', '%Y-%m-%d %H:%M:%S'), TO_DATE('2023-06-04 15:59:19', '%Y-%m-%d %H:%M:%S'), 'LHR', 'DUBAI', '60');
INSERT INTO flight (flight_number, departure_time, arrival_time, origin_airport, destination_airport, maximum_capacity) VALUES ('TY567', current, current, 'LHR', 'DUBAI', '60');
