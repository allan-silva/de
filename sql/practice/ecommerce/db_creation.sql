CREATE DATABASE ecommerce;

CREATE TABLE user_session_events(
    id SERIAL PRIMARY KEY,
    event_time TIMESTAMP,
    event_type VARCHAR(10),
    product_id VARCHAR(30),
    category_id VARCHAR(30),
    category_code VARCHAR(100),
    brand VARCHAR(100),
    price NUMERIC(10,2),
    user_id VARCHAR(30),
    user_session VARCHAR(100)
);
