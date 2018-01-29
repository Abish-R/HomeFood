package abish.veettusorudemo.network.response;

import com.google.gson.annotations.SerializedName;

import abish.veettusorudemo.model.ResponseSuccessFinder;
import abish.veettusorudemo.network.model.OrderData;

/**
 * Created by Abish on 8/17/2017.
 */

public class OrderResponse implements ResponseSuccessFinder {

    @SerializedName ("status")
    private String status;

    @SerializedName ("response")
    private int response;

    @SerializedName ("orderData")
    private OrderData order;

    public OrderResponse(){

    }

    //@Override
    public String getStatus() {
        return status;
    }

   // @Override
    public void setStatus(String status) {
        this.status = status;
    }

    //@Override
    public int getResponse() {
        return response;
    }

    //@Override
    public void setResponse(int response) {
        this.response = response;
    }

    public OrderData  getorder() {
        return order;
    }

    public void setorder(OrderData response) {
        this.order = order;
    }

    @Override
    public boolean isSuccess() {
        return true;
    }
}
