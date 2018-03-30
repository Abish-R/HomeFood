package abish.veettusorudemo.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import abish.veettusorudemo.model.ResponseSuccessFinder;
import abish.veettusorudemo.network.model.FoodDetail;

/**
 * Created by Abish on 9/4/2017.
 */

public class FoodListResponse implements ResponseSuccessFinder {

    @SerializedName("status")
    private String status;

    @SerializedName("response")
    private String response;

    @SerializedName("fooddetails")
    private ArrayList<FoodDetail> foodList;

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

    public ArrayList<FoodDetail> getFoodList() {
        return foodList;
    }

    public void setFoodList(ArrayList<FoodDetail> categoryList) {
        this.foodList = categoryList;
    }

    @Override
    public boolean isSuccess() {
        return "1".equals(getResponse());
    }
}
