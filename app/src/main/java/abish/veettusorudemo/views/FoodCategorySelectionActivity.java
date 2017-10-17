package abish.veettusorudemo.views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import java.util.ArrayList;

import abish.veettusorudemo.constants.Constants;
import abish.veettusorudemo.network.GsonRequest;
import abish.veettusorudemo.network.model.FoodCategory;
import abish.veettusorudemo.network.response.FoodCategoryResponse;
import abish.veettusorudemo.network.VolleyApiClient;
import abish.veettusorudemo.constants.UrlConstants;
import abish.veettusorudemo.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static abish.veettusorudemo.Utils.displayLoader;
import static abish.veettusorudemo.Utils.hideLoader;

public class FoodCategorySelectionActivity extends AppCompatActivity {

    @Bind(R.id.rv_food_category)
    RecyclerView mRecyclerView;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.title)
    TextView toolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_category_selection);

        ButterKnife.bind(this);

        setTitle();

        getFoodCategories();
    }

    private void setTitle() {
        toolbarTitle.setText(R.string.title_food_category);
        setSupportActionBar(toolbar);
    }

    public void callNextPage(FoodCategory category) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Constants.SELECTED_FOOD_CATEGORY, category);
        startActivity(intent);
        overridePendingTransitionEnter();
    }

    private void getFoodCategories() {
        String url = UrlConstants.GET_CATEGORY_URL;
        displayLoader(this, "Receiving Food Categories...");

        GsonRequest request = new GsonRequest<FoodCategoryResponse>(url, null,
                FoodCategoryResponse.class, null, new Response.Listener<FoodCategoryResponse>() {
            @Override
            public void onResponse(FoodCategoryResponse response) {
                Log.d("Food categories Ok", response.toString());
                FoodCategoryAdapter mAdapter = new FoodCategoryAdapter(
                        FoodCategorySelectionActivity.this, response.getCategoryList());
                mRecyclerView.setLayoutManager(new LinearLayoutManager(FoodCategorySelectionActivity.this));
                mRecyclerView.setAdapter(mAdapter);

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

    public class FoodCategoryAdapter extends RecyclerView.Adapter {
        Context context;
        LayoutInflater inflater;
        ArrayList<FoodCategory> foodCategoryList;

        public FoodCategoryAdapter(Context context, ArrayList<FoodCategory> foodCategory) {
            this.context = context;
            inflater = LayoutInflater.from(context);

            this.foodCategoryList = foodCategory;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new FoodCategoryAdapter.MyViewHolder(inflater.inflate(R.layout.item_food_category_selection, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof FoodCategoryAdapter.MyViewHolder) {
                ((FoodCategoryAdapter.MyViewHolder) holder).bind(foodCategoryList.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return foodCategoryList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            @Bind(R.id.bt_food_category)
            Button btFoodCategory;

            @Bind(R.id.tv_category_info)
            TextView tvCategoryInfo;

            FoodCategory foodCategory;

            public MyViewHolder(View itemView) {
                super(itemView);

                ButterKnife.bind(this, itemView);
            }

            @OnClick(R.id.bt_food_category)
            public void clickOnButton() {
                callNextPage(foodCategory);
            }

            public void bind(FoodCategory foodCategory) {
                this.foodCategory = foodCategory;
                btFoodCategory.setText(foodCategory.getCategoryName());
                tvCategoryInfo.setText(foodCategory.getOrderTiming());
            }
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
