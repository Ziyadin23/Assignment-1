# REAL ESTATE AGENCY

### This repository uses the following Java features:

- Classes & objects
- Encapsulation (private fields + getters/setters)
- Constructors
- Inheritance (`Apartment extends Property`)
- Polymorphism / method overriding (`getCommissionRate()` override)
- toString(), equals(), hashCode() overrides
- Collections & Generics (`List<Property>`, `ArrayList<>`)
- Java Streams & Lambdas (filter, collect, sorted)
- Method references (e.g., `Property::getPrice`)
- Comparator & sorting (`Comparator.comparingDouble`)
- Defensive copying (`new ArrayList<>(listings)`)
- Primitives and numeric literals (`long`, `double`, underscores like `120_000`)
- Entry point (`public static void main(String[] args)`) and standard output (`System.out.println`, `System.out.printf`)
- `final` fields for immutability (e.g., `id`, `agencyName`)
- `instanceof` checks and `@Override` annotation
- Default & static interface methods (`ValidationRules`)
- Builder + Factory design patterns (see `Property.builder` and `PropertyFactory`)

## Packages in src

- `domain`
    - `Property`, `Apartment`, `Realtor`, `RealEstateAgency`, `Agency`
- `dto`
    - `AgencyRecord`, `RealtorRecord`, `PropertyRecord`
- `repository`
    - CRUD interfaces
- `repository.jdbc`
    - JDBC implementations using `PreparedStatement`
- `service`
    - validation + business rules
- `api`
    - REST API server (Javalin)
- `app`
    - command-line demo + Swing UI
- `config`, `exceptions`, `patterns`, `util`

## Notes:
- This is just for practice.
- I’m still learning Git/GitHub, so the structure is simple.

- Realtor.java
    - field: name
    - method: calculateCommission(Property p) -> price * commissionRate

- RealEstateAgency.java
    - keeps a list of Property
    - methods: add, all, filterByCity(city), searchByPrice(min, max), sortByPriceAsc()
    - has toString, equals, hashCode

- Main.java
    - creates some objects and calls the methods to show how it works

## Notes:
- This is just for practice.
- I’m still learning Git/GitHub, so the structure is simple.
