package abish.veettusorudemo.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import abish.veettusorudemo.network.model.FoodCategory;

/**
 * Created by Abish on 8/17/2017.
 * </p>
 */

public class FoodCategoryResponse {

    @SerializedName ("status")
    private String status;

    @SerializedName ("response")
    private String response;

    @SerializedName ("categories")
    private ArrayList<FoodCategory> foodCategoryList;

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

    public ArrayList<FoodCategory> getCategoryList() {
        return foodCategoryList;
    }

    public void setCategoryList(ArrayList<FoodCategory> categoryList) {
        this.foodCategoryList = categoryList;
    }
}
