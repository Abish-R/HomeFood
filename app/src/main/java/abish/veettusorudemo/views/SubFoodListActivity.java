package abish.veettusorudemo.views;

import android.content.Intent;
import android.os.Bundle;
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
import abish.veettusorudemo.Utils;
import abish.veettusorudemo.constants.Constants;
import abish.veettusorudemo.constants.UrlConstants;
import abish.veettusorudemo.network.GsonRequest;
import abish.veettusorudemo.network.VolleyApiClient;
import abish.veettusorudemo.network.model.FoodDetail;
import abish.veettusorudemo.network.response.FoodFavouriteResponse;
import abish.veettusorudemo.network.response.FoodListResponse;
import abish.veettusorudemo.views.adapter.SubFoodListAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static abish.veettusorudemo.Utils.displayLoader;
import static abish.veettusorudemo.Utils.hideLoader;

public class SubFoodListActivity extends AppCompatActivity implements TransformIntent {

    @BindView(R.id.bt_continue)
    Button btContinue;

    @BindView(R.id.bt_place_order)
    Button btPlaceOrder;

    @BindView(R.id.rv_sub_food_list)
    RecyclerView rvSubFoodList;

    @BindView(R.id.toolbar)
    Toolbar toolBar;

    @BindView(R.id.title)
    TextView toolbarTitle;

    @BindView(R.id.bt_retry)
    Button btRetry;

    @BindView(R.id.bt_no_food)
    Button btNoFood;

    private List<FoodDetail> subCourseList = new ArrayList<>();
    private ArrayList<FoodDetail> selectedFoodDetailList;
    private String foodCategoryId;
    private SubFoodListAdapter mAdapter;
    private String mainCourseId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_food_list);

        ButterKnife.bind(this);
        setTitle();

        rvSubFoodList.setLayoutManager(new LinearLayoutManager(this));

        if (getIntent().getExtras() != null) {
            selectedFoodDetailList = getIntent().getExtras().getParcelableArrayList(Constants.SELECTED_MAIN_FOODS);
            foodCategoryId = getIntent().getExtras().getString(Constants.SELECTED_FOOD_CATEGORY_ID);
        }

        if (selectedFoodDetailList != null) {
            mAdapter = new SubFoodListAdapter(this, subCourseList, this);
            rvSubFoodList.setAdapter(mAdapter);

            StringBuilder stringBuilder = new StringBuilder();
            for (FoodDetail foodDetail : selectedFoodDetailList) {
                if (stringBuilder.toString().isEmpty()) {
                    stringBuilder.append(foodDetail.getId());
                } else {
                    stringBuilder.append(",").append(foodDetail.getId());
                }
            }
            mainCourseId = stringBuilder.toString();
            getSubDishList();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransitionExit();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

    @OnClick(R.id.bt_continue)
    public void onContinuePressed() {
        Utils.alertMessage(new AlertDialogListener() {
                               @Override
                               public void onOkClick() {
                                   finish();
                                   overridePendingTransitionExit();
                               }

                               @Override
                               public void onCancelClick() {
                                   // Do Nothing
                               }
                           }, SubFoodListActivity.this
                , getString(R.string.sub_food_selection_done_message)
                , getString(R.string.text_yes), getString(R.string.text_no));

    }

    @OnClick(R.id.bt_place_order)
    public void onPlaceOrderPressed() {
        Utils.alertMessage(new AlertDialogListener() {
                               @Override
                               public void onOkClick() {
                                   goToALLOrderCheckScreen();
                               }

                               @Override
                               public void onCancelClick() {
                                   // Do Nothing
                               }
                           }, SubFoodListActivity.this
                , getString(R.string.sub_food_order_done_message)
                , getString(R.string.text_ok), null);
    }

    public void noFoodAction(View view) {
        onBackPressed();
    }

    public void retryFood(View view) {
        getSubDishList();
    }

    private void goToALLOrderCheckScreen() {
        int totalMainFood = 0;
        int totalSubFoodFree = 0;
        int oneFreeFoodAmount = 0;
        boolean isFreeSelectionExceed = false;
        ArrayList<FoodDetail> selectedSubFoodList = new ArrayList<>();
        for (FoodDetail mainFoodItem : selectedFoodDetailList) {
            totalMainFood += mainFoodItem.getSelectedFoodCountNumber();
        }
        for (FoodDetail subFoodList : subCourseList) {
            if (subFoodList.isChecked()) {
                subFoodList.setFoodCategoryId(Constants.SUB_COURSE_TYPE);
                selectedSubFoodList.add(subFoodList);
            }
            if (subFoodList.isChecked() && subFoodList.isReallyFree()) {
                totalSubFoodFree += subFoodList.getSelectedFoodCountNumber();
                oneFreeFoodAmount = subFoodList.getPriceAfterOffer();
            }
            if (totalMainFood < totalSubFoodFree) {
                isFreeSelectionExceed = true;
                subFoodList.setExtraFreeFoodCount(totalSubFoodFree - totalMainFood);
                //break;
            }
        }
        if (isFreeSelectionExceed) {
            extraFreeFoodSelectionAlert(totalMainFood, totalSubFoodFree, oneFreeFoodAmount, selectedSubFoodList);
        } else {
            goToAllOrderCheckActivity(totalMainFood, totalSubFoodFree, oneFreeFoodAmount, selectedSubFoodList);
        }
    }

    private void goToAllOrderCheckActivity(int totalMainFood, int totalSubFoodFree, int oneFreeFoodAmount, ArrayList<FoodDetail> selectedSubFoodList) {
        selectedFoodDetailList.addAll(selectedSubFoodList);
        Intent intent = new Intent(SubFoodListActivity.this, AllOrderCheckActivity.class);
        intent.putParcelableArrayListExtra(Constants.ALL_SELECTED_FOOD_LIST, selectedFoodDetailList);
        intent.putExtra(Constants.SELECTED_FOOD_CATEGORY_ID, foodCategoryId);
        intent.putExtra(Constants.FREE_SUB_FOOD_PRICE, oneFreeFoodAmount);
        intent.putExtra(Constants.EXTRA_FREE_SUB_FOOD_COUNT, totalSubFoodFree - totalMainFood);
        startActivity(intent);
        finish();
    }

    private void extraFreeFoodSelectionAlert(final int totalMainFood, final int totalSubFoodFree,
                                             final int oneFreeFoodAmount, final ArrayList<FoodDetail> selectedSubFoodList) {
        Utils.alertMessage(new AlertDialogListener() {
                               @Override
                               public void onOkClick() {
                                   goToAllOrderCheckActivity(totalMainFood, totalSubFoodFree, oneFreeFoodAmount, selectedSubFoodList);
                               }

                               @Override
                               public void onCancelClick() {

                               }
                           }, SubFoodListActivity.this
                , getString(R.string.free_sub_food_extra_selection_message)
                , getString(R.string.text_ok), getString(R.string.text_cancel));
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
                    subCourseList.clear();
                    subCourseList.addAll(response.getFoodList());
                    canFreePossible(response.getFoodList());
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

    private void canFreePossible(List<FoodDetail> foodList) {
        List<String> freeFoodList = new ArrayList<>();
        for (FoodDetail foodDetail : foodList) {
            if (foodDetail.isFreeFood() && !freeFoodList.contains(foodDetail.getMainCourseId())) {
                freeFoodList.add(foodDetail.getMainCourseId());
                foodDetail.setReallyFree("S");
            }
        }
    }

    private void setNoDataLogic(boolean isRetry) {
        if (isRetry) {
            btRetry.setVisibility(View.VISIBLE);
            btNoFood.setVisibility(View.GONE);
        } else {
            btNoFood.setVisibility(View.VISIBLE);
            btRetry.setVisibility(View.GONE);
        }
        subCourseList.clear();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLaunchActivity(FoodDetail foodDetail, int position, Intent intent, boolean needFinishActivity) {
        intent.putExtra(Constants.SELECTED_FOOD, foodDetail);
        subCourseList.get(position).setSelectedFoodCount(foodDetail.getSelectedFoodCountNumber());
        if (needFinishActivity) {
            startActivity(intent);
            finish();
            overridePendingTransitionExit();
        } else {
            startActivity(intent);
            overridePendingTransitionEnter();
        }
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
