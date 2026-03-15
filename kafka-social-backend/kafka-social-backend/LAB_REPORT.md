# Отчет по лабораторной работе sis6
Данная лабораторная работа реализована как multi-module проект на Spring Boot 3 и состоит из трех модулей
- `common-events` содержит общее Kafka-событие `PostCreatedEvent`
- `post-service` работает как producer-сервис
- `feed-service` работает как consumer-сервис для ленты и уведомлений

В проекте используются PostgreSQL как основная база данных, Apache Kafka в режиме KRaft, Flyway для миграций базы данных и Swagger/OpenAPI для документации REST API

# Что сделала
## Задание 1. Producer: публикация поста
Реализовано в `post-service`
- REST endpoint `POST /posts`
- валидация входящего запроса с помощью Bean Validation:
  - `userId` не должен быть пустым
  - `content` не должен быть пустым
  - максимальная длина `content` составляет 280 символов
- для каждого поста генерируется уникальный UUID
- пост сохраняется в таблицу PostgreSQL `posts`
- статус поста сохраняется как `PUBLISHED`
- после сохранения сервис публикует `PostCreatedEvent` в Kafka topic `posts`
- событие сериализуется в JSON
- таблица `posts` создается через Flyway migration
- endpoint задокументирован в Swagger

Дополнительный endpoint
- `GET /posts/{postId}` возвращает пост по его идентификатору
- если пост не найден, API возвращает `404 Not Found` с JSON-ответом об ошибке

## Задание 2. Consumer: сервис ленты
Реализовано в `feed-service`
- Kafka listener `FeedConsumer` подписан на topic `posts`
- consumer group id: `feed-group`
- событие `PostCreatedEvent` десериализуется из JSON
- данные события сохраняются в таблицу PostgreSQL `feed_items`
- сервис пишет логи при обработке сообщений
- REST endpoint `GET /feed?userId=...` возвращает элементы ленты для указанного пользователя
- endpoint задокументирован в Swagger
- таблица `feed_items` создается через Flyway migration

## Задание 3. Consumer: сервис уведомлений
Реализовано в `feed-service` как второй Kafka listener
- Kafka listener `NotificationConsumer` подписан на тот же topic `posts`
- consumer group id: `notification-group`
- так как используется другой group id, этот listener получает те же события независимо от `FeedConsumer`
- уведомления сохраняются в таблицу PostgreSQL `notifications`
- сервис пишет логи при обработке уведомлений
- таблица `notifications` создается через Flyway migration

# Задание 4. Базовая обработка ошибок
в Kafka consumers, если поле `content` пустое или равно `null`, обработка пропускается и записывается warning в лог
`POST /posts` валидирует входящий запрос и возвращает `400 Bad Request` при некорректных данных
`GET /posts/{postId}` возвращает `404 Not Found`, если пост не существует
ответы с ошибками возвращаются в формате JSON
валидация и ответы с ошибками задокументированы в Swagger

## Задание 5. Swagger-документация
- настроена глобальная OpenAPI-информация: название, описание и версия API
- REST endpoints используют `@Operation`
- endpoints содержат `@ApiResponses`
- request и response DTO используют `@Schema`
- Swagger UI доступен по адресам:
  - `http://localhost:8080/swagger-ui.html`
  - `http://localhost:8081/swagger-ui.html`

# как работает система
1. Пользователь отправляет `POST /posts` в `post-service`.
2. `post-service` валидирует входящий запрос.
3. Пост сохраняется в таблицу PostgreSQL `posts`.
4. `post-service` публикует `PostCreatedEvent` в Kafka topic `posts`.
5. `FeedConsumer` получает событие в consumer group `feed-group` и сохраняет его в `feed_items`.
6. `NotificationConsumer` получает то же самое событие в consumer group `notification-group` и сохраняет его в `notifications`.
7. Пользователь может вызвать `GET /feed?userId=...`, чтобы получить элементы ленты из базы данных.

## структура
- Kafka запускается локально через Docker Compose в режиме KRaft
- PostgreSQL запускается локально через Docker Compose
- Zookeeper не юзала
- JPA использует `ddl-auto=validate`, поэтому схема базы данных управляется только через Flyway


## Вывод
Лабораторная работа содержит обязательный producer и двух независимых consumers, сохранение данных в PostgreSQL, миграции Flyway, документацию Swagger, валидацию запросов и базовую обработку ошибок. Структура проекта и логика работы соответствуют требованиям задания по event-driven architecture.
