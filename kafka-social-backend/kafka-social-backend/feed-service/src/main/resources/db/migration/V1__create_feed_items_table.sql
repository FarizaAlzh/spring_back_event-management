CREATE TABLE IF NOT EXISTS feed_items (
    id UUID PRIMARY KEY,
    post_id UUID NOT NULL UNIQUE,
    user_id VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    hashtags TEXT,
    created_at TIMESTAMP NOT NULL
);
