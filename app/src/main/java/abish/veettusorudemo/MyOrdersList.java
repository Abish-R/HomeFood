package abish.veettusorudemo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abish on 12/5/2017.
 * </p>
 */

public class MyOrdersList implements Parcelable {

    @SerializedName("order_id")
    private int orderId;

    @SerializedName("address")
    private String address;

    @SerializedName("delivery_date_time")
    private String deliveryDateTime;

    @SerializedName("order_date_time")
    private String orderDateTime;

    @SerializedName("order_status")
    private String orderStatus;

    @SerializedName("total_amount")
    private int totalAmount;

    @SerializedName("discount_amount")
    private int discountAmount;

    @SerializedName("final_amount")
    private int finalAmount;

    @SerializedName("order_details")
    private List<OrderDetail> orderDetail;

    private transient String totalQty;
    private transient String nameUser;
    private transient String phone;

    public MyOrdersList() {
        orderDetail = new ArrayList<>();
    }

    protected MyOrdersList(Parcel in) {
        orderId = in.readInt();
        address = in.readString();
        deliveryDateTime = in.readString();
        orderDateTime = in.readString();
        orderStatus = in.readString();
        totalAmount = in.readInt();
        discountAmount = in.readInt();
        finalAmount = in.readInt();
        totalQty = in.readString();
        orderDetail = new ArrayList<OrderDetail>();
        in.readTypedList(orderDetail, OrderDetail.CREATOR);
        nameUser = in.readString();
        phone = in.readString();
    }

    public static final Creator<MyOrdersList> CREATOR = new Creator<MyOrdersList>() {
        @Override
        public MyOrdersList createFromParcel(Parcel in) {
            return new MyOrdersList(in);
        }

        @Override
        public MyOrdersList[] newArray(int size) {
            return new MyOrdersList[size];
        }
    };

    public List<OrderDetail> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<OrderDetail> orderDetail) {
        this.orderDetail = orderDetail;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDeliveryDateTime() {
        return deliveryDateTime;
    }

    public void setDeliveryDateTime(String deliveryDateTime) {
        this.deliveryDateTime = deliveryDateTime;
    }

    public String getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(String orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getTotalAmount() {
        return totalAmount + "";
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDiscountAmount() {
        return discountAmount + "";
    }

    public void setDiscountAmount(int discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getFinalAmount() {
        return finalAmount + "";
    }

    public void setFinalAmount(int finalAmount) {
        this.finalAmount = finalAmount;
    }

    public String getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(String totalQty) {
        this.totalQty = totalQty;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = totalQty;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = totalQty;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(orderId);
        parcel.writeString(address);
        parcel.writeString(deliveryDateTime);
        parcel.writeString(orderDateTime);
        parcel.writeString(orderStatus);
        parcel.writeInt(totalAmount);
        parcel.writeInt(discountAmount);
        parcel.writeInt(finalAmount);
        parcel.writeString(totalQty);
        parcel.writeTypedList(orderDetail);
        parcel.writeString(nameUser);
        parcel.writeString(phone);
    }
}
