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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

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
import butterknife.Bind;
import butterknife.ButterKnife;

import static abish.veettusorudemo.Utils.hideLoader;

/**
 * Main Food list Adapter
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
            try {
                ((MyViewHolder) holder).bind(position);
            } catch (Exception e) {
                Log.e("Exception", e.getMessage());
            }
    }

    @Override
    public int getItemCount() {
        return foodDetailList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

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

        @Bind(R.id.progress_bar)
        ProgressBar progressBar;

        private MyViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        private void bind(final int position) {
            final FoodDetail foodDetailData = foodDetailList.get(position);

            tvFoodName.setText(foodDetailData.getFoodName());
            OfferDetail offerDetail = !foodDetailData.getOfferDetails().isEmpty() ? foodDetailData.getOfferDetails().get(0) : null;
            StringBuilder actualPrice = new StringBuilder();
            if (offerDetail != null) {
                String offerPrice = !offerDetail.getOfferPrice().isEmpty() ? offerDetail.getOfferPrice() : null;
                String offerPercentage = !offerDetail.getOfferPricePercentage().isEmpty() ? offerDetail.getOfferPricePercentage() : null;
                int offerValueOnPercentage = offerPercentage != null ? Integer.parseInt(offerPercentage.substring(0, offerPercentage.length() - 1)) / 100 : 0;
                int price = offerPrice != null ? Integer.parseInt(offerPrice) :
                        (Integer.parseInt(foodDetailData.getPrice()) * offerValueOnPercentage);
                String finalFoodPrice = String.valueOf(Integer.parseInt(foodDetailData.getPrice()) - price);
                actualPrice.append("Price: ").append(foodDetailData.getPrice()).
                        append(" ").append(finalFoodPrice);
                int strikeTroughLength = actualPrice.length() - finalFoodPrice.length();
                tvPrice.setText(actualPrice.toString(), TextView.BufferType.SPANNABLE);
                Spannable spannable = (Spannable) tvPrice.getText();
                spannable.setSpan(new StrikethroughSpan(), 7, strikeTroughLength, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                foodDetailData.setPriceAfterOffer(finalFoodPrice);
            } else {
                actualPrice.append("Price: ").append(foodDetailData.getPrice());
                tvPrice.setText(actualPrice.toString());
                foodDetailData.setPriceAfterOffer(foodDetailData.getPrice());
            }
            tvCount.setText("0");

            if (!foodDetailData.getFoodImageUrl().isEmpty()) {
                Glide.with(context)
                        .load(foodDetailData.getFoodImageUrl())
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                progressBar.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                progressBar.setVisibility(View.GONE);
                                ivFood.setImageResource(R.drawable.heart_outline);
                                return false;
                            }
                        })
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
                    if (mListener != null) {
                        Intent intent = new Intent(context, FoodDescriptionActivity.class);
                        mListener.onLaunchActivity(foodDetailData, intent, false);
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
            int newCount = foodDetailData.getSelectedFoodCountNumber() + count;

            if (newCount <= 0) {
                Snackbar.make(ivPlus, "Order atleast one to proceed", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } else if (newCount > 10) {
                Snackbar.make(ivPlus, "You can order max 10, Contact directly, see 'About Us' page.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } else {
                foodDetailData.setSelectedFoodCount(newCount);
                tvCount.setText(String.valueOf(newCount));
            }
        }

        private void updateFavourite(final FoodDetail foodDetailData) {
            //TODO : Change hard coded values in UrlConstants
            String url = UrlConstants.GET_FAV_FOOD_URL
                    + UrlConstants.FAV_FOOD_PARAM_USER_ID + Utils.getSavedUserDetail(context, Constants.LOGIN_USER_ID)
                    + UrlConstants.FAV_FOOD_PARAM_COURSE_ID + foodDetailData.getId()
                    + UrlConstants.FAV_FOOD_PARAM_COURSE_TYPE + Constants.MAIN_COURSE_TYPE;
            Utils.displayLoader(context, "Updating Favourite...");

            GsonRequest request = new GsonRequest<>(url, null,
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
