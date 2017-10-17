package abish.veettusorudemo.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import abish.veettusorudemo.network.model.LocationFoodAvailable;

/**
 * Created by Abish on 8/12/2017.
 */

public class LocationResponse {

    @SerializedName ("status")
    private String status;

    @SerializedName ("response")
    private String response;

    @SerializedName ("locations")
    private ArrayList<LocationFoodAvailable> locationList;

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

    public ArrayList<LocationFoodAvailable> getLocationList() {
        return locationList;
    }

    public void setLocationList(ArrayList<LocationFoodAvailable> locationList) {
        this.locationList = locationList;
    }
}
