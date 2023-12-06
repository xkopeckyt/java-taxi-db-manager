--
-- Department table definition
--
CREATE TABLE IF NOT EXISTS "Category"
(
    `id`        BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    `name`      VARCHAR(50) NOT NULL,
    `guid`      VARCHAR     NOT NULL UNIQUE,
    `createdAt` TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP
);

--
-- Employee table definition
--
CREATE TABLE IF NOT EXISTS "Ride"
(
    `id`                BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    `distance`          FLOAT NOT NULL,
    `dateTime`          TIMESTAMP NOT NULL,
    `price`             FLOAT NOT NULL,
    `originalCurrency`  VARCHAR(50) NOT NULL,
    `categoryId`        BIGINT REFERENCES "Category"(`id`),
    `passengersCount`   INT NOT NULL,
    `guid`              VARCHAR      NOT NULL UNIQUE,
    `createdAt`         TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

--
-- Template tebale definition
--
CREATE TABLE IF NOT EXISTS "Template"
(
    `id`                BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    `name`              VARCHAR(50) NOT NULL,
    `distance`          FLOAT NOT NULL,
    `dateTime`          TIMESTAMP NOT NULL,
    `price`             FLOAT NOT NULL,
    `originalCurrency`  VARCHAR(50) NOT NULL,
    `categoryId`        BIGINT REFERENCES "Category"(`id`) ON DELETE CASCADE,
    `passengersCount`   INT NOT NULL,
    `guid`              VARCHAR      NOT NULL UNIQUE,
    `createdAt`         TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);
