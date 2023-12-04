-- Generate data for the database

-- Generate warehouses
INSERT INTO warehouses (name, location, address, postcode, country, contact_name, contact_email, contact_phone)
VALUES ('Solar warehouse', 'Amsterdam', 'Amsterdamseweg 1', '1111AA', 'The Netherlands', 'John Doe', 'jane@gmail.com',
        '0612345678'),
       ('Lunar warehouse', 'Rotterdam', 'Rotterdamseweg 2', '2222BB', 'The Netherlands', 'Jane Doe', 'john@gmail.com',
        '0712345678'),
       ('Star warehouse', 'The Hague', 'Haagseweg 3', '3333CC', 'The Netherlands', 'Jim Doe', 'jim@gmail.com',
        '0812345678'),
       ('Galaxy warehouse', 'Utrecht', 'Utrechtseweg 4', '4444DD', 'The Netherlands', 'Jill Doe', 'jill@gmail.com',
        '0912345678');

-- Generate product categories
INSERT INTO product_categories (name, description)
VALUES ('Solar panel',
        'A solar panel is a panel designed to absorb the sun"s rays as a source of energy for generating electricity or heating.'),
       ('Solar inverter',
        'A solar inverter, or PV inverter, converts the variable direct current (DC) output of a photovoltaic (PV) solar panel into a utility frequency alternating current (AC).'),
       ('Solar battery',
        'A solar battery is a rechargeable battery that integrates a solar cell with battery power storage.'),
       ('Solar charge controller',
        'A solar charge controller, or solar regulator, is an electronic device that prevents batteries from overcharging.'),
       ('Solar mounting system',
        'A solar mounting system is used to fix solar panels on surfaces like roofs, building facades, or the ground.'),
       ('Solar cable', 'A solar cable is the interconnection cable used in photovoltaic power generation.'),
       ('Solar connector', 'A solar connector is an electrical connector designed to connect solar panels.');

-- Generate products
INSERT INTO Products (name, description, category_id , price)
VALUES ('Solar Panel 100W', '100W Solar Panel for generating electricity',
        (SELECT id FROM product_categories WHERE name = 'Solar panel'), 100.00),
       ('Solar Panel 200W', '200W Solar Panel for generating electricity',
        (SELECT id FROM product_categories WHERE name = 'Solar panel'), 200.00),
       ('Solar Panel 300W', '300W Solar Panel for generating electricity',
        (SELECT id FROM product_categories WHERE name = 'Solar panel'), 300.00),
       ('Solar Panel 400W', '400W Solar Panel for generating electricity',
        (SELECT id FROM product_categories WHERE name = 'Solar panel'), 400.00),
       ('Solar Inverter 500W', '500W Solar Inverter for converting DC to AC',
        (SELECT id FROM product_categories WHERE name = 'Solar inverter'), 500.00),
       ('Solar Inverter 600W', '600W Solar Inverter for converting DC to AC',
        (SELECT id FROM product_categories WHERE name = 'Solar inverter'), 600.00),
       ('Solar Inverter 700W', '700W Solar Inverter for converting DC to AC',
        (SELECT id FROM product_categories WHERE name = 'Solar inverter'), 700.00),
       ('Solar Inverter 800W', '800W Solar Inverter for converting DC to AC',
        (SELECT id FROM product_categories WHERE name = 'Solar inverter'), 800.00),
       ('Solar Battery 200Ah', '200Ah Solar Battery for storing electricity',
        (SELECT id FROM product_categories WHERE name = 'Solar battery'), 200.00),
       ('Solar Battery 300Ah', '300Ah Solar Battery for storing electricity',
        (SELECT id FROM product_categories WHERE name = 'Solar battery'), 300.00),
       ('Solar Battery 400Ah', '400Ah Solar Battery for storing electricity',
        (SELECT id FROM product_categories WHERE name = 'Solar battery'), 400.00),
       ('Solar Battery 500Ah', '500Ah Solar Battery for storing electricity',
        (SELECT id FROM product_categories WHERE name = 'Solar battery'), 500.00),
       ('Solar Charge Controller 20A', '20A Solar Charge Controller for controlling charge',
        (SELECT id FROM product_categories WHERE name = 'Solar charge controller'), 20.00),
       ('Solar Mounting System', 'Solar Mounting System for mounting solar panels',
        (SELECT id FROM product_categories WHERE name = 'Solar mounting system'), 150.00),
       ('Solar Cable 10m', '10m Solar Cable for connecting solar components',
        (SELECT id FROM product_categories WHERE name = 'Solar cable'), 10.00),
       ('Solar Connector', 'Solar Connector for connecting solar panels',
        (SELECT id FROM product_categories WHERE name = 'Solar connector'), 5.00);

-- Generate teams
INSERT INTO teams (name, warehouse_id)
VALUES ('Solar Warriors', 1),
       ('Solar Legends', 2),
       ('Solar Heroes', 3),
       ('Solar Champions', 4),
       ('Lunar Amazonians', 2),
       ('Lunar Spartans', 2),
       ('Lunar Titans', 2),
       ('Lunar Gods', 2),
       ('Star Jupiter', 3),
       ('Star Saturn', 3),
       ('Star Mars', 3),
       ('Star Venus', 3),
       ('Galaxy Andromeda', 4),
       ('Galaxy Milky Way', 4),
       ('Galaxy Black Hole', 4),
       ('Galaxy Big Bang', 4);

-- Generate users
INSERT INTO users (name, email, password, active, role, team_id)
VALUES ('John Doe', 'admin@admin.com', 'admin',true, 'ADMIN', 1),
       ('Jane Smith', 'user@user.com', 'user',true, 'USER', 2),
       ('Robert Johnson', 'robert.johnson@company.com', 'robertjohnsonpassword',true, 'USER', 3),
       ('Patricia Williams', 'patricia.williams@company.com', 'patriciawilliamspassword',true, 'USER', 4),
       ('Michael Brown', 'michael.brown@company.com', 'michaelbrownpassword',true, 'USER', 5),
       ('Linda Miller', 'linda.miller@company.com', 'lindamillerpassword',true,  'USER', 6),
       ('James Davis', 'james.davis@company.com', 'jamesdavispassword',true,  'USER', 7),
       ('Elizabeth Garcia', 'elizabeth.garcia@company.com', 'elizabethgarciapassword',true, 'USER', 8),
       ('William Rodriguez', 'william.rodriguez@company.com', 'williamrodriguezpassword',true, 'USER', 9),
       ('Jennifer Wilson', 'jennifer.wilson@company.com', 'jenniferwilsonpassword',true, 'USER', 9),
       ('Alice Johnson', 'alice.johnson@company.com', 'alicejohnsonpassword',true, 'USER', 9),
       ('Bob Williams', 'bob.williams@company.com', 'bobwilliamspassword',true, 'USER', 10),
       ('Charlie Brown', 'charlie.brown@company.com', 'charliebrownpassword',true, 'USER', 11),
       ('David Miller', 'david.miller@company.com', 'davidmillerpassword',true, 'USER', 12),
       ('Eve Davis', 'eve.davis@company.com', 'evedavispassword',true, 'USER', 13),
       ('Frank Garcia', 'frank.garcia@company.com', 'frankgarciapassword',true, 'USER', 14),
       ('Grace Rodriguez', 'grace.rodriguez@company.com', 'gracerodriguezpassword',true, 'USER', 15),
       ('Harry Wilson', 'harry.wilson@company.com', 'harrywilsonpassword',true, 'USER', 16),
       ('Isaac Newton', 'isaac.newton@company.com', 'isaacnewtonpassword',true, 'USER', 1),
       ('Albert Einstein', 'albert.einstein@company.com', 'alberteinsteinpassword',true, 'USER', 1),
       ('Marie Curie', 'marie.curie@company.com', 'mariecuriepassword',true, 'USER', 2),
       ('Nikola Tesla', 'nikola.tesla@company.com', 'nikolateslapassword',true, 'USER', 2),
       ('Thomas Edison', 'thomas.edison@company.com', 'thomasedisonpassword',true, 'USER', 3),
       ('Galileo Galilei', 'galileo.galilei@company.com', 'galileogalileipassword',true, 'USER', 3),
       ('Leonardo Da Vinci', 'leonardo.davinci@company.com', 'leonardodavincipassword',true, 'USER', 4),
       ('Archimedes', 'archimedes@company.com', 'archimedespassword',true, 'USER', 4),
       ('Ada Lovelace', 'ada.lovelace@company.com', 'adalovelacepassword',true, 'USER', 5),
       ('Alan Turing', 'alan.turing@company.com', 'alanturingpassword',true, 'USER', 5),
       ('James Maxwell', 'james.maxwell@company.com', 'jamesmaxwellpassword',true, 'USER', 6),
       ('Michael Faraday', 'michael.faraday@company.com', 'michaelfaradaypassword',true, 'USER', 6),
       ('Johannes Kepler', 'johannes.kepler@company.com', 'johanneskeplerpassword',true, 'USER', 7),
       ('Isaac Asimov', 'isaac.asimov@company.com', 'isaacasimovpassword',true, 'USER', 7),
       ('Stephen Hawking', 'stephen.hawking@company.com', 'stephenhawkingpassword',true, 'USER', 8),
       ('Richard Feynman', 'richard.feynman@company.com', 'richardfeynmanpassword',true, 'USER', 8),
       ('Carl Sagan', 'carl.sagan@company.com', 'carlsaganpassword',true, 'USER', 9),
       ('Erwin Schrödinger', 'erwin.schrodinger@company.com', 'erwinschrodingerpassword',true, 'USER', 9),
       ('Niels Bohr', 'niels.bohr@company.com', 'nielsbohrpassword',true, 'USER', 10),
       ('Werner Heisenberg', 'werner.heisenberg@company.com', 'wernerheisenbergpassword', true, 'USER', 10),
       ('Max Planck', 'max.planck@company.com', 'maxplanckpassword',true, 'USER', 11),
       ('Paul Dirac', 'paul.dirac@company.com', 'pauldiracpassword',true, 'USER', 11),
       ('Enrico Fermi', 'enrico.fermi@company.com', 'enricofermipassword',true, 'USER', 12),
       ('Robert Oppenheimer', 'robert.oppenheimer@company.com', 'robertoppenheimerpassword',true, 'USER', 12),
       ('Louis Pasteur', 'louis.pasteur@company.com', 'louispasteurpassword',true, 'USER', 13),
       ('Alexander Fleming', 'alexander.fleming@company.com', 'alexanderflemingpassword',true, 'USER', 13),
       ('Gregor Mendel', 'gregor.mendel@company.com', 'gregormendelpassword',true, 'USER', null),
       ('Charles Darwin', 'charles.darwin@company.com', 'charlesdarwinpassword',true, 'USER', null),
       ('Rosalind Franklin', 'rosalind.franklin@company.com', 'rosalindfranklinpassword',true, 'USER', null),
       ('Francis Crick', 'francis.crick@company.com', 'franciscrickpassword',true, 'USER', null),
       ('James Watson', 'james.watson@company.com', 'jameswatsonpassword',true, 'USER', null),
       ('Maurice Wilkins', 'maurice.wilkins@company.com', 'mauricewilkinspassword',true, 'USER', null);

INSERT INTO warehouse_product_category_capacities (capacity, minimum_stock_level, warehouse_id, product_category_id)
VALUES
    (100, 10, 1, 1),
    (200, 20, 1, 2),
    (300, 30, 1, 3),
    (400, 40, 1, 4),
    (500, 50, 1, 5),
    (600, 60, 1, 6),
    (700, 70, 1, 7),
    (800, 80, 2, 1),
    (900, 90, 2, 2),
    (1000, 100, 2, 3),
    (1100, 110, 2, 4),
    (1200, 120, 2, 5),
    (1300, 130, 2, 6),
    (1400, 140, 2, 7),
    (1500, 150, 3, 1),
    (1600, 160, 3, 2),
    (1700, 170, 3, 3),
    (1800, 180, 3, 4),
    (1900, 190, 3, 5),
    (2000, 200, 3, 6),
    (2100, 210, 3, 7),
    (2200, 220, 4, 1),
    (2300, 230, 4, 2),
    (2400, 240, 4, 3),
    (2500, 250, 4, 4),
    (2600, 260, 4, 5),
    (2700, 270, 4, 6),
    (2800, 280, 4, 7);

INSERT INTO Orders (description, is_delivered, order_date, delivery_date, warehouse_id, ordered_by_id)
VALUES ('Order 1', false, '2023-01-01', '2023-01-10', 1, 1),
       ('Order 2', true, '2023-02-01', '2023-02-10', 1, 2),
       ('Order 3', false, '2023-03-01', '2023-03-10', 2, 3),
       ('Order 4', true, '2023-04-01', '2023-04-10', 2, 4),
       ('Order 5', false, '2023-05-01', '2023-05-10', 3, 5),
       ('Order 6', true, '2023-06-01', '2023-06-10', 3, 6),
       ('Order 7', false, '2023-07-01', '2023-07-10', 4, 7),
       ('Order 8', true, '2023-08-01', '2023-08-10', 4, 8),
       ('Order 9', false, '2023-07-01', '2023-07-10', 1, 9),
       ('Order 10', true, '2023-07-15', '2023-07-25', 1, 10),
       ('Order 11', false, '2023-07-30', '2023-08-09', 2, 11),
       ('Order 12', true, '2023-08-14', '2023-08-24', 2, 12),
       ('Order 13', false, '2023-08-29', '2023-09-08', 3, 13),
       ('Order 14', true, '2023-09-13', '2023-09-23', 3, 14),
       ('Order 15', false, '2023-09-28', '2023-10-08', 4, 15),
       ('Order 16', true, '2023-10-13', '2023-10-23', 4, 16),
       ('Order 17', false, '2023-10-28', '2023-11-07', 1, 17),
       ('Order 18', true, '2023-11-12', '2023-11-22', 1, 18);

INSERT INTO Projects (name, description, location, client_name, client_email, client_phone, start_date, end_date, status,
                      team_id)
VALUES ('Project 1', 'Description for Project 1', 'Location 1', 'Client 1', 'client1@email.com', '111-111-1111',
        '2023-01-01', '2023-12-31', 'IN_PROGRESS', 1),
       ('Project 2', 'Description for Project 2', 'Location 2', 'Client 2', 'client2@email.com', '222-222-2222',
        '2023-02-01', '2023-12-31', 'IN_PROGRESS', 2),
       ('Project 3', 'Description for Project 3', 'Location 3', 'Client 3', 'client3@email.com', '333-333-3333',
        '2023-03-01', '2023-12-31', 'IN_PROGRESS', 3),
       ('Project 4', 'Description for Project 4', 'Location 4', 'Client 4', 'client4@email.com', '444-444-4444',
        '2023-04-01', '2023-12-31', 'FINISHED', 4),
       ('Project 5', 'Description for Project 5', 'Location 5', 'Client 5', 'client5@email.com', '555-555-5555',
        '2023-05-01', '2023-12-31', 'IN_PROGRESS', 5),
       ('Project 6', 'Description for Project 6', 'Location 6', 'Client 6', 'client6@email.com', '666-666-6666',
        '2023-06-01', '2023-12-31', 'IN_PROGRESS', 6),
       ('Project 7', 'Description for Project 7', 'Location 7', 'Client 7', 'client7@email.com', '777-777-7777',
        '2023-07-01', '2023-12-31', 'IN_PROGRESS', 7),
       ('Project 8', 'Description for Project 8', 'Location 8', 'Client 8', 'client8@email.com', '888-888-8888',
        '2023-08-01', '2023-12-31', 'IN_PROGRESS', 8),
       ('Project 9', 'Description for Project 9', 'Location 9', 'Client 9', 'client9@email.com', '999-999-9999',
        '2023-09-01', '2023-12-31', 'FINISHED', 9),
       ('Project 10', 'Description for Project 10', 'Location 10', 'Client 10', 'client10@email.com', '101-101-1010',
        '2023-10-01', '2023-12-31', 'IN_PROGRESS', 10),
       ('Project 11', 'Description for Project 11', 'Location 11', 'Client 11', 'client11@email.com', '111-111-1111',
        '2023-11-01', '2023-12-31', 'IN_PROGRESS', 11),
       ('Project 12', 'Description for Project 12', 'Location 12', 'Client 12', 'client12@email.com', '121-121-1212',
        '2023-12-01', '2023-12-31', 'IN_PROGRESS', 12),
       ('Project 13', 'Description for Project 13', 'Location 13', 'Client 13', 'client13@email.com', '131-131-1313',
        '2024-01-01', '2024-12-31', 'IN_PROGRESS', 13),
       ('Project 14', 'Description for Project 14', 'Location 14', 'Client 14', 'client14@email.com', '141-141-1414',
        '2024-02-01', '2024-12-31', 'FINISHED', 14),
       ('Project 15', 'Description for Project 15', 'Location 15', 'Client 15', 'client15@email.com', '151-151-1515',
        '2024-03-01', '2024-12-31', 'IN_PROGRESS', 15),
       ('Project 16', 'Description for Project 16', 'Location 16', 'Client 16', 'client16@email.com', '161-161-1616',
        '2024-04-01', '2024-12-31', 'IN_PROGRESS', 16),
       ('Project 17', 'Description for Project 17', 'Location 17', 'Client 17', 'client17@email.com', '171-171-1717',
        '2024-05-01', '2024-12-31', 'IN_PROGRESS', 1),
       ('Project 18', 'Description for Project 18', 'Location 18', 'Client 18', 'client18@email.com', '181-181-1818',
        '2024-06-01', '2024-12-31', 'IN_PROGRESS', 1),
       ('Project 19', 'Description for Project 19', 'Location 19', 'Client 19', 'client19@email.com', '191-191-1919',
        '2024-07-01', '2024-12-31', 'FINISHED', 2),
       ('Project 20', 'Description for Project 20', 'Location 20', 'Client 20', 'client20@email.com', '202-202-2020',
        '2024-08-01', '2024-12-31', 'IN_PROGRESS', 2),
       ('Project 21', 'Description for Project 21', 'Location 21', 'Client 21', 'client21@email.com', '211-211-2111',
        '2024-09-01', '2024-12-31', 'IN_PROGRESS', 3),
       ('Project 22', 'Description for Project 22', 'Location 22', 'Client 22', 'client22@email.com', '222-222-2222',
        '2024-10-01', '2024-12-31', 'IN_PROGRESS', 4),
       ('Project 23', 'Description for Project 23', 'Location 23', 'Client 23', 'client23@email.com', '233-233-2333',
        '2024-11-01', '2024-12-31', 'IN_PROGRESS', 5),
       ('Project 24', 'Description for Project 24', 'Location 24', 'Client 24', 'client24@email.com', '244-244-2444',
        '2024-12-01', '2024-12-31', 'FINISHED', 6),
       ('Project 25', 'Description for Project 25', 'Location 25', 'Client 25', 'client25@email.com', '255-255-2555',
        '2025-01-01', '2025-12-31', 'IN_PROGRESS', 7),
       ('Project 26', 'Description for Project 26', 'Location 26', 'Client 26', 'client26@email.com', '266-266-2666',
        '2025-02-01', '2025-12-31', 'IN_PROGRESS', 8),
       ('Project 27', 'Description for Project 27', 'Location 27', 'Client 27', 'client27@email.com', '277-277-2777',
        '2025-03-01', '2025-12-31', 'IN_PROGRESS', 9),
       ('Project 28', 'Description for Project 28', 'Location 28', 'Client 28', 'client28@email.com', '288-288-2888',
        '2025-04-01', '2025-12-31', 'IN_PROGRESS', 10),
       ('Project 29', 'Description for Project 29', 'Location 29', 'Client 29', 'client29@email.com', '299-299-2999',
        '2025-05-01', '2025-12-31', 'FINISHED', 11),
       ('Project 30', 'Description for Project 30', 'Location 30', 'Client 30', 'client30@email.com', '300-300-3000',
        '2025-06-01', '2025-12-31', 'IN_PROGRESS', 12);

-- Generate Project transactions
INSERT INTO Transactions (quantity, product_id, transaction_date, warehouse_id, project_id, transfer_from_id, order_id, transaction_type)
SELECT 10, 1, start_date, (SELECT warehouse_id FROM teams WHERE id = project_id), project_id, NULL, NULL, 'PROJECT_MATERIAL'
FROM (SELECT id as project_id, start_date FROM Projects WHERE id BETWEEN 1 AND 30) AS project_data
UNION ALL
SELECT 2, 5, start_date, (SELECT warehouse_id FROM teams WHERE id = project_id), project_id, NULL, NULL, 'PROJECT_MATERIAL'
FROM (SELECT id as project_id, start_date FROM Projects WHERE id BETWEEN 1 AND 30) AS project_data
UNION ALL
SELECT 5, 10, start_date, (SELECT warehouse_id FROM teams WHERE id = project_id), project_id, NULL, NULL, 'PROJECT_MATERIAL'
FROM (SELECT id as project_id, start_date FROM Projects WHERE id BETWEEN 1 AND 30) AS project_data
UNION ALL
SELECT 5, 13, start_date, (SELECT warehouse_id FROM teams WHERE id = project_id), project_id, NULL, NULL, 'PROJECT_MATERIAL'
FROM (SELECT id as project_id, start_date FROM Projects WHERE id BETWEEN 1 AND 30) AS project_data
UNION ALL
SELECT 10, 14, start_date, (SELECT warehouse_id FROM teams WHERE id = project_id), project_id, NULL, NULL, 'PROJECT_MATERIAL'
FROM (SELECT id as project_id, start_date FROM Projects WHERE id BETWEEN 1 AND 30) AS project_data
UNION ALL
SELECT 15, 15, start_date, (SELECT warehouse_id FROM teams WHERE id = project_id), project_id, NULL, NULL, 'PROJECT_MATERIAL'
FROM (SELECT id as project_id, start_date FROM Projects WHERE id BETWEEN 1 AND 30) AS project_data
UNION ALL
SELECT 12, 16, start_date, (SELECT warehouse_id FROM teams WHERE id = project_id), project_id, NULL, NULL, 'PROJECT_MATERIAL'
FROM (SELECT id as project_id, start_date FROM Projects WHERE id BETWEEN 1 AND 30) AS project_data;

-- Generate Order transactions
INSERT INTO Transactions (quantity, product_id, transaction_date, warehouse_id, order_id, project_id, transfer_from_id, transaction_type)
SELECT 12, 1, order_date, warehouse_id, order_id, NULL, NULL, 'ORDER'
FROM (SELECT id as order_id, order_date, warehouse_id FROM Orders WHERE id BETWEEN 1 AND 18) AS order_data
UNION ALL
SELECT 3, 5, order_date, warehouse_id, order_id, NULL, NULL, 'ORDER'
FROM (SELECT id as order_id, order_date, warehouse_id FROM Orders WHERE id BETWEEN 1 AND 18) AS order_data
UNION ALL
SELECT 6, 10, order_date, warehouse_id, order_id, NULL, NULL, 'ORDER'
FROM (SELECT id as order_id, order_date, warehouse_id FROM Orders WHERE id BETWEEN 1 AND 18) AS order_data
UNION ALL
SELECT 7, 13, order_date, warehouse_id, order_id, NULL, NULL, 'ORDER'
FROM (SELECT id as order_id, order_date, warehouse_id FROM Orders WHERE id BETWEEN 1 AND 18) AS order_data
UNION ALL
SELECT 13, 14, order_date, warehouse_id, order_id, NULL, NULL, 'ORDER'
FROM (SELECT id as order_id, order_date, warehouse_id FROM Orders WHERE id BETWEEN 1 AND 18) AS order_data
UNION ALL
SELECT 16, 15, order_date, warehouse_id, order_id, NULL, NULL, 'ORDER'
FROM (SELECT id as order_id, order_date, warehouse_id FROM Orders WHERE id BETWEEN 1 AND 18) AS order_data
UNION ALL
SELECT 17, 16, order_date, warehouse_id, order_id, NULL, NULL, 'ORDER'
FROM (SELECT id as order_id, order_date, warehouse_id FROM Orders WHERE id BETWEEN 1 AND 18) AS order_data;

-- Generate Tasks
INSERT INTO Tasks (name, description, deadline, priority, status, project_id)
SELECT 'Task 1', 'Description for Task 1', start_date, 'HIGH', 'TODO', project_id
FROM (SELECT id as project_id, start_date FROM Projects WHERE id BETWEEN 1 AND 30) AS project_data
UNION ALL
SELECT 'Task 2', 'Description for Task 2', start_date, 'MEDIUM', 'IN_PROGRESS', project_id
FROM (SELECT id as project_id, start_date FROM Projects WHERE id BETWEEN 1 AND 30) AS project_data
UNION ALL
SELECT 'Task 3', 'Description for Task 3', start_date, 'LOW', 'DONE', project_id
FROM (SELECT id as project_id, start_date FROM Projects WHERE id BETWEEN 1 AND 30) AS project_data;



