# Elmenus Lite - Cart Management Module

Elmenus Lite is a Spring Boot backend project that handles cart operations for a food ordering application. This module allows customers to add, modify, remove, and view items in their cart.

---

## Features

* Add to Cart
* View Cart
* Modify Item Quantity
* Remove Item from Cart
* Clear Entire Cart

---


## Time Estimation vs. Actual
| Task | Amel                  | Asmaa | Mohamed               |
| ------------------------------  |-----------------------| ---- |-----------------------|
| Add to Cart                     | Estimated 6 Actual 3  |    |                       |
| View Cart                       |                       |   | Estimated 4 Actual 4  |
| Update Item Quantity            |                       |      | Estimated 3 Actual 5  |
| Remove Item from Cart           |  |   |                       |
| Clear Cart                      |  |   |                       |
| Testing                         | Estimated 16 Actual 10 |    | Estimated 10 Actual 12 |

---


## Technology Stack

* Java 17
* Spring Boot
* Maven
* PostgreSQL
* Liquibase

---

## Installation & Setup
> **Note:** This project uses **Liquibase** for managing database schema migrations. Liquibase changelogs are located under `src/main/resources/db/` and run automatically during application startup.


1. **Clone the repository:**

```bash
https://github.com/your-username/elmenus-lite.git
cd elmenus-lite
```

2. **Install PostgreSQL and PostGIS:**

Make sure PostgreSQL is installed. Then install PostGIS:

```bash
sudo apt install postgis postgresql-14-postgis-3
```

3. **Configure your PostgreSQL database:**

Create a PostgreSQL database and enable PostGIS:

```sql
CREATE DATABASE elmenus;
\c elmenus
CREATE EXTENSION postgis;
```

Then update `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/elmenus
    username: your_db_user
    password: your_db_password
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

4. **Build and run the application:**

```bash
./mvnw spring-boot:run
```

The application will be running on: `http://localhost:8080`

---

## Project Structure

```
elmenus-lite
├── controller         # REST controllers for cart and items
├── dto                # Data transfer objects
├── entity             # JPA entities (Cart, CartItem, etc.)
├── repository         # Spring Data JPA interfaces
├── service            # Business logic layer
│   └── implementation # Service implementations
├── handlerException   # Global error handling
├── mapper             # MapStruct mappers
├── resources
│   ├── application.yml
│   └── db              # SQL scripts
└── ElmenusLiteApplication.java # Main entry point
```
---
## Error Handling

400 Bad Request: Returned when required input is missing or invalid (e.g., invalid IDs, null fields).

500 Internal Server Error: Returned on unexpected system failure or unhandled exceptions.

---

## API Endpoints

### 1. Add Item to Cart

**POST** `/api/v1/cartItem`

**Request Body:**

```json
{
  "customerId": 1,
  "menuItemId": 2,
  "quantity": 3
}
```

**Response:**

```json
{
  "success": true,
  "message": "Cart item added successfully",
  "response": null
}
```

---

### 2. View Cart by Customer ID

**GET** `/api/v1/cart/customer/{customerId}`

**Response:**

```json
{
  "success": true,
  "message": "Cart retrieved successfully",
  "response": {
    "cartId": 1,
    "total": 99.0,
    "items": [
      {
        "cartItemId": 5,
        "menuItem": {
          "menuItemId": 2,
          "menu": {
            "menuId": 1,
            "menuName": "Lunch Specials"
          },
          "itemName": "Example Item",
          "price": 12.5,
          "available": true
        },
        "price": 12.5,
        "quantity": 3,
        "total": 37.5
      },
      {
        "cartItemId": 6,
        "menuItem": {
          "menuItemId": 4,
          "menu": {
            "menuId": 1,
            "menuName": "Lunch Specials"
          },
          "itemName": "Example Item",
          "price": 20,
          "available": true
        },
        "price": 20.0,
        "quantity": 2,
        "total": 40.0
      }
    ]
  }
}
```

---

### 3. Update Item Quantity

**PATCH** `/api/v1/cart/{cartId}/{cartItemId}/quantity`

**Request Body:**

````json
{
  "quantity": 2
}
````
**Response:**
```json
{
  "success": true,
  "message": "Quantity updated successfully",
  "response": {
    "cartItemId": 6,
    "menuItem": {
      "menuItemId": 4,
      "menu": {
        "menuId": 1,
        "menuName": "Lunch Specials"
      },
      "itemName": "Example Item",
      "price": 20,
      "available": true
    },
    "price": 20.0,
    "quantity": 2,
    "total": 40.0
  }
}
````

---

### 4. Remove Item from Cart

**DELETE** `/api/v1/cartItem/{cartItemId}`

**Response:**

```json
{
  "success": true,
  "message": "Cart item deleted successfully",
  "response": null
}
```

---

### 5. Clear Cart

**DELETE** `/api/v1/cart/{cartId}`

**Response:**

```json
{
  "success": true,
  "message": "Cart cleared successfully",
  "response": null
}
```

---

## Unit Tests

Unit tests are located in `src/test/java`. Use the following command to run all tests:

```bash
./mvnw test
```

---
