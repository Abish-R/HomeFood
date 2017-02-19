package abish.veettusorudemo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abish on 19/02/2017.
 */
public class FoodListAdapter extends RecyclerView.Adapter {
    Context context;
    LayoutInflater inflater;
    List<FoodGetter> list = new ArrayList<>();

    public FoodListAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        list = getFoodList();
    }

    private List getFoodList() {
        List list = new ArrayList<>();
        FoodGetter fg = new FoodGetter();
        fg.setFoodName("CHICKEN RICE");
        fg.setFoodPrice("Rs. 100");
        fg.setFoodCount(0);
        fg.setFoodImage(context.getDrawable(R.drawable.egg_fried_rice));
        fg.setFavouriteEnabled(false);;
        list.add(fg);
        FoodGetter fg1 = new FoodGetter();
        fg1.setFoodName("TOMATTO RICE");
        fg1.setFoodPrice("Rs. 50");
        fg1.setFoodCount(0);
        fg1.setFoodImage(context.getDrawable(R.drawable.tomato));
        fg1.setFavouriteEnabled(false);;
        list.add(fg1);
        FoodGetter fg2 = new FoodGetter();
        fg2.setFoodName("CURD RICE");
        fg2.setFoodPrice("Rs. 50");
        fg2.setFoodCount(0);
        fg2.setFoodImage(context.getDrawable(R.drawable.curd_rice));
        fg2.setFavouriteEnabled(false);;
        list.add(fg2);
        FoodGetter fg3 = new FoodGetter();
        fg3.setFoodName("LEMON RICE");
        fg3.setFoodPrice("Rs. 50");
        fg3.setFoodCount(0);
        fg3.setFoodImage(context.getDrawable(R.drawable.lemon_rice));
        fg3.setFavouriteEnabled(false);;
        list.add(fg3);
        FoodGetter fg4 = new FoodGetter();
        fg4.setFoodName("TAMARIND RICE");
        fg4.setFoodPrice("Rs. 50");
        fg4.setFoodCount(0);
        fg4.setFoodImage(context.getDrawable(R.drawable.tamarind_rice));
        fg4.setFavouriteEnabled(false);;
        list.add(fg4);
        FoodGetter fg5 = new FoodGetter();
        fg5.setFoodName("VEG FRIED RICE");
        fg5.setFoodPrice("Rs. 100");
        fg5.setFoodCount(0);
        fg5.setFoodImage(context.getDrawable(R.drawable.veg_fried_rice));
        fg5.setFavouriteEnabled(false);;
        list.add(fg5);
        FoodGetter fg6 = new FoodGetter();
        fg6.setFoodName("VEG BRIYANI");
        fg6.setFoodPrice("Rs. 150");
        fg6.setFoodCount(0);
        fg6.setFoodImage(context.getDrawable(R.drawable.veg_briyani));
        fg6.setFavouriteEnabled(false);;
        list.add(fg6);
        FoodGetter fg7 = new FoodGetter();
        fg7.setFoodName("CHICKEN BRIYANI");
        fg7.setFoodPrice("Rs. 200");
        fg7.setFoodCount(0);
        fg7.setFoodImage(context.getDrawable(R.drawable.chicken_briyani));
        fg7.setFavouriteEnabled(false);;
        list.add(fg7);
        FoodGetter fg8 = new FoodGetter();
        fg8.setFoodName("MUTTON BRIYANI");
        fg8.setFoodPrice("Rs. 250");
        fg8.setFoodCount(0);
        fg8.setFoodImage(context.getDrawable(R.drawable.mutton_briyani));
        fg8.setFavouriteEnabled(false);;
        list.add(fg8);
        FoodGetter fg9 = new FoodGetter();
        fg9.setFoodName("BEAF BRIYANI");
        fg9.setFoodPrice("Rs. 250");
        fg9.setFoodCount(0);
        fg9.setFoodImage(context.getDrawable(R.drawable.beaf_briyani));
        fg9.setFavouriteEnabled(false);;
        list.add(fg9);
        FoodGetter fg10 = new FoodGetter();
        fg10.setFoodName("CHAPATHI");
        fg10.setFoodPrice("Rs. 60");
        fg10.setFoodCount(0);
        fg10.setFoodImage(context.getDrawable(R.drawable.chapatis));
        fg10.setFavouriteEnabled(false);;
        list.add(fg10);
        FoodGetter fg11 = new FoodGetter();
        fg11.setFoodName("PAROTTA");
        fg11.setFoodPrice("Rs. 60");
        fg11.setFoodCount(0);
        fg11.setFoodImage(context.getDrawable(R.drawable.parotta));
        fg11.setFavouriteEnabled(false);;
        list.add(fg11);
        FoodGetter fg12 = new FoodGetter();
        fg12.setFoodName("CHICKEN FRIED RICE");
        fg12.setFoodPrice("Rs. 150");
        fg12.setFoodCount(0);
        fg12.setFoodImage(context.getDrawable(R.drawable.egg_fried_rice));
        fg12.setFavouriteEnabled(false);;
        list.add(fg12);
        FoodGetter fg13 = new FoodGetter();
        fg13.setFoodName("MUTTON FRIED RICE");
        fg13.setFoodPrice("Rs. 180");
        fg13.setFoodCount(0);
        fg13.setFoodImage(context.getDrawable(R.drawable.egg_fried_rice));
        fg13.setFavouriteEnabled(false);
        list.add(fg13);
        FoodGetter fg14 = new FoodGetter();
        fg14.setFoodName("EGG FRIED RICE");
        fg14.setFoodPrice("Rs. 110");
        fg14.setFoodCount(0);
        fg14.setFoodImage(context.getDrawable(R.drawable.egg_fried_rice));
        fg14.setFavouriteEnabled(false);
        list.add(fg14);
        FoodGetter fg15 = new FoodGetter();
        fg15.setFoodName("MUSHROOM FRIED RICE");
        fg15.setFoodPrice("Rs. 120");
        fg15.setFoodCount(0);
        fg15.setFoodImage(context.getDrawable(R.drawable.mushroom_fried_rice));
        fg15.setFavouriteEnabled(false);
        list.add(fg15);
        FoodGetter fg16 = new FoodGetter();
        fg16.setFoodName("PANNEER FRIED RICE");
        fg16.setFoodPrice("Rs. 120");
        fg16.setFoodCount(0);
        fg16.setFoodImage(context.getDrawable(R.drawable.mushroom_fried_rice));
        fg16.setFavouriteEnabled(false);
        list.add(fg16);
        FoodGetter fg17 = new FoodGetter();
        fg17.setFoodName("VEG SPECIAL RICE");
        fg17.setFoodPrice("Rs. 150");
        fg17.setFoodCount(0);
        fg17.setFoodImage(context.getDrawable(R.drawable.rice));
        fg17.setFavouriteEnabled(false);
        list.add(fg17);
        FoodGetter fg18 = new FoodGetter();
        fg18.setFoodName("CHICKEN SPECIAL RICE");
        fg18.setFoodPrice("Rs. 180");
        fg18.setFoodCount(0);
        fg18.setFoodImage(context.getDrawable(R.drawable.chicken_briyani));
        fg18.setFavouriteEnabled(false);
        list.add(fg18);
        FoodGetter fg19 = new FoodGetter();
        fg19.setFoodName("CHICKEN SPECIAL RICE");
        fg19.setFoodPrice("Rs. 200");
        fg19.setFoodCount(0);
        fg19.setFoodImage(context.getDrawable(R.drawable.beaf_briyani));
        fg19.setFavouriteEnabled(false);
        list.add(fg19);
        FoodGetter fg20 = new FoodGetter();
        fg20.setFoodName("Mutton SPECIAL RICE");
        fg20.setFoodPrice("Rs. 220");
        fg20.setFoodCount(0);
        fg20.setFoodImage(context.getDrawable(R.drawable.mutton_briyani));
        fg20.setFavouriteEnabled(false);
        list.add(fg20);
        return list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.item_main_dish, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder)
            ((MyViewHolder) holder).bind(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView shareFood, ivFood, ivMinus, ivPlus, ivFavourite;
        TextView tvFoodName, tvPrice, tvCount;

        public MyViewHolder(View itemView) {
            super(itemView);
            shareFood = (ImageView) itemView.findViewById(R.id.share_food);
            ivFood = (ImageView) itemView.findViewById(R.id.iv_food);
            ivMinus = (ImageView) itemView.findViewById(R.id.iv_minus);
            ivPlus = (ImageView) itemView.findViewById(R.id.iv_plus);
            ivFavourite = (ImageView) itemView.findViewById(R.id.iv_favourite);

            tvFoodName = (TextView) itemView.findViewById(R.id.tv_food_name);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
            tvCount = (TextView) itemView.findViewById(R.id.tv_count);
        }

        public void bind(final int position) {
            tvFoodName.setText(list.get(position).getFoodName());
            tvPrice.setText("Price: " + list.get(position).getFoodPrice());
            tvCount.setText("0");

            ivFood.setImageDrawable(list.get(position).getFoodImage());

            shareFood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(context)
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
                    Intent intent = new Intent(context, FoodDescriptionActivity.class);
                    Bundle bundle =new Bundle();
                    bundle.putString("food_name", list.get(position).getFoodName());
                    bundle.putString("quantity", tvCount.getText().toString());
                    bundle.putString("food_price", list.get(position).getFoodPrice());
                    bundle.putBoolean("isFavourite", list.get(position).isFoodFavEnabled());
                    bundle.putInt("drawable_src", R.drawable.ic_menu_gallery);
                    intent.putExtra("BUNDLE",bundle);
                    context.startActivity(intent);
                }
            });

            ivMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setFoodCount(-1, position);
                }
            });

            ivPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setFoodCount(1, position);
                }
            });

            ivFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!list.get(position).isFoodFavEnabled()) {
                        list.get(position).setFavouriteEnabled(true);
                        ivFavourite.setBackgroundResource(R.drawable.heart_filled);
                    }
                    else {
                        list.get(position).setFavouriteEnabled(false);
                        ivFavourite.setBackgroundResource(R.drawable.heart_outline);
                    }
                }
            });
        }

        private void setFoodCount(int count, int position) {
            int newCount = list.get(position).getFoodCount() + count;

            if (newCount <= 0) {
                Snackbar.make(ivPlus, "Order atleast one to proceed", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } else if (newCount > 10) {
                Snackbar.make(ivPlus, "You can order max 10, Contact directly, see 'About Us' page.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } else {
                list.get(position).setFoodCount(newCount);
                tvCount.setText(newCount + "");
            }
        }
    }
}
