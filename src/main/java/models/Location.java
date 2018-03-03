package models;

public class Location {
    private String address;
    private String country;
    private String code;

    public Location(){};

    public Location(String address, String country, String  code) {
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

    public String getCode() {
        return code;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
