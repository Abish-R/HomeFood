package abish.veettusorudemo.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import java.util.ArrayList;

import abish.veettusorudemo.MyOrdersList;
import abish.veettusorudemo.R;
import abish.veettusorudemo.Utils;
import abish.veettusorudemo.constants.Constants;
import abish.veettusorudemo.constants.UrlConstants;
import abish.veettusorudemo.network.GsonRequest;
import abish.veettusorudemo.network.VolleyApiClient;
import abish.veettusorudemo.network.response.MyOrdersResponse;

import static abish.veettusorudemo.Utils.displayLoader;
import static abish.veettusorudemo.Utils.hideLoader;

public class MyOrdersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        if (!Utils.getSavedUserDetail(this, Constants.LOGIN_USER_ID).equals("null")) {
            getMyOrders(Utils.getSavedUserDetail(this, Constants.LOGIN_USER_ID));
        } else {
            Intent intent = new Intent(MyOrdersActivity.this, UserLoginControlActivity.class);
            intent.putExtra(Constants.LOGIN_TRANSITION_DECIDER, Constants.LOGIN_AFTER_DELIVERY);
        }
    }

    private void getMyOrders(String userId) {
        displayLoader(this, "Getting your Orders...");

        String url = UrlConstants.GET_USER_ORDERS_URL + UrlConstants.ORDERS_CUSTOMER_ID + userId;

        GsonRequest request = new GsonRequest<MyOrdersResponse>(
                url, null,
                MyOrdersResponse.class, null, new Response.Listener<MyOrdersResponse>() {
            @Override
            public void onResponse(MyOrdersResponse response) {
                if (response.isSuccess() && response.getMyOrdersData() != null &&
                        response.getMyOrdersData().getOrderList() != null  && !response.getMyOrdersData().getOrderList().isEmpty()) {
                    ArrayList<MyOrdersList> list = response.getMyOrdersData().getOrderList();
                }
                hideLoader();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error: " + error.getMessage());
                hideLoader();
            }
        });
        VolleyApiClient.getInstance().addToRequestQueue(request, "Order Food");
    }
}
