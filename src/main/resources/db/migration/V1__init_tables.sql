CREATE TABLE fridge
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(200) NOT NULL
);

CREATE TABLE product
(
    id        BIGSERIAL PRIMARY KEY,
    type      VARCHAR(20) NOT NULL,
    quantity  INTEGER     NOT NULL,
    fridge_id BIGINT REFERENCES fridge (id)
);