CREATE TABLE media_data
(
    id         UUID PRIMARY KEY,
    profile_id UUID         NOT NULL,
    name       VARCHAR(255) NOT NULL,
    type       VARCHAR(255) NOT NULL,
    link       VARCHAR(255) NOT NULL
);