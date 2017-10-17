package abish.veettusorudemo.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import abish.veettusorudemo.R;
import abish.veettusorudemo.network.response.FoodDetail;

/**
 * Created by Abish on 10/15/2017.
 */

class FoodDeliveryListAdapter extends RecyclerView.Adapter {
    private Context context;
    private LayoutInflater inflater;
    private List<FoodDetail> deliveryFoodList;

    public FoodDeliveryListAdapter(Context context, List<FoodDetail> deliveryFoodList) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.deliveryFoodList = deliveryFoodList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.item_side_dish, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder)
            ((MyViewHolder) holder).bind(deliveryFoodList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView shareFood, ivFood, ivMinus, ivPlus, ivFavourite;
        TextView tvFoodName, tvPrice, tvCount, tvDescription;
        CheckBox cbSubFood;

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

            cbSubFood = (CheckBox) itemView.findViewById(R.id.cb_sub_food);
        }

        public void bind(final FoodDetail foodDetail, final int position) {
            tvFoodName.setText(foodDetail.getFoodName());
            tvPrice.setText(foodDetail.getPrice());
            tvDescription.setText(foodDetail.getShortDescription());
            tvCount.setText(foodDetail.getSelectedFoodCount() + "");
            cbSubFood.setChecked(foodDetail.isChecked());

            if (!foodDetail.getFoodImageUrl().isEmpty()) {
                Glide.with(context)
                        .load(foodDetail.getFoodImageUrl())
                        .error(R.drawable.heart_filled)
                        .placeholder(R.drawable.heart_outline)
                        .into(ivFood);
            }

            cbSubFood.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    foodDetail.setChecked(isChecked);
//                    if (isChecked)
//                        setFoodCount(1, position);
//                    else
//                        setFoodCount(-1, position);
                }
            });

            shareFood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    new AlertDialog.Builder(context)
//                            .setMessage("Food Details will be shared in Social Media. Will be handled later on.")
//                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                }
//                            }).show();
                }
            });

//            ivFood.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(mListener != null) {
//                        Intent intent = new Intent(context, FoodDescriptionActivity.class);
//                        intent.putExtra(Constants.SELECTED_FOOD, foodDetail);
//                        //context.startActivity(intent);
//                        mListener.onLaunchActivity(intent, false);
//                    }
//                }
//            });
//
//            ivMinus.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    setFoodCount(-1, position);
//                }
//            });
//
//            ivPlus.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    setFoodCount(1, position);
//                }
//            });

            ivFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //updateFavourite(subCourseList.get(position));
//                    if (!foodDetail.isFavourite()) {
//                        foodDetail.setFavourite(true);
//                        ivFavourite.setImageResource(R.drawable.heart_filled);
//                    } else {
//                        foodDetail.setFavourite(false);
//                        ivFavourite.setImageResource(R.drawable.heart_outline);
//                    }
                }
            });
        }

//        private void updateFavourite(final FoodDetail foodDetailData) {
//            //TODO : Change hard coded values in UrlConstants
//            String url = UrlConstants.GET_FAV_FOOD_URL + UrlConstants.FAV_FOOD_PARAM_USER_ID + ""
//                    + UrlConstants.FAV_FOOD_PARAM_COURSE_ID + ""
//                    + UrlConstants.FAV_FOOD_PARAM_COURSE_TYPE + "";
//            Utils.displayLoader(context, "Updating Favourite...");
//
//            GsonRequest request = new GsonRequest<FoodFavouriteResponse>(url, null,
//                    FoodFavouriteResponse.class, null, new Response.Listener<FoodFavouriteResponse>() {
//                @Override
//                public void onResponse(FoodFavouriteResponse response) {
//                    if (response.isSuccess()) {
//                        foodDetailData.setFavourite(!foodDetailData.isFavourite());
//                        notifier();
//                    }
//                    hideLoader();
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    VolleyLog.d("Error: " + error.getMessage());
//                    hideLoader();
//                }
//            });
//            VolleyApiClient.getInstance().addToRequestQueue(request, "Food Categories");
//        }
    }
}
