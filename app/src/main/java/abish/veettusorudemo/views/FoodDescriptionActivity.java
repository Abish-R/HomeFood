package abish.veettusorudemo.views;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import abish.veettusorudemo.R;
import abish.veettusorudemo.Utils;
import abish.veettusorudemo.constants.Constants;
import abish.veettusorudemo.constants.UrlConstants;
import abish.veettusorudemo.network.GsonRequest;
import abish.veettusorudemo.network.VolleyApiClient;
import abish.veettusorudemo.network.response.FoodDetail;
import abish.veettusorudemo.network.response.FoodFavouriteResponse;
import butterknife.Bind;
import butterknife.ButterKnife;

import static abish.veettusorudemo.Utils.hideLoader;

public class FoodDescriptionActivity extends AppCompatActivity {

    @Bind(R.id.share_food)
    ImageView shareFood;

    @Bind(R.id.iv_food)
    ImageView ivFood;

    @Bind(R.id.iv_minus)
    ImageView ivMinus;

    @Bind(R.id.iv_plus)
    ImageView ivPlus;

    @Bind(R.id.iv_favourite)
    ImageView ivFavourite;

    @Bind(R.id.tv_food_name)
    TextView tvFoodName;

    @Bind(R.id.tv_price)
    TextView tvPrice;

    @Bind(R.id.tv_count)
    TextView tvCount;

    @Bind(R.id.tv_description)
    TextView tvDescription;

    @Bind(R.id.bt_add)
    Button btAdd;

    @Bind(R.id.bt_back)
    Button btBack;

    @Bind(R.id.controls)
    RelativeLayout controls;

    @Bind(R.id.toolbar)
    Toolbar toolBar;

    @Bind(R.id.title)
    TextView toolbarTitle;

    private FoodDetail foodDetailData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_description);

        ButterKnife.bind(this);
        setTitle();

        foodDetailData = getIntent().getExtras().getParcelable(Constants.SELECTED_FOOD);

        if (foodDetailData != null) {
            tvFoodName.setText(foodDetailData.getFoodName());
            tvCount.setText(foodDetailData.getSelectedFoodCount());
            tvPrice.setText(foodDetailData.getPrice());
            tvDescription.setText(foodDetailData.getFullDescription());

            if (foodDetailData.isFavourite()) {
                ivFavourite.setBackgroundResource(R.drawable.heart_filled);
            } else {
                ivFavourite.setBackgroundResource(R.drawable.heart_outline);
            }

            if (!foodDetailData.getFoodImageUrl().isEmpty()) {
                Glide.with(this)
                        .load(foodDetailData.getFoodImageUrl())
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                //progressBar.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                //progressBar.setVisibility(View.GONE);
                                ivFood.setImageResource(R.drawable.heart_outline);
                                return false;
                            }
                        })
                        .into(ivFood);
            }
        }

        shareFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(FoodDescriptionActivity.this)
                        .setMessage("Food Details will be shared in Social Media. Will be handled later on.")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
            }
        });

        ivFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ivMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFoodCount(-1);
            }
        });

        ivPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFoodCount(1);
            }
        });

        ivFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateFavourite();
            }
        });

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvCount.setText("1");
                controls.setVisibility(View.VISIBLE);
                btAdd.setVisibility(View.INVISIBLE);
            }
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(FoodDescriptionActivity.this)
                        .setMessage("To order this item, press 'Add' from screen. Press 'Cancel' to exit the screen")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                                overridePendingTransitionExit();
                            }
                        }).show();
            }
        });
    }

    private void setTitle() {
        toolbarTitle.setText(R.string.title_food_main_page);
        setSupportActionBar(toolBar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setFoodCount(int count) {
        int newCount = Integer.parseInt(tvCount.getText().toString()) + count;
        if (newCount < 0) {
            Snackbar.make(ivPlus, "Order atleast one to proceed", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else if (newCount > 10) {
            Snackbar.make(ivPlus, "You can order max 10, Contact directly, see 'About Us' page.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            tvCount.setText(String.valueOf(newCount));
        }
    }

    private void updateFavourite() {
        //TODO : Change hard coded values in UrlConstants
        String url = UrlConstants.GET_FAV_FOOD_URL
                + UrlConstants.FAV_FOOD_PARAM_USER_ID + Utils.getSavedUserDetail(this, Constants.LOGIN_USER_ID)
                + UrlConstants.FAV_FOOD_PARAM_COURSE_ID + foodDetailData.getId()
                + UrlConstants.FAV_FOOD_PARAM_COURSE_TYPE + Constants.MAIN_COURSE_TYPE;
        Utils.displayLoader(this, "Updating Favourite...");

        GsonRequest request = new GsonRequest<>(url, null,
                FoodFavouriteResponse.class, null, new Response.Listener<FoodFavouriteResponse>() {
            @Override
            public void onResponse(FoodFavouriteResponse response) {
                if (response.isSuccess()) {
                    foodDetailData.setFavourite(!foodDetailData.isFavourite());
                    if (!foodDetailData.isFavourite()) {
                        ivFavourite.setImageResource(R.drawable.heart_filled);
                    } else {
                        ivFavourite.setImageResource(R.drawable.heart_outline);
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
        VolleyApiClient.getInstance().addToRequestQueue(request, "Food Categories");
    }

    @Override
    public void onBackPressed() {
        finish();
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
