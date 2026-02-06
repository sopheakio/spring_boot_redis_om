# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.5.10/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.5.10/maven-plugin/build-image.html)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

## Redis Stack OM
```azure
docker run -d --name demo-redis-stack -p 6390:6379 redis/redis-stack:latest redis-stack-server --requirepass myRedisPassword
```

# Spring Boot with Redis OM

## 1. Overview

Spring Boot with **Redis OM** is a modern approach to building high‑performance, data‑intensive applications using Redis as more than just a cache. Redis OM allows developers to treat Redis like a document database with indexing and querying capabilities, while still benefiting from Redis’ in‑memory speed.

This setup is especially popular in **microservices, real‑time systems, authentication services, and read‑heavy APIs**.

---

## 2. What is Redis OM?

**Redis OM (Object Mapping)** is a developer library that maps Java objects to Redis data structures and modules.

In the Spring ecosystem, **Redis OM Spring** provides:

* Repository abstractions (similar to Spring Data JPA)
* Automatic index creation
* Object‑to‑JSON mapping
* Query methods using method names

Redis OM **requires Redis Stack**, not plain Redis.

---

## 3. Redis Stack vs Plain Redis

| Feature              | Plain Redis | Redis Stack |
| -------------------- | ----------- | ----------- |
| Key‑Value storage    | ✅           | ✅           |
| RedisJSON            | ❌           | ✅           |
| RediSearch (indexes) | ❌           | ✅           |
| Full‑text search     | ❌           | ✅           |
| Redis OM support     | ❌           | ✅           |

Redis OM relies on:

* **RedisJSON** → store Java objects as JSON
* **RediSearch** → index and query data

---

## 4. Architecture with Spring Boot

Typical architecture:

```
Client
  ↓
Spring Boot API
  ↓
Redis OM (Repositories)
  ↓
Redis Stack (JSON + Search)
```

In many systems:

* Redis OM is used for **fast reads and searches**
* PostgreSQL / MySQL remains the **source of truth**

---

## 5. Project Setup

### Dependencies (Gradle example)

```gradle
implementation "com.redis.om:redis-om-spring:0.9.5"
```

### Redis Stack (Docker)

Redis OM will only work if Redis Stack modules are loaded.

---

## 6. Entity (Model) Definition

Redis OM entities are defined using `@Document`.

```java
@Document("product")
public class Product {

    @Id
    private String id;

    @Indexed
    private String sku;

    @Indexed
    private String name;
}
```

### Annotation meanings

* `@Document` → marks the class as a Redis OM entity
* `@Id` → unique identifier (used in Redis key)
* `@Indexed` → creates searchable fields in RediSearch

Redis stores this object as **JSON**, not as a hash.

---

## 7. Repository Layer

Repositories look similar to Spring Data JPA.

```java
public interface ProductRepository
        extends RedisDocumentRepository<Product, String> {

    Optional<Product> findBySku(String sku);
}
```

Redis OM translates these methods into **RediSearch queries**.

---

## 8. Index Management

Indexes are created automatically at application startup.

### Enable repositories

```java
@SpringBootApplication
@EnableRedisRepositories(
    indexRecreateMode = IndexRecreateMode.RECREATE
)
public class Application {
}
```

### Index recreate modes

* `NONE` → do nothing
* `CREATE` → create index if missing
* `RECREATE` → drop and recreate indexes

> ⚠️ `RECREATE` is recommended for development only.

---

## 9. Common Pitfalls

### Empty query results

Data exists but queries return empty when:

* Index does not exist
* Fields are missing `@Indexed`
* Data was inserted manually using `JSON.SET`

Redis OM **does not auto‑index existing data**.

### Wrong Redis startup

Using `redis-server` instead of `redis-stack-server` will cause:

```
ERR unknown command 'JSON.SET'
```

---

## 10. When to Use Redis OM

### Good use cases

* Authentication profiles
* Product catalogs
* Search‑heavy APIs
* Real‑time dashboards
* Session and token storage

### Not recommended

* Financial transactions
* Strong relational data
* Long‑term durable records

---

## 11. Best Practices

* Use Redis OM as a **read‑optimized store**
* Keep a relational DB as the source of truth
* Avoid storing large blobs
* Use TTLs where possible
* Use `CREATE` mode in production

---

## 12. Summary

Spring Boot with Redis OM provides:

* Extremely fast data access
* Simple repository‑based development
* Powerful search and indexing

When combined with Redis Stack, Redis OM becomes a powerful tool for building **modern, scalable, and high‑performance services**.

---

**End of document**
