# Real Estate Management System - Frontend

A modern, responsive web frontend for the Real Estate Management System REST API.

## Features

- ✅ **Full CRUD Operations** for Agencies, Realtors, and Properties
- ✅ **Real-time API Connection Status** monitoring
- ✅ **Beautiful Modern UI** with gradient design
- ✅ **Responsive Design** - works on desktop and mobile
- ✅ **Error Handling** with user-friendly toast notifications
- ✅ **Tab-based Navigation** for easy switching between entities
- ✅ **Form Validation** for data integrity
- ✅ **Confirmation Dialogs** for delete operations

## How to Use

### 1. Start the Backend API Server

Make sure the REST API server is running on `http://localhost:7070`

```bash
cd /home/runner/work/Assignment-1/Assignment-1
java -cp "target/classes:lib/*" api.RestApiServer
```

### 2. Open the Frontend

Open `index.html` in your web browser:

```bash
# Option 1: Direct file open
open frontend/index.html  # macOS
xdg-open frontend/index.html  # Linux
start frontend/index.html  # Windows

# Option 2: Using Python's HTTP server (recommended)
cd frontend
python3 -m http.server 8000
# Then open http://localhost:8000 in your browser
```

### 3. Use the Application

**Connection Status**: The top bar shows the API connection status
- 🟢 Green = Connected
- 🔴 Red = Disconnected

**Managing Agencies**:
1. Click "Agencies" tab
2. Click "+ Add New Agency" to create
3. Fill in name and address
4. Use Edit/Delete buttons on each card

**Managing Realtors**:
1. Click "Realtors" tab
2. Click "+ Add New Realtor" to create
3. Fill in name
4. Use Edit/Delete buttons on each card

**Managing Properties**:
1. Click "Properties" tab
2. Click "+ Add New Property" to create
3. Fill in city and price
4. Use Edit/Delete buttons on each card

## Technologies Used

- **HTML5** - Semantic markup
- **CSS3** - Modern styling with gradients and animations
- **JavaScript (ES6+)** - Async/await, Fetch API
- **No external dependencies** - Pure vanilla JavaScript

## API Integration

The frontend connects to the REST API at:
- Base URL: `http://localhost:7070/api`
- CORS is enabled on the backend
- All operations use proper HTTP methods (GET, POST, PUT, DELETE)

## Error Handling

- Connection failures are detected and displayed
- API errors show user-friendly messages
- Form validation prevents invalid submissions
- Toast notifications confirm successful operations

## Security Features

- XSS prevention with HTML escaping
- Input validation on forms
- Confirmation dialogs for destructive operations
- CORS properly configured

## Browser Compatibility

Works on all modern browsers:
- Chrome/Edge (latest)
- Firefox (latest)
- Safari (latest)
- Opera (latest)
