import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RealtorDAO {

    public int insertRealtor(String name) throws Exception {
        String sql = "INSERT INTO realtor (name) VALUES (?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            return stmt.executeUpdate();
        }
    }

    public RealtorRecord getRealtorById(int id) throws Exception {
        String sql = "SELECT id, name FROM realtor WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new RealtorRecord(rs.getInt("id"), rs.getString("name"));
                }
            }
        }
        return null;
    }

    public List<RealtorRecord> listRealtors() throws Exception {
        String sql = "SELECT id, name FROM realtor ORDER BY id";
        List<RealtorRecord> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new RealtorRecord(rs.getInt("id"), rs.getString("name")));
            }
        }
        return list;
    }

    public int updateRealtor(int id, String name) throws Exception {
        String sql = "UPDATE realtor SET name = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setInt(2, id);
            return stmt.executeUpdate();
        }
    }

    public int deleteRealtor(int id) throws Exception {
        String sql = "DELETE FROM realtor WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate();
        }
    }
}