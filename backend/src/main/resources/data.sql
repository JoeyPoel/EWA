-- generate data for the database

-- generate warehouses
insert into warehouses (name, location, address, postcode, country, contact_name, contact_email, contact_phone)
values ('solar warehouse', 'amsterdam', 'amsterdamseweg 1', '1111aa', 'the netherlands', 'john doe', 'jane@gmail.com',
        '0612345678'),
       ('lunar warehouse', 'rotterdam', 'rotterdamseweg 2', '2222bb', 'the netherlands', 'jane doe', 'john@gmail.com',
        '0712345678'),
       ('star warehouse', 'the hague', 'haagseweg 3', '3333cc', 'the netherlands', 'jim doe', 'jim@gmail.com',
        '0812345678'),
       ('galaxy warehouse', 'utrecht', 'utrechtseweg 4', '4444dd', 'the netherlands', 'jill doe', 'jill@gmail.com',
        '0912345678');

-- generate product categories
insert into product_categories (name, description)
values ('solar panel',
        'a solar panel is a panel designed to absorb the sun"s rays as a source of energy for generating electricity or heating.'),
       ('solar inverter',
        'a solar inverter, or pv inverter, converts the variable direct current (dc) output of a photovoltaic (pv) solar panel into a utility frequency alternating current (ac).'),
       ('solar battery',
        'a solar battery is a rechargeable battery that integrates a solar cell with battery power storage.'),
       ('solar charge controller',
        'a solar charge controller, or solar regulator, is an electronic device that prevents batteries from overcharging.'),
       ('solar mounting system',
        'a solar mounting system is used to fix solar panels on surfaces like roofs, building facades, or the ground.'),
       ('solar cable', 'a solar cable is the interconnection cable used in photovoltaic power generation.'),
       ('solar connector', 'a solar connector is an electrical connector designed to connect solar panels.');

-- generate products
insert into products (name, description, category_id , price)
values ('solar panel 100w', '100w solar panel for generating electricity',
        (select id from product_categories where name = 'solar panel'), 100.00),
       ('solar panel 200w', '200w solar panel for generating electricity',
        (select id from product_categories where name = 'solar panel'), 200.00),
       ('solar panel 300w', '300w solar panel for generating electricity',
        (select id from product_categories where name = 'solar panel'), 300.00),
       ('solar panel 400w', '400w solar panel for generating electricity',
        (select id from product_categories where name = 'solar panel'), 400.00),
       ('solar inverter 500w', '500w solar inverter for converting dc to ac',
        (select id from product_categories where name = 'solar inverter'), 500.00),
       ('solar inverter 600w', '600w solar inverter for converting dc to ac',
        (select id from product_categories where name = 'solar inverter'), 600.00),
       ('solar inverter 700w', '700w solar inverter for converting dc to ac',
        (select id from product_categories where name = 'solar inverter'), 700.00),
       ('solar inverter 800w', '800w solar inverter for converting dc to ac',
        (select id from product_categories where name = 'solar inverter'), 800.00),
       ('solar battery 200ah', '200ah solar battery for storing electricity',
        (select id from product_categories where name = 'solar battery'), 200.00),
       ('solar battery 300ah', '300ah solar battery for storing electricity',
        (select id from product_categories where name = 'solar battery'), 300.00),
       ('solar battery 400ah', '400ah solar battery for storing electricity',
        (select id from product_categories where name = 'solar battery'), 400.00),
       ('solar battery 500ah', '500ah solar battery for storing electricity',
        (select id from product_categories where name = 'solar battery'), 500.00),
       ('solar charge controller 20a', '20a solar charge controller for controlling charge',
        (select id from product_categories where name = 'solar charge controller'), 20.00),
       ('solar mounting system', 'solar mounting system for mounting solar panels',
        (select id from product_categories where name = 'solar mounting system'), 150.00),
       ('solar cable 10m', '10m solar cable for connecting solar components',
        (select id from product_categories where name = 'solar cable'), 10.00),
       ('solar connector', 'solar connector for connecting solar panels',
        (select id from product_categories where name = 'solar connector'), 5.00);

-- generate teams
insert into teams (name, warehouse_id)
values ('solar warriors', 1),
       ('solar legends', 2),
       ('solar heroes', 3),
       ('solar champions', 4),
       ('lunar amazonians', 2),
       ('lunar spartans', 2),
       ('lunar titans', 2),
       ('lunar gods', 2),
       ('star jupiter', 3),
       ('star saturn', 3),
       ('star mars', 3),
       ('star venus', 3),
       ('galaxy andromeda', 4),
       ('galaxy milky way', 4),
       ('galaxy black hole', 4),
       ('galaxy big bang', 4);

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
       ('gregor mendel', 'gregor.mendel@company.com', 'gregormendelpassword', 'USER', null),
       ('charles darwin', 'charles.darwin@company.com', 'charlesdarwinpassword', 'USER', null),
       ('rosalind franklin', 'rosalind.franklin@company.com', 'rosalindfranklinpassword', 'USER', null),
       ('francis crick', 'francis.crick@company.com', 'franciscrickpassword', 'USER', null),
       ('james watson', 'james.watson@company.com', 'jameswatsonpassword', 'USER', null),
       ('maurice wilkins', 'maurice.wilkins@company.com', 'mauricewilkinspassword', 'USER', null),
       ('johnny magielse', 'johnnymihai33@gmail.com', 'johnny', 'ADMIN', null);

insert into warehouse_product_category_capacities (capacity, minimum_stock_level, warehouse_id, product_category_id)
values
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

insert into inventoryorders (description, is_delivered, order_date, delivery_date, warehouse_id, ordered_by_id)
values ('inventoryorder 1', false, '2023-01-01', '2023-01-10', 1, 1),
       ('inventoryorder 2', true, '2023-02-01', '2023-02-10', 1, 2),
       ('inventoryorder 3', false, '2023-03-01', '2023-03-10', 2, 3),
       ('inventoryorder 4', true, '2023-04-01', '2023-04-10', 2, 4),
       ('inventoryorder 5', false, '2023-05-01', '2023-05-10', 3, 5),
       ('inventoryorder 6', true, '2023-06-01', '2023-06-10', 3, 6),
       ('inventoryorder 7', false, '2023-07-01', '2023-07-10', 4, 7),
       ('inventoryorder 8', true, '2023-08-01', '2023-08-10', 4, 8),
       ('inventoryorder 9', false, '2023-07-01', '2023-07-10', 1, 9),
       ('inventoryorder 10', true, '2023-07-15', '2023-07-25', 1, 10),
       ('inventoryorder 11', false, '2023-07-30', '2023-08-09', 2, 11),
       ('inventoryorder 12', true, '2023-08-14', '2023-08-24', 2, 12),
       ('inventoryorder 13', false, '2023-08-29', '2023-09-08', 3, 13),
       ('inventoryorder 14', true, '2023-09-13', '2023-09-23', 3, 14),
       ('inventoryorder 15', false, '2023-09-28', '2023-10-08', 4, 15),
       ('inventoryorder 16', true, '2023-10-13', '2023-10-23', 4, 16),
       ('inventoryorder 17', false, '2023-10-28', '2023-11-07', 1, 17),
       ('inventoryorder 18', true, '2023-11-12', '2023-11-22', 1, 18),
       ('inventoryorder 19', false, '2023-12-03', '2023-12-13', 2, 19),
       ('inventoryorder 20', true, '2024-01-20', '2024-01-30', 3, 20),
       ('inventoryorder 21', false, '2023-12-18', '2023-12-28', 4, 21),
       ('inventoryorder 22', true,  '2024-01-04', '2024-01-14', 1, 22),
       ('inventoryorder 23', false, '2024-01-19', '2024-01-29', 2, 23),
       ('inventoryorder 24', true,  '2024-02-03', '2024-02-13', 3, 24),
       ('inventoryorder 25', false, '2024-02-18', '2024-02-28', 4, 25),
       ('inventoryorder 26', true,  '2023-12-13', '2023-12-23', 4, 26),
       ('inventoryorder 27', false, '2024-01-08', '2024-01-18', 1, 27),
       ('inventoryorder 28', true,  '2024-01-13', '2024-01-23', 2, 28),
       ('inventoryorder 29', false, '2024-02-08', '2024-02-18', 3, 29),
       ('inventoryorder 30', true,  '2024-02-23', '2024-03-05', 4, 30);

insert into projects (name, description, location, client_name, client_email, client_phone, start_date, end_date, status,
                      team_id)
values ('energy infrastructure', 'revamping the energy infrastructure', 'amsterdam', 'amsterdam municipality', 'municipality1@email.com', '112-113-1141',
        '2023-07-01', '2023-10-15', 'IN_PROGRESS', 1),
       ('the canal rejuvenation', 'improving the quality of canals', 'rotterdam', 'rotterdam city', 'cityrotterdam@email.com', '221-212-2222',
        '2023-07-05', '2023-11-01', 'FINISHED', 2),
       ('implementing smart city', 'implementation of smart city solutions', 'eindhoven', 'eindhoven city', 'cityeindhoven@email.com', '331-333-3311',
        '2023-07-15', '2023-11-15', 'IN_PROGRESS', 3),
       ('transport campaign', 'launch of new transport campaign', 'utrecht', 'utrecht govt.', 'govtutrecht@email.com', '441-422-4442',
        '2023-08-01', '2023-12-01', 'IN_PROGRESS', 4),
       ('cycling track enhancement', 'improvement and enhancement of cycling tracks', 'groningen', 'groningen city', 'citygroningen@email.com', '551-532-5550',
        '2023-08-05', '2023-10-30', 'FINISHED', 5),
       ('smart parking solution', 'introduction of smart parking solutions', 'the hague', 'hague city', 'cityhague@email.com', '661-644-6664',
        '2023-09-01', '2024-01-01', 'IN_PROGRESS', 6),
       ('urban reforming', 'urban reforming projects', 'delft', 'delft city', 'citydelft@email.com', '771-772-7772',
        '2023-10-01', '2024-01-15', 'IN_PROGRESS', 7),
       ('modern agriculture', 'introduction of modern agriculture techniques', 'tilburg', 'tilburg city', 'citytilburg@email.com', '881-882-8882',
        '2023-10-15', '2024-01-10', 'FINISHED', 8),
       ('smart traffic signals', 'implementing smart traffic signals', 'nijmegen', 'nijmegen govt.', 'govtnijmegen@email.com', '991-992-9992',
        '2023-11-01', '2024-01-10', 'IN_PROGRESS', 9),
       ('eco-friendly solutions', 'introducing eco-friendly living solutions', 'haarlem', 'haarlem city', 'cityhaarlem@email.com', '101-112-1102',
        '2023-12-01', '2024-01-05', 'IN_PROGRESS', 10),
       ('tourism enhancement', 'projects enhancing tourism possibilities', 'dordrecht', 'dordrecht city', 'citydordrecht@email.com', '121-132-1212',
        '2023-12-01', '2024-01-15', 'IN_PROGRESS', 11),
       ('digitalization of libraries', 'digitalizing the city libraries', 'amsterdam', 'amsterdam city', 'cityamsterdam1@email.com', '131-142-1312',
        '2023-09-01', '2024-01-02', 'FINISHED', 12),
       ('modern sanitation', 'introducing modern sanitation and recycling', 'enschede', 'enschede govt.', 'govtenschede@email.com', '141-152-1422',
        '2023-09-15', '2023-12-31', 'IN_PROGRESS', 13),
       ('city wifi project', 'providing wi-fi connectivity in public places', 'leeuwarden', 'leeuwarden city', 'cityleeuwarden@email.com', '151-162-1512',
        '2023-08-01', '2023-12-15', 'IN_PROGRESS', 1),
       ('urban gardening', 'promoting urban gardening', 'arnhem', 'arnhem city', 'cityarnhem@email.com', '162-163-1622',
        '2023-07-01', '2023-12-31', 'FINISHED', 2),
       ('renewable energy', 'promotion of renewable energy usage', 'breda', 'breda govt.', 'govtbreda@email.com', '173-174-1732',
        '2023-08-01', '2023-12-10', 'IN_PROGRESS', 3),
       ('health campaign', 'health awareness campaign', 'almere', 'almere city', 'cityalmere@email.com', '184-185-1842',
        '2023-08-15', '2024-01-05', 'IN_PROGRESS', 4),
       ('public transport upgrade', 'upgrading public transport system', 'spijkenisse', 'spijkenisse city', 'cityspijkenisse@email.com', '195-196-1952',
        '2023-07-15', '2023-10-30', 'FINISHED', 5),
       ('revamp of parks', 'revamping of city parks', 'apeldoorn', 'apeldoorn city', 'cityapeldoorn@email.com', '206-207-2062',
        '2023-09-01', '2023-12-20', 'IN_PROGRESS', 6),
       ('affordable housing', 'projects for affordable housing', 'venlo', 'venlo govt.', 'govtvenlo@email.com', '217-218-2172',
        '2023-08-01', '2023-12-15', 'IN_PROGRESS', 7),
       ('city aquatics', 'improvement of city aquatics facilities', 'zaanstad', 'zaanstad city', 'cityzaanstad@email.com', '228-229-2282',
        '2023-07-01', '2023-10-20', 'FINISHED', 8),
       ('vinex housing project', 'working on large-scale vinex housing projects', 'utrecht', 'utrecht govt.', 'govtutrecht1@email.com', '239-240-2392',
        '2023-08-01', '2023-12-30', 'IN_PROGRESS', 9),
       ('elderly care houses', 'building elderly care houses', 's-hertogenbosch', 's-hertogenbosch city', 'cityshertogenbosch@email.com', '250-251-2502',
        '2023-07-01', '2023-11-01', 'IN_PROGRESS', 10),
       ('museum renovation', 'renovation of the city museum', 'zoetermeer', 'zoetermeer city', 'cityzoetermeer@email.com', '261-262-2612',
        '2023-09-01', '2023-12-31', 'FINISHED', 11),
       ('public space upgradation', 'improving public spaces', 'maastricht', 'maastricht city', 'citymaastricht@email.com', '272-273-2722',
        '2023-10-01', '2024-01-15', 'IN_PROGRESS', 12),
       ('historic sites revival', 'revival of historic sites', 'leiden', 'leiden city', 'cityleiden@email.com', '287-282-2882',
        '2023-10-01', '2023-12-31', 'FINISHED', 13),
       ('smart street lighting', 'implementing smart street lighting', 'zwolle', 'zwolle city', 'cityzwolle@email.com', '296-290-2990',
        '2023-09-01', '2023-12-27', 'FINISHED', 14),
       ('solar power harvesting', 'solar power harvesting projects', 'lelystad', 'lelystad city', 'citylelystad@email.com', '309-310-3020',
        '2023-07-25', '2023-12-24', 'FINISHED', 15),
       ('water management', 'water management and waste water treatment', 'roermond', 'roermond city', 'cityroermond@email.com', '312-311-3131',
        '2023-09-01', '2024-01-01', 'FINISHED', 16),
       ('educational reform', 'educational reform in primary schools', 'zutphen', 'zutphen govt.', 'govtzutphen@email.com', '324-322-3232',
        '2023-09-15', '2024-01-01', 'FINISHED', 1),
       ('public health care', 'upgrade of public health care facilities', 'assen', 'assen city', 'cityassen@email.com', '335-332-3422',
        '2023-07-10', '2023-10-31', 'FINISHED', 2),
       ('music fest organization', 'organizing the annual music festival', 'goes', 'goes city', 'citygoes@email.com', '345-352-3512',
        '2023-09-09', '2023-12-09', 'FINISHED', 3),
       ('public library expansion', 'expansion of public libraries', 'vlissingen', 'vlissingen city', 'cityvlissingen@email.com', '365-364-3634',
        '2023-07-07', '2023-11-07', 'FINISHED', 4),
       ('recycling campaign', 'citywide recycling and waste management campaign', 'alkmaar', 'alkmaar city', 'cityalkmaar@email.com', '375-374-3734',
        '2023-09-01', '2024-01-01', 'FINISHED', 5),
       ('digital governance', 'implementing digital governance system', 'gouda', 'gouda govt.', 'govtgouda@email.com', '387-386-3854',
        '2023-11-01', '2024-01-05', 'FINISHED', 6),
       ('green transport initiative', 'improving green transport methods', 'amsterdam', 'amsterdam municipality', 'municipality1@email.com', '388-389-3901',
        '2024-01-01', '2024-04-15', 'IN_PROGRESS', 1),
       ('smart city project', 'upgrading city infrastructures with smart solutions', 'rotterdam', 'rotterdam city', 'cityrotterdam@email.com', '391-392-3932',
        '2024-01-05', '2024-05-01', 'FINISHED', 2),
       ('digital education', 'implementing digital education in schools', 'eindhoven', 'eindhoven city', 'cityeindhoven@email.com', '394-395-3963',
        '2024-01-10', '2024-05-15', 'IN_PROGRESS', 3),
       ('tourism promotion campaign', 'promoting regional tourism', 'utrecht', 'utrecht govt.', 'govtutrecht@email.com', '397-398-3994',
        '2024-01-15', '2024-05-20', 'IN_PROGRESS', 4),
       ('eco city project', 'transitioning towards an eco-friendly city', 'groningen', 'groningen city', 'citygroningen@email.com', '400-401-4025',
        '2024-01-20', '2024-05-25', 'FINISHED', 5),
       ('digital economy', 'encouraging the digital economy through various initiatives', 'the hague', 'hague city', 'cityhague@email.com', '403-404-4056',
        '2024-01-25', '2024-05-30', 'IN_PROGRESS', 6),
       ('renewable energy project', 'implementing renewable energy solutions', 'delft', 'delft city', 'citydelft@email.com', '406-407-4087',
        '2024-01-30', '2024-06-05', 'IN_PROGRESS', 7),
       ('smart agriculture', 'modernizing agriculture with smart techniques', 'tilburg', 'tilburg city', 'citytilburg@email.com', '409-410-4118',
        '2024-02-04', '2024-06-10', 'FINISHED', 8),
       ('smart traffic management', 'upgrading traffic signals and management', 'nijmegen', 'nijmegen govt.', 'govtnijmegen@email.com', '412-413-4149',
        '2024-02-09', '2024-06-15', 'IN_PROGRESS', 9),
       ('environmental awareness campaign', 'running an environmental awareness campaign', 'haarlem', 'haarlem city', 'cityhaarlem@email.com', '415-416-4170',
        '2024-02-14', '2024-06-20', 'IN_PROGRESS', 10),
       ('public safety measures', 'enhancing public safety measures', 'dordrecht', 'dordrecht city', 'citydordrecht@email.com', '418-419-4201',
        '2024-02-19', '2024-06-25', 'IN_PROGRESS', 11),
       ('heritage site restoration', 'restoration and preservation of heritage sites', 'amsterdam', 'amsterdam city', 'cityamsterdam1@email.com', '421-422-4232',
        '2024-02-24', '2024-06-30', 'FINISHED', 12),
       ('public healthcare improvement', 'upgrading public healthcare facilities and services', 'enschede', 'enschede govt.', 'govtenschede@email.com', '424-425-4263',
        '2024-02-29', '2024-07-05', 'IN_PROGRESS', 13),
       ('rural development project', 'improving infrastructure and opportunities in rural areas', 'leeuwarden', 'leeuwarden city', 'cityleeuwarden@email.com', '427-428-4294',
        '2024-03-05', '2024-07-10', 'IN_PROGRESS', 1),
       ('free public wifi', 'implementing free public wifi in select city areas', 'arnhem', 'arnhem city', 'cityarnhem@email.com', '430-431-4325',
        '2024-03-10', '2024-07-15', 'FINISHED', 2),
       ('public library digitalization', 'digitalizing public libraries', 'breda', 'breda govt.', 'govtbreda@email.com', '433-434-4356',
        '2024-03-15', '2024-07-20', 'IN_PROGRESS', 3),
       ('urban housing scheme', 'initiating affordable urban housing projects', 'spijkenisse', 'spijkenisse city', 'cityspijkenisse@email.com', '436-437-4387',
        '2024-03-20', '2024-07-25', 'IN_PROGRESS', 4),
       ('riverfront development', 'developing and beautifying the riverfront area', 'apeldoorn', 'apeldoorn city', 'cityapeldoorn@email.com', '439-440-4418',
        '2024-03-25', '2024-07-30', 'FINISHED', 5),
       ('public transport reform', 'reforming public transport for better efficiency and reach', 'venlo', 'venlo govt.', 'govtvenlo@email.com', '442-443-4449',
        '2024-03-30', '2024-08-04', 'IN_PROGRESS', 6),
       ('solar energy project', 'implementing solar energy projects and schemes', 'zaanstad', 'zaanstad city', 'cityzaanstad@email.com', '445-446-4470',
        '2024-04-04', '2024-08-09', 'IN_PROGRESS', 7);


-- generate project transactions
insert into transactions (quantity, product_id, transaction_date, warehouse_id, project_id, transfer_from_id, inventory_order_id, transaction_type)
select 10, 1, start_date, (select warehouse_id from teams where id = project_id), project_id, null, null, 'PROJECT_MATERIAL'
from (select id as project_id, start_date from projects where id between 1 and 30) as project_data
union all
select 2, 5, start_date, (select warehouse_id from teams where id = project_id), project_id, null, null, 'PROJECT_MATERIAL'
from (select id as project_id, start_date from projects where id between 1 and 30) as project_data
union all
select 5, 10, start_date, (select warehouse_id from teams where id = project_id), project_id, null, null, 'PROJECT_MATERIAL'
from (select id as project_id, start_date from projects where id between 1 and 30) as project_data
union all
select 5, 13, start_date, (select warehouse_id from teams where id = project_id), project_id, null, null, 'PROJECT_MATERIAL'
from (select id as project_id, start_date from projects where id between 1 and 30) as project_data
union all
select 10, 14, start_date, (select warehouse_id from teams where id = project_id), project_id, null, null, 'PROJECT_MATERIAL'
from (select id as project_id, start_date from projects where id between 1 and 30) as project_data
union all
select 15, 15, start_date, (select warehouse_id from teams where id = project_id), project_id, null, null, 'PROJECT_MATERIAL'
from (select id as project_id, start_date from projects where id between 1 and 30) as project_data
union all
select 12, 16, start_date, (select warehouse_id from teams where id = project_id), project_id, null, null, 'PROJECT_MATERIAL'
from (select id as project_id, start_date from projects where id between 1 and 30) as project_data;

-- generate inventoryorder transactions
insert into transactions (quantity, product_id, transaction_date, warehouse_id, inventory_order_id, project_id, transfer_from_id, transaction_type)
select 12, 1, order_date, warehouse_id, order_id, null, null, 'INVENTORY_ORDER'
from (select id as order_id, order_date, warehouse_id from INVENTORYORDERS where id between 1 and 18) as inventoryOrder_data
union all
select 3, 5, order_date, warehouse_id, order_id, null, null, 'INVENTORY_ORDER'
from (select id as order_id, order_date, warehouse_id from INVENTORYORDERS where id between 1 and 18) as inventoryOrder_data
union all
select 6, 10, order_date, warehouse_id, order_id, null, null, 'INVENTORY_ORDER'
from (select id as order_id, order_date, warehouse_id from INVENTORYORDERS where id between 1 and 18) as inventoryOrder_data
union all
select 7, 13, order_date, warehouse_id, order_id, null, null, 'INVENTORY_ORDER'
from (select id as order_id, order_date, warehouse_id from INVENTORYORDERS where id between 1 and 18) as inventoryOrder_data
union all
select 13, 14, order_date, warehouse_id, order_id, null, null, 'INVENTORY_ORDER'
from (select id as order_id, order_date, warehouse_id from INVENTORYORDERS where id between 1 and 18) as inventoryOrder_data
union all
select 16, 15, order_date, warehouse_id, order_id, null, null, 'INVENTORY_ORDER'
from (select id as order_id, order_date, warehouse_id from INVENTORYORDERS where id between 1 and 18) as inventoryOrder_data
union all
select 17, 16, order_date, warehouse_id, order_id, null, null, 'INVENTORY_ORDER'
from (select id as order_id, order_date, warehouse_id from INVENTORYORDERS where id between 1 and 18) as inventoryOrder_data
union all
select 12, 1, order_date, warehouse_id, order_id, null, null, 'INVENTORY_ORDER'
from (select id as order_id, order_date, warehouse_id from INVENTORYORDERS where id between 19 and 30) as inventoryOrder_data
union all
select 3, 5, order_date, warehouse_id, order_id, null, null, 'INVENTORY_ORDER'
from (select id as order_id, order_date, warehouse_id from INVENTORYORDERS where id between 19 and 30) as inventoryOrder_data
union all
select 6, 10, order_date, warehouse_id, order_id, null, null, 'INVENTORY_ORDER'
from (select id as order_id, order_date, warehouse_id from INVENTORYORDERS where id between 19 and 30) as inventoryOrder_data
union all
select 7, 13, order_date, warehouse_id, order_id, null, null, 'INVENTORY_ORDER'
from (select id as order_id, order_date, warehouse_id from INVENTORYORDERS where id between 19 and 30) as inventoryOrder_data
union all
select 13, 14, order_date, warehouse_id, order_id, null, null, 'INVENTORY_ORDER'
from (select id as order_id, order_date, warehouse_id from INVENTORYORDERS where id between 19 and 30) as inventoryOrder_data
union all
select 16, 15, order_date, warehouse_id, order_id, null, null, 'INVENTORY_ORDER'
from (select id as order_id, order_date, warehouse_id from INVENTORYORDERS where id between 19 and 30) as inventoryOrder_data
union all
select 17, 16, order_date, warehouse_id, order_id, null, null, 'INVENTORY_ORDER'
from (select id as order_id, order_date, warehouse_id from INVENTORYORDERS where id between 19 and 30) as inventoryOrder_data;




