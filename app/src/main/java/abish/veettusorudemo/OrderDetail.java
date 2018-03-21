package abish.veettusorudemo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Abish on 12/5/2017.
 * </p>
 */

public class OrderDetail implements Parcelable {

    @SerializedName("course_id")
    private String courseId;

    @SerializedName("quantity")
    private String foodQuantity;

    @SerializedName("offer")
    private String offer;

    @SerializedName("type")
    private String type;

    @SerializedName("short_name")
    private String shortName;

    @SerializedName("food_name")
    private String foodName;

    @SerializedName("amount")
    private String amount;

    @SerializedName("price")
    private String price;

    protected OrderDetail(Parcel in) {
        courseId = in.readString();
        foodQuantity = in.readString();
        offer = in.readString();
        type = in.readString();
        shortName = in.readString();
        foodName = in.readString();
        amount = in.readString();
        price = in.readString();
    }

    public static final Creator<OrderDetail> CREATOR = new Creator<OrderDetail>() {
        @Override
        public OrderDetail createFromParcel(Parcel in) {
            return new OrderDetail(in);
        }

        @Override
        public OrderDetail[] newArray(int size) {
            return new OrderDetail[size];
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

    public String getOfferId() {
        return offer;
    }

    public void setOfferId(String offerId) {
        this.offer = offer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(courseId);
        parcel.writeString(foodQuantity);
        parcel.writeString(offer);
        parcel.writeString(type);
        parcel.writeString(shortName);
        parcel.writeString(foodName);
        parcel.writeString(amount);
        parcel.writeString(price);
    }
}
