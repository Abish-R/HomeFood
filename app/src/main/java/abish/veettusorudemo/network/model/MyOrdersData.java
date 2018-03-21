package abish.veettusorudemo.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import abish.veettusorudemo.MyOrdersList;

/**
 * Created by Abish on 3/19/2018.
 * </p>
 */

public class MyOrdersData {

    @SerializedName("order_list")
    private ArrayList<MyOrdersList> orderList;

    @SerializedName("customer_details")
    private MyOrderCustomerDetail customerDetails;

    public MyOrdersData() {

    }

    public ArrayList<MyOrdersList> getOrderList() {
        return orderList;
    }

    public void setOrderList(ArrayList<MyOrdersList> orderList) {
        this.orderList = orderList;
    }

    public MyOrderCustomerDetail getCustomerDetails() {
        return customerDetails;
    }

    public void setCustomerDetails(MyOrderCustomerDetail customerDetails) {
        this.customerDetails = customerDetails;
    }
}
