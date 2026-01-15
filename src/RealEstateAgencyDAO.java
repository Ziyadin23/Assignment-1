import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RealEstateAgencyDAO {

    // INSERT
    public void insertAgency(String name, String address) throws Exception {
        String sql = "INSERT INTO real_estate_agency (name, address) VALUES (?, ?)";

        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, name);
        stmt.setString(2, address);

        stmt.executeUpdate();
        conn.close();
    }

    // READ
    public void getAllAgencies() throws Exception {
        String sql = "SELECT * FROM real_estate_agency";

        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            System.out.println(
                    rs.getInt("id") + " | " +
                            rs.getString("name") + " | " +
                            rs.getString("address")
            );
        }

        conn.close();
    }
}
