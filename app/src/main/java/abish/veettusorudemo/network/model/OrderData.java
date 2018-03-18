package abish.veettusorudemo.network.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Abish on 11/26/2017.
 * </p>
 */

public class OrderData implements Parcelable {

    @SerializedName("courseId")
    private String courseId;

    @SerializedName("foodQuantity")
    private String foodQuantity;

    @SerializedName("offer_id")
    private String offer_id;

    @SerializedName("type")
    private String type;

    public OrderData(){
    }

    protected OrderData(Parcel in) {
        courseId = in.readString();
        foodQuantity = in.readString();
        offer_id = in.readString();
        type = in.readString();
    }

    public static final Creator<OrderData> CREATOR = new Creator<OrderData>() {
        @Override
        public OrderData createFromParcel(Parcel in) {
            return new OrderData(in);
        }

        @Override
        public OrderData[] newArray(int size) {
            return new OrderData[size];
        }
    };

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getFoodQuantity() {
        return foodQuantity;
    }

    public void setFoodQuantity(String foodQuantity) {
        this.foodQuantity = foodQuantity;
    }

    public String getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(String offer_id) {
        this.offer_id = offer_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(courseId);
        parcel.writeString(foodQuantity);
        parcel.writeString(offer_id);
        parcel.writeString(type);
    }
}
