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
public class SubFoodListAdapter extends RecyclerView.Adapter {
    Context context;
    LayoutInflater inflater;
    List<FoodGetter> list = new ArrayList<>();

    public SubFoodListAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        list = getFoodList();
    }

    private List getFoodList() {
        List list = new ArrayList<>();
        FoodGetter fg = new FoodGetter();
        fg.setFoodName("CURD");
        fg.setFoodPrice("Rs. 20");
        fg.setFoodCount(0);
        fg.setFoodDescription("Normal Curd with sodium salt");
        fg.setFoodImage(context.getDrawable(R.drawable.curd));
        fg.setFavouriteEnabled(false);
        list.add(fg);
        FoodGetter fg1 = new FoodGetter();
        fg1.setFoodName("CHICKEN 65");
        fg1.setFoodPrice("Rs. 120");
        fg1.setFoodCount(0);
        fg1.setFoodDescription("South Indian moderate spicy special dish");
        fg1.setFoodImage(context.getDrawable(R.drawable.chicken65));
        fg1.setFavouriteEnabled(false);
        list.add(fg1);
        FoodGetter fg2 = new FoodGetter();
        fg2.setFoodName("CHILLY CHICKEN");
        fg2.setFoodPrice("Rs. 150");
        fg2.setFoodCount(0);
        fg2.setFoodDescription("South Indian moderate spicy special dish");
        fg2.setFoodImage(context.getDrawable(R.drawable.chilli_hicken));
        fg2.setFavouriteEnabled(false);
        list.add(fg2);
        FoodGetter fg3 = new FoodGetter();
        fg3.setFoodName("MUTTON FRY");
        fg3.setFoodPrice("Rs. 250");
        fg3.setFoodCount(0);
        fg3.setFoodDescription("South Indian moderate spicy special dish");
        fg3.setFoodImage(context.getDrawable(R.drawable.mutton_fry));
        fg3.setFavouriteEnabled(false);
        list.add(fg3);
        FoodGetter fg4 = new FoodGetter();
        fg4.setFoodName("MUSHROOM DRIED");
        fg4.setFoodPrice("Rs. 100");
        fg4.setFoodCount(0);
        fg4.setFoodDescription("South Indian moderate spicy special dish");
        fg4.setFoodImage(context.getDrawable(R.drawable.mushroom_fry));
        fg4.setFavouriteEnabled(false);
        list.add(fg4);
        FoodGetter fg5 = new FoodGetter();
        fg5.setFoodName("PANNEER DRIED");
        fg5.setFoodPrice("Rs. 100");
        fg5.setFoodCount(0);
        fg5.setFoodDescription("South Indian moderate spicy special dish");
        fg5.setFoodImage(context.getDrawable(R.drawable.paneer_fry));
        fg5.setFavouriteEnabled(false);
        list.add(fg5);
        FoodGetter fg6 = new FoodGetter();
        fg6.setFoodName("DAL SOUTH");
        fg6.setFoodPrice("Rs. 50");
        fg6.setFoodCount(0);
        fg6.setFoodDescription("South Indian moderate spicy special dish");
        fg6.setFoodImage(context.getDrawable(R.drawable.dal_fry));
        fg6.setFavouriteEnabled(false);
        list.add(fg6);
        FoodGetter fg7 = new FoodGetter();
        fg7.setFoodName("SPECIAL DAL");
        fg7.setFoodPrice("Rs. 80");
        fg7.setFoodCount(0);
        fg7.setFoodDescription("South Indian moderate spicy special dish");
        fg7.setFoodImage(context.getDrawable(R.drawable.dal));
        fg7.setFavouriteEnabled(false);
        list.add(fg7);
        FoodGetter fg8 = new FoodGetter();
        fg8.setFoodName("CHICKEN KOLAMBU");
        fg8.setFoodPrice("Rs. 80");
        fg8.setFoodCount(0);
        fg8.setFoodDescription("South Indian moderate spicy special dish");
        fg8.setFoodImage(context.getDrawable(R.drawable.chilli_hicken));
        fg8.setFavouriteEnabled(false);
        list.add(fg8);
        FoodGetter fg9 = new FoodGetter();
        fg9.setFoodName("MUTTON KULAMBU");
        fg9.setFoodPrice("Rs. 100");
        fg9.setFoodCount(0);
        fg9.setFoodDescription("South Indian moderate spicy special dish");
        fg9.setFoodImage(context.getDrawable(R.drawable.mutton));
        fg9.setFavouriteEnabled(false);
        list.add(fg9);
        FoodGetter fg10 = new FoodGetter();
        fg10.setFoodName("DRIED PRON");
        fg10.setFoodPrice("Rs. 200");
        fg10.setFoodCount(0);
        fg10.setFoodDescription("South Indian moderate spicy special dish");
        fg10.setFoodImage(context.getDrawable(R.drawable.prons_fry));
        fg10.setFavouriteEnabled(false);
        list.add(fg10);
        FoodGetter fg11 = new FoodGetter();
        fg11.setFoodName("DRIED CRAB");
        fg11.setFoodPrice("Rs. 150");
        fg11.setFoodCount(0);
        fg11.setFoodDescription("South Indian moderate spicy special dish");
        fg11.setFoodImage(context.getDrawable(R.drawable.crab_curry));
        fg11.setFavouriteEnabled(false);
        list.add(fg11);
        FoodGetter fg12 = new FoodGetter();
        fg12.setFoodName("PRON FRY");
        fg12.setFoodPrice("Rs. 100");
        fg12.setFoodCount(0);
        fg12.setFoodDescription("South Indian moderate spicy special dish");
        fg12.setFoodImage(context.getDrawable(R.drawable.prons_fry));
        fg12.setFavouriteEnabled(false);
        list.add(fg12);
        FoodGetter fg13 = new FoodGetter();
        fg13.setFoodName("CHICKEN KOLAMBU");
        fg13.setFoodPrice("Rs. 100");
        fg13.setFoodCount(0);
        fg13.setFoodDescription("South Indian moderate spicy special dish");
        fg13.setFoodImage(context.getDrawable(R.drawable.chilli_hicken));
        fg13.setFavouriteEnabled(false);
        list.add(fg13);
        FoodGetter fg14 = new FoodGetter();
        fg14.setFoodName("PRONS CURRY");
        fg14.setFoodPrice("Rs. 100");
        fg14.setFoodCount(0);
        fg14.setFoodDescription("South Indian moderate spicy special dish");
        fg14.setFoodImage(context.getDrawable(R.drawable.prons_curry));
        fg14.setFavouriteEnabled(false);
        list.add(fg14);
        FoodGetter fg15 = new FoodGetter();
        fg15.setFoodName("CRAB CURRY");
        fg15.setFoodPrice("Rs. 100");
        fg15.setFoodCount(0);
        fg15.setFoodDescription("South Indian moderate spicy special dish");
        fg15.setFoodImage(context.getDrawable(R.drawable.crab_curry));
        fg15.setFavouriteEnabled(false);
        list.add(fg15);
        return list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.item_side_dish, parent, false));
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
        TextView tvFoodName, tvPrice, tvCount, tvDescription;

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
            tvDescription = (TextView) itemView.findViewById(R.id.tv_description);
        }

        public void bind(final int position) {
            tvFoodName.setText(list.get(position).getFoodName());
            tvPrice.setText(list.get(position).getFoodPrice());
            tvCount.setText("0");
            tvDescription.setText(list.get(position).getFoodDescription());

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
