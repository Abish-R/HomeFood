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

import com.bumptech.glide.Glide;

import abish.veettusorudemo.R;
import abish.veettusorudemo.constants.Constants;
import abish.veettusorudemo.network.response.FoodDetail;
import butterknife.Bind;
import butterknife.ButterKnife;

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

    boolean isFoodFavEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_description);

        ButterKnife.bind(this);
        setTitle();

        FoodDetail foodDetailData = getIntent().getExtras().getParcelable(Constants.SELECTED_FOOD);

        if (foodDetailData != null) {
            tvFoodName.setText(foodDetailData.getFoodName());
            tvCount.setText(foodDetailData.getSelectedFoodCount()+"");
            tvPrice.setText(foodDetailData.getPrice());
            tvDescription.setText("Lorem Ipsum is simply dummy text of the printing and typesetting industry. " +
                    "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown " +
                    "printer took a galley of type and scrambled it to make a type specimen book. It has survived " +
                    "not only five centuries, but also the leap into electronic typesetting, remaining essentially " +
                    "unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem " +
                    "Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including " +
                    "versions of Lorem Ipsum.");

            boolean fav = foodDetailData.isFavourite();

            if(fav){
                ivFavourite.setBackgroundResource(R.drawable.heart_filled);
            } else{
                ivFavourite.setBackgroundResource(R.drawable.heart_outline);
            }

            if(!foodDetailData.getFoodImageUrl().isEmpty()) {
                Glide.with(this)
                        .load(foodDetailData.getFoodImageUrl())
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
                if (!isFoodFavEnabled) {
                    isFoodFavEnabled = true;
                    ivFavourite.setImageResource(R.drawable.heart_filled);
                }
                else {
                    isFoodFavEnabled = false;
                    ivFavourite.setImageResource(R.drawable.heart_outline);
                }
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
        int newCount = Integer.parseInt(tvCount.getText().toString())+count;
        if (newCount < 0) {
            Snackbar.make(ivPlus, "Order atleast one to proceed", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else if (newCount > 10) {
            Snackbar.make(ivPlus, "You can order max 10, Contact directly, see 'About Us' page.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            tvCount.setText(newCount + "");
        }
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
