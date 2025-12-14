public class RealEstateAgency {
    private String agencyName;

    public RealEstateAgency(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public void show() {
        System.out.println("Agency: " + agencyName);
    }
}

