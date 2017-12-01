package abish.veettusorudemo.network.request;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import abish.veettusorudemo.network.serializer.OrderSerializer;
import abish.veettusorudemo.network.model.OrderData;

/**
 * Created by Abish on 11/26/2017.
 */

public class OrderRequest implements Parcelable {

    @Expose
    @SerializedName("userID")
    private String userID;

    @Expose
    @SerializedName("userLocation")
    private String userLocation;

    @Expose
    @SerializedName("deliveryTime")
    private String deliveryTime;

    @Expose
    @SerializedName("orderDetail")
    private ArrayList<OrderData> orderDetail;

    public OrderRequest() {
    }

    protected OrderRequest(Parcel in) {
        userID = in.readString();
        userLocation = in.readString();
        deliveryTime = in.readString();
        orderDetail = in.readArrayList(OrderData.class.getClassLoader());
    }

    public static final Creator<OrderRequest> CREATOR = new Creator<OrderRequest>() {
        @Override
        public OrderRequest createFromParcel(Parcel in) {
            return new OrderRequest(in);
        }

        @Override
        public OrderRequest[] newArray(int size) {
            return new OrderRequest[size];
        }
    };

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public List<OrderData> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(ArrayList<OrderData> orderDetail) {
        this.orderDetail = orderDetail;
    }

    public String getOrderRequest(OrderRequest request) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(OrderData.class, new OrderSerializer());
        Gson gson = gsonBuilder.create();
        return gson.toJson(request);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userID);
        parcel.writeString(userLocation);
        parcel.writeString(deliveryTime);
    }
}
