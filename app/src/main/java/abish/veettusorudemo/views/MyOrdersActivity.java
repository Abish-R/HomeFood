package abish.veettusorudemo.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import java.util.ArrayList;
import java.util.List;

import abish.veettusorudemo.MyOrdersList;
import abish.veettusorudemo.R;
import abish.veettusorudemo.Utils;
import abish.veettusorudemo.constants.Constants;
import abish.veettusorudemo.constants.UrlConstants;
import abish.veettusorudemo.network.GsonRequest;
import abish.veettusorudemo.network.VolleyApiClient;
import abish.veettusorudemo.network.response.MyOrdersResponse;
import abish.veettusorudemo.views.adapter.MyOrdersListAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

import static abish.veettusorudemo.Utils.displayLoader;
import static abish.veettusorudemo.Utils.hideLoader;

public class MyOrdersActivity extends AppCompatActivity implements MyOrdersListAdapter.OnItemPressListener {

    @BindView(R.id.toolbar)
    Toolbar toolBar;

    @BindView(R.id.title)
    TextView toolbarTitle;

    @BindView(R.id.my_orders_list)
    RecyclerView rvMyOrdersList;

    private MyOrdersListAdapter mAdapter;
    private List<MyOrdersList> myOrdersList = new ArrayList<>();
    private String nameUser;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        ButterKnife.bind(this);
        setTitle();

        rvMyOrdersList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyOrdersListAdapter(this, this, myOrdersList);
        rvMyOrdersList.setAdapter(mAdapter);

        if (!Utils.getSavedUserDetail(this, Constants.LOGIN_USER_ID).equals("null")) {
            getMyOrders(Utils.getSavedUserDetail(this, Constants.LOGIN_USER_ID));
        } else {
            Intent intent = new Intent(MyOrdersActivity.this, UserLoginControlActivity.class);
            intent.putExtra(Constants.LOGIN_TRANSITION_DECIDER, Constants.LOGIN_AFTER_DELIVERY);
        }
    }

    private void setTitle() {
        toolbarTitle.setText(R.string.title_my_orders);
        setSupportActionBar(toolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransitionExit();
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
                        response.getMyOrdersData().getOrderList() != null && !response.getMyOrdersData().getOrderList().isEmpty()) {
                    myOrdersList.clear();
                    myOrdersList.addAll(response.getMyOrdersData().getOrderList());
                    mAdapter.notifyDataSetChanged();
                    if (response.getMyOrdersData().getCustomerDetails() != null) {
                        nameUser = response.getMyOrdersData().getCustomerDetails().getNameUser();
                        phone = response.getMyOrdersData().getCustomerDetails().getPhone();
                    }
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
        VolleyApiClient.getInstance().addToRequestQueue(request, "My Orders");
    }

    @Override
    public void onMyOrdersItemClicked(MyOrdersList myOrderItem, int position) {
        int totalQty = 0;
        for (MyOrdersList myOrder : myOrdersList) {
            for (int i = 0; i < myOrder.getOrderDetail().size(); i++) {
                totalQty += Integer.parseInt(myOrder.getOrderDetail().get(i).getFoodQuantity());
            }
        }
        myOrderItem.setTotalQty(totalQty + "");
        myOrderItem.setNameUser(nameUser);
        myOrderItem.setPhone(phone);
        Intent intent = new Intent(this, MyOrderDescriptionActivity.class);
        intent.putExtra(Constants.MY_ORDER_DATA, myOrderItem);
        startActivity(intent);
    }

    /**
     * Overrides the pending Activity transition by performing the "Enter" animation.
     */
    protected void overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation.
     */
    protected void overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
