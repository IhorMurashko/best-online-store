# bestStore-security-starter

A **plug-and-play Spring Boot security starter** designed for microservice-based applications. It provides a simple, consistent, and centralized approach to securing both REST services and API Gateways, so that your team can focus on building features — not writing repetitive security config.

---

## 🚀 What is this project?

This starter acts as a **drop-in library** that enables JWT-based security across all your services.

Instead of rewriting `SecurityFilterChain`, JWT parsing, and token revocation logic in every service, just:

* Annotate your service with `@EnableGatewaySecurity` or `@EnableRestSecurity`
* Add `@PreAuthorize` or other Spring Security annotations where needed

**That's it!** The starter takes care of:

* JWT validation and parsing
* Security filters for Gateway and REST
* Revoke token logic via Redis
* User context extraction from headers

---

## 📦 Modules Included

This starter is divided into three main modules:

### 1. `core`

Provides shared infrastructure used by all modules:

* JWT creation and validation (`JwtTokenProvider`)
* Token claim constants
* Secret key configuration via `application.yml`

### 2. `gateway`

Configures filters and utilities to secure Spring Cloud Gateway:

* Validates JWT tokens
* Extracts user info from claims
* Forwards claims as headers to downstream services
* Revocation support via Redis

Enable it with:

```java
@EnableGatewaySecurity
```

### 3. `rest`

Secures individual microservices:

* Parses headers from Gateway and builds `UserContext`
* Sets up Spring Security context based on received headers
* Optionally restricts token types (e.g. disallow access tokens on refresh endpoints)

Enable it with:

```java
@EnableRestSecurity
```

---

## ⚙️ How to Use

### 1. Add the dependency

> Available  on GitHub Packages.

```xml
<dependency>
    <groupId>com.bestStore</groupId>
    <artifactId>security-starter</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

### 2. Set up application.yaml

```yaml
security-lib:
  secret:
    key:
      jwt: your-very-secret-key-at-least-32-chars
      internal: your-internal-secret-key-32-chars-min
  refresh-token:
    mode:
      enabled: true
      allowed-refresh-access:
        - /auth/refresh
```

### 3. Enable configuration

Use one of the annotations in your application:

```java
@SpringBootApplication
@EnableRestSecurity
public class ProductServiceApplication {
}
```

Or:

```java
@SpringBootApplication
@EnableGatewaySecurity
public class ApiGatewayApplication {
}
```

---

## 🔐 Security Flow

### In Gateway:

* Incoming request → JWT checked → claims extracted → forwarded as headers

### In REST services:

* Headers received → `UserContext` built → roles resolved → Spring Security context populated

### Token Types:

* Supports `ACCESS` and `REFRESH` types
* Customizable logic to allow/disallow token types per endpoint

---

## ❌ Revoked Tokens

* Tokens can be blacklisted using Redis
* Expired tokens are cleaned up automatically

---

## ✅ Features Summary

| Feature                  | Gateway | REST Service  |
| ------------------------ | ------- | ------------- |
| JWT validation           | ✅       | ✅             |
| Header propagation       | ✅       | ✅ (read only) |
| Spring Security context  | ⛔       | ✅             |
| Token revocation (Redis) | ✅       | ✅             |
| Internal secret check    | ✅       | ✅             |
| Filter auto-registration | ✅       | ✅             |
| Role-based auth          | ⛔       | ✅             |

---

## 👨‍💻 Author

Made with care by **Ihor Murashko**

