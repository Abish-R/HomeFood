package abish.veettusorudemo.views.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

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
import abish.veettusorudemo.views.UserLoginControlActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import static abish.veettusorudemo.R.id.progress_bar;
import static abish.veettusorudemo.Utils.hideLoader;

/**
 * Created by Abish on 19/02/2017.
 * </p>
 */
public class SubFoodListAdapter extends RecyclerView.Adapter {
    private Context context;
    private LayoutInflater inflater;
    private List<FoodDetail> subCourseList;
    private TransformIntent mListener;

    public SubFoodListAdapter(Context context, List<FoodDetail> subCourseList, TransformIntent listener) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.subCourseList = subCourseList;
        this.mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.item_side_dish, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder)
            ((MyViewHolder) holder).bind(subCourseList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return subCourseList.size();
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

        @BindView(R.id.tv_description)
        TextView tvDescription;

        @BindView(R.id.cb_sub_food)
        CheckBox cbSubFood;

        @BindView(progress_bar)
        ProgressBar progressBar;

        private MyViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        public void bind(final FoodDetail foodDetail, final int position) {
            tvFoodName.setText(foodDetail.getFoodName());
            OfferDetail offerDetail = !foodDetail.getOfferDetails().isEmpty() ? foodDetail.getOfferDetails().get(0) : null;
            StringBuilder actualPrice = new StringBuilder();
            if (offerDetail != null) {
                String offerPrice = !offerDetail.getOfferPrice().isEmpty() ? offerDetail.getOfferPrice() : null;
                String offerPercentage = !offerDetail.getOfferPricePercentage().isEmpty() ? offerDetail.getOfferPricePercentage() : null;
                int offerValueOnPercentage = offerPercentage != null ? Integer.parseInt(offerPercentage.substring(0, offerPercentage.length() - 1)) / 100 : 0;
                int price = offerPrice != null ? Integer.parseInt(offerPrice) :
                        (Integer.parseInt(foodDetail.getPrice()) * offerValueOnPercentage);
                String finalFoodPrice = String.valueOf(Integer.parseInt(foodDetail.getPrice()) - price);
                actualPrice.append("Price: ").append(foodDetail.getPrice()).
                        append(" ").append(finalFoodPrice);
                int strikeTroughLength = actualPrice.length() - finalFoodPrice.length();
                tvPrice.setText(actualPrice.toString(), TextView.BufferType.SPANNABLE);
                Spannable spannable = (Spannable) tvPrice.getText();
                spannable.setSpan(new StrikethroughSpan(), 7, strikeTroughLength, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                foodDetail.setPriceAfterOffer(finalFoodPrice);
            } else if (foodDetail.isReallyFree()) {
                tvPrice.setText(context.getString(R.string.text_free));
            } else {
                actualPrice.append("Price: ").append(foodDetail.getPrice());
                tvPrice.setText(actualPrice.toString());
                foodDetail.setPriceAfterOffer(foodDetail.getPrice());
            }
            tvDescription.setText(foodDetail.getShortDescription());
            tvCount.setText(foodDetail.getSelectedFoodCount());
            cbSubFood.setChecked(foodDetail.isChecked());
            foodDetail.setPriceAfterOffer(foodDetail.getPrice());

            if (!foodDetail.getFoodImageUrl().isEmpty()) {
                Picasso.with(context).load(foodDetail.getFoodImageUrl()).into(ivFood,
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

            cbSubFood.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked && !foodDetail.isChecked())
                        setFoodCount(1, position);
                    else if (!isChecked && foodDetail.isChecked())
                        setFoodCount(0, position);
                }
            });

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
                        mListener.onLaunchActivity(foodDetail, intent, false);
                    }
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
                    final String userId = Utils.getSavedUserDetail(context, Constants.LOGIN_USER_ID);
                    if (userId != null && !userId.equals("null")) {
                        updateFavourite(userId, subCourseList.get(position));
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

        private void setFoodCount(int count, int position) {
            int newCount = subCourseList.get(position).getSelectedFoodCountNumber() + count;

            if (count == 0 || newCount == 0) {
                updateFoodSelectionAndCount(position, 0, false);
            } else if (newCount < 0) {
                Snackbar.make(ivPlus, "You already deselected the food", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } else if (newCount > 10) {
                Snackbar.make(ivPlus, "You can order max 10, Contact directly, see 'About Us' page.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } else {
                updateFoodSelectionAndCount(position, newCount, true);
            }
        }

        private void updateFoodSelectionAndCount(int position, int newCount, boolean isChecked) {
            subCourseList.get(position).setSelectedFoodCount(newCount);
            tvCount.setText(context.getString(R.string.empty_string, newCount));
            subCourseList.get(position).setChecked(isChecked);
            notifier();
        }

        private void updateFavourite(String userID, final FoodDetail foodDetailData) {
            //TODO : Change hard coded values in UrlConstants
            String url = UrlConstants.GET_FAV_FOOD_URL
                    + UrlConstants.FAV_FOOD_PARAM_USER_ID + userID
                    + UrlConstants.FAV_FOOD_PARAM_COURSE_ID + foodDetailData.getId()
                    + UrlConstants.FAV_FOOD_PARAM_COURSE_TYPE + Constants.SUB_COURSE_TYPE;
            Utils.displayLoader(context, "Updating Favourite...");

            GsonRequest request = new GsonRequest<>(url, null,
                    FoodFavouriteResponse.class, null, new Response.Listener<FoodFavouriteResponse>() {
                @Override
                public void onResponse(FoodFavouriteResponse response) {
                    if (response.isSuccess()) {
                        foodDetailData.setFavourite(!foodDetailData.isFavourite());
//                        if (!foodDetailData.isFavourite()) {
//                            foodDetailData.setFavourite(true);
//                            ivFavourite.setImageResource(R.drawable.heart_filled);
//                        } else {
//                            foodDetailData.setFavourite(false);
//                            ivFavourite.setImageResource(R.drawable.heart_outline);
//                        }
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
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }
}
