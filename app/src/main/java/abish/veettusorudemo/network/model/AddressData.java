package abish.veettusorudemo.network.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Abish on 10/19/2017.
 * </p>
 */

public class AddressData implements Parcelable {

    @SerializedName("type")
    private String addressType;

    @SerializedName("add_id")
    private String addressId;

    @SerializedName("email")
    private String email;

    @SerializedName("mobile")
    private String mobile;

    @SerializedName("street")
    private String street;

    @SerializedName("city")
    private String city;

    @SerializedName("state")
    private String state;

    @SerializedName("country")
    private String country;

    @SerializedName("pincode")
    private String pinCode;

    private boolean isChecked;

    public AddressData() {

    }

    protected AddressData(Parcel in) {
        addressType = in.readString();
        addressId = in.readString();
        email = in.readString();
        mobile = in.readString();
        street = in.readString();
        city = in.readString();
        state = in.readString();
        country = in.readString();
        pinCode = in.readString();
        isChecked = in.readByte() != 0;
    }

    public static final Creator<AddressData> CREATOR = new Creator<AddressData>() {
        @Override
        public AddressData createFromParcel(Parcel in) {
            return new AddressData(in);
        }

        @Override
        public AddressData[] newArray(int size) {
            return new AddressData[size];
        }
    };

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String type) {
        this.addressType = type;
    }

    public String getAddressId() {
        return addressId != null ? addressId : "null";
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPincode() {
        return pinCode;
    }

    public void setPincode(String pincode) {
        this.pinCode = pincode;
    }

    public String getAddress() {
        return street + "\n" + city + "\n" +
                state + ", " + country + "\n" +
                "Pin : " + pinCode;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(addressType);
        parcel.writeString(addressId);
        parcel.writeString(email);
        parcel.writeString(mobile);
        parcel.writeString(street);
        parcel.writeString(city);
        parcel.writeString(state);
        parcel.writeString(country);
        parcel.writeString(pinCode);
        parcel.writeByte((byte) (isChecked ? 1 : 0));
    }
}
