package abish.veettusorudemo.network.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Abish on 8/17/2017.
 */

public class FoodCategory implements Parcelable {

    @SerializedName ("id")
    private String id;

    @SerializedName("category_name")
    private String categoryName;

    @SerializedName("order_timing")
    private String orderTiming;

    @SerializedName("added_date")
    private String addedDate;

    protected FoodCategory(Parcel in) {
        id = in.readString();
        categoryName = in.readString();
        orderTiming = in.readString();
        addedDate = in.readString();
    }

    public static final Creator<FoodCategory> CREATOR = new Creator<FoodCategory>() {
        @Override
        public FoodCategory createFromParcel(Parcel in) {
            return new FoodCategory(in);
        }

        @Override
        public FoodCategory[] newArray(int size) {
            return new FoodCategory[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getOrderTiming() {
        return orderTiming;
    }

    public void setOrderTiming(String orderTiming) {
        this.orderTiming = orderTiming;
    }

    public String getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(categoryName);
        parcel.writeString(orderTiming);
        parcel.writeString(addedDate);
    }
}
