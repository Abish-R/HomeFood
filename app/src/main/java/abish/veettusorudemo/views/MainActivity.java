package abish.veettusorudemo.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import java.util.ArrayList;
import java.util.List;

import abish.veettusorudemo.ActivityFoodDetailUpdater;
import abish.veettusorudemo.R;
import abish.veettusorudemo.Utils;
import abish.veettusorudemo.constants.Constants;
import abish.veettusorudemo.constants.UrlConstants;
import abish.veettusorudemo.network.GsonRequest;
import abish.veettusorudemo.network.VolleyApiClient;
import abish.veettusorudemo.network.model.FoodCategory;
import abish.veettusorudemo.network.response.FoodDetail;
import abish.veettusorudemo.network.response.FoodListResponse;
import abish.veettusorudemo.views.adapter.FoodListAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

import static abish.veettusorudemo.Utils.displayLoader;
import static abish.veettusorudemo.Utils.hideLoader;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        TransformIntent, ActivityFoodDetailUpdater.ActivityRefresh {

    @BindView(R.id.delivery_timing)
    TextView deliveryTiming;

    @BindView(R.id.food_list)
    RecyclerView foodListRecycler;

    @BindView(R.id.toolbar)
    Toolbar toolBar;

    @BindView(R.id.title)
    TextView toolbarTitle;

    @BindView(R.id.bt_no_food)
    Button btNoFood;

    @BindView(R.id.bt_retry)
    Button btRetry;

    private FoodCategory foodCategoryData;
    private String foodCategoryID;
    private String foodCategory;
    private String foodCategoryTiming;
    private FoodListAdapter mAdapter;
    private List<FoodDetail> foodDetailList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        setTitle();

        foodListRecycler.setLayoutManager(new LinearLayoutManager(this));

        if (getIntent().getExtras() != null && getIntent().getExtras().getParcelable(Constants.SELECTED_FOOD_CATEGORY) != null) {
            foodCategoryData = getIntent().getExtras().getParcelable(Constants.SELECTED_FOOD_CATEGORY);
            if (foodCategoryData != null) {
                foodCategoryID = foodCategoryData.getId();
                foodCategory = foodCategoryData.getCategoryName();
                foodCategoryTiming = foodCategoryData.getOrderTiming();
                deliveryTiming.setText("For party, make your order by " + foodCategoryTiming + " of same day");
            }
        }

        mAdapter = new FoodListAdapter(this, foodDetailList, this);
        foodListRecycler.setAdapter(mAdapter);

        getMainDishList();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<FoodDetail> selectedFoodDetailList = new ArrayList<>();
                for (FoodDetail foodDetail : foodDetailList) {
                    if (foodDetail.getSelectedFoodCountNumber() >= 1) {
                        foodDetail.setFoodCategoryId(Constants.MAIN_COURSE_TYPE);
                        selectedFoodDetailList.add(foodDetail);
                    }
                }
                if (!selectedFoodDetailList.isEmpty()) {
                    Intent intent = new Intent(MainActivity.this, SubFoodListActivity.class);
                    intent.putParcelableArrayListExtra(Constants.SELECTED_MAIN_FOODS, selectedFoodDetailList);
                    intent.putExtra(Constants.SELECTED_FOOD_CATEGORY_ID, foodCategoryID);
                    startActivity(intent);
                } else {
                    alertFoodAtLeastOne();
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void getMainDishList() {
        //TODO : Change User ID - Tell back to provide non login users
        String userID = Utils.getSavedUserDetail(this, Constants.LOGIN_USER_ID);
        userID = userID.equals("null") ? "0" : userID;

        String url = UrlConstants.GET_MAIN_FOOD_URL + UrlConstants.MAIN_FOOD_PARAM_FOOD_CATEGORY + foodCategoryID
                + UrlConstants.MAIN_FOOD_PARAM_USER_ID + userID;
        displayLoader(this, "Receiving Food List...");

        GsonRequest request = new GsonRequest<FoodListResponse>(url, null,
                FoodListResponse.class, null, new Response.Listener<FoodListResponse>() {
            @Override
            public void onResponse(FoodListResponse response) {
                if (response.isSuccess() && !response.getFoodList().isEmpty()) {
                    foodDetailList.addAll(response.getFoodList());
                    mAdapter.notifyDataSetChanged();
                } else {
                    setNoDataLogic(false);
                }
                hideLoader();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error: " + error.getMessage());
                hideLoader();
                setNoDataLogic(true);
            }
        });
        VolleyApiClient.getInstance().addToRequestQueue(request, "Food Categories");
    }

    private void setNoDataLogic(boolean isRetry) {
        if (isRetry) {
            btRetry.setVisibility(View.VISIBLE);
            btNoFood.setVisibility(View.GONE);
        } else {
            btNoFood.setVisibility(View.VISIBLE);
            btRetry.setVisibility(View.GONE);
        }
        foodDetailList.clear();
        mAdapter.notifyDataSetChanged();
    }

    public void noFoodAction(View view) {
        onBackPressed();
    }

    public void retryFood(View view) {
        getMainDishList();
    }

    private void setTitle() {
        toolbarTitle.setText(R.string.title_food_main_page);
        setSupportActionBar(toolBar);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        foodCategory = item.getTitle().toString();

        //noinspection SimplifiableIfStatement
        if (id == R.id.option1) {
            foodDetailList.clear();
            foodCategoryID = "1";
            getMainDishList();
            return true;
        } else if (id == R.id.option2) {
            foodDetailList.clear();
            foodCategoryID = "2";
            getMainDishList();
            return true;
        } else if (id == R.id.option3) {
            foodDetailList.clear();
            foodCategoryID = "3";
            getMainDishList();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_list_foods) {
            startActivity(new Intent(this, FoodCategorySelectionActivity.class));
        } else if (id == R.id.nav_orders) {
            startActivity(new Intent(this, MyOrdersActivity.class));
        } else if (id == R.id.nav_favourites) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_enquiry) {

        } else if (id == R.id.nav_about_us) {

        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void alertFoodAtLeastOne() {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage("Select at least one Food to proceed further!")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                    }
                }).show();
    }

    @Override
    public void onLaunchActivity(FoodDetail foodDetail, Intent intent, boolean needFinishActivity) {
        intent.putExtra(Constants.SELECTED_FOOD, foodDetail);
        if (needFinishActivity) {
            startActivity(intent);
            finish();
            overridePendingTransitionExit();
        } else {
            startActivity(intent);
            overridePendingTransitionEnter();
        }
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
    public void refreshData() {

    }
}
