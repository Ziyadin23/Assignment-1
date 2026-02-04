package dto;

import java.util.Objects;

public class RealtorRecord {
    private int id;
    private String name;

    public RealtorRecord() {}
    public RealtorRecord(int id, String name) {
        this.id = id; this.name = name;
    }
    public RealtorRecord(String name) { this.name = name; }

    public int getId() { return id; }
    public String getName() { return name; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return "RealtorRecord{id=" + id + ", name='" + name + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RealtorRecord)) return false;
        RealtorRecord that = (RealtorRecord) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
