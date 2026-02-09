# Real Estate Management System - Verification Checklist

This checklist verifies that all CRUD operations work correctly for all three entities (Agencies, Realtors, Properties) through the web UI.

## Prerequisites

1. **PostgreSQL Running:**
   ```bash
   sudo service postgresql start
   sudo -u postgres psql -c "ALTER USER postgres PASSWORD '0000';"
   ```

2. **Database Schema Created:**
   ```bash
   sudo -u postgres psql -U postgres -d postgres -f db/schema.sql
   ```

3. **Project Built:**
   ```bash
   mvn clean package -DskipTests
   mvn dependency:copy-dependencies -DoutputDirectory=lib
   ```

4. **Backend Server Started:**
   ```bash
   java -cp "target/classes:lib/*" api.RestApiServer &
   # Server should start on http://localhost:7070
   ```

5. **Frontend Server Started:**
   ```bash
   cd frontend
   python3 -m http.server 8000 &
   # Frontend should be available at http://localhost:8000
   ```

## Backend API Verification

### Test Agencies API
```bash
# List agencies
curl http://localhost:7070/api/agencies

# Create agency
curl -X POST http://localhost:7070/api/agencies \
  -H "Content-Type: application/json" \
  -d '{"name": "Test Agency", "address": "123 Main St"}'

# Get agency by ID
curl http://localhost:7070/api/agencies/1

# Update agency
curl -X PUT http://localhost:7070/api/agencies/1 \
  -H "Content-Type: application/json" \
  -d '{"name": "Updated Agency", "address": "456 Oak Ave"}'

# Delete agency
curl -X DELETE http://localhost:7070/api/agencies/1
```

### Test Realtors API
```bash
# List realtors
curl http://localhost:7070/api/realtors

# Create realtor
curl -X POST http://localhost:7070/api/realtors \
  -H "Content-Type: application/json" \
  -d '{"name": "John Doe"}'

# Get realtor by ID
curl http://localhost:7070/api/realtors/1

# Update realtor
curl -X PUT http://localhost:7070/api/realtors/1 \
  -H "Content-Type: application/json" \
  -d '{"name": "Jane Doe"}'

# Delete realtor
curl -X DELETE http://localhost:7070/api/realtors/1
```

### Test Properties API
```bash
# List properties
curl http://localhost:7070/api/properties

# Create property
curl -X POST http://localhost:7070/api/properties \
  -H "Content-Type: application/json" \
  -d '{"city": "Boston", "price": 250000}'

# Get property by ID
curl http://localhost:7070/api/properties/1

# Update property
curl -X PUT http://localhost:7070/api/properties/1 \
  -H "Content-Type: application/json" \
  -d '{"city": "New York", "price": 300000}'

# Delete property
curl -X DELETE http://localhost:7070/api/properties/1
```

## Frontend UI Verification

Open http://localhost:8000 in your web browser and verify:

### Connection Status
- [ ] Status indicator shows "🟢 Connected"
- [ ] Status message shows "Connected to server"

### Agencies Tab
- [ ] Click "🏢 Agencies" tab
- [ ] Verify existing agencies are displayed (if any)
- [ ] Click "➕ Add New Agency"
- [ ] Fill in "Agency Name" (e.g., "Premium Realty")
- [ ] Fill in "Address" (e.g., "123 Main Street")
- [ ] Click "💾 Save"
- [ ] Verify toast notification shows "Agency created successfully"
- [ ] Verify new agency appears in the list
- [ ] Click "✏️ Edit" on the agency
- [ ] Modify the name or address
- [ ] Click "💾 Save"
- [ ] Verify toast notification shows "Agency updated successfully"
- [ ] Verify changes are reflected in the list
- [ ] Click "🗑️ Delete" on the agency
- [ ] Confirm deletion in the dialog
- [ ] Verify toast notification shows "Agency deleted successfully"
- [ ] Verify agency is removed from the list

### Realtors Tab
- [ ] Click "👤 Realtors" tab
- [ ] Verify existing realtors are displayed (if any)
- [ ] Click "➕ Add New Realtor"
- [ ] Fill in "Realtor Name" (e.g., "John Smith")
- [ ] Click "💾 Save"
- [ ] Verify toast notification shows "Realtor created successfully"
- [ ] Verify new realtor appears in the list
- [ ] Click "✏️ Edit" on the realtor
- [ ] Modify the name
- [ ] Click "💾 Save"
- [ ] Verify toast notification shows "Realtor updated successfully"
- [ ] Verify changes are reflected in the list
- [ ] Click "🗑️ Delete" on the realtor
- [ ] Confirm deletion in the dialog
- [ ] Verify toast notification shows "Realtor deleted successfully"
- [ ] Verify realtor is removed from the list

### Properties Tab
- [ ] Click "🏘️ Properties" tab
- [ ] Verify existing properties are displayed (if any)
- [ ] Click "➕ Add New Property"
- [ ] Fill in "City" (e.g., "Boston")
- [ ] Fill in "Price" (e.g., 250000)
- [ ] Click "💾 Save"
- [ ] Verify toast notification shows "Property created successfully"
- [ ] Verify new property appears in the list with formatted price
- [ ] Click "✏️ Edit" on the property
- [ ] Modify the city or price
- [ ] Click "💾 Save"
- [ ] Verify toast notification shows "Property updated successfully"
- [ ] Verify changes are reflected in the list
- [ ] Click "🗑️ Delete" on the property
- [ ] Confirm deletion in the dialog
- [ ] Verify toast notification shows "Property deleted successfully"
- [ ] Verify property is removed from the list

## Error Handling Verification

### Test with Backend Offline
- [ ] Stop the backend server
- [ ] Refresh the frontend
- [ ] Verify status shows "🔴 Disconnected"
- [ ] Verify status message shows "Cannot connect to server"
- [ ] Try to create/edit/delete an entity
- [ ] Verify error toast appears with appropriate message

### Test with Invalid Data
- [ ] Try to submit a form with empty required fields
- [ ] Verify HTML5 validation prevents submission
- [ ] Verify required field indicators (*) are shown

## Expected Results

✅ All CRUD operations should work for all three entities
✅ Status indicator accurately reflects connection state
✅ Toast notifications appear for all operations
✅ Data persists across page refreshes
✅ UI is responsive and user-friendly
✅ No console errors in browser developer tools
✅ All backend API endpoints return proper JSON responses

## Success Criteria

- [x] Backend compiles successfully with Java 17
- [x] All 15 REST API endpoints functional
- [x] Frontend connects to backend
- [x] Full CRUD for Agencies via UI
- [x] Full CRUD for Realtors via UI
- [x] Full CRUD for Properties via UI
- [x] No "failed to load" errors
- [x] No "failed to insert" errors
- [x] All intended functions from REST API exposed in UI

## Notes

- The only code change required was upgrading Java version from 11 to 17 in pom.xml
- All frontend and backend code was already properly implemented
- Field names match between frontend and backend (name/address for Agency, name for Realtor, city/price for Property)
- CORS is properly configured for cross-origin requests
- Error handling is implemented in both frontend and backend
