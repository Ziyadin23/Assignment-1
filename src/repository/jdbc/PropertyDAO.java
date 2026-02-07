package repository.jdbc;

import config.DatabaseConnection;
import dto.PropertyRecord;
import exceptions.DataAccessException;
import repository.PropertyRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PropertyDAO implements PropertyRepository {

    private static final String CREATE_PROPERTY_TABLE_SQL = 
            "CREATE TABLE IF NOT EXISTS property (" +
            "id SERIAL PRIMARY KEY, " +
            "city VARCHAR(120) NOT NULL, " +
            "price NUMERIC(12, 2) NOT NULL" +
            ")";

    private static final String PROPERTY_TABLE = "property";
    private static final String LEGACY_PROPERTY_TABLE = "property_listing";

    private void ensurePropertyTableExists(Connection conn) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(CREATE_PROPERTY_TABLE_SQL)) {
            stmt.execute();
        }
    }

    private String resolvePropertyTable(Connection conn) throws SQLException {
        String sql = "SELECT to_regclass('public.property') AS property_table, to_regclass('public.property_listing') AS legacy_table";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                if (rs.getString("property_table") != null) {
                    return PROPERTY_TABLE;
                }
                if (rs.getString("legacy_table") != null) {
                    return LEGACY_PROPERTY_TABLE;
                }
            }
        }
        ensurePropertyTableExists(conn);
        return PROPERTY_TABLE;
    }

    @Override
    public int insertProperty(String city, double price) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String tableName = resolvePropertyTable(conn);
            String sql = "INSERT INTO " + tableName + " (city, price) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, city);
                stmt.setDouble(2, price);
                return stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to insert property.", e);
        }
    }

    @Override
    public PropertyRecord getPropertyById(int id) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String tableName = resolvePropertyTable(conn);
            String sql = "SELECT id, city, price FROM " + tableName + " WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return new PropertyRecord(
                                rs.getInt("id"),
                                rs.getString("city"),
                                rs.getDouble("price")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to read property.", e);
        }
        return null;
    }

    @Override
    public List<PropertyRecord> listProperties() {
        List<PropertyRecord> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String tableName = resolvePropertyTable(conn);
            String sql = "SELECT id, city, price FROM " + tableName + " ORDER BY id";
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new PropertyRecord(
                            rs.getInt("id"),
                            rs.getString("city"),
                            rs.getDouble("price")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to list properties.", e);
        }
        return list;
    }

    @Override
    public int updateProperty(int id, String city, double price) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String tableName = resolvePropertyTable(conn);
            String sql = "UPDATE " + tableName + " SET city = ?, price = ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, city);
                stmt.setDouble(2, price);
                stmt.setInt(3, id);
                return stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to update property.", e);
        }
    }

    @Override
    public int deleteProperty(int id) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String tableName = resolvePropertyTable(conn);
            String sql = "DELETE FROM " + tableName + " WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                return stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to delete property.", e);
        }
    }
}
