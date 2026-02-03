import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PropertyDAO {

    public int insertProperty(String city, double price) throws Exception {
        String sql = "INSERT INTO property_listing (city, price) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, city);
            stmt.setDouble(2, price);
            return stmt.executeUpdate();
        }
    }

    public PropertyRecord getPropertyById(int id) throws Exception {
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
        }
        return null;
    }

    public List<PropertyRecord> listProperties() throws Exception {
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
        }
        return list;
    }

    public int updateProperty(int id, String city, double price) throws Exception {
        String sql = "UPDATE property_listing SET city = ?, price = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, city);
            stmt.setDouble(2, price);
            stmt.setInt(3, id);
            return stmt.executeUpdate();
        }
    }

    public int deleteProperty(int id) throws Exception {
        String sql = "DELETE FROM property_listing WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate();
        }
    }
}
