CREATE INDEX event_type_ix ON user_session_events USING hash (
    event_type
);
