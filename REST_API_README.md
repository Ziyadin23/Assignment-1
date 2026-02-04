# REST API for Real Estate Agency

## Building the Project

1. Download dependencies:
```bash
mvn dependency:copy-dependencies -DoutputDirectory=lib
```

2. Compile the REST API server and dependencies:
```bash
mvn -q -DskipTests package
```

## Database Setup

1. Ensure PostgreSQL is running and update `DatabaseConnection.java` if your credentials differ.
2. Run the schema script:

```bash
psql -U postgres -d postgres -f db/schema.sql
```

## Running the REST API Server

```bash
java -cp "target/classes:lib/*" api.RestApiServer
```

The server will start on `http://localhost:7070`

## API Endpoints

### Agencies

- `GET /api/agencies` - List all agencies
- `GET /api/agencies/{id}` - Get specific agency by ID
- `POST /api/agencies` - Create new agency
  - Request body: `{"name": "Agency Name", "address": "Address"}`
- `PUT /api/agencies/{id}` - Update agency
  - Request body: `{"name": "New Name", "address": "New Address"}`
- `DELETE /api/agencies/{id}` - Delete agency

### Realtors

- `GET /api/realtors` - List all realtors
- `GET /api/realtors/{id}` - Get specific realtor by ID
- `POST /api/realtors` - Create new realtor
  - Request body: `{"name": "Realtor Name"}`
- `PUT /api/realtors/{id}` - Update realtor
  - Request body: `{"name": "New Name"}`
- `DELETE /api/realtors/{id}` - Delete realtor

### Properties

- `GET /api/properties` - List all properties
- `GET /api/properties/{id}` - Get specific property by ID
- `POST /api/properties` - Create new property
  - Request body: `{"city": "City Name", "price": 120000}`
- `PUT /api/properties/{id}` - Update property
  - Request body: `{"city": "New City", "price": 150000}`
- `DELETE /api/properties/{id}` - Delete property

## Testing

You can test the API using:
- The provided `test.html` file (open in browser)
- curl commands
- Postman
- Any HTTP client

### Example curl Commands

```bash
# List all agencies
curl http://localhost:7070/api/agencies

# Get agency by ID
curl http://localhost:7070/api/agencies/1

# Create new agency
curl -X POST http://localhost:7070/api/agencies \
  -H "Content-Type: application/json" \
  -d '{"name": "Test Agency", "address": "123 Main St"}'

# Update agency
curl -X PUT http://localhost:7070/api/agencies/1 \
  -H "Content-Type: application/json" \
  -d '{"name": "Updated Agency", "address": "456 Oak Ave"}'

# Delete agency
curl -X DELETE http://localhost:7070/api/agencies/1

# List all properties
curl http://localhost:7070/api/properties

# Create new property
curl -X POST http://localhost:7070/api/properties \
  -H "Content-Type: application/json" \
  -d '{"city": "Boston", "price": 250000}'
```

## Response Format

### Success Responses

For list operations:
```json
[
  {"id": 1, "name": "Premium Realty", "address": "123 Main St"},
  {"id": 2, "name": "Elite Homes", "address": "456 Oak Ave"}
]
```

For create/update/delete operations:
```json
{"success": true, "message": "Operation successful"}
```

### Error Responses

```json
{"success": false, "error": "Error message"}
```

## HTTP Status Codes

- `200 OK` - Successful GET/PUT/DELETE
- `201 Created` - Successful POST
- `400 Bad Request` - Invalid input
- `404 Not Found` - Resource not found
- `500 Internal Server Error` - Server error
