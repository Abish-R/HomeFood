package abish.veettusorudemo.network.response;

import com.google.gson.annotations.SerializedName;

import abish.veettusorudemo.model.ResponseSuccessFinder;
import abish.veettusorudemo.network.model.MyOrdersData;

/**
 * Created by Abish on 3/19/2018.
 * </p>
 */

public class MyOrdersResponse implements ResponseSuccessFinder {

    @SerializedName("status")
    private String status;

    @SerializedName ("response")
    private int response;

    @SerializedName ("orders")
    private MyOrdersData myOrdersData;

    public MyOrdersResponse(){

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getResponse() {
        return response;
    }

    public void setResponse(int response) {
        this.response = response;
    }

    public MyOrdersData getMyOrdersData() {
        return myOrdersData;
    }

    public void setMyOrdersData(MyOrdersData myOrdersData) {
        this.myOrdersData = myOrdersData;
    }

    @Override
    public boolean isSuccess() {
        return false;
    }
}
