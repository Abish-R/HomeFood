package abish.veettusorudemo.network.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Abish on 8/17/2017.
 */

public class LoginResponse {

    @SerializedName ("status")
    private String status;

    @SerializedName ("response")
    private String response;

    @SerializedName ("userId")
    private String userId;

    @SerializedName ("name")
    private String name;

    @SerializedName ("email")
    private String email;

    @SerializedName ("mobile")
    private String mobile;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
