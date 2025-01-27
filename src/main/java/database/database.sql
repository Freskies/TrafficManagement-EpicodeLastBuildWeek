CREATE TYPE subscription_duration AS ENUM ('WEEKLY', 'MONTHLY');
CREATE TYPE dispenser_status AS ENUM ('ACTIVE', 'INACTIVE');
CREATE TYPE type_of_transport AS ENUM ('BUS', 'TRAM');

CREATE TABLE dispensers
(
    dispenser_id SERIAL PRIMARY KEY,
    location     VARCHAR(50)      NOT NULL,
    status       dispenser_status NOT NULL
);

CREATE TABLE cards
(
    card_id         SERIAL PRIMARY KEY,
    owner_full_name VARCHAR(50) NOT NULL,
    release_date    DATE        NOT NULL,
    dispenser_id    INT references dispensers (dispenser_id)
);

CREATE TABLE subscriptions
(
    subscription_id SERIAL PRIMARY KEY,
    card_id         INT REFERENCES cards (card_id),
    dispenser_id    INT REFERENCES dispensers (dispenser_id),
    release_date    DATE                  NOT NULL,
    duration        subscription_duration NOT NULL
);

CREATE TABLE means_of_transport
(
    means_of_transport_id SERIAL PRIMARY KEY,
    type_of_transport     type_of_transport NOT NULL,
    model                 VARCHAR(8)        NOT NULL,
    seats                 INT               NOT NULL
);

CREATE TABLE maintenances
(
    maintenance_id        SERIAL PRIMARY KEY,
    start_date            DATE NOT NULL,
    end_date              DATE NOT NULL,
    means_of_transport_id INT REFERENCES means_of_transport (means_of_transport_id)
);

CREATE TABLE routes
(
    route_id             SERIAL PRIMARY KEY,
    route_start          VARCHAR(50) NOT NULL,
    route_end            VARCHAR(50) NOT NULL,
    expected_travel_time INTERVAL    NOT NULL
);

CREATE TABLE use_routes
(
    use_route_id          SERIAL PRIMARY KEY,
    means_of_transport_id INT REFERENCES means_of_transport (means_of_transport_id),
    route_id              SERIAL REFERENCES routes (route_id),
    real_travel_time      INTERVAL NOT NULL,
    date                  DATE     NOT NULL
);

CREATE TABLE tickets
(
    ticket_id    SERIAL PRIMARY KEY,
    release_date DATE NOT NULL,
    use_route_id INT REFERENCES use_routes (use_route_id),
    dispenser_id INT REFERENCES dispensers (dispenser_id)
);

-- DUMMY DATA:

INSERT INTO dispensers (location, status)
VALUES ('Central Station', 'ACTIVE'),
       ('East Side', 'INACTIVE'),
       ('West End', 'ACTIVE'),
       ('North Plaza', 'INACTIVE'),
       ('South Square', 'ACTIVE'),
       ('Downtown', 'INACTIVE'),
       ('Uptown', 'ACTIVE'),
       ('Main Street', 'INACTIVE'),
       ('Riverfront', 'ACTIVE'),
       ('Park Avenue', 'INACTIVE'),
       ('Sunset Boulevard', 'ACTIVE'),
       ('Ocean Drive', 'INACTIVE'),
       ('Mountain View', 'ACTIVE'),
       ('Lakefront', 'INACTIVE'),
       ('City Center', 'ACTIVE'),
       ('Suburbia', 'INACTIVE'),
       ('Hillside', 'ACTIVE'),
       ('Valley Road', 'INACTIVE'),
       ('Countryside', 'ACTIVE'),
       ('Metro Station', 'INACTIVE');

INSERT INTO cards (owner_full_name, release_date, dispenser_id)
VALUES ('John Doe', '2023-04-01', 1),
       ('Jane Doe', '2023-04-02', 2),
       ('Alice Smith', '2023-04-15', 3),
       ('Bob Smith', '2023-05-01', 4),
       ('Charlie Brown', '2023-05-10', 5),
       ('Daisy Brown', '2023-06-01', 6),
       ('Eve Johnson', '2023-06-10', 7),
       ('Frank Johnson', '2023-07-01', 8),
       ('Grace Williams', '2023-07-10', 9),
       ('Henry Williams', '2023-08-01', 10),
       ('Ivy Davis', '2023-08-10', 11),
       ('Jack Davis', '2023-09-01', 12),
       ('Kelly Miller', '2023-09-10', 13),
       ('Larry Miller', '2023-10-01', 14),
       ('Molly Wilson', '2023-10-10', 15),
       ('Nancy Wilson', '2023-11-01', 16),
       ('Oliver Moore', '2023-11-10', 17),
       ('Penny Moore', '2023-12-01', 18),
       ('Quincy Taylor', '2023-12-10', 19),
       ('Rose Taylor', '2024-01-01', 20);

INSERT INTO subscriptions (card_id, dispenser_id, release_date, duration)
VALUES (1, 1, '2023-04-01', 'WEEKLY'),
       (2, 2, '2023-04-02', 'MONTHLY'),
       (3, 3, '2023-04-15', 'WEEKLY'),
       (4, 4, '2023-05-01', 'MONTHLY'),
       (5, 5, '2023-05-10', 'WEEKLY'),
       (7, 7, '2023-06-01', 'WEEKLY'),
       (8, 8, '2023-06-10', 'MONTHLY');

INSERT INTO means_of_transport (type_of_transport, model, seats)
VALUES ('BUS', 'ModelX', 40),
       ('TRAM', 'ModelY', 30),
       ('BUS', 'ModelZ', 50),
       ('BUS', 'ModelV', 45),
       ('TRAM', 'ModelU', 30),
       ('BUS', 'ModelT', 40),
       ('TRAM', 'ModelS', 25),
       ('BUS', 'ModelR', 55),
       ('TRAM', 'ModelQ', 40),
       ('BUS', 'ModelP', 60),
       ('TRAM', 'ModelO', 35),
       ('BUS', 'ModelN', 50),
       ('TRAM', 'ModelM', 30),
       ('BUS', 'ModelL', 45),
       ('TRAM', 'ModelK', 40),
       ('BUS', 'ModelJ', 55),
       ('TRAM', 'ModelI', 30),
       ('BUS', 'ModelH', 50),
       ('TRAM', 'ModelG', 35);

INSERT INTO maintenances (start_date, end_date, means_of_transport_id)
VALUES ('2023-05-01', '2023-05-10', 1),
       ('2023-06-01', '2023-06-05', 2),
       ('2023-07-01', '2023-07-10', 3),
       ('2023-08-01', '2023-08-05', 4),
       ('2023-09-01', '2023-09-10', 5),
       ('2023-10-01', '2023-10-05', 6),
       ('2023-11-01', '2023-11-10', 7),
       ('2023-12-01', '2023-12-05', 8),
       ('2024-01-01', '2024-01-10', 9);

INSERT INTO routes (route_start, route_end, expected_travel_time)
VALUES ('Station A', 'Station B', '00:30:00'),
       ('Station C', 'Station D', '00:45:00'),
       ('Station E', 'Station F', '00:25:00'),
       ('Station G', 'Station H', '00:40:00'),
       ('Station I', 'Station J', '00:35:00'),
       ('Station K', 'Station L', '00:50:00'),
       ('Station M', 'Station N', '00:30:00'),
       ('Station O', 'Station P', '00:55:00'),
       ('Station Q', 'Station R', '00:20:00'),
       ('Station S', 'Station T', '00:45:00'),
       ('Station U', 'Station V', '00:35:00'),
       ('Station W', 'Station X', '00:40:00'),
       ('Station Y', 'Station Z', '00:25:00'),
       ('Station AA', 'Station BB', '00:50:00'),
       ('Station CC', 'Station DD', '00:30:00'),
       ('Station EE', 'Station FF', '00:45:00'),
       ('Station GG', 'Station HH', '00:35:00'),
       ('Station II', 'Station JJ', '00:40:00'),
       ('Station KK', 'Station LL', '00:25:00'),
       ('Station MM', 'Station NN', '00:50:00');

INSERT INTO use_routes (means_of_transport_id, route_id, real_travel_time, date)
VALUES (1, 1, '00:35:00', '2023-05-01'),
       (2, 2, '00:50:00', '2023-06-01'),
       (3, 3, '00:30:00', '2023-07-01'),
       (4, 4, '00:45:00', '2023-08-01'),
       (5, 5, '00:40:00', '2023-09-01'),
       (6, 6, '00:55:00', '2023-10-01'),
       (7, 7, '00:35:00', '2023-11-01'),
       (8, 8, '00:50:00', '2023-12-01'),
       (9, 9, '00:30:00', '2024-01-01');

INSERT INTO tickets (release_date, dispenser_id)
VALUES ('2023-05-01', 1),
       ('2023-06-01', 2),
       ('2023-07-01', 3),
       ('2023-08-01', 4),
       ('2023-09-01', 5),
       ('2023-11-01', 7),
       ('2023-12-01', 8);

INSERT INTO tickets (release_date, dispenser_id, use_route_id)
VALUES ('2024-05-01', 1, 1),
       ('2024-06-01', 2, 2),
       ('2024-07-01', 3, 3);