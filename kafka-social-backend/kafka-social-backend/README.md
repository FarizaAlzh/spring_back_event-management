# Kafka Social Backend

Multi-module Spring Boot project for Lecture 6 assignment: Kafka, PostgreSQL, Flyway, Swagger.

## Modules

- `common-events` - shared Kafka event classes
- `post-service` - creates posts and publishes `PostCreatedEvent`
- `feed-service` - consumes events, saves feed items and notifications

## Requirements

- Java 17
- Maven 3.9+ or IntelliJ Maven support
- Docker and Docker Compose

## Run infrastructure

```bash
docker compose up -d
```

This starts:

- Kafka in KRaft mode on `localhost:9092`
- PostgreSQL on `localhost:5432`

## Run services

Start `post-service` and `feed-service` separately from IntelliJ or with Maven:

```bash
mvn -pl post-service spring-boot:run
mvn -pl feed-service spring-boot:run
```

Ports:

- `post-service`: `http://localhost:8080`
- `feed-service`: `http://localhost:8081`

Swagger UI:

- `http://localhost:8080/swagger-ui.html`
- `http://localhost:8081/swagger-ui.html`

## API flow

### 1. Create post

`POST http://localhost:8080/posts`

Body:

```json
{
  "userId": "u1",
  "content": "Hello Kafka world!",
  "hashtags": ["kafka", "spring"]
}
```

Result:

- post saved in `posts`
- `PostCreatedEvent` published to Kafka topic `posts`

### 2. Read feed

`GET http://localhost:8081/feed?userId=u1`

Result:

- returns feed items stored in `feed_items`

## Flyway migrations

- `post-service/src/main/resources/db/migration/V1__create_posts_table.sql`
- `feed-service/src/main/resources/db/migration/V1__create_feed_items_table.sql`
- `feed-service/src/main/resources/db/migration/V2__create_notifications_table.sql`

## Notes

- JPA uses `ddl-auto=validate`
- Kafka JSON trusted packages are enabled
- feed and notification listeners use different consumer groups:
  - `feed-group`
  - `notification-group`

## Assignment checklist

- `POST /posts` validates input, saves a `PUBLISHED` post in PostgreSQL, and publishes `PostCreatedEvent` to Kafka topic `posts`
- `GET /posts/{postId}` returns the saved post or a documented `404`
- `feed-service` listens to the `posts` topic with group `feed-group` and saves rows in `feed_items`
- `feed-service` also listens with independent group `notification-group` and saves rows in `notifications`
- invalid Kafka events with blank `content` are skipped safely with warning logs
- all schema changes are managed by Flyway migrations
- Swagger/OpenAPI is configured in both services with UI available on each service port
