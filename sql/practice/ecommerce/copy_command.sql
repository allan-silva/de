COPY
    user_session_events(event_time, event_type, product_id, category_id, category_code, brand, price, user_id, user_session)
FROM
    '~/Downloads/2019-Nov.csv'
WITH (
    FORMAT CSV,
    HEADER TRUE,
    FORCE_NULL(event_type, product_id, category_id, category_code, brand, price, user_id, user_session)
);