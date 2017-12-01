package abish.veettusorudemo.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import abish.veettusorudemo.model.ResponseSuccessFinder;
import abish.veettusorudemo.network.model.AddressData;

/**
 * Created by Abish on 8/17/2017.
 */

public class AddressResponse implements ResponseSuccessFinder {

    @SerializedName ("status")
    private String status;

    @SerializedName ("response")
    private String response;

    @SerializedName ("address")
    private List<AddressData> addressList;

    public AddressResponse(){

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<AddressData> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<AddressData> addressList) {
        this.addressList = addressList;
    }

    @Override
    public boolean isSuccess() {
        return "1".equals(getResponse());
    }
}
