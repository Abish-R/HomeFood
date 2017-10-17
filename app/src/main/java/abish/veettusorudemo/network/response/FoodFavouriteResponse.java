package abish.veettusorudemo.network.response;

import com.google.gson.annotations.SerializedName;

import abish.veettusorudemo.model.ResponseSuccessFinder;
import abish.veettusorudemo.model.StatusResponseHandler;

/**
 * Created by Abish on 8/17/2017.
 */

public class FoodFavouriteResponse implements StatusResponseHandler, ResponseSuccessFinder {

    @SerializedName("status")
    private String status;

    @SerializedName("response")
    private String response;


    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String getResponse() {
        return response;
    }

    @Override
    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public boolean isSuccess() {
        return "1".equals(getResponse());
    }
}
