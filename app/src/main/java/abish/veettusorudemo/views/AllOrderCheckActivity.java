package abish.veettusorudemo.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import abish.veettusorudemo.R;
import abish.veettusorudemo.Utils;
import abish.veettusorudemo.constants.Constants;
import abish.veettusorudemo.network.model.OrderData;
import abish.veettusorudemo.network.model.FoodDetail;
import abish.veettusorudemo.views.adapter.AllOrderCheckListAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AllOrderCheckActivity extends AppCompatActivity implements AllOrderCheckListAdapter.OnCountChangeListener {

    @BindView(R.id.toolbar)
    Toolbar toolBar;

    @BindView(R.id.title)
    TextView toolbarTitle;

    @BindView(R.id.bt_order)
    Button btOrder;

    @BindView(R.id.delivery_recycler_view)
    RecyclerView deliveryRecyclerView;

    private ArrayList<FoodDetail> selectedFoodDetailList;
    private AllOrderCheckListAdapter mAdapter;
    private String foodCategoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_order_check);

        ButterKnife.bind(this);
        setTitle();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            selectedFoodDetailList = bundle.getParcelableArrayList(Constants.ALL_SELECTED_FOOD_LIST);
            foodCategoryId = getIntent().getExtras().getString(Constants.SELECTED_FOOD_CATEGORY_ID);
        }

        if (selectedFoodDetailList != null) {
            mAdapter = new AllOrderCheckListAdapter(this, this, selectedFoodDetailList);
            deliveryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            deliveryRecyclerView.setAdapter(mAdapter);

            totalPrice();
        }
    }

    private void totalPrice() {
        int totalPayable = 0;
        for (FoodDetail foodDetail : selectedFoodDetailList) {
            if (!foodDetail.isReallyFree()) {
                totalPayable += foodDetail.getPriceAfterOffer() * foodDetail.getSelectedFoodCountNumber();
            }
        }
        btOrder.setText(getString(R.string.text_order, " Rs. " + totalPayable));
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
        finish();
        overridePendingTransitionExit();
    }

    private void setTitle() {
        toolbarTitle.setText(R.string.title_total_order);
        setSupportActionBar(toolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    public void goToDeliveryConfirm(View view) {
        Intent intent;
        if (!Utils.getSavedUserDetail(this, Constants.LOGIN_USER_ID).equals("null")) {
            ArrayList<OrderData> orderDatas = new ArrayList<>();
            for (FoodDetail selectedFoodData : selectedFoodDetailList) {
                if (selectedFoodData.getSelectedFoodCountNumber() > 0) {
                    OrderData orderData = new OrderData();
                    orderData.setCourseId(selectedFoodData.getId() + "");
                    orderData.setFoodQuantity(selectedFoodData.getSelectedFoodCountNumber() + "");
                    String offerId = (selectedFoodData.getOfferDetails() != null && selectedFoodData.getOfferDetails().get(0) != null) ? selectedFoodData.getOfferDetails().get(0).getOffer_id() : "-1";
                    orderData.setOffer_id(offerId);
                    orderData.setType(selectedFoodData.getFoodCategoryId());
                    orderDatas.add(orderData);
                }
            }
            if (!orderDatas.isEmpty()) {
                intent = new Intent(AllOrderCheckActivity.this, DeliveryManagementActivity.class);
                intent.putParcelableArrayListExtra(Constants.ALL_ORDERS, orderDatas);
                intent.putExtra(Constants.SELECTED_FOOD_CATEGORY_ID, foodCategoryId);
            } else {
                Toast.makeText(this, "Nothing Selected", Toast.LENGTH_SHORT).show();
                intent = new Intent(AllOrderCheckActivity.this, MainActivity.class);
            }
        } else {
            intent = new Intent(AllOrderCheckActivity.this, UserLoginControlActivity.class);
            intent.putExtra(Constants.LOGIN_TRANSITION_DECIDER, Constants.LOGIN_AFTER_DELIVERY);
        }
        startActivity(intent);
        finish();
        overridePendingTransitionExit();
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

    @Override
    public void onCountChange(FoodDetail foodDetail, int position) {
        totalPrice();
        if (foodDetail.getSelectedFoodCountNumber() < 1) {
            selectedFoodDetailList.remove(position);
        } else {
            selectedFoodDetailList.set(position, foodDetail);
        }
        mAdapter.notifyDataSetChanged();
    }
}
