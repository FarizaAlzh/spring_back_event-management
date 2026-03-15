CREATE TABLE IF NOT EXISTS notifications (
    id UUID PRIMARY KEY,
    post_id UUID NOT NULL,
    user_id VARCHAR(100) NOT NULL,
    message TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL
);
