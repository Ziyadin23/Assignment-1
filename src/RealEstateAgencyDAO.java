import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RealEstateAgencyDAO {

    public int insertAgency(String name, String address) throws Exception {
        String sql = "INSERT INTO real_estate_agency (name, address) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, address);
            return stmt.executeUpdate();
        }
    }

    public AgencyRecord getAgencyById(int id) throws Exception {
        String sql = "SELECT id, name, address FROM real_estate_agency WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
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
        return null;
    }

    public List<AgencyRecord> listAgencies() throws Exception {
        String sql = "SELECT id, name, address FROM real_estate_agency ORDER BY id";
        List<AgencyRecord> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new AgencyRecord(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("address")
                ));
            }
        }
        return list;
    }

    public int updateAgency(int id, String name, String address) throws Exception {
        String sql = "UPDATE real_estate_agency SET name = ?, address = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, address);
            stmt.setInt(3, id);
            return stmt.executeUpdate();
        }
    }

    public int deleteAgency(int id) throws Exception {
        String sql = "DELETE FROM real_estate_agency WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate();
        }
    }
}