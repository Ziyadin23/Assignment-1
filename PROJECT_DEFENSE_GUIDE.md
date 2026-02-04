# Project Defense Guide (Real Estate Agency)

This guide maps every defense requirement to the exact place in the codebase and explains how to demo it.

---

## 1) Project Entities (Basics)
**Where to show it**
- `domain/Property`, `domain/Apartment`, `domain/Realtor`, `domain/Agency` and `domain/RealEstateAgency` include constructors, private fields, getters/setters, and `toString/equals/hashCode` implementations.  

**What to say**
- These are the core project entities. They encapsulate data and expose behavior (e.g., `Realtor.calculateCommission` uses polymorphism via `Property.getCommissionRate`).

---

## 2) OOP Principles (Abstraction, Encapsulation, Inheritance, Polymorphism)
**Where to show it**
- **Abstraction:** repository and service interfaces (`repository/*`, `service/*`).  
- **Encapsulation:** private fields + validation in `domain/Property`.  
- **Inheritance:** `domain/Apartment extends Property`.  
- **Polymorphism:** `Property.getCommissionRate` overridden in `Apartment` and used by `Realtor.calculateCommission`.

---

## 3) Data Pool (In-memory collection)
**Where to show it**
- `domain/RealEstateAgency` stores a `List<Property>` and supports filtering, searching, and sorting.

**Demo**
- Run `app.Main` to see `filterByCity`, `searchByPrice`, and `sortByPriceAsc` in action.

---

## 4) Exception Handling (at least 2 custom exceptions)
**Where to show it**
- `exceptions/InvalidInputException`  
- `exceptions/NotFoundException`  
- (Bonus) `exceptions/DataAccessException` for JDBC errors

**Demo**
- Call REST endpoints with invalid IDs or missing fields to show 400/404 responses.

---

## 5) Database (PostgreSQL + JDBC)
**Where to show it**
- JDBC code in `repository/jdbc/*` with `PreparedStatement`.  
- DB connection in `config/DatabaseConnection`.  
- Schema in `db/schema.sql` includes 3 tables.

---

## 6) REST API (JSON + DB)
**Where to show it**
- `api/RestApiServer` provides CRUD endpoints for agencies, realtors, and properties.

**Demo**
- Use Postman/Insomnia/curl or the included `test.html` file.

---

## 7) SOLID (DIP mandatory)
**Before (problem)**
- REST API and UI directly depended on concrete JDBC DAO classes (tight coupling).  

**After (solution)**
- **DIP:** `RestApiServer` + `AgencyApp` depend on **service interfaces** (`service/*`) and **repository interfaces** (`repository/*`).  
- **SRP:** data access in `repository/jdbc/*`, validation/business rules in `service/*`, API in `api/*`.

---

## 8) Language Features (Generics + Lambdas + 1 extra)
**Where to show it**
- Generics/Lambdas: `domain/RealEstateAgency` (`List<Property>`, streams, filters, sorting).  
- **Extra feature:** default + static interface methods in `util/ValidationRules` (used by services).

---

## 9) Design Patterns (at least 2)
**Where to show it**
1. **Builder:** `domain/Property.builder(...)`  
2. **Factory:** `patterns/PropertyFactory`

**Demo**
- `app.CommandShell` creates properties via Builder and Factory and uses them in runtime flow.

---

## 10) Component/Package Structure
**Where to show it**
- Clear packages: `domain`, `dto`, `repository`, `repository.jdbc`, `service`, `api`, `app`, `config`, `exceptions`, `patterns`, `util`.
- Database/API logic are separated from domain and service logic.

---

# How to Run / Demo

## A) Command-line demo (OOP + patterns + data pool)
```bash
java -cp "target/classes:lib/*" app.Main
```

## B) REST API demo (DB + JSON)
1) Create tables:
```bash
psql -U postgres -d postgres -f db/schema.sql
```

2) Build:
```bash
mvn -q -DskipTests package
```

3) Start server:
```bash
java -cp "target/classes:lib/*" api.RestApiServer
```

## C) Swing UI demo (optional)
```bash
java -cp "target/classes:lib/*" app.AgencyApp
```

---

# Quick Script for Defense
1. Start REST API + show CRUD with Postman.
2. Show JDBC code and DB schema.
3. Run `app.Main` to show data pool, builder + factory, and polymorphism.
4. Walk through package structure and SOLID refactor.
