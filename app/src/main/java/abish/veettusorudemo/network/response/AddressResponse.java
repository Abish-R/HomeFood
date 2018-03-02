package abish.veettusorudemo.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import abish.veettusorudemo.model.ResponseSuccessFinder;
import abish.veettusorudemo.network.model.AddressData;

/**
 * Created by Abish on 8/17/2017.
 * </p>
 */

public class AddressResponse implements ResponseSuccessFinder {

    @SerializedName("status")
    private String status;

    @SerializedName("response")
    private int response;

    @SerializedName("address")
    private List<AddressData> addressList;

    @SerializedName("deliveryTime")
    private List<String> deliveryTime;

    public AddressResponse() {

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

    public List<AddressData> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<AddressData> addressList) {
        this.addressList = addressList;
    }

    public List<String> getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(List<String> deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    @Override
    public boolean isSuccess() {
        return 1 == getResponse();
    }
}
