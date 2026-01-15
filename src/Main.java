public class Main{
    public static void main(String[] args) throws Exception {

        RealEstateAgencyDAO dao = new RealEstateAgencyDAO();

        // CREATE
        dao.insertAgency("Dream Homes", "Almaty");

        // READ
        dao.getAllAgencies();
    }

}


