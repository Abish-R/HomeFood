package abish.veettusorudemo.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import abish.veettusorudemo.constants.Constants;
import abish.veettusorudemo.constants.UrlConstants;
import abish.veettusorudemo.network.GsonRequest;
import abish.veettusorudemo.network.VolleyApiClient;
import abish.veettusorudemo.network.response.FoodDetail;
import abish.veettusorudemo.network.response.FoodListResponse;
import abish.veettusorudemo.views.adapter.SubFoodListAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;

import static abish.veettusorudemo.Utils.displayLoader;
import static abish.veettusorudemo.Utils.hideLoader;

public class SubFoodListActivity extends AppCompatActivity implements TransformIntent {
    private SubFoodListAdapter mAdapter;
    private List<FoodDetail> foodDetailList = new ArrayList<>();
    private String mainCourseId = "";

    @Bind(R.id.bt_continue)
    Button btContinue;

    @Bind(R.id.bt_place_order)
    Button btPlaceOrder;

    @Bind(R.id.rv_sub_food_list)
    RecyclerView rvSubFoodList;

    @Bind(R.id.toolbar)
    Toolbar toolBar;

    @Bind(R.id.title)
    TextView toolbarTitle;

    @Bind(R.id.bt_retry)
    Button btRetry;

    @Bind(R.id.bt_no_food)
    Button btNoFood;

    private List<FoodDetail> subCourseList = new ArrayList<>();
    private ArrayList<FoodDetail> selectedFoodDetailList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_food_list);

        ButterKnife.bind(this);
        setTitle();

        rvSubFoodList.setLayoutManager(new LinearLayoutManager(this));

        selectedFoodDetailList = getIntent().getExtras().
                getParcelableArrayList(Constants.SELECTED_MAIN_FOODS);

        if (selectedFoodDetailList != null) {
            mAdapter = new SubFoodListAdapter(this, subCourseList, this);
            rvSubFoodList.setAdapter(mAdapter);

            for (FoodDetail foodDetail : selectedFoodDetailList) {
                if (mainCourseId.isEmpty()) {
                    mainCourseId += foodDetail.getId();
                } else {
                    mainCourseId += "," + foodDetail.getId();
                }
            }
            getSubDishList();
        }

        btContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(SubFoodListActivity.this)
                        .setMessage("If you finished purchasing press Ok. To continue purchase press Cancel.")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                overridePendingTransitionExit();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }).show();
            }
        });

        btPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(SubFoodListActivity.this)
                        .setMessage("Your order is placed successfully. Few more steps to confirm again!")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
//                                Intent intent = new Intent(SubFoodListActivity.this, UserLoginControlActivity.class);
                                goToALLORderCheckScreen();
                            }
                        }).show();
            }
        });
    }

    public void noFoodAction(View view) {
        onBackPressed();
    }

    public void retryFood(View view) {
        getSubDishList();
    }

    private void goToALLORderCheckScreen() {
        for (FoodDetail subFoodList : subCourseList) {
            if (subFoodList.isChecked()) {
                subFoodList.setFoodCategoryId(Constants.SUB_COURSE_TYPE);
                selectedFoodDetailList.add(subFoodList);
            }
        }
        Intent intent = new Intent(SubFoodListActivity.this, AllOrderCheckActivity.class);
        intent.putParcelableArrayListExtra(Constants.ALL_SELECTED_FOOD_LIST, selectedFoodDetailList);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransitionExit();
    }

    private void setTitle() {
        toolbarTitle.setText(R.string.title_sub_food_category);
        setSupportActionBar(toolBar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

    private void getSubDishList() {
        //TODO : Change User ID - Tell back to provide non login users
        String url = UrlConstants.GET_SUB_FOOD_URL + UrlConstants.SUB_FOOD_PARAM_MAIN_ID + mainCourseId
                + UrlConstants.SUB_FOOD_PARAM_USER_ID + "2";
        displayLoader(this, "Receiving Food List...");

        GsonRequest request = new GsonRequest<FoodListResponse>(url, null,
                FoodListResponse.class, null, new Response.Listener<FoodListResponse>() {
            @Override
            public void onResponse(FoodListResponse response) {
                Log.d("Food categories Ok", response.toString());
                if (response.isSuccess() && !response.getFoodList().isEmpty()) {
                    subCourseList.addAll(response.getFoodList());
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
}
