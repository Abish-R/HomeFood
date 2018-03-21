package abish.veettusorudemo.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import abish.veettusorudemo.MyOrdersList;
import abish.veettusorudemo.R;
import abish.veettusorudemo.constants.Constants;
import abish.veettusorudemo.views.adapter.MyOrdersDescriptionAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MyOrderDescriptionActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolBar;

    @BindView(R.id.title)
    TextView toolbarTitle;

    @BindView(R.id.tv_order_status)
    TextView tvOrderStatus;

    @BindView(R.id.tv_order_date)
    TextView tvOrderDate;

    @BindView(R.id.tv_delivery_date)
    TextView tvDeliveryDate;

    @BindView(R.id.item_title_container)
    LinearLayout itemTitleContainer;

    @BindView(R.id.rv_my_order_food_list)
    RecyclerView rvMyOrderFoodList;

    @BindView(R.id.tv_total_qty)
    TextView tvTotalQty;

    @BindView(R.id.tv_total_amount)
    TextView tvTotalAmount;

    @BindView(R.id.tv_total_offer)
    TextView tvTotalOffer;

    @BindView(R.id.tv_final_price)
    TextView tvFinalPrice;

    @BindView(R.id.tv_name_user)
    TextView tvNameUser;

    @BindView(R.id.tv_phone)
    TextView tvPhone;

    private MyOrdersList myOrdersListItem;
    private MyOrdersDescriptionAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_description);
        ButterKnife.bind(this);
        setTitle();

        if (getIntent().getExtras() != null) {
            myOrdersListItem = getIntent().getExtras().getParcelable(Constants.MY_ORDER_DATA);
        }

        rvMyOrderFoodList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyOrdersDescriptionAdapter(this, myOrdersListItem.getOrderDetail());
        rvMyOrderFoodList.setAdapter(mAdapter);

        setDataInViews();
    }

    private void setTitle() {
        toolbarTitle.setText(R.string.title_my_order);
        setSupportActionBar(toolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void setDataInViews() {
        tvNameUser.setText(myOrdersListItem.getNameUser());
        tvPhone.setText(myOrdersListItem.getPhone());
        tvOrderStatus.setText(myOrdersListItem.getOrderStatus());
        tvOrderDate.setText(myOrdersListItem.getOrderDateTime());
        tvDeliveryDate.setText(myOrdersListItem.getDeliveryDateTime());
        tvTotalQty.setText(myOrdersListItem.getTotalQty());
        tvTotalAmount.setText(myOrdersListItem.getTotalAmount());
        tvTotalOffer.setText(myOrdersListItem.getDiscountAmount());
        tvFinalPrice.setText(myOrdersListItem.getFinalAmount());
    }
}
