CREATE TABLE users
(
    id           BIGSERIAL PRIMARY KEY,
    username     VARCHAR(50) NOT NULL,
    password     VARCHAR(50) NOT NULL,
    email        VARCHAR(50) NOT NULL,
    verified     BOOLEAN     NOT NULL,
    phone_number VARCHAR(50),
    first_name   VARCHAR(50),
    last_name    VARCHAR(50),
    middle_name  VARCHAR(50),
    role         VARCHAR(10) NOT NULL,
    address      VARCHAR(50),
    city         VARCHAR(50),
    state        VARCHAR(50)
);

CREATE TABLE otp
(
    id         BIGSERIAL PRIMARY KEY,
    user_id    BIGINT                   NOT NULL REFERENCES users (id),
    code       BIGINT                   NOT NULL,
    confirmed  BOOLEAN                  NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    expired_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE category
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(50) NOT NULL,
    description VARCHAR(50)
);

CREATE TABLE product
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(50) NOT NULL,
    description VARCHAR(50),
    price       BIGINT      NOT NULL,
    quantity    BIGINT      NOT NULL,
    category_id BIGINT      NOT NULL REFERENCES category (id)
);

CREATE TABLE orders
(
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT                   NOT NULL REFERENCES users (id),
    total_price BIGINT                   NOT NULL,
    created_at  TIMESTAMP WITH TIME ZONE NOT NULL,
    status      VARCHAR(15)
);

CREATE TABLE payment
(
    id         UUID PRIMARY KEY,
    user_id    BIGINT                   NOT NULL REFERENCES users (id),
    order_id   BIGINT                   NOT NULL REFERENCES orders (id),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    paid_at    TIMESTAMP WITH TIME ZONE,
    amount     BIGINT                   NOT NULL,
    status     VARCHAR(15)
);