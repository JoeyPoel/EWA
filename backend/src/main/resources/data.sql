-- generate data for the database

-- generate warehouses
INSERT INTO warehouses (name, location, address, postcode, country, contact_name, contact_email, contact_phone)
VALUES
    ('Andromeda Warehouse', 'Amsterdam', 'Andromedaweg 1', '1001AD', 'The Netherlands', 'Alice Star', 'alice@andromeda.com', '0611111111'),
    ('Sirius Storage', 'Rotterdam', 'Siriusstraat 2', '2002SR', 'The Netherlands', 'Bob Bright', 'bob@sirius.com', '0622222222'),
    ('Milky Way Depot', 'The Hague', 'Milkyweg 3', '3003MW', 'The Netherlands', 'Carol Cosmos', 'carol@milkyway.com', '0633333333'),
    ('Orion Facility', 'Utrecht', 'Orionlaan 4', '4004OR', 'The Netherlands', 'Dave Orion', 'dave@orion.com', '0644444444'),
    ('Vega Vault', 'Leiden', 'Vegaweg 5', '5005VG', 'The Netherlands', 'Eve Vega', 'eve@vega.com', '0655555555'),
    ('Centauri Center', 'Eindhoven', 'Centauristraat 6', '6006CN', 'The Netherlands', 'Frank Centauri', 'frank@centauri.com', '0666666666'),
    ('Polaris Port', 'Groningen', 'Polarisplein 7', '7007PL', 'The Netherlands', 'Grace Polaris', 'grace@polaris.com', '0677777777'),
    ('Lyra Loft', 'Maastricht', 'Lyralaan 8', '8008LY', 'The Netherlands', 'Henry Lyra', 'henry@lyra.com', '0688888888'),
    ('Cassiopeia Compound', 'Breda', 'Cassiopeiaweg 9', '9009CS', 'The Netherlands', 'Ivy Cassiopeia', 'ivy@cassiopeia.com', '0699999999'),
    ('Galileo Garage', 'Almere', 'Galileistraat 10', '1010GL', 'The Netherlands', 'Jack Galileo', 'jack@galileo.com', '0610101010');


-- generate product categories
INSERT INTO product_categories (name, description)
VALUES
    ('Solar Panel', 'Photovoltaic modules designed for converting solar energy into electrical power'),
    ('Inverter', 'Device that converts direct current (DC) from solar panels into alternating current (AC) for use in homes and businesses'),
    ('Solar Energy Monitoring System', 'Systems designed to monitor and manage the performance and output of solar energy installations');

-- generate products
INSERT INTO products (name, description, category_id, price)
VALUES
    ('Enphase IQ7+', 'Advanced microinverter for efficient solar energy conversion', 2, 210.00),
    ('DENIM 405 Black', 'High-performance black solar panel with a 405W capacity', 1, 180.00),
    ('MB 385 White', 'Eco-friendly white solar panel with a 385W capacity', 1, 170.00),
    ('Gateway Standard', 'Standard solar system gateway for monitoring and control', 2, 120.00),
    ('MB 390wp White', 'Energy-efficient white solar panel with 390 watts peak power', 1, 175.00),
    ('Enphase Envoy', 'Smart solar energy monitoring system for efficient management', 3, 200.00);

-- generate teams
INSERT INTO teams (name, warehouse_id)
VALUES
    ('Galaxy Orion', 5),
    ('Galaxy Nebula', 5),
    ('Galaxy Comet', 5),
    ('Galaxy Cosmos', 5),
    ('Cosmic Pioneers', 6),
    ('Cosmic Voyagers', 6),
    ('Cosmic Explorers', 6),
    ('Cosmic Navigators', 6),
    ('Stellar Supernovas', 7),
    ('Stellar Meteors', 7),
    ('Stellar Quasars', 7),
    ('Stellar Pulsars', 7),
    ('Solar Pioneers', 1),
    ('Solar Voyagers', 1),
    ('Solar Explorers', 1),
    ('Solar Navigators', 1),
    ('Lunar Rangers', 2),
    ('Lunar Pathfinders', 2),
    ('Lunar Discoverers', 2),
    ('Star Astronauts', 3),
    ('Star Cosmonauts', 3),
    ('Galaxy Astronomers', 4);

-- generate users
insert into users (name, email, password, role, team_id)
values ('john doe', 'admin@admin.com', 'admin', 'ADMIN', 1),
       ('jane smith', 'user@user.com', 'user', 'USER', 2),
       ('robert johnson', 'robert.johnson@company.com', 'robertjohnsonpassword', 'USER', 3),
       ('patricia williams', 'patricia.williams@company.com', 'patriciawilliamspassword', 'USER', 4),
       ('michael brown', 'michael.brown@company.com', 'michaelbrownpassword', 'USER', 5),
       ('linda miller', 'linda.miller@company.com', 'lindamillerpassword', 'USER', 6),
       ('james davis', 'james.davis@company.com', 'jamesdavispassword', 'USER', 7),
       ('elizabeth garcia', 'elizabeth.garcia@company.com', 'elizabethgarciapassword', 'USER', 8),
       ('william rodriguez', 'william.rodriguez@company.com', 'williamrodriguezpassword', 'USER', 9),
       ('jennifer wilson', 'jennifer.wilson@company.com', 'jenniferwilsonpassword', 'USER', 9),
       ('alice johnson', 'alice.johnson@company.com', 'alicejohnsonpassword', 'USER', 9),
       ('bob williams', 'bob.williams@company.com', 'bobwilliamspassword', 'USER', 10),
       ('charlie brown', 'charlie.brown@company.com', 'charliebrownpassword', 'USER', 11),
       ('david miller', 'david.miller@company.com', 'davidmillerpassword', 'USER', 12),
       ('eve davis', 'eve.davis@company.com', 'evedavispassword', 'USER', 13),
       ('frank garcia', 'frank.garcia@company.com', 'frankgarciapassword', 'USER', 14),
       ('grace rodriguez', 'grace.rodriguez@company.com', 'gracerodriguezpassword', 'USER', 15),
       ('harry wilson', 'harry.wilson@company.com', 'harrywilsonpassword', 'USER', 16),
       ('isaac newton', 'isaac.newton@company.com', 'isaacnewtonpassword', 'USER', 1),
       ('albert einstein', 'albert.einstein@company.com', 'alberteinsteinpassword', 'USER', 1),
       ('marie curie', 'marie.curie@company.com', 'mariecuriepassword', 'USER', 2),
       ('nikola tesla', 'nikola.tesla@company.com', 'nikolateslapassword', 'USER', 2),
       ('thomas edison', 'thomas.edison@company.com', 'thomasedisonpassword', 'USER', 3),
       ('galileo galilei', 'galileo.galilei@company.com', 'galileogalileipassword', 'USER', 3),
       ('leonardo da vinci', 'leonardo.davinci@company.com', 'leonardodavincipassword', 'USER', 4),
       ('archimedes', 'archimedes@company.com', 'archimedespassword', 'USER', 4),
       ('ada lovelace', 'ada.lovelace@company.com', 'adalovelacepassword', 'USER', 5),
       ('alan turing', 'alan.turing@company.com', 'alanturingpassword', 'USER', 5),
       ('james maxwell', 'james.maxwell@company.com', 'jamesmaxwellpassword', 'USER', 6),
       ('michael faraday', 'michael.faraday@company.com', 'michaelfaradaypassword', 'USER', 6),
       ('johannes kepler', 'johannes.kepler@company.com', 'johanneskeplerpassword', 'USER', 7),
       ('isaac asimov', 'isaac.asimov@company.com', 'isaacasimovpassword', 'USER', 7),
       ('stephen hawking', 'stephen.hawking@company.com', 'stephenhawkingpassword', 'USER', 8),
       ('richard feynman', 'richard.feynman@company.com', 'richardfeynmanpassword', 'USER', 8),
       ('carl sagan', 'carl.sagan@company.com', 'carlsaganpassword', 'USER', 9),
       ('erwin schrödinger', 'erwin.schrodinger@company.com', 'erwinschrodingerpassword', 'USER', 9),
       ('niels bohr', 'niels.bohr@company.com', 'nielsbohrpassword', 'USER', 10),
       ('werner heisenberg', 'werner.heisenberg@company.com', 'wernerheisenbergpassword', 'USER', 10),
       ('max planck', 'max.planck@company.com', 'maxplanckpassword', 'USER', 11),
       ('paul dirac', 'paul.dirac@company.com', 'pauldiracpassword', 'USER', 11),
       ('enrico fermi', 'enrico.fermi@company.com', 'enricofermipassword', 'USER', 12),
       ('robert oppenheimer', 'robert.oppenheimer@company.com', 'robertoppenheimerpassword', 'USER', 12),
       ('louis pasteur', 'louis.pasteur@company.com', 'louispasteurpassword', 'USER', 13),
       ('alexander fleming', 'alexander.fleming@company.com', 'alexanderflemingpassword', 'USER', 13),
       ('gregor mendel', 'gregor.mendel@company.com', 'gregormendelpassword', 'USER', 17),
       ('charles darwin', 'charles.darwin@company.com', 'charlesdarwinpassword', 'USER', 18),
       ('rosalind franklin', 'rosalind.franklin@company.com', 'rosalindfranklinpassword', 'USER', 19),
       ('francis crick', 'francis.crick@company.com', 'franciscrickpassword', 'USER', 20),
       ('james watson', 'james.watson@company.com', 'jameswatsonpassword', 'USER', 22),
       ('maurice wilkins', 'maurice.wilkins@company.com', 'mauricewilkinspassword', 'USER', 21),
       ('johnny magielse', 'johnnymihai33@gmail.com', 'johnny', 'ADMIN', null);

insert into warehouse_product_category_capacities (capacity, minimum_stock_level, warehouse_id, product_category_id)
values
    (500, 50, 1, 1),  -- Warehouse 1, Solar Panel
    (300, 30, 1, 2),  -- Warehouse 1, Inverter
    (200, 20, 1, 3),  -- Warehouse 1, Solar Energy Monitoring System
    (450, 45, 2, 1),  -- Warehouse 2, Solar Panel
    (350, 35, 2, 2),  -- Warehouse 2, Inverter
    (250, 25, 2, 3),  -- Warehouse 2, Solar Energy Monitoring System
    (400, 40, 3, 1),  -- Warehouse 3, Solar Panel
    (200, 20, 3, 2),  -- Warehouse 3, Inverter
    (150, 15, 3, 3),  -- Warehouse 3, Solar Energy Monitoring System
    (550, 55, 4, 1),  -- Warehouse 4, Solar Panel
    (250, 25, 4, 2),  -- Warehouse 4, Inverter
    (100, 10, 4, 3),  -- Warehouse 4, Solar Energy Monitoring System
    (500, 50, 5, 1),  -- Warehouse 5, Solar Panel
    (300, 30, 5, 2),  -- Warehouse 5, Inverter
    (200, 20, 5, 3),  -- Warehouse 5, Solar Energy Monitoring System
    (450, 45, 6, 1),  -- Warehouse 6, Solar Panel
    (350, 35, 6, 2),  -- Warehouse 6, Inverter
    (250, 25, 6, 3),  -- Warehouse 6, Solar Energy Monitoring System
    (400, 40, 7, 1),  -- Warehouse 7, Solar Panel
    (200, 20, 7, 2),  -- Warehouse 7, Inverter
    (150, 15, 7, 3),  -- Warehouse 7, Solar Energy Monitoring System
    (550, 55, 8, 1),  -- Warehouse 8, Solar Panel
    (250, 25, 8, 2),  -- Warehouse 8, Inverter
    (100, 10, 8, 3),  -- Warehouse 8, Solar Energy Monitoring System
    (500, 50, 9, 1),  -- Warehouse 9, Solar Panel
    (300, 30, 9, 2),  -- Warehouse 9, Inverter
    (200, 20, 9, 3),  -- Warehouse 9, Solar Energy Monitoring System
    (450, 45, 10, 1),  -- Warehouse 10, Solar Panel
    (350, 35, 10, 2),  -- Warehouse 10, Inverter
    (250, 25, 10, 3);  -- Warehouse 10, Solar Energy Monitoring System


INSERT INTO inventoryorders (description, order_date, delivery_date, warehouse_id, ordered_by_id)
VALUES
    ('Solar Panel Bulk Order', '2023-04-15', '2023-04-25', 1, 1),
    ('Inverter Supply Order', '2023-04-20', '2023-04-30', 2, 2),
    ('Solar Monitoring Kit Order', '2023-04-25', '2023-05-05', 3, 3),
    ('Photovoltaic System Order', '2023-05-01', '2023-05-11', 4, 4),
    ('Green Energy Equipment Order', '2023-05-10', '2023-05-20', 5, 5),
    ('Advanced Inverter Purchase', '2023-05-15', '2023-05-25', 6, 6),
    ('Residential Solar Panel Order', '2023-05-22', '2023-06-01', 7, 7),
    ('Commercial Solar Equipment Order', '2023-06-01', '2023-06-11', 8, 8),
    ('Solar Battery Systems Order', '2023-06-10', '2023-06-20', 9, 9),
    ('Eco-friendly Solar Accessories', '2023-06-18', '2023-06-28', 10, 10),
    ('Solar Energy Storage Solutions', '2023-07-01', '2023-07-11', 1, 1),
    ('High-Efficiency Solar Panels', '2023-07-15', '2023-07-25', 2, 2),
    ('Innovative Solar Inverters', '2023-07-22', '2023-08-01', 3, 3),
    ('Smart Solar Monitoring Systems', '2023-08-05', '2023-08-15', 4, 4),
    ('Renewable Energy Supply Order', '2023-08-28', '2023-09-05', 5, 5),
    ('Bulk Order of Solar Panels', '2023-09-05', '2023-09-15', 6, 1),
    ('Order for High-capacity Inverters', '2023-09-20', '2023-09-30', 7, 2),
    ('Renewable Energy Equipment', '2023-10-10', '2023-10-20', 8, 3),
    ('Solar Monitoring Systems', '2023-11-05', '2023-11-15', 9, 4),
    ('Photovoltaic Panels Procurement', '2023-12-01', '2023-12-11', 10, 5),
    ('Advanced Solar Inverter Order', '2024-01-02', '2024-01-12', 1, 6),
    ('Green Energy Supply Order', '2024-01-07', '2024-01-17', 2, 7),
    ('Sustainable Solar Equipments', '2024-01-15', '2024-01-25', 3, 8),
    ('High Efficiency Solar Panels', '2024-01-20', '2024-01-30', 4, 9),
    ('Solar Panel Bulk Order', '2023-10-05', '2023-10-15', 1, 1),
    ('Inverter Supply Order', '2023-10-20', '2023-10-30', 2, 2),
    ('Solar Monitoring Kit Order', '2023-11-03', '2023-11-13', 3, 3),
    ('Photovoltaic System Order', '2023-11-17', '2023-11-27', 4, 4),
    ('Green Energy Equipment Order', '2023-12-01', '2023-12-11', 5, 5),
    ('Advanced Inverter Purchase', '2023-12-15', '2023-12-25', 6, 6),
    ('Residential Solar Panel Order', '2024-01-08', '2024-01-18', 7, 7),
    ('Solar Chargers', '2023-09-05', '2023-09-15', 8, 8),
    ('Energy Storage', '2023-09-20', '2023-09-30', 9, 9),
    ('Eco Inverters', '2023-10-10', '2023-10-20', 10, 10),
    ('PV Panels', '2023-10-25', '2023-11-04', 1, 1),
    ('Solar Kits', '2023-11-10', '2023-11-20', 2, 2),
    ('Battery Systems', '2023-11-25', '2023-12-05', 3, 3),
    ('Panel Procurement', '2023-12-10', '2023-12-20', 4, 4),
    ('Inverter Purchase', '2023-12-25', '2024-01-04', 5, 5),
    ('Monitoring Systems', '2024-01-10', '2024-01-20', 6, 6),
    ('Eco Accessories', '2024-01-25', '2024-02-04', 7, 7);


insert into projects (name, description, location, client_name, client_email, client_phone, start_date, end_date, status,
                      team_id)
values ('Urban Solar Initiative', 'Installation of solar panels in urban areas', 'Amsterdam', 'Green Amsterdam', 'contact@greenamsterdam.nl', '011-223-3344', '2023-11-15', '2023-12-20', 'FINISHED', 1),
       ('Renewable Energy Development', 'Development of renewable energy resources', 'Rotterdam', 'Rotterdam Energy', 'info@rotterdamenergy.nl', '022-334-4455', '2023-07-01', '2023-09-30', 'FINISHED', 2),
       ('Eco-friendly Infrastructure', 'Building eco-friendly infrastructure in public spaces', 'The Hague', 'The Hague Municipality', 'municipality@thehague.nl', '033-445-5566', '2023-10-10', '2023-10-10', 'FINISHED', 3),
       ('Smart City Solutions', 'Implementing smart city technology', 'Eindhoven', 'Eindhoven Smart City', 'smartcity@eindhoven.nl', '044-556-6677', '2023-07-20', '2024-01-05', 'FINISHED', 4),
       ('Green Transport System', 'Developing a green public transport system', 'Utrecht', 'Utrecht Transport', 'transport@utrecht.nl', '055-667-7788', '2023-12-05', '2024-02-10', 'IN_PROGRESS', 5),
       ('Sustainable Housing Project', 'Creating sustainable housing developments', 'Groningen', 'Groningen Housing', 'housing@groningen.nl', '066-778-8899', '2023-12-01', '2024-03-15', 'IN_PROGRESS', 6),
       ('Energy Efficiency Program', 'Program to enhance energy efficiency in buildings', 'Leiden', 'Leiden Energy', 'energy@leiden.nl', '077-889-9900', '2023-10-15', '2024-04-20', 'IN_PROGRESS', 7),
       ('Solar Park Development', 'Construction of a large solar park', 'Maastricht', 'Maastricht Solar', 'solar@maastricht.nl', '088-990-0011', '2023-11-05', '2024-05-10', 'IN_PROGRESS', 8),
       ('Eco-Park Initiative', 'Development of an eco-friendly park', 'Breda', 'Breda Eco', 'eco@breda.nl', '099-001-1122', '2023-12-10', '2024-06-15', 'IN_PROGRESS', 9),
       ('Climate Resilience Study', 'Study to improve city climate resilience', 'Almere', 'Almere Climate', 'climate@almere.nl', '010-112-2233', '2024-01-05', '2024-07-10', 'IN_PROGRESS', 10),
       ('Citywide LED Upgrade', 'Upgrading street lighting to energy-efficient LEDs', 'Amsterdam', 'Amsterdam Public Works', 'publicworks@amsterdam.nl', '101-202-3030', '2023-04-15', '2023-08-15', 'FINISHED', 11),
       ('Green School Initiative', 'Implementing green energy solutions in schools', 'Rotterdam', 'Rotterdam Education Board', 'education@rotterdam.nl', '202-303-4040', '2023-05-10', '2023-09-01', 'FINISHED', 12),
       ('Solar Farm Expansion', 'Expanding existing solar farm capacity', 'Utrecht', 'Utrecht Energy', 'info@utrechtenergy.nl', '303-404-5050', '2023-10-05', '2023-11-05', 'FINISHED', 13),
       ('Eco-friendly Public Transit', 'Developing eco-friendly public transportation options', 'The Hague', 'The Hague Transit Authority', 'transit@thehague.nl', '404-505-6060', '2023-07-01', '2023-12-01', 'FINISHED', 14),
       ('Urban Tree Planting', 'Large-scale urban tree planting and green space development', 'Leiden', 'Leiden City Council', 'citycouncil@leiden.nl', '505-606-7070', '2023-08-15', '2024-01-15', 'FINISHED', 15),
       ('Sustainable Water Management', 'Implementing sustainable water management practices', 'Eindhoven', 'Eindhoven Water Board', 'waterboard@eindhoven.nl', '606-707-8080', '2023-11-10', '2024-02-10', 'IN_PROGRESS', 16),
       ('Renewable Energy Research', 'Research project on renewable energy technologies', 'Groningen', 'Groningen University', 'university@groningen.nl', '707-808-9090', '2023-10-05', '2024-03-05', 'IN_PROGRESS', 17),
       ('Waste Recycling Program', 'Developing a comprehensive waste recycling program', 'Maastricht', 'Maastricht Waste Management', 'waste@maastricht.nl', '808-909-1010', '2023-11-01', '2024-04-01', 'IN_PROGRESS', 18),
       ('Energy Saving Initiative', 'City-wide initiative to reduce energy consumption', 'Breda', 'Breda Energy Board', 'energy@breda.nl', '909-101-1111', '2023-12-15', '2024-05-15', 'IN_PROGRESS', 19),
       ('Green Building Regulations', 'Developing new green building regulations and codes', 'Almere', 'Almere City Planning', 'cityplanning@almere.nl', '101-111-1212', '2024-01-10', '2024-06-10', 'IN_PROGRESS', 1),
       ('Public Park Renewal', 'Renewing and upgrading public parks with sustainable features', 'Nijmegen', 'Nijmegen Parks Department', 'parks@nijmegen.nl', '111-121-1313', '2024-02-15', '2024-07-15', 'IN_PROGRESS',2),
       ('Community Solar Project', 'Developing a community-based solar power project', 'Haarlem', 'Haarlem Community Energy', 'community@haarlem.nl', '121-131-1414', '2024-03-20', '2024-08-20', 'IN_PROGRESS', 3),
       ('Eco Mobility Plan', 'Creating a plan for eco-friendly mobility in the city', 'Delft', 'Delft Urban Planning', 'urbanplanning@delft.nl', '131-141-1515', '2024-04-25', '2024-09-25', 'IN_PROGRESS', 4),
       ('Sustainable Agriculture Initiative', 'Supporting sustainable agricultural practices in rural areas', 'Zwolle', 'Zwolle Agriculture Board', 'agriculture@zwolle.nl', '141-151-1616', '2024-03-30', '2024-09-30', 'IN_PROGRESS', 5),
       ('Urban Green Spaces', 'Creating and enhancing urban green spaces for sustainability', 'Tilburg', 'Tilburg Municipality', 'municipality@tilburg.nl', '151-161-1717', '2023-04-20', '2023-10-20', 'FINISHED', 6),
       ('Renewable Urban Solutions', 'Integrating renewable solutions in urban planning', 'Haarlem', 'Haarlem City Council', 'citycouncil@haarlem.nl', '161-171-1818', '2023-05-05', '2023-11-05', 'FINISHED', 7),
       ('Eco-friendly Transportation System', 'Developing an eco-friendly transportation network', 'Nijmegen', 'Nijmegen Transport Authority', 'transport@nijmegen.nl', '171-181-1919', '2023-06-15', '2024-01-15', 'IN_PROGRESS', 8),
       ('Sustainable Energy Research', 'Conducting research on sustainable energy solutions', 'Delft', 'Delft University of Technology', 'tech@delftuniversity.nl', '181-191-2020', '2023-07-10', '2024-02-10', 'IN_PROGRESS', 9),
       ('Green Building Project', 'Promoting green building practices in construction', 'Groningen', 'Groningen Construction Board', 'construction@groningen.nl', '191-202-2121', '2023-08-01', '2024-03-01', 'IN_PROGRESS', 10),
       ('Solar Energy Advancement', 'Advancing solar energy technology and application', 'Leiden', 'Leiden Energy Lab', 'energy@leidenlab.nl', '202-212-2222', '2023-09-25', '2024-04-15', 'IN_PROGRESS', 1),
       ('Eco-friendly Urban Development', 'Urban development projects with a focus on sustainability', 'Maastricht', 'Maastricht Urban Planning', 'urban@maastricht.nl', '212-222-2323', '2023-10-10', '2024-05-10', 'IN_PROGRESS', 2),
       ('Water Conservation Initiative', 'Initiative to promote water conservation in cities', 'Breda', 'Breda Water Board', 'water@breda.nl', '222-232-2424', '2023-11-20', '2024-06-20', 'IN_PROGRESS', 3),
       ('Community Renewable Projects', 'Community-led projects for renewable energy', 'Almere', 'Almere Community Energy', 'community@almereenergy.nl', '232-242-2525', '2024-01-01', '2024-07-01', 'IN_PROGRESS', 4),
       ('Sustainable Public Housing', 'Developing sustainable public housing projects', 'Eindhoven', 'Eindhoven Housing Authority', 'housing@eindhoven.nl', '242-252-2626', '2024-02-20', '2024-08-20', 'IN_PROGRESS', 5),
       ('Urban Renewal Initiative', 'Revitalizing urban areas with sustainable practices', 'Zwolle', 'Zwolle City Council', 'citycouncil@zwolle.nl', '252-262-2727', '2023-04-25', '2023-10-25', 'FINISHED', 6),
       ('Green Tech Innovation', 'Fostering green technology innovations in the city', 'Delft', 'Delft Tech Hub', 'techhub@delft.nl', '262-272-2828', '2023-05-15', '2023-11-15', 'FINISHED', 7),
       ('Eco-friendly Waste Management', 'Implementing eco-friendly waste management systems', 'Leiden', 'Leiden Environmental Dept.', 'environment@leiden.nl', '272-282-2929', '2023-06-20', '2024-01-20', 'IN_PROGRESS', 8),
       ('Sustainable Urban Mobility', 'Developing sustainable mobility solutions for urban areas', 'Groningen', 'Groningen Mobility Board', 'mobility@groningen.nl', '282-292-3030', '2023-07-10', '2024-02-10', 'IN_PROGRESS', 9),
       ('Climate Adaptation Strategy', 'Creating strategies for climate adaptation in urban planning', 'Maastricht', 'Maastricht Climate Board', 'climate@maastricht.nl', '292-303-3131', '2023-08-05', '2024-02-05', 'IN_PROGRESS', 10),
       ('Clean Energy Campaign', 'Promoting the use of clean and renewable energy sources', 'Haarlem', 'Haarlem Energy Board', 'energy@haarlem.nl', '303-313-3232', '2023-09-10', '2024-02-10', 'IN_PROGRESS', 1),
       ('Urban Biodiversity Project', 'Enhancing urban biodiversity through sustainable landscaping', 'Tilburg', 'Tilburg Environmental Dept.', 'environment@tilburg.nl', '313-323-3333', '2023-10-15', '2024-01-15', 'IN_PROGRESS', 2),
       ('Urban Green Spaces', 'Creating green areas in cities', 'Zwolle', 'Zwolle Municipality', 'info@zwolle.nl', '100-111-2222', '2023-09-05', '2024-01-15', 'IN_PROGRESS', 10),
       ('Water Conservation Project', 'Efficient water use initiatives', 'Nijmegen', 'Nijmegen Water Board', 'water@nijmegen.nl', '111-222-3333', '2023-10-10', '2024-02-20', 'IN_PROGRESS', 1),
       ('Recycling Enhancement', 'Improving city recycling programs', 'Almere', 'Almere City', 'recycle@almere.nl', '122-333-4444', '2023-11-15', '2024-02-25', 'IN_PROGRESS', 2),
       ('Renewable Energy Education', 'Educational programs on renewable energy', 'Leeuwarden', 'Leeuwarden Education', 'edu@leeuwarden.nl', '133-444-5555', '2023-12-20', '2024-04-30', 'IN_PROGRESS', 3),
       ('Eco-friendly Tourism', 'Sustainable tourism initiatives', 'Middelburg', 'Middelburg Tourism', 'tourism@middelburg.nl', '144-555-6666', '2024-01-24', '2024-02-22', 'IN_PROGRESS', 4),
       ('Green Building Standards', 'Implementing sustainable building codes', 'Assen', 'Assen Construction', 'build@assen.nl', '155-666-7777', '2024-02-28', '2024-03-30', 'IN_PROGRESS', 5),
       ('Urban Farming Expansion', 'Expanding urban agriculture', 'Haarlem', 'Haarlem Agriculture', 'farm@haarlem.nl', '166-777-8888', '2024-03-05', '2024-04-10', 'IN_PROGRESS', 6),
       ('Community Solar Program', 'Solar energy for community buildings', 'Lelystad', 'Lelystad Community', 'community@lelystad.nl', '177-888-9999', '2024-03-20', '2024-5-25', 'IN_PROGRESS', 7),
       ('Waste-to-Energy Plant', 'Building a waste-to-energy facility', 'Gouda', 'Gouda Energy', 'energy@gouda.nl', '188-999-0000', '2024-04-04', '2024-05-09', 'IN_PROGRESS', 8),
       ('Sustainable Urban Design', 'Eco-friendly urban development project', 'Dordrecht', 'Dordrecht Design', 'design@dordrecht.nl', '199-000-1111', '2024-04-15', '2024-06-20', 'IN_PROGRESS', 9),
       ('Urban Renewable Energy', 'Urban solar and wind energy integration', 'Venlo', 'Venlo Energy', 'energy@venlo.nl', '200-111-2223', '2023-09-12', '2024-04-22', 'IN_PROGRESS', 10),
       ('Green Public Transport', 'Eco-friendly public transport systems', 'Amersfoort', 'Amersfoort Transit', 'transit@amersfoort.nl', '211-222-3334', '2023-10-17', '2024-05-27', 'IN_PROGRESS', 1),
       ('Sustainable Landscaping', 'Eco-friendly urban landscaping projects', 'Sittard', 'Sittard Landscaping', 'landscape@sittard.nl', '222-333-4445', '2023-11-21', '2024-05-31', 'IN_PROGRESS', 2),
       ('Eco Education Initiative', 'Sustainability education programs', 'Hoofddorp', 'Hoofddorp Schools', 'education@hoofddorp.nl', '233-444-5556', '2023-12-26', '2024-05-05', 'IN_PROGRESS', 3),
       ('Renewable Resource Research', 'Research on new renewable resources', 'Vlaardingen', 'Vlaardingen Research', 'research@vlaardingen.nl', '244-555-6667', '2024-01-30', '2024-06-09', 'IN_PROGRESS', 4),
       ('Community Eco Projects', 'Community-driven environmental projects', 'Heerlen', 'Heerlen Community', 'community@heerlen.nl', '255-666-7778', '2024-03-07', '2024-07-16', 'IN_PROGRESS', 5),
       ('Smart Energy Systems', 'Implementation of smart energy solutions', 'Emmen', 'Emmen Energy', 'smart@emmen.nl', '266-777-8889', '2024-03-22', '2024-07-31', 'IN_PROGRESS', 6),
       ('Eco-friendly Material Use', 'Promoting sustainable materials in construction', 'Roosendaal', 'Roosendaal Construction', 'material@roosendaal.nl', '277-888-9990', '2024-03-06', '2024-05-15', 'IN_PROGRESS', 7),
       ('Waste Management Improvement', 'Enhanced waste management and recycling', 'Drachten', 'Drachten Waste', 'waste@drachten.nl', '288-999-0001', '2024-02-20', '2024-07-29', 'IN_PROGRESS', 8),
       ('City Biodiversity Project', 'Projects to increase urban biodiversity', 'Zaandam', 'Zaandam Environment', 'biodiversity@zaandam.nl', '299-000-1112', '2024-02-05', '2024-04-09', 'IN_PROGRESS', 9);
-- Tasks
INSERT INTO Tasks (task_order, name, description, deadline, status, project_id, personaltodolistowner_id) VALUES
-- User 1 tasks
(1, 'Task 1', 'This task involves the initial planning phase of the project', '2023-07-01', 'TODO', 1, 1),
(2, 'Task 2', 'This task is about the design and architecture of the project', '2023-07-02', 'IN_PROGRESS', 1, 1),
(3, 'Task 3', 'This task is about the implementation of the project', '2023-07-03', 'DONE', 2, 1),
-- User 2 tasks
(4, 'Task 4', 'This task involves the testing phase of the project', '2023-07-04', 'TODO', 2, 2),
(5, 'Task 5', 'This task is about the deployment of the project', '2023-07-05', 'IN_PROGRESS', 3, 2),
(6, 'Task 6', 'This task is about the maintenance of the project', '2023-07-06', 'DONE', 3, 2),
-- User 3 tasks
(7, 'Task 7', 'This task involves gathering requirements for the project', '2023-07-07', 'TODO', 4, 3),
(8, 'Task 8', 'This task is about the feasibility study of the project', '2023-07-08', 'IN_PROGRESS', 4, 3),
(9, 'Task 9', 'This task is about the risk analysis of the project', '2023-07-09', 'DONE', 5, 3),
-- User 4 tasks
(10, 'Task 10', 'This task involves the initial planning phase of the project', '2023-07-10', 'TODO', 5, 4),
(11, 'Task 11', 'This task is about the design and architecture of the project', '2023-07-11', 'IN_PROGRESS', 5, 4),
(12, 'Task 12', 'This task is about the implementation of the project', '2023-07-12', 'DONE', 6, 4),
-- User 5 tasks
(13, 'Task 13', 'This task involves the testing phase of the project', '2023-07-13', 'TODO', 6, 5),
(14, 'Task 14', 'This task is about the deployment of the project', '2023-07-14', 'IN_PROGRESS', 7, 5),
(15, 'Task 15', 'This task is about the maintenance of the project', '2023-07-15', 'DONE', 7, 5),
-- User 6 tasks
(16, 'Task 16', 'This task involves gathering requirements for the project', '2023-07-16', 'TODO', 8, 6),
(17, 'Task 17', 'This task is about the feasibility study of the project', '2023-07-17', 'IN_PROGRESS', 8, 6),
(18, 'Task 18', 'This task is about the risk analysis of the project', '2023-07-18', 'DONE', 9, 6);

-- generate project transactions
INSERT INTO transactions (quantity, product_id, transaction_date, warehouse_id, project_id, transfer_from_id, inventory_order_id, transaction_type)
SELECT 5, 1, start_date, (SELECT warehouse_id FROM teams WHERE id = project_id), project_id, NULL, NULL, 'PROJECT_MATERIAL'
FROM (SELECT id AS project_id, start_date FROM projects WHERE id BETWEEN 1 AND 60) AS project_data
UNION ALL
SELECT 10, 2, start_date, (SELECT warehouse_id FROM teams WHERE id = project_id), project_id, NULL, NULL, 'PROJECT_MATERIAL'
FROM (SELECT id AS project_id, start_date FROM projects WHERE id BETWEEN 1 AND 60) AS project_data
UNION ALL
SELECT 7, 3, start_date, (SELECT warehouse_id FROM teams WHERE id = project_id), project_id, NULL, NULL, 'PROJECT_MATERIAL'
FROM (SELECT id AS project_id, start_date FROM projects WHERE id BETWEEN 1 AND 60) AS project_data
UNION ALL
SELECT 6, 4, start_date, (SELECT warehouse_id FROM teams WHERE id = project_id), project_id, NULL, NULL, 'PROJECT_MATERIAL'
FROM (SELECT id AS project_id, start_date FROM projects WHERE id BETWEEN 1 AND 60) AS project_data
UNION ALL
SELECT 11, 5, start_date, (SELECT warehouse_id FROM teams WHERE id = project_id), project_id, NULL, NULL, 'PROJECT_MATERIAL'
FROM (SELECT id AS project_id, start_date FROM projects WHERE id BETWEEN 1 AND 60) AS project_data
UNION ALL
SELECT 7, 6, start_date, (SELECT warehouse_id FROM teams WHERE id = project_id), project_id, NULL, NULL, 'PROJECT_MATERIAL'
FROM (SELECT id AS project_id, start_date FROM projects WHERE id BETWEEN 1 AND 60) AS project_data;


-- generate inventoryorder transactions
insert into transactions (quantity, product_id, transaction_date, warehouse_id, inventory_order_id, project_id, transfer_from_id, transaction_type)
select 14, 1, order_date, warehouse_id, order_id, null, null, 'INVENTORY_ORDER'
from (select id as order_id, order_date, warehouse_id from INVENTORYORDERS where id between 1 and 50) as inventoryOrder_data
union all
select 20, 2, order_date, warehouse_id, order_id, null, null, 'INVENTORY_ORDER'
from (select id as order_id, order_date, warehouse_id from INVENTORYORDERS where id between 1 and 50) as inventoryOrder_data
union all
select 17, 3, order_date, warehouse_id, order_id, null, null, 'INVENTORY_ORDER'
from (select id as order_id, order_date, warehouse_id from INVENTORYORDERS where id between 1 and 50) as inventoryOrder_data
union all
select 14, 4, order_date, warehouse_id, order_id, null, null, 'INVENTORY_ORDER'
from (select id as order_id, order_date, warehouse_id from INVENTORYORDERS where id between 1 and 50) as inventoryOrder_data
union all
select 21, 5, order_date, warehouse_id, order_id, null, null, 'INVENTORY_ORDER'
from (select id as order_id, order_date, warehouse_id from INVENTORYORDERS where id between 1 and 50) as inventoryOrder_data
union all
select 16, 6, order_date, warehouse_id, order_id, null, null, 'INVENTORY_ORDER'
from (select id as order_id, order_date, warehouse_id from INVENTORYORDERS where id between 1 and 50) as inventoryOrder_data;




