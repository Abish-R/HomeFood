package abish.veettusorudemo.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import abish.veettusorudemo.OrderDetail;
import abish.veettusorudemo.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abish on 03/24/2018.
 * </p>
 */

public class MyOrdersDescriptionAdapter extends RecyclerView.Adapter {
    private LayoutInflater inflater;
    private List<OrderDetail> myOrderDetail;

    public MyOrdersDescriptionAdapter(Context context, List<OrderDetail> myOrderDetail) {
        this.myOrderDetail = myOrderDetail;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.item_my_order_description, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder)
            ((MyViewHolder) holder).bind(myOrderDetail.get(position));
    }

    @Override
    public int getItemCount() {
        return myOrderDetail.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_food_name)
        TextView tvFoodName;

        @BindView(R.id.tv_food_price)
        TextView tvFoodPrice;

        @BindView(R.id.tv_food_qty)
        TextView tvFoodQty;

        @BindView(R.id.tv_food_total_price)
        TextView tvFoodTotalPrice;

        private MyViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        public void bind(final OrderDetail myOrderDetail) {
            tvFoodName.setText(myOrderDetail.getFoodName());
            tvFoodPrice.setText(myOrderDetail.getPrice());
            tvFoodQty.setText(myOrderDetail.getFoodQuantity());
            tvFoodTotalPrice.setText(myOrderDetail.getAmount());
        }
    }
}
