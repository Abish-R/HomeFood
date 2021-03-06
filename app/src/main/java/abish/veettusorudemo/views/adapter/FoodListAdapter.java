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

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import abish.veettusorudemo.R;
import abish.veettusorudemo.Utils;
import abish.veettusorudemo.constants.Constants;
import abish.veettusorudemo.network.model.FoodDetail;
import abish.veettusorudemo.network.model.OfferDetail;
import abish.veettusorudemo.views.FoodDescriptionActivity;
import abish.veettusorudemo.views.TransformIntent;
import abish.veettusorudemo.views.UserLoginControlActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Main Food list Adapter
 * Created by Abish on 19/02/2017.
 */
public class FoodListAdapter extends RecyclerView.Adapter {
    private Context context;
    private LayoutInflater inflater;
    private List<FoodDetail> foodDetailList = new ArrayList<>();
    private TransformIntent mListener;
    private boolean isMyFavScreen;

    public FoodListAdapter(Context context, List<FoodDetail> foodDetailList,
                           TransformIntent listener, boolean isMyFavScreen) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.foodDetailList = foodDetailList;
        this.mListener = listener;
        this.isMyFavScreen = isMyFavScreen;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.item_main_dish, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder)
            try {
                ((MyViewHolder) holder).bind(foodDetailList.get(position), position);
            } catch (Exception e) {
                Log.e("Exception", e.getMessage());
            }
    }

    @Override
    public int getItemCount() {
        return foodDetailList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.share_food)
        ImageView shareFood;

        @BindView(R.id.iv_food)
        ImageView ivFood;

        @BindView(R.id.iv_minus)
        ImageView ivMinus;

        @BindView(R.id.iv_plus)
        ImageView ivPlus;

        @BindView(R.id.iv_favourite)
        ImageView ivFavourite;

        @BindView(R.id.tv_food_name)
        TextView tvFoodName;

        @BindView(R.id.tv_price)
        TextView tvPrice;

        @BindView(R.id.tv_count)
        TextView tvCount;

        @BindView(R.id.progress_bar)
        ProgressBar progressBar;

        private MyViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        private void bind(final FoodDetail foodDetailData, final int position) {
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
            if (isMyFavScreen) {
                ivPlus.setVisibility(View.GONE);
                ivMinus.setVisibility(View.GONE);
                tvCount.setVisibility(View.GONE);
            } else {
                tvCount.setText(foodDetailData.getSelectedFoodCount());
            }

            if (!foodDetailData.getFoodImageUrl().isEmpty()) {
                Picasso.with(context).load(foodDetailData.getFoodImageUrl()).into(ivFood,
                        new Callback() {
                            @Override
                            public void onSuccess() {
                                progressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError() {
                                progressBar.setVisibility(View.GONE);
                                ivFood.setImageResource(R.color.light_grey);
                            }
                        });
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
                    //Utils.shareData(context, foodDetailData.getFoodImageUrl(), foodDetailData.getFoodShortName());
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
                        mListener.onLaunchActivity(foodDetailData, position, intent, false);
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
                    final String userId = Utils.getSavedUserDetail(context, Constants.LOGIN_USER_ID);
                    if (userId != null && !userId.equals("null")) {
                        if (mListener != null) mListener.updateFavourite(userId, foodDetailData);
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                        alertDialog.setMessage("Login to set favourite foods.");
                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        context.startActivity(new Intent(context, UserLoginControlActivity.class));
                                    }
                                });
                        alertDialog.show();
                    }
                }
            });
        }

        private void setFoodCount(int count, FoodDetail foodDetailData) {
            int newCount = foodDetailData.getSelectedFoodCountNumber() + count;

            if (newCount < 0) {
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

//        private void updateFavourite(String userID, final FoodDetail foodDetail) {
//            String url = UrlConstants.GET_FAV_FOOD_URL
//                    + UrlConstants.FAV_FOOD_PARAM_USER_ID + userID
//                    + UrlConstants.FAV_FOOD_PARAM_COURSE_ID + foodDetail.getId()
//                    + UrlConstants.FAV_FOOD_PARAM_COURSE_TYPE + Constants.MAIN_COURSE_TYPE;
//            Utils.displayLoader(context, "Updating Favourite...");
//
//            GsonRequest request = new GsonRequest<>(url, null,
//                    FoodFavouriteResponse.class, null, new Response.Listener<FoodFavouriteResponse>() {
//                @Override
//                public void onResponse(FoodFavouriteResponse response) {
//                    if (response.isSuccess()) {
//                        foodDetail.setFavourite(!foodDetail.isFavourite());
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

    private void notifier() {
        notifyDataSetChanged();
    }
}
