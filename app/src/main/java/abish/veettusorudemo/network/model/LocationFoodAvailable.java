package abish.veettusorudemo.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Abish on 8/12/2017.
 */

public class LocationFoodAvailable {

    @SerializedName("id")
    private int id;

    @SerializedName("country")
    private String country;

    @SerializedName("state")
    private String state;

    @SerializedName("district")
    private String district;

    @SerializedName("city")
    private String city;

    @SerializedName("area")
    private String area;

    @SerializedName("street")
    private String street;

    @SerializedName("landmark")
    private String landmark;

    @SerializedName("pincode")
    private String pincode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}
