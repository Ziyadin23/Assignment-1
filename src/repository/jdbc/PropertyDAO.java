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

    @Override
    public int insertProperty(String city, double price) {
        String sql = "INSERT INTO property_listing (city, price) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, city);
            stmt.setDouble(2, price);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Failed to insert property.", e);
        }
    }

    @Override
    public PropertyRecord getPropertyById(int id) {
        String sql = "SELECT id, city, price FROM property_listing WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
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
        } catch (SQLException e) {
            throw new DataAccessException("Failed to read property.", e);
        }
        return null;
    }

    @Override
    public List<PropertyRecord> listProperties() {
        String sql = "SELECT id, city, price FROM property_listing ORDER BY id";
        List<PropertyRecord> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new PropertyRecord(
                        rs.getInt("id"),
                        rs.getString("city"),
                        rs.getDouble("price")
                ));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to list properties.", e);
        }
        return list;
    }

    @Override
    public int updateProperty(int id, String city, double price) {
        String sql = "UPDATE property_listing SET city = ?, price = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, city);
            stmt.setDouble(2, price);
            stmt.setInt(3, id);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Failed to update property.", e);
        }
    }

    @Override
    public int deleteProperty(int id) {
        String sql = "DELETE FROM property_listing WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Failed to delete property.", e);
        }
    }
}
