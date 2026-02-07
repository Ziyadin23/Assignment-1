#!/bin/bash
#
# Quick Start Script for Real Estate Management System
# This script sets up and starts both backend and frontend servers
#

set -e

echo "==================================="
echo "Real Estate Management System"
echo "Quick Start Setup"
echo "==================================="
echo ""

# Check if PostgreSQL is installed
if ! command -v psql &> /dev/null; then
    echo "❌ PostgreSQL is not installed. Please install PostgreSQL first."
    exit 1
fi

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "❌ Java is not installed. Please install Java 17+ first."
    exit 1
fi

# Check Java version
JAVA_VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | awk -F '.' '{print $1}')
if [ "$JAVA_VERSION" -lt 17 ]; then
    echo "❌ Java 17 or higher is required. Current version: $JAVA_VERSION"
    exit 1
fi

echo "✅ Java version $JAVA_VERSION detected"

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven is not installed. Please install Maven first."
    exit 1
fi

echo "✅ Maven detected"

# Start PostgreSQL
echo ""
echo "📦 Starting PostgreSQL..."
sudo service postgresql start
sleep 2

# Set PostgreSQL password
echo "🔑 Setting up PostgreSQL user..."
sudo -u postgres psql -c "ALTER USER postgres PASSWORD '0000';" 2>/dev/null || true

# Create database schema
echo "🗄️  Creating database schema..."
sudo -u postgres psql -U postgres -d postgres -f db/schema.sql

# Build the project
echo ""
echo "🔨 Building the project..."
mvn clean package -DskipTests -q

# Download dependencies
echo "📥 Downloading dependencies..."
mvn dependency:copy-dependencies -DoutputDirectory=lib -q

echo ""
echo "✅ Build completed successfully!"

# Start backend server
echo ""
echo "🚀 Starting backend server on http://localhost:7070..."
java -cp "target/classes:lib/*" api.RestApiServer > /tmp/backend.log 2>&1 &
BACKEND_PID=$!
echo "Backend PID: $BACKEND_PID"
sleep 3

# Test backend connection
if curl -s http://localhost:7070/api/agencies > /dev/null; then
    echo "✅ Backend server is running"
else
    echo "❌ Backend server failed to start. Check /tmp/backend.log for errors."
    exit 1
fi

# Start frontend server
echo ""
echo "🌐 Starting frontend server on http://localhost:8000..."
cd frontend
python3 -m http.server 8000 > /tmp/frontend.log 2>&1 &
FRONTEND_PID=$!
echo "Frontend PID: $FRONTEND_PID"
cd ..
sleep 2

echo ""
echo "==================================="
echo "✨ Setup Complete!"
echo "==================================="
echo ""
echo "Backend:  http://localhost:7070"
echo "Frontend: http://localhost:8000"
echo ""
echo "Backend PID:  $BACKEND_PID"
echo "Frontend PID: $FRONTEND_PID"
echo ""
echo "To stop the servers, run:"
echo "  kill $BACKEND_PID $FRONTEND_PID"
echo ""
echo "Logs:"
echo "  Backend:  /tmp/backend.log"
echo "  Frontend: /tmp/frontend.log"
echo ""
echo "Open http://localhost:8000 in your browser to use the application."
echo ""
