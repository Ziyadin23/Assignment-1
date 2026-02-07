package repository.jdbc;

import config.DatabaseConnection;
import dto.AgencyRecord;
import exceptions.DataAccessException;
import repository.AgencyRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RealEstateAgencyDAO implements AgencyRepository {

    private static final String CREATE_AGENCY_TABLE_SQL = """
            CREATE TABLE IF NOT EXISTS real_estate_agency (
                id SERIAL PRIMARY KEY,
                name VARCHAR(255) NOT NULL,
                address VARCHAR(255) NOT NULL
            )
            """;

    private void ensureAgencyTableExists(Connection conn) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(CREATE_AGENCY_TABLE_SQL)) {
            stmt.execute();
        }
    }

    @Override
    public int insertAgency(String name, String address) {
        String sql = "INSERT INTO real_estate_agency (name, address) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection()) {
            ensureAgencyTableExists(conn);
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, name);
                stmt.setString(2, address);
                return stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to insert agency.", e);
        }
    }

    @Override
    public AgencyRecord getAgencyById(int id) {
        String sql = "SELECT id, name, address FROM real_estate_agency WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            ensureAgencyTableExists(conn);
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return new AgencyRecord(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("address")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to read agency.", e);
        }
        return null;
    }

    @Override
    public List<AgencyRecord> listAgencies() {
        String sql = "SELECT id, name, address FROM real_estate_agency ORDER BY id";
        List<AgencyRecord> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            ensureAgencyTableExists(conn);
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new AgencyRecord(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("address")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to list agencies.", e);
        }
        return list;
    }

    @Override
    public int updateAgency(int id, String name, String address) {
        String sql = "UPDATE real_estate_agency SET name = ?, address = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            ensureAgencyTableExists(conn);
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, name);
                stmt.setString(2, address);
                stmt.setInt(3, id);
                return stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to update agency.", e);
        }
    }

    @Override
    public int deleteAgency(int id) {
        String sql = "DELETE FROM real_estate_agency WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            ensureAgencyTableExists(conn);
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                return stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to delete agency.", e);
        }
    }
}
