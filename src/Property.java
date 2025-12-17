public class Property {
    private String city;
    private double price;

    public Property(String city, double price) {
        this.city = city;
        this.price = price;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void show() {
        System.out.println(city + " " + price);
    }
}

