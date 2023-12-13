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
INSERT INTO users (name, email, password, role, team_id)
VALUES ('John Doe', 'admin@admin.com', 'admin', 'ADMIN', 1),
       ('Jane Smith', 'user@user.com', 'user', 'USER', 2),
       ('Robert Johnson', 'robert.johnson@company.com', 'robertjohnsonpassword', 'USER', 3),
       ('Patricia Williams', 'patricia.williams@company.com', 'patriciawilliamspassword', 'USER', 4),
       ('Michael Brown', 'michael.brown@company.com', 'michaelbrownpassword', 'USER', 5),
       ('Linda Miller', 'linda.miller@company.com', 'lindamillerpassword', 'USER', 6),
       ('James Davis', 'james.davis@company.com', 'jamesdavispassword', 'USER', 7),
       ('Elizabeth Garcia', 'elizabeth.garcia@company.com', 'elizabethgarciapassword', 'USER', 8),
       ('William Rodriguez', 'william.rodriguez@company.com', 'williamrodriguezpassword', 'USER', 9),
       ('Jennifer Wilson', 'jennifer.wilson@company.com', 'jenniferwilsonpassword', 'USER', 9),
       ('Alice Johnson', 'alice.johnson@company.com', 'alicejohnsonpassword', 'USER', 9),
       ('Bob Williams', 'bob.williams@company.com', 'bobwilliamspassword', 'USER', 10),
       ('Charlie Brown', 'charlie.brown@company.com', 'charliebrownpassword', 'USER', 11),
       ('David Miller', 'david.miller@company.com', 'davidmillerpassword', 'USER', 12),
       ('Eve Davis', 'eve.davis@company.com', 'evedavispassword', 'USER', 13),
       ('Frank Garcia', 'frank.garcia@company.com', 'frankgarciapassword', 'USER', 14),
       ('Grace Rodriguez', 'grace.rodriguez@company.com', 'gracerodriguezpassword', 'USER', 15),
       ('Harry Wilson', 'harry.wilson@company.com', 'harrywilsonpassword', 'USER', 16),
       ('Isaac Newton', 'isaac.newton@company.com', 'isaacnewtonpassword', 'USER', 1),
       ('Albert Einstein', 'albert.einstein@company.com', 'alberteinsteinpassword', 'USER', 1),
       ('Marie Curie', 'marie.curie@company.com', 'mariecuriepassword', 'USER', 2),
       ('Nikola Tesla', 'nikola.tesla@company.com', 'nikolateslapassword', 'USER', 2),
       ('Thomas Edison', 'thomas.edison@company.com', 'thomasedisonpassword', 'USER', 3),
       ('Galileo Galilei', 'galileo.galilei@company.com', 'galileogalileipassword', 'USER', 3),
       ('Leonardo Da Vinci', 'leonardo.davinci@company.com', 'leonardodavincipassword', 'USER', 4),
       ('Archimedes', 'archimedes@company.com', 'archimedespassword', 'USER', 4),
       ('Ada Lovelace', 'ada.lovelace@company.com', 'adalovelacepassword', 'USER', 5),
       ('Alan Turing', 'alan.turing@company.com', 'alanturingpassword', 'USER', 5),
       ('James Maxwell', 'james.maxwell@company.com', 'jamesmaxwellpassword', 'USER', 6),
       ('Michael Faraday', 'michael.faraday@company.com', 'michaelfaradaypassword', 'USER', 6),
       ('Johannes Kepler', 'johannes.kepler@company.com', 'johanneskeplerpassword', 'USER', 7),
       ('Isaac Asimov', 'isaac.asimov@company.com', 'isaacasimovpassword', 'USER', 7),
       ('Stephen Hawking', 'stephen.hawking@company.com', 'stephenhawkingpassword', 'USER', 8),
       ('Richard Feynman', 'richard.feynman@company.com', 'richardfeynmanpassword', 'USER', 8),
       ('Carl Sagan', 'carl.sagan@company.com', 'carlsaganpassword', 'USER', 9),
       ('Erwin Schrödinger', 'erwin.schrodinger@company.com', 'erwinschrodingerpassword', 'USER', 9),
       ('Niels Bohr', 'niels.bohr@company.com', 'nielsbohrpassword', 'USER', 10),
       ('Werner Heisenberg', 'werner.heisenberg@company.com', 'wernerheisenbergpassword', 'USER', 10),
       ('Max Planck', 'max.planck@company.com', 'maxplanckpassword', 'USER', 11),
       ('Paul Dirac', 'paul.dirac@company.com', 'pauldiracpassword', 'USER', 11),
       ('Enrico Fermi', 'enrico.fermi@company.com', 'enricofermipassword', 'USER', 12),
       ('Robert Oppenheimer', 'robert.oppenheimer@company.com', 'robertoppenheimerpassword', 'USER', 12),
       ('Louis Pasteur', 'louis.pasteur@company.com', 'louispasteurpassword', 'USER', 13),
       ('Alexander Fleming', 'alexander.fleming@company.com', 'alexanderflemingpassword', 'USER', 13),
       ('Gregor Mendel', 'gregor.mendel@company.com', 'gregormendelpassword', 'USER', null),
       ('Charles Darwin', 'charles.darwin@company.com', 'charlesdarwinpassword', 'USER', null),
       ('Rosalind Franklin', 'rosalind.franklin@company.com', 'rosalindfranklinpassword', 'USER', null),
       ('Francis Crick', 'francis.crick@company.com', 'franciscrickpassword', 'USER', null),
       ('James Watson', 'james.watson@company.com', 'jameswatsonpassword', 'USER', null),
       ('Maurice Wilkins', 'maurice.wilkins@company.com', 'mauricewilkinspassword', 'USER', null),
       ('Johnny Magielse', 'johnnymihai33@gmail.com', 'johnny', 'ADMIN', null);

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
       ('Order 18', true, '2023-11-12', '2023-11-22', 1, 18),
       ('Order 19', false, '2023-12-03', '2023-12-13', 2, 19),
       ('Order 20', true, '2024-01-20', '2024-01-30', 3, 20),
       ('Order 21', false, '2023-12-18', '2023-12-28', 4, 21),
       ('Order 22', true,  '2024-01-04', '2024-01-14', 1, 22),
       ('Order 23', false, '2024-01-19', '2024-01-29', 2, 23),
       ('Order 24', true,  '2024-02-03', '2024-02-13', 3, 24),
       ('Order 25', false, '2024-02-18', '2024-02-28', 4, 25),
       ('Order 26', true,  '2023-12-13', '2023-12-23', 4, 26),
       ('Order 27', false, '2024-01-08', '2024-01-18', 1, 27),
       ('Order 28', true,  '2024-01-13', '2024-01-23', 2, 28),
       ('Order 29', false, '2024-02-08', '2024-02-18', 3, 29),
       ('Order 30', true,  '2024-02-23', '2024-03-05', 4, 30);

INSERT INTO Projects (name, description, location, client_name, client_email, client_phone, start_date, end_date, status,
                      team_id)
VALUES ('Energy InfraStructure', 'Revamping the energy infrastructure', 'Amsterdam', 'Amsterdam Municipality', 'municipality1@email.com', '112-113-1141',
        '2023-07-01', '2023-10-15', 'IN_PROGRESS', 1),
       ('The Canal Rejuvenation', 'Improving the quality of canals', 'Rotterdam', 'Rotterdam City', 'cityRotterdam@email.com', '221-212-2222',
        '2023-07-05', '2023-11-01', 'FINISHED', 2),
       ('Implementing Smart City', 'Implementation of Smart City solutions', 'Eindhoven', 'Eindhoven City', 'cityEindhoven@email.com', '331-333-3311',
        '2023-07-15', '2023-11-15', 'IN_PROGRESS', 3),
       ('Transport Campaign', 'Launch of new transport campaign', 'Utrecht', 'Utrecht Govt.', 'govtUtrecht@email.com', '441-422-4442',
        '2023-08-01', '2023-12-01', 'IN_PROGRESS', 4),
       ('Cycling Track Enhancement', 'Improvement and enhancement of cycling tracks', 'Groningen', 'Groningen City', 'cityGroningen@email.com', '551-532-5550',
        '2023-08-05', '2023-10-30', 'FINISHED', 5),
       ('Smart Parking Solution', 'Introduction of smart parking solutions', 'The Hague', 'Hague City', 'cityHague@email.com', '661-644-6664',
        '2023-09-01', '2024-01-01', 'IN_PROGRESS', 6),
       ('Urban Reforming', 'Urban reforming projects', 'Delft', 'Delft City', 'cityDelft@email.com', '771-772-7772',
        '2023-10-01', '2024-01-15', 'IN_PROGRESS', 7),
       ('Modern Agriculture', 'Introduction of modern agriculture techniques', 'Tilburg', 'Tilburg City', 'cityTilburg@email.com', '881-882-8882',
        '2023-10-15', '2024-01-10', 'FINISHED', 8),
       ('Smart Traffic Signals', 'Implementing smart traffic signals', 'Nijmegen', 'Nijmegen Govt.', 'govtNijmegen@email.com', '991-992-9992',
        '2023-11-01', '2024-01-10', 'IN_PROGRESS', 9),
       ('Eco-Friendly Solutions', 'Introducing eco-friendly living solutions', 'Haarlem', 'Haarlem City', 'cityHaarlem@email.com', '101-112-1102',
        '2023-12-01', '2024-01-05', 'IN_PROGRESS', 10),
       ('Tourism Enhancement', 'Projects enhancing tourism possibilities', 'Dordrecht', 'Dordrecht City', 'cityDordrecht@email.com', '121-132-1212',
        '2023-12-01', '2024-01-15', 'IN_PROGRESS', 11),
       ('Digitalization of Libraries', 'Digitalizing the city libraries', 'Amsterdam', 'Amsterdam City', 'cityAmsterdam1@email.com', '131-142-1312',
        '2023-09-01', '2024-01-02', 'FINISHED', 12),
       ('Modern Sanitation', 'Introducing modern sanitation and recycling', 'Enschede', 'Enschede Govt.', 'govtEnschede@email.com', '141-152-1422',
        '2023-09-15', '2023-12-31', 'IN_PROGRESS', 13),
       ('City Wifi Project', 'Providing Wi-Fi connectivity in public places', 'Leeuwarden', 'Leeuwarden City', 'cityLeeuwarden@email.com', '151-162-1512',
        '2023-08-01', '2023-12-15', 'IN_PROGRESS', 1),
       ('Urban Gardening', 'Promoting urban gardening', 'Arnhem', 'Arnhem City', 'cityArnhem@email.com', '162-163-1622',
        '2023-07-01', '2023-12-31', 'FINISHED', 2),
       ('Renewable Energy', 'Promotion of renewable energy usage', 'Breda', 'Breda Govt.', 'govtBreda@email.com', '173-174-1732',
        '2023-08-01', '2023-12-10', 'IN_PROGRESS', 3),
       ('Health Campaign', 'Health awareness campaign', 'Almere', 'Almere City', 'cityAlmere@email.com', '184-185-1842',
        '2023-08-15', '2024-01-05', 'IN_PROGRESS', 4),
       ('Public Transport Upgrade', 'Upgrading public transport system', 'Spijkenisse', 'Spijkenisse City', 'citySpijkenisse@email.com', '195-196-1952',
        '2023-07-15', '2023-10-30', 'FINISHED', 5),
       ('Revamp of Parks', 'Revamping of city parks', 'Apeldoorn', 'Apeldoorn City', 'cityApeldoorn@email.com', '206-207-2062',
        '2023-09-01', '2023-12-20', 'IN_PROGRESS', 6),
       ('Affordable Housing', 'Projects for affordable housing', 'Venlo', 'Venlo Govt.', 'govtVenlo@email.com', '217-218-2172',
        '2023-08-01', '2023-12-15', 'IN_PROGRESS', 7),
       ('City Aquatics', 'Improvement of city aquatics facilities', 'Zaanstad', 'Zaanstad City', 'cityZaanstad@email.com', '228-229-2282',
        '2023-07-01', '2023-10-20', 'FINISHED', 8),
       ('Vinex Housing Project', 'Working on large-scale Vinex housing projects', 'Utrecht', 'Utrecht Govt.', 'govtUtrecht1@email.com', '239-240-2392',
        '2023-08-01', '2023-12-30', 'IN_PROGRESS', 9),
       ('Elderly Care Houses', 'Building elderly care houses', 's-Hertogenbosch', 's-Hertogenbosch City', 'citysHertogenbosch@email.com', '250-251-2502',
        '2023-07-01', '2023-11-01', 'IN_PROGRESS', 10),
       ('Museum Renovation', 'Renovation of the city museum', 'Zoetermeer', 'Zoetermeer City', 'cityZoetermeer@email.com', '261-262-2612',
        '2023-09-01', '2023-12-31', 'FINISHED', 11),
       ('Public Space Upgradation', 'Improving public spaces', 'Maastricht', 'Maastricht City', 'cityMaastricht@email.com', '272-273-2722',
        '2023-10-01', '2024-01-15', 'IN_PROGRESS', 12),
       ('Historic Sites Revival', 'Revival of historic sites', 'Leiden', 'Leiden City', 'cityLeiden@email.com', '287-282-2882',
        '2023-10-01', '2023-12-31', 'FINISHED', 13),
       ('Smart Street Lighting', 'Implementing smart street lighting', 'Zwolle', 'Zwolle City', 'cityZwolle@email.com', '296-290-2990',
        '2023-09-01', '2023-12-27', 'FINISHED', 14),
       ('Solar Power Harvesting', 'Solar power harvesting projects', 'Lelystad', 'Lelystad City', 'cityLelystad@email.com', '309-310-3020',
        '2023-07-25', '2023-12-24', 'FINISHED', 15),
       ('Water Management', 'Water management and waste water treatment', 'Roermond', 'Roermond City', 'cityRoermond@email.com', '312-311-3131',
        '2023-09-01', '2024-01-01', 'FINISHED', 16),
       ('Educational Reform', 'Educational reform in primary schools', 'Zutphen', 'Zutphen Govt.', 'govtZutphen@email.com', '324-322-3232',
        '2023-09-15', '2024-01-01', 'FINISHED', 1),
       ('Public Health Care', 'Upgrade of public health care facilities', 'Assen', 'Assen City', 'cityAssen@email.com', '335-332-3422',
        '2023-07-10', '2023-10-31', 'FINISHED', 2),
       ('Music Fest Organization', 'Organizing the annual music festival', 'Goes', 'Goes City', 'cityGoes@email.com', '345-352-3512',
        '2023-09-09', '2023-12-09', 'FINISHED', 3),
       ('Public Library Expansion', 'Expansion of public libraries', 'Vlissingen', 'Vlissingen City', 'cityVlissingen@email.com', '365-364-3634',
        '2023-07-07', '2023-11-07', 'FINISHED', 4),
       ('Recycling Campaign', 'Citywide recycling and waste management campaign', 'Alkmaar', 'Alkmaar City', 'cityAlkmaar@email.com', '375-374-3734',
        '2023-09-01', '2024-01-01', 'FINISHED', 5),
       ('Digital Governance', 'Implementing digital governance system', 'Gouda', 'Gouda Govt.', 'govtGouda@email.com', '387-386-3854',
        '2023-11-01', '2024-01-05', 'FINISHED', 6),
       ('Green Transport Initiative', 'Improving green transport methods', 'Amsterdam', 'Amsterdam Municipality', 'Municipality1@email.com', '388-389-3901',
        '2024-01-01', '2024-04-15', 'IN_PROGRESS', 1),
       ('Smart City Project', 'Upgrading city infrastructures with Smart Solutions', 'Rotterdam', 'Rotterdam City', 'CityRotterdam@email.com', '391-392-3932',
        '2024-01-05', '2024-05-01', 'FINISHED', 2),
       ('Digital Education', 'Implementing Digital Education in Schools', 'Eindhoven', 'Eindhoven City', 'cityEindhoven@email.com', '394-395-3963',
        '2024-01-10', '2024-05-15', 'IN_PROGRESS', 3),
       ('Tourism Promotion Campaign', 'Promoting Regional Tourism', 'Utrecht', 'Utrecht Govt.', 'GovtUtrecht@email.com', '397-398-3994',
        '2024-01-15', '2024-05-20', 'IN_PROGRESS', 4),
       ('Eco City Project', 'Transitioning towards an Eco-Friendly City', 'Groningen', 'Groningen City', 'CityGroningen@email.com', '400-401-4025',
        '2024-01-20', '2024-05-25', 'FINISHED', 5),
       ('Digital Economy', 'Encouraging the Digital Economy through various initiatives', 'The Hague', 'Hague City', 'CityHague@email.com', '403-404-4056',
        '2024-01-25', '2024-05-30', 'IN_PROGRESS', 6),
       ('Renewable Energy Project', 'Implementing Renewable Energy Solutions', 'Delft', 'Delft City', 'cityDelft@email.com', '406-407-4087',
        '2024-01-30', '2024-06-05', 'IN_PROGRESS', 7),
       ('Smart Agriculture', 'Modernizing Agriculture with Smart Techniques', 'Tilburg', 'Tilburg City', 'cityTilburg@email.com', '409-410-4118',
        '2024-02-04', '2024-06-10', 'FINISHED', 8),
       ('Smart Traffic Management', 'Upgrading Traffic Signals and Management', 'Nijmegen', 'Nijmegen Govt.', 'GovtNijmegen@email.com', '412-413-4149',
        '2024-02-09', '2024-06-15', 'IN_PROGRESS', 9),
       ('Environmental Awareness Campaign', 'Running an Environmental Awareness Campaign', 'Haarlem', 'Haarlem City', 'cityHaarlem@email.com', '415-416-4170',
        '2024-02-14', '2024-06-20', 'IN_PROGRESS', 10),
       ('Public Safety Measures', 'Enhancing Public Safety Measures', 'Dordrecht', 'Dordrecht City', 'CityDordrecht@email.com', '418-419-4201',
        '2024-02-19', '2024-06-25', 'IN_PROGRESS', 11),
       ('Heritage Site Restoration', 'Restoration and Preservation of Heritage sites', 'Amsterdam', 'Amsterdam City', 'cityAmsterdam1@email.com', '421-422-4232',
        '2024-02-24', '2024-06-30', 'FINISHED', 12),
       ('Public Healthcare Improvement', 'Upgrading Public Healthcare Facilities and Services', 'Enschede', 'Enschede Govt.', 'GovtEnschede@email.com', '424-425-4263',
        '2024-02-29', '2024-07-05', 'IN_PROGRESS', 13),
       ('Rural Development Project', 'Improving Infrastructure and Opportunities in Rural Areas', 'Leeuwarden', 'Leeuwarden City', 'CityLeeuwarden@email.com', '427-428-4294',
        '2024-03-05', '2024-07-10', 'IN_PROGRESS', 1),
       ('Free Public WiFi', 'Implementing Free Public WiFi in select city areas', 'Arnhem', 'Arnhem City', 'CityArnhem@email.com', '430-431-4325',
        '2024-03-10', '2024-07-15', 'FINISHED', 2),
       ('Public Library Digitalization', 'Digitalizing Public Libraries', 'Breda', 'Breda Govt.', 'GovtBreda@email.com', '433-434-4356',
        '2024-03-15', '2024-07-20', 'IN_PROGRESS', 3),
       ('Urban Housing Scheme', 'Initiating Affordable Urban Housing Projects', 'Spijkenisse', 'Spijkenisse City', 'CitySpijkenisse@email.com', '436-437-4387',
        '2024-03-20', '2024-07-25', 'IN_PROGRESS', 4),
       ('Riverfront Development', 'Developing and Beautifying the Riverfront area', 'Apeldoorn', 'Apeldoorn City', 'CityApeldoorn@email.com', '439-440-4418',
        '2024-03-25', '2024-07-30', 'FINISHED', 5),
       ('Public Transport Reform', 'Reforming Public Transport for better efficiency and reach', 'Venlo', 'Venlo Govt.', 'GovtVenlo@email.com', '442-443-4449',
        '2024-03-30', '2024-08-04', 'IN_PROGRESS', 6),
       ('Solar Energy Project', 'Implementing Solar Energy Projects and Schemes', 'Zaanstad', 'Zaanstad City', 'CityZaanstad@email.com', '445-446-4470',
        '2024-04-04', '2024-08-09', 'IN_PROGRESS', 7);

INSERT INTO Task (order, name, description, deadline, status, project_id, personalTodoListOwner_id)
VALUES (1, 'Task 1', 'This is task 1', '2023-07-01', 'TODO', 1, 1),
       (2, 'Task 2', 'This is task 2', '2023-07-02', 'IN_PROGRESS', 1, 2),
       (3, 'Task 3', 'This is task 3', '2023-07-03', 'DONE', 2, 3),
       (4, 'Task 4', 'This is task 4', '2023-07-04', 'TODO', 2, 4),
       (5, 'Task 5', 'This is task 5', '2023-07-05', 'IN_PROGRESS', 3, 5),
       (6, 'Task 6', 'This is task 6', '2023-07-06', 'DONE', 3, 6),
       (7, 'Task 7', 'This is task 7', '2023-07-07', 'TODO', 4, 7),
       (8, 'Task 8', 'This is task 8', '2023-07-08', 'IN_PROGRESS', 4, 8),
       (9, 'Task 9', 'This is task 9', '2023-07-09', 'DONE', 5, 9),
       (10, 'Task 10', 'This is task 10', '2023-07-10', 'TODO', 5, 10);


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
FROM (SELECT id as order_id, order_date, warehouse_id FROM Orders WHERE id BETWEEN 1 AND 18) AS order_data
UNION ALL
SELECT 12, 1, order_date, warehouse_id, order_id, NULL, NULL, 'ORDER'
FROM (SELECT id as order_id, order_date, warehouse_id FROM Orders WHERE id BETWEEN 19 AND 30) AS order_data
UNION ALL
SELECT 3, 5, order_date, warehouse_id, order_id, NULL, NULL, 'ORDER'
FROM (SELECT id as order_id, order_date, warehouse_id FROM Orders WHERE id BETWEEN 19 AND 30) AS order_data
UNION ALL
SELECT 6, 10, order_date, warehouse_id, order_id, NULL, NULL, 'ORDER'
FROM (SELECT id as order_id, order_date, warehouse_id FROM Orders WHERE id BETWEEN 19 AND 30) AS order_data
UNION ALL
SELECT 7, 13, order_date, warehouse_id, order_id, NULL, NULL, 'ORDER'
FROM (SELECT id as order_id, order_date, warehouse_id FROM Orders WHERE id BETWEEN 19 AND 30) AS order_data
UNION ALL
SELECT 13, 14, order_date, warehouse_id, order_id, NULL, NULL, 'ORDER'
FROM (SELECT id as order_id, order_date, warehouse_id FROM Orders WHERE id BETWEEN 19 AND 30) AS order_data
UNION ALL
SELECT 16, 15, order_date, warehouse_id, order_id, NULL, NULL, 'ORDER'
FROM (SELECT id as order_id, order_date, warehouse_id FROM Orders WHERE id BETWEEN 19 AND 30) AS order_data
UNION ALL
SELECT 17, 16, order_date, warehouse_id, order_id, NULL, NULL, 'ORDER'
FROM (SELECT id as order_id, order_date, warehouse_id FROM Orders WHERE id BETWEEN 19 AND 30) AS order_data;




