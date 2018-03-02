package abish.veettusorudemo.views.adapter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import abish.veettusorudemo.R;
import abish.veettusorudemo.network.response.FoodDetail;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abish on 10/15/2017.
 * </p>
 */

public class AllOrderCheckListAdapter extends RecyclerView.Adapter {
    private Context context;
    private LayoutInflater inflater;
    private List<FoodDetail> deliveryFoodList;
    private OnCountChangeListener listener;

    public interface OnCountChangeListener {
        void onCountChange(FoodDetail foodDetail, int position);
    }

    public AllOrderCheckListAdapter(Context context, OnCountChangeListener listener, List<FoodDetail> deliveryFoodList) {
        this.context = context;
        this.deliveryFoodList = deliveryFoodList;
        this.listener = listener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.item_total_display, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder)
            ((MyViewHolder) holder).bind(deliveryFoodList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return deliveryFoodList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.iv_minus)
//        ImageView shareFood;
//
//        @BindView(R.id.iv_plus)
//        ImageView ivFood;

        @BindView(R.id.iv_minus)
        ImageView ivMinus;

        @BindView(R.id.iv_plus)
        ImageView ivPlus;

        @BindView(R.id.tv_food_name)
        TextView tvFoodName;

        @BindView(R.id.tv_price)
        TextView tvPrice;

        @BindView(R.id.tv_count)
        TextView tvCount;

        @BindView(R.id.tv_item_count)
        TextView tvItemCount;

        private MyViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        public void bind(final FoodDetail foodDetail, final int position) {
            tvFoodName.setText(foodDetail.getFoodName());
            if (!foodDetail.isReallyFree()) {
                tvPrice.setText(context.getString(R.string.price_display, "" +
                        foodDetail.getPriceAfterOffer() * foodDetail.getSelectedFoodCountNumber()));
            } else {
                tvPrice.setText(context.getString(R.string.text_free));
            }
            tvItemCount.setText(position + 1 + ".");
            tvCount.setText(foodDetail.getSelectedFoodCount());

            ivMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setFoodCount(-1, position);
                    if (listener != null) {
                        listener.onCountChange(foodDetail, position);
                    }
                }
            });

            ivPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setFoodCount(1, position);
                    if (listener != null) {
                        listener.onCountChange(foodDetail, position);
                    }
                }
            });
        }

        private void setFoodCount(int count, int position) {
            int newCount = deliveryFoodList.get(position).getSelectedFoodCountNumber() + count;

            if (newCount < 0) {
                Snackbar.make(ivPlus, "Order atleast one to proceed", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } else if (newCount > 10) {
                Snackbar.make(ivPlus, "You can order max 10, Contact directly, see 'About Us' page.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } else {
                deliveryFoodList.get(position).setSelectedFoodCount(newCount);
                tvCount.setText(newCount + "");
            }
            notifyDataSetChange();
        }
    }

    private void notifyDataSetChange() {
        notifyDataSetChanged();
    }
}
