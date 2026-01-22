import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RealEstateAgencyDAO {

    // CREATE
    public int insertAgency(String name, String address) throws Exception {
        String sql = "INSERT INTO real_estate_agency (name, address) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, address);
            return stmt.executeUpdate();
        }
    }

    // READ as objects (for UI table)
    public List<Agency> listAgencies() throws Exception {
        String sql = "SELECT id, name, address FROM real_estate_agency ORDER BY id";
        List<Agency> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Agency(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("address")
                ));
            }
        }
        return list;
    }

    // OPTIONAL: keep your old console READ for quick checks
    public void getAllAgencies() throws Exception {
        for (Agency a : listAgencies()) {
            System.out.println(a.getId() + " | " + a.getName() + " | " + a.getAddress());
        }
    }

    // UPDATE - update both name and address by id
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

    // DELETE - by id
    public int deleteAgency(int id) throws Exception {
        String sql = "DELETE FROM real_estate_agency WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate();
        }
    }
}