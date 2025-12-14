public class Main {
    public static void main(String[] args) {

        Property p1 = new Property("Astana", 120000);
        Property p2 = new Property("Almaty", 150000);

        Realtor r = new Realtor("Arman");
        RealEstateAgency a = new RealEstateAgency("KazEstate");

        p1.show();
        p2.show();
        r.show();
        a.show();

        if (p1.getPrice() > p2.getPrice()) {
            System.out.println("First property is more expensive");
        } else {
            System.out.println("Second property is more expensive");
        }
    }
}
