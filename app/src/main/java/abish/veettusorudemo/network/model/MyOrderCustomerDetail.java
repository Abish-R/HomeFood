package abish.veettusorudemo.network.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Abish on 12/5/2017.
 * </p>
 */

public class MyOrderCustomerDetail implements Parcelable {

    @SerializedName("customer")
    private String nameUser;

    @SerializedName("phone")
    private String phone;

    public MyOrderCustomerDetail() {
    }

    protected MyOrderCustomerDetail(Parcel in) {
        nameUser = in.readString();
        phone = in.readString();
    }

    public static final Creator<MyOrderCustomerDetail> CREATOR = new Creator<MyOrderCustomerDetail>() {
        @Override
        public MyOrderCustomerDetail createFromParcel(Parcel in) {
            return new MyOrderCustomerDetail(in);
        }

        @Override
        public MyOrderCustomerDetail[] newArray(int size) {
            return new MyOrderCustomerDetail[size];
        }
    };

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String address) {
        this.nameUser = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String deliveryDateTime) {
        this.phone = deliveryDateTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nameUser);
        parcel.writeString(phone);
    }
}
