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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import java.util.ArrayList;
import java.util.List;

import abish.veettusorudemo.R;
import abish.veettusorudemo.Utils;
import abish.veettusorudemo.constants.Constants;
import abish.veettusorudemo.constants.UrlConstants;
import abish.veettusorudemo.network.GsonRequest;
import abish.veettusorudemo.network.VolleyApiClient;
import abish.veettusorudemo.network.model.FoodDetail;
import abish.veettusorudemo.network.response.FoodFavouriteResponse;
import abish.veettusorudemo.network.response.FoodListResponse;
import abish.veettusorudemo.views.adapter.FoodListAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

import static abish.veettusorudemo.Utils.displayLoader;
import static abish.veettusorudemo.Utils.hideLoader;

public class MyFavFoodActivity extends AppCompatActivity
        implements TransformIntent {

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

    private FoodListAdapter mAdapter;
    private List<FoodDetail> foodDetailList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fav_food);

        ButterKnife.bind(this);
        setTitle();

        foodListRecycler.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new FoodListAdapter(this, foodDetailList, this, true);
        foodListRecycler.setAdapter(mAdapter);

        getFavDishList();
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

    private void getFavDishList() {
        String userID = Utils.getSavedUserDetail(this, Constants.LOGIN_USER_ID);
        if (userID.equals("null")) {
            return;
        }

        String url = UrlConstants.GET_MY_FAV_FOOD_URL + UrlConstants.FAV_FOOD_PARAM_USER_ID + userID;
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
        getFavDishList();
    }

    private void setTitle() {
        toolbarTitle.setText(R.string.title_sub_food_category);
        setSupportActionBar(toolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransitionExit();
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
    public void onLaunchActivity(FoodDetail foodDetail, int position, Intent intent, boolean needFinishActivity) {
        // Do Nothing
    }

    @Override
    public void updateFavourite(String userID, final FoodDetail foodDetail) {
        String url = UrlConstants.GET_FAV_FOOD_URL
                + UrlConstants.FAV_FOOD_PARAM_USER_ID + userID
                + UrlConstants.FAV_FOOD_PARAM_COURSE_ID + foodDetail.getId()
                + UrlConstants.FAV_FOOD_PARAM_COURSE_TYPE + Constants.MAIN_COURSE_TYPE;
        Utils.displayLoader(this, "Updating Favourite...");

        GsonRequest request = new GsonRequest<>(url, null,
                FoodFavouriteResponse.class, null, new Response.Listener<FoodFavouriteResponse>() {
            @Override
            public void onResponse(FoodFavouriteResponse response) {
                if (response.isSuccess()) {
                    foodDetail.setFavourite(!foodDetail.isFavourite());
                    if (mAdapter != null) mAdapter.notifyDataSetChanged();
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
        VolleyApiClient.getInstance().addToRequestQueue(request, "Food Categories");
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
