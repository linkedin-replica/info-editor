package models;

public class Location {
    private String address;
    private String country;
    private int code;

    public Location(String address, String country, int code) {
        this.address = address;
        this.country = country;
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public String getCountry() {
        return country;
    }

    public int getCode() {
        return code;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
