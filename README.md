# SIS 4 Event Management API - Report

## 1.сам проект
Этот проект представляет собой backend API для управления мероприятиями 'KBTU event'.
Приложение построено на Spring Boot и работает с PostgreSQL.

Основная сущность проекта - `Event`.
Система позволяет:
- создать мероприятие
- получить список мероприятий с пагинацией
- получить одно мероприятие по `id`
- обновить мероприятие
- удалить мероприятие

Все endpointы вынесены под версию API:

- `/api/v1/events`
Это значит, что API уже подготовлен к будущим изменениям без поломки старых клиентов.

## 2.что выполнено по 4 лабораторной

# 2.1 Flyway и versioning схемы базы данных

в проекте подключен Flyway и база поднимается не через `ddl-auto=create`, а через миграции.
Используются 2 миграции:
- `V1__init_schema.sql` - создаёт таблицу `events`
- `V2__add_event_description.sql` - добавляет колонку `description`

Это лучше для production-подхода потому что:
- история изменений базы хранится последовательно
- можно понять, какая версия схемы сейчас в базе

Также в application.properties указано:
- PostgreSQL datasource
- `spring.jpa.hibernate.ddl-auto=validate`

# 2.2 ну вроде бы чистая архитектура

В проекте соблюдена слоистая архитектура
- `Controller` принимает HTTP-запрос
- `Service` содержит бизнес-логику
- `Repository` работает с базой
- `Database` хранит данные

Цепочка работы выглядит так =>
`EventController -> EventService -> EventRepository -> PostgreSQL`

такой подход помог потому что
- контроллер не содержит бизнес-логику
- код проще поддерживать
- каждый слой отвечает только за свою задачу

# 2.3 DTO и MapStruct

В проекте entity не возвращается напрямую клиенту.
Для этого используются DTOшка
- `CreateEventRequest`
- `UpdateEventRequest`
- `EventResponse`

Маппинг между entity и DTO сделан через MapStruct
- `EventMapper`

После сборки MapStruct автоматически генерирует реализацию `EventMapperImpl`.
Это значит, что преобразование выполняется не вручную и не через reflection, а через сгенерированный Java-код на этапе компиляции.
это лучше потому что
- меньше ручного кода
- меньше ошибок при преобразовании данных

# 2.4 Bean Validation
Входящие запросы валидируются до записи в базу.
- `@NotBlank` для обязательных строк
- `@Email` для email организатора
- `@NotNull` для обязательных полей
- `@Min(1)` для `maxParticipants`

# 2.5 Logging
Вместо `System.out.println` используется Lombok-аннотации юзала
- `@Slf4j`

Логи есть в сервисном слое:
- при создании события
- при чтении
- при обновлении
- при удалении

## 3. Что выполнено по 5 лабораторной

# 3.1 Swagger / OpenAPI
В проект добавлена зависимость
- `springdoc-openapi-starter-webmvc-ui`

Это значит, что Swagger UI подключён.
Во время запуска приложения SpringDoc действительно активируется.

# 3.2 Документация controller endpoints
В `EventController` используются =>
- `@Tag`
- `@Operation`
- `@ApiResponses`

# 3.3 Документация DTO
`CreateEventRequest` и `EventResponse` документированы с помощью:
- `@Schema(description = ... , example = ... )`

# 3.4 Валидация в Swagger
Так как в request DTO стоят аннотации:
- `@NotBlank`
- `@Email`
- `@NotNull`
- `@Min`
Swagger будет показывать ограничения для полей автоматически:)

# 3.5 Скрытие entity из Swagger
Класс `Event` помечен:
- `@Hidden`
чтобы внутренняя entity не отображалась как публичный API-контракт.

# 3.6 Versioning API
Версионирование выполнено через URI
- `/api/v1/events`

# 3.7 Pagination
Метод получения списка использует
- `Pageable`
- `Page<EventResponse>`

## 4. Полный Global Exception Handler
- `404 Not Found` для отсутствующих ресурсов
- `400 Bad Request` для ошибок валидации
- `400 Bad Request` для некорректного JSON или неверного формата поля
- `500 Internal Server Error` для непредвиденных ошибок

Также теперь используется единый `ErrorResponse` с полями
- `timestamp`
- `status`
- `error`
- `message`
- `path`
- `validationErrors`
Это делает ответы API понятными и одинаковыми по структуре

# 4.1 Логирование стало полноценным
Теперь в проекте есть примеры всех нужных уровней логирования
- `INFO` для бизнес-событий
- `DEBUG` для технических деталей
- `ERROR` для обработанных исключений

# 4.2 Swagger-документация усилена
- задокументирован `UpdateEventRequest`
- добавлены схемы `ErrorResponse`
- добавлены примеры error response для `400`, `404`, `500`
- добавлена отдельная OpenAPI-конфигурация с названием API, версией и описанием
- установлен удобный Swagger UI path: `/swagger-ui.html`

## 8. Общий вывод
Проект выглядит готовом и реализован production-подход
- PostgreSQL
- Flyway
- DTO
- MapStruct
- validation
- Swagger / OpenAPI
- versioning
- pagination
- structured error handling
- structured logging
- deliverables for submission

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
