package dto;

import java.util.Objects;

public class AgencyRecord {
    private int id;
    private String name;
    private String address;

    public AgencyRecord() {}
    public AgencyRecord(int id, String name, String address) {
        this.id = id; this.name = name; this.address = address;
    }
    public AgencyRecord(String name, String address) {
        this.name = name; this.address = address;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getAddress() { return address; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setAddress(String address) { this.address = address; }

    @Override
    public String toString() {
        return "AgencyRecord{id=" + id + ", name='" + name + "', address='" + address + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AgencyRecord)) return false;
        AgencyRecord that = (AgencyRecord) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
