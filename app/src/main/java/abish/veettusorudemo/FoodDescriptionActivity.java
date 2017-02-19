package abish.veettusorudemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FoodDescriptionActivity extends AppCompatActivity {

    ImageView shareFood, ivFood, ivMinus, ivPlus, ivFavourite;
    TextView tvFoodName, tvPrice, tvCount,tvDescription;
    Button btAdd,btBack;
    RelativeLayout controls;
    boolean isFoodFavEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_description);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        shareFood = (ImageView) findViewById(R.id.share_food);
        ivFood = (ImageView) findViewById(R.id.iv_food);
        ivMinus = (ImageView) findViewById(R.id.iv_minus);
        ivPlus = (ImageView) findViewById(R.id.iv_plus);
        ivFavourite = (ImageView) findViewById(R.id.iv_favourite);

        tvFoodName = (TextView) findViewById(R.id.tv_food_name);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvCount = (TextView) findViewById(R.id.tv_count);
        tvDescription = (TextView) findViewById(R.id.tv_description);

        btAdd = (Button) findViewById(R.id.bt_add);
        btBack = (Button) findViewById(R.id.bt_back);
        controls = (RelativeLayout) findViewById(R.id.controls);

        Bundle extras = getIntent().getExtras().getBundle("BUNDLE");

        if (extras != null) {
            tvFoodName.setText(extras.getString("food_name"));
            tvCount.setText(extras.getString("quantity"));
            tvPrice.setText(extras.getString("food_price"));
            tvDescription.setText("Lorem Ipsum is simply dummy text of the printing and typesetting industry. " +
                    "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown " +
                    "printer took a galley of type and scrambled it to make a type specimen book. It has survived " +
                    "not only five centuries, but also the leap into electronic typesetting, remaining essentially " +
                    "unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem " +
                    "Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including " +
                    "versions of Lorem Ipsum.");

            boolean fav = extras.getBoolean("isFavourite");
            //int imageId = extras.getInt("drawable_src");

            if(fav){
                ivFavourite.setBackgroundResource(R.drawable.heart_filled);
            } else{
                ivFavourite.setBackgroundResource(R.drawable.heart_outline);
            }
            //ivFood.setBackgroundResource(imageId);
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
                    ivFavourite.setBackgroundResource(R.drawable.heart_filled);
                }
                else {
                    isFoodFavEnabled = false;
                    ivFavourite.setBackgroundResource(R.drawable.heart_outline);
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
                finish();
            }
        });
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
}
