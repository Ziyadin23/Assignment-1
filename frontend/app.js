// API Configuration
const API_BASE_URL = 'http://localhost:7070/api';

// Global State
let currentTab = 'agencies';

// Initialize app
document.addEventListener('DOMContentLoaded', () => {
    checkServerConnection();
    loadAgencies();
});

// Server Connection Check
async function checkServerConnection() {
    try {
        const response = await fetch(`${API_BASE_URL}/agencies`);
        if (response.ok) {
            updateStatus(true, 'Connected to server');
        } else {
            updateStatus(false, 'Server error');
        }
    } catch (error) {
        updateStatus(false, 'Cannot connect to server');
    }
}

function updateStatus(connected, message) {
    const statusIndicator = document.getElementById('apiStatus');
    const statusMessage = document.getElementById('statusMessage');
    
    if (connected) {
        statusIndicator.textContent = '🟢 Connected';
        statusIndicator.style.color = '#28a745';
    } else {
        statusIndicator.textContent = '🔴 Disconnected';
        statusIndicator.style.color = '#dc3545';
    }
    
    statusMessage.textContent = message;
}

// Tab Management
function showTab(tabName, button) {
    // Hide all tabs
    document.querySelectorAll('.tab-content').forEach(tab => {
        tab.classList.remove('active');
    });
    document.querySelectorAll('.tab-button').forEach(button => {
        button.classList.remove('active');
    });
    
    // Show selected tab
    document.getElementById(`${tabName}-tab`).classList.add('active');
    if (button) {
        button.classList.add('active');
    }
    
    currentTab = tabName;
    
    // Load data for the selected tab
    if (tabName === 'agencies') {
        loadAgencies();
    } else if (tabName === 'realtors') {
        loadRealtors();
    } else if (tabName === 'properties') {
        loadProperties();
    }
}

// Toast Notifications
function showToast(message, type = 'info') {
    const toast = document.getElementById('toast');
    toast.textContent = message;
    toast.className = `toast ${type} show`;
    
    setTimeout(() => {
        toast.classList.remove('show');
    }, 3000);
}

// ==================== AGENCIES ====================

async function loadAgencies() {
    const listContainer = document.getElementById('agenciesList');
    listContainer.innerHTML = '<div class="loading">Loading agencies...</div>';
    
    try {
        const response = await fetch(`${API_BASE_URL}/agencies`);
        if (!response.ok) throw new Error('Failed to load agencies');
        
        const agencies = await response.json();
        
        if (agencies.length === 0) {
            listContainer.innerHTML = `
                <div class="empty-state">
                    <h3>No agencies found</h3>
                    <p>Click "Add New Agency" to create your first agency</p>
                </div>
            `;
            return;
        }
        
        listContainer.innerHTML = agencies.map(agency => `
            <div class="data-card">
                <h3>🏢 ${escapeHtml(agency.name)}</h3>
                <p><strong>ID:</strong> ${agency.id}</p>
                <p><strong>Address:</strong> ${escapeHtml(agency.address)}</p>
                <div class="card-actions">
                    <button class="btn btn-success" onclick="editAgency(${agency.id})">✏️ Edit</button>
                    <button class="btn btn-danger" onclick="deleteAgency(${agency.id})">🗑️ Delete</button>
                </div>
            </div>
        `).join('');
        
    } catch (error) {
        listContainer.innerHTML = `
            <div class="empty-state">
                <h3>❌ Error loading agencies</h3>
                <p>${error.message}</p>
            </div>
        `;
        showToast('Failed to load agencies', 'error');
    }
}

function showCreateAgencyForm() {
    document.getElementById('agencyFormTitle').textContent = 'Create New Agency';
    document.getElementById('agencyId').value = '';
    document.getElementById('agencyName').value = '';
    document.getElementById('agencyAddress').value = '';
    document.getElementById('agencyForm').style.display = 'block';
}

function cancelAgencyForm() {
    document.getElementById('agencyForm').style.display = 'none';
}

async function saveAgency(event) {
    event.preventDefault();
    
    const id = document.getElementById('agencyId').value;
    const name = document.getElementById('agencyName').value;
    const address = document.getElementById('agencyAddress').value;
    
    const data = { name, address };
    
    try {
        let response;
        if (id) {
            // Update
            response = await fetch(`${API_BASE_URL}/agencies/${id}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(data)
            });
        } else {
            // Create
            response = await fetch(`${API_BASE_URL}/agencies`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(data)
            });
        }
        
        const result = await response.json();
        
        if (response.ok && result.success) {
            showToast(id ? 'Agency updated successfully' : 'Agency created successfully', 'success');
            cancelAgencyForm();
            loadAgencies();
        } else {
            showToast(result.error || 'Operation failed', 'error');
        }
    } catch (error) {
        showToast('Error saving agency: ' + error.message, 'error');
    }
}

async function editAgency(id) {
    try {
        const response = await fetch(`${API_BASE_URL}/agencies/${id}`);
        const agency = await response.json();
        
        if (response.ok) {
            document.getElementById('agencyFormTitle').textContent = 'Edit Agency';
            document.getElementById('agencyId').value = agency.id;
            document.getElementById('agencyName').value = agency.name;
            document.getElementById('agencyAddress').value = agency.address;
            document.getElementById('agencyForm').style.display = 'block';
        } else {
            showToast(agency.error || 'Failed to load agency', 'error');
        }
    } catch (error) {
        showToast('Error loading agency: ' + error.message, 'error');
    }
}

async function deleteAgency(id) {
    if (!confirm('Are you sure you want to delete this agency?')) {
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE_URL}/agencies/${id}`, {
            method: 'DELETE'
        });
        
        const result = await response.json();
        
        if (response.ok && result.success) {
            showToast('Agency deleted successfully', 'success');
            loadAgencies();
        } else {
            showToast(result.error || 'Failed to delete agency', 'error');
        }
    } catch (error) {
        showToast('Error deleting agency: ' + error.message, 'error');
    }
}

// ==================== REALTORS ====================

async function loadRealtors() {
    const listContainer = document.getElementById('realtorsList');
    listContainer.innerHTML = '<div class="loading">Loading realtors...</div>';
    
    try {
        const response = await fetch(`${API_BASE_URL}/realtors`);
        if (!response.ok) throw new Error('Failed to load realtors');
        
        const realtors = await response.json();
        
        if (realtors.length === 0) {
            listContainer.innerHTML = `
                <div class="empty-state">
                    <h3>No realtors found</h3>
                    <p>Click "Add New Realtor" to create your first realtor</p>
                </div>
            `;
            return;
        }
        
        listContainer.innerHTML = realtors.map(realtor => `
            <div class="data-card">
                <h3>👤 ${escapeHtml(realtor.name)}</h3>
                <p><strong>ID:</strong> ${realtor.id}</p>
                <div class="card-actions">
                    <button class="btn btn-success" onclick="editRealtor(${realtor.id})">✏️ Edit</button>
                    <button class="btn btn-danger" onclick="deleteRealtor(${realtor.id})">🗑️ Delete</button>
                </div>
            </div>
        `).join('');
        
    } catch (error) {
        listContainer.innerHTML = `
            <div class="empty-state">
                <h3>❌ Error loading realtors</h3>
                <p>${error.message}</p>
            </div>
        `;
        showToast('Failed to load realtors', 'error');
    }
}

function showCreateRealtorForm() {
    document.getElementById('realtorFormTitle').textContent = 'Create New Realtor';
    document.getElementById('realtorId').value = '';
    document.getElementById('realtorName').value = '';
    document.getElementById('realtorForm').style.display = 'block';
}

function cancelRealtorForm() {
    document.getElementById('realtorForm').style.display = 'none';
}

async function saveRealtor(event) {
    event.preventDefault();
    
    const id = document.getElementById('realtorId').value;
    const name = document.getElementById('realtorName').value;
    
    const data = { name };
    
    try {
        let response;
        if (id) {
            response = await fetch(`${API_BASE_URL}/realtors/${id}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(data)
            });
        } else {
            response = await fetch(`${API_BASE_URL}/realtors`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(data)
            });
        }
        
        const result = await response.json();
        
        if (response.ok && result.success) {
            showToast(id ? 'Realtor updated successfully' : 'Realtor created successfully', 'success');
            cancelRealtorForm();
            loadRealtors();
        } else {
            showToast(result.error || 'Operation failed', 'error');
        }
    } catch (error) {
        showToast('Error saving realtor: ' + error.message, 'error');
    }
}

async function editRealtor(id) {
    try {
        const response = await fetch(`${API_BASE_URL}/realtors/${id}`);
        const realtor = await response.json();
        
        if (response.ok) {
            document.getElementById('realtorFormTitle').textContent = 'Edit Realtor';
            document.getElementById('realtorId').value = realtor.id;
            document.getElementById('realtorName').value = realtor.name;
            document.getElementById('realtorForm').style.display = 'block';
        } else {
            showToast(realtor.error || 'Failed to load realtor', 'error');
        }
    } catch (error) {
        showToast('Error loading realtor: ' + error.message, 'error');
    }
}

async function deleteRealtor(id) {
    if (!confirm('Are you sure you want to delete this realtor?')) {
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE_URL}/realtors/${id}`, {
            method: 'DELETE'
        });
        
        const result = await response.json();
        
        if (response.ok && result.success) {
            showToast('Realtor deleted successfully', 'success');
            loadRealtors();
        } else {
            showToast(result.error || 'Failed to delete realtor', 'error');
        }
    } catch (error) {
        showToast('Error deleting realtor: ' + error.message, 'error');
    }
}

// ==================== PROPERTIES ====================

async function loadProperties() {
    const listContainer = document.getElementById('propertiesList');
    listContainer.innerHTML = '<div class="loading">Loading properties...</div>';
    
    try {
        const response = await fetch(`${API_BASE_URL}/properties`);
        if (!response.ok) {
            const errorBody = await response.text();
            console.error('Failed to load properties - Status:', response.status, 'Response:', errorBody);
            throw new Error(`Failed to load properties (${response.status}): ${errorBody}`);
        }
        
        const properties = await response.json();
        
        if (properties.length === 0) {
            listContainer.innerHTML = `
                <div class="empty-state">
                    <h3>No properties found</h3>
                    <p>Click "Add New Property" to create your first property</p>
                </div>
            `;
            return;
        }
        
        listContainer.innerHTML = properties.map(property => `
            <div class="data-card">
                <h3>🏘️ Property in ${escapeHtml(property.city)}</h3>
                <p><strong>ID:</strong> ${property.id}</p>
                <p><strong>City:</strong> ${escapeHtml(property.city)}</p>
                <p><strong>Price:</strong> $${formatNumber(property.price)}</p>
                <div class="card-actions">
                    <button class="btn btn-success" onclick="editProperty(${property.id})">✏️ Edit</button>
                    <button class="btn btn-danger" onclick="deleteProperty(${property.id})">🗑️ Delete</button>
                </div>
            </div>
        `).join('');
        
    } catch (error) {
        console.error('Error in loadProperties:', error);
        listContainer.innerHTML = `
            <div class="empty-state">
                <h3>❌ Error loading properties</h3>
                <p>${error.message}</p>
            </div>
        `;
        showToast('Failed to load properties', 'error');
    }
}

function showCreatePropertyForm() {
    document.getElementById('propertyFormTitle').textContent = 'Create New Property';
    document.getElementById('propertyId').value = '';
    document.getElementById('propertyCity').value = '';
    document.getElementById('propertyPrice').value = '';
    document.getElementById('propertyForm').style.display = 'block';
}

function cancelPropertyForm() {
    document.getElementById('propertyForm').style.display = 'none';
}

async function saveProperty(event) {
    event.preventDefault();
    
    const id = document.getElementById('propertyId').value;
    const city = document.getElementById('propertyCity').value;
    const price = parseFloat(document.getElementById('propertyPrice').value);
    
    const data = { city, price };
    
    console.log('Saving property:', data);
    
    try {
        let response;
        if (id) {
            response = await fetch(`${API_BASE_URL}/properties/${id}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(data)
            });
        } else {
            response = await fetch(`${API_BASE_URL}/properties`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(data)
            });
        }
        
        console.log('Response status:', response.status);
        
        const result = await response.json();
        console.log('Response body:', result);
        
        if (response.ok && result.success) {
            showToast(id ? 'Property updated successfully' : 'Property created successfully', 'success');
            cancelPropertyForm();
            loadProperties();
        } else {
            const errorMsg = result.error || 'Operation failed';
            console.error('Operation failed:', errorMsg);
            showToast(errorMsg, 'error');
        }
    } catch (error) {
        console.error('Error saving property:', error);
        showToast('Error saving property: ' + error.message, 'error');
    }
}

async function editProperty(id) {
    try {
        const response = await fetch(`${API_BASE_URL}/properties/${id}`);
        console.log('Edit property response status:', response.status);
        
        const property = await response.json();
        console.log('Edit property response body:', property);
        
        if (response.ok) {
            document.getElementById('propertyFormTitle').textContent = 'Edit Property';
            document.getElementById('propertyId').value = property.id;
            document.getElementById('propertyCity').value = property.city;
            document.getElementById('propertyPrice').value = property.price;
            document.getElementById('propertyForm').style.display = 'block';
        } else {
            const errorMsg = property.error || 'Failed to load property';
            console.error('Failed to load property:', errorMsg);
            showToast(errorMsg, 'error');
        }
    } catch (error) {
        console.error('Error loading property:', error);
        showToast('Error loading property: ' + error.message, 'error');
    }
}

async function deleteProperty(id) {
    if (!confirm('Are you sure you want to delete this property?')) {
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE_URL}/properties/${id}`, {
            method: 'DELETE'
        });
        
        console.log('Delete property response status:', response.status);
        
        const result = await response.json();
        console.log('Delete property response body:', result);
        
        if (response.ok && result.success) {
            showToast('Property deleted successfully', 'success');
            loadProperties();
        } else {
            const errorMsg = result.error || 'Failed to delete property';
            console.error('Failed to delete property:', errorMsg);
            showToast(errorMsg, 'error');
        }
    } catch (error) {
        console.error('Error deleting property:', error);
        showToast('Error deleting property: ' + error.message, 'error');
    }
}

// ==================== UTILITIES ====================

function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

function formatNumber(num) {
    return new Intl.NumberFormat('en-US', {
        minimumFractionDigits: 2,
        maximumFractionDigits: 2
    }).format(num);
}
