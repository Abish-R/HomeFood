package abish.veettusorudemo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Abish on 12/5/2017.
 * </p>
 */

public class MyOrdersList {

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
    private List<OrderDetail> orderDetail = null;

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

    public int getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(int discountAmount) {
        this.discountAmount = discountAmount;
    }

    public int getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(int finalAmount) {
        this.finalAmount = finalAmount;
    }
}
