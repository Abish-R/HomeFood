package abish.veettusorudemo.views.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import abish.veettusorudemo.R;
import abish.veettusorudemo.Utils;
import abish.veettusorudemo.constants.Constants;
import abish.veettusorudemo.constants.UrlConstants;
import abish.veettusorudemo.network.GsonRequest;
import abish.veettusorudemo.network.VolleyApiClient;
import abish.veettusorudemo.network.response.FoodDetail;
import abish.veettusorudemo.network.response.FoodFavouriteResponse;
import abish.veettusorudemo.network.response.OfferDetail;
import abish.veettusorudemo.views.FoodDescriptionActivity;
import abish.veettusorudemo.views.TransformIntent;

import static abish.veettusorudemo.Utils.hideLoader;

/**
 * Created by Abish on 19/02/2017.
 */
public class FoodListAdapter extends RecyclerView.Adapter {
    private Context context;
    private LayoutInflater inflater;
    private List<FoodDetail> foodDetailList = new ArrayList<>();
    private TransformIntent mListener;

    public FoodListAdapter(Context context, List<FoodDetail> foodDetailList, TransformIntent listener) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.foodDetailList = foodDetailList;
        this.mListener = listener;
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
        return foodDetailList.size();
    }


    private class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView shareFood, ivFood, ivMinus, ivPlus, ivFavourite;
        TextView tvFoodName, tvPrice, tvCount;

        private MyViewHolder(View itemView) {
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

        private void bind(final int position) {
            final FoodDetail foodDetailData = foodDetailList.get(position);

            tvFoodName.setText(foodDetailData.getFoodName());
            OfferDetail offerDetail = !foodDetailData.getOfferDetails().isEmpty() ? foodDetailData.getOfferDetails().get(0) : null;
            StringBuilder actualPrice = new StringBuilder();
            if (offerDetail != null) {
                String offerPrice = !offerDetail.getOfferPrice().isEmpty() ? offerDetail.getOfferPrice() : null;
                String offerPercentage = !offerDetail.getOfferPricePercentage().isEmpty() ? offerDetail.getOfferPricePercentage() : null;
                int price = offerPrice != null ? Integer.parseInt(offerPrice) :
                        (Integer.parseInt(foodDetailData.getPrice()) * (Integer.parseInt(offerPercentage.substring(0, 2)) / 100));
                String finalFoodPrice = String.valueOf(Integer.parseInt(foodDetailData.getPrice()) - price);
                actualPrice.append("Price: ").append(foodDetailData.getPrice()).
                        append(" ").append(finalFoodPrice);
                int strikeTroughLength = actualPrice.length() - finalFoodPrice.length();
                tvPrice.setText(actualPrice.toString(), TextView.BufferType.SPANNABLE);
                Spannable spannable = (Spannable) tvPrice.getText();
                spannable.setSpan(new StrikethroughSpan(), 7, strikeTroughLength, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvCount.setText("0");
            } else {
                actualPrice.append("Price: ").append(foodDetailData.getPrice());
                tvPrice.setText(actualPrice.toString());
            }
            tvCount.setText("0");

            if (!foodDetailData.getFoodImageUrl().isEmpty()) {
                Glide.with(context)
                        .load(foodDetailData.getFoodImageUrl())
                        .error(R.drawable.heart_filled)
                        .placeholder(R.drawable.heart_outline)
                        .into(ivFood);
            }

            if (!foodDetailData.isFavourite()) {
                foodDetailData.setFavourite(true);
                ivFavourite.setImageResource(R.drawable.heart_filled);
            } else {
                foodDetailData.setFavourite(false);
                ivFavourite.setImageResource(R.drawable.heart_outline);
            }

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
                    if(mListener != null) {
                        Intent intent = new Intent(context, FoodDescriptionActivity.class);
                        intent.putExtra(Constants.SELECTED_FOOD, foodDetailData);
                        //context.startActivity(intent);
                        mListener.onLaunchActivity(intent, false);
                    }
                }
            });

            ivMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setFoodCount(-1, foodDetailData);
                }
            });

            ivPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setFoodCount(1, foodDetailData);
                }
            });

            ivFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateFavourite(foodDetailList.get(position));
                }
            });
        }

        private void setFoodCount(int count, FoodDetail foodDetailData) {
            int newCount = foodDetailData.getSelectedFoodCount() + count;

            if (newCount <= 0) {
                Snackbar.make(ivPlus, "Order atleast one to proceed", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } else if (newCount > 10) {
                Snackbar.make(ivPlus, "You can order max 10, Contact directly, see 'About Us' page.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } else {
                foodDetailData.setSelectedFoodCount(newCount);
                tvCount.setText(newCount + "");
            }
        }

        private void updateFavourite(final FoodDetail foodDetailData) {
            //TODO : Change hard coded values in UrlConstants
            String url = UrlConstants.GET_FAV_FOOD_URL + UrlConstants.FAV_FOOD_PARAM_USER_ID + ""
                    + UrlConstants.FAV_FOOD_PARAM_COURSE_ID + ""
                    + UrlConstants.FAV_FOOD_PARAM_COURSE_TYPE + "";
            Utils.displayLoader(context, "Updating Favourite...");

            GsonRequest request = new GsonRequest<FoodFavouriteResponse>(url, null,
                    FoodFavouriteResponse.class, null, new Response.Listener<FoodFavouriteResponse>() {
                @Override
                public void onResponse(FoodFavouriteResponse response) {
                    if (response.isSuccess()) {
                        foodDetailData.setFavourite(!foodDetailData.isFavourite());
                        notifier();
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
    }

    private void notifier() {
        notifyDataSetChanged();
    }
}
