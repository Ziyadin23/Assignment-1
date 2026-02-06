package repository.jdbc;

import config.DatabaseConnection;
import dto.RealtorRecord;
import exceptions.DataAccessException;
import repository.RealtorRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RealtorDAO implements RealtorRepository {

    private static final String CREATE_REALTOR_TABLE_SQL = """
            CREATE TABLE IF NOT EXISTS realtor (
                id SERIAL PRIMARY KEY,
                name VARCHAR(255) NOT NULL
            )
            """;

    private void ensureRealtorTableExists(Connection conn) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(CREATE_REALTOR_TABLE_SQL)) {
            stmt.execute();
        }
    }

    @Override
    public int insertRealtor(String name) {
        String sql = "INSERT INTO realtor (name) VALUES (?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ensureRealtorTableExists(conn);
            stmt.setString(1, name);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Failed to insert realtor.", e);
        }
    }

    @Override
    public RealtorRecord getRealtorById(int id) {
        String sql = "SELECT id, name FROM realtor WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            ensureRealtorTableExists(conn);
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return new RealtorRecord(rs.getInt("id"), rs.getString("name"));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to read realtor.", e);
        }
        return null;
    }

    @Override
    public List<RealtorRecord> listRealtors() {
        String sql = "SELECT id, name FROM realtor ORDER BY id";
        List<RealtorRecord> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            ensureRealtorTableExists(conn);
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new RealtorRecord(rs.getInt("id"), rs.getString("name")));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to list realtors.", e);
        }
        return list;
    }

    @Override
    public int updateRealtor(int id, String name) {
        String sql = "UPDATE realtor SET name = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ensureRealtorTableExists(conn);
            stmt.setString(1, name);
            stmt.setInt(2, id);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Failed to update realtor.", e);
        }
    }

    @Override
    public int deleteRealtor(int id) {
        String sql = "DELETE FROM realtor WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ensureRealtorTableExists(conn);
            stmt.setInt(1, id);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Failed to delete realtor.", e);
        }
    }
}
