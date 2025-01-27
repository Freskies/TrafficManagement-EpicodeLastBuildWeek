CREATE TYPE subscription_duration AS ENUM ('WEEKLY', 'MONTHLY');
CREATE TYPE dispenser_status AS ENUM ('ACTIVE', 'INACTIVE');
CREATE TYPE type_of_transport AS ENUM ('BUS', 'TRAM');

CREATE TABLE cards
(
    card_id         SERIAL PRIMARY KEY,
    owner_full_name VARCHAR(50) NOT NULL,
    release_date    DATE        NOT NULL
);

CREATE TABLE dispensers
(
    dispenser_id SERIAL PRIMARY KEY,
    location     VARCHAR(50)      NOT NULL,
    status       dispenser_status NOT NULL
);

CREATE TABLE tickets
(
    ticket_id       SERIAL PRIMARY KEY,
    release_date    DATE NOT NULL,
    obliterate_date DATE,
    dispenser_id    INT REFERENCES dispensers (dispenser_id)
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
    route_id    SERIAL PRIMARY KEY,
    route_start VARCHAR(50) NOT NULL,
    route_end   VARCHAR(50) NOT NULL,
    expected_travel_time INTERVAL NOT NULL
);

CREATE TABLE use_routes
(
    use_route_id          SERIAL PRIMARY KEY,
    means_of_transport_id INT REFERENCES means_of_transport (means_of_transport_id),
    route_id              SERIAL REFERENCES routes (route_id),
    real_travel_time INTERVAL NOT NULL
);