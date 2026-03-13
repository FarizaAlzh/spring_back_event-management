CREATE TABLE events (
                        id BIGSERIAL PRIMARY KEY,
                        title VARCHAR(255) NOT NULL,
                        organizer_email VARCHAR(255) NOT NULL,
                        event_date TIMESTAMP NOT NULL,
                        location VARCHAR(255) NOT NULL,
                        category VARCHAR(50) NOT NULL,
                        format VARCHAR(50) NOT NULL,
                        max_participants INT NOT NULL
);