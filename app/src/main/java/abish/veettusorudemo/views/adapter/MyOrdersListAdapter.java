package abish.veettusorudemo.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import abish.veettusorudemo.MyOrdersList;
import abish.veettusorudemo.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abish on 03/19/2018.
 * </p>
 */

public class MyOrdersListAdapter extends RecyclerView.Adapter {
    private Context context;
    private LayoutInflater inflater;
    private List<MyOrdersList> myOrdersList;
    private OnItemPressListener listener;

    public interface OnItemPressListener {
        void onMyOrdersItemClicked(MyOrdersList myOrderItem, int position);
    }

    public MyOrdersListAdapter(Context context, OnItemPressListener listener, List<MyOrdersList> myOrdersList) {
        this.context = context;
        this.myOrdersList = myOrdersList;
        this.listener = listener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.item_my_order_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder)
            ((MyViewHolder) holder).bind(myOrdersList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return myOrdersList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_order_title)
        TextView tvOrderTitle;

        @BindView(R.id.tv_order_date)
        TextView tvOrderDate;

        @BindView(R.id.tv_delivery_date)
        TextView tvDeliveryDate;

        @BindView(R.id.tv_total_amount)
        TextView tvTotalAmount;

        @BindView(R.id.tv_order_status)
        TextView tvOrderStatus;

        @BindView(R.id.parent_container)
        RelativeLayout parentContainer;

        private MyViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        public void bind(final MyOrdersList myOrdersItem, final int position) {
            String orderTitle;
            if(myOrdersItem.getOrderDetail() != null && myOrdersItem.getOrderDetail().size() > 1) {
                orderTitle = myOrdersItem.getOrderDetail().get(0).getShortName()
                + " +" + myOrdersItem.getOrderDetail().size() + "more";
            } else {
                orderTitle = myOrdersItem.getOrderDetail().get(0).getShortName();
            }

            tvOrderTitle.setText(orderTitle);
            tvOrderDate.setText(myOrdersItem.getOrderDateTime());
            tvDeliveryDate.setText(myOrdersItem.getDeliveryDateTime());
            tvTotalAmount.setText(myOrdersItem.getTotalAmount());
            tvOrderStatus.setText(myOrdersItem.getOrderStatus());

            // TODO : fix status code validation and color change
            tvOrderStatus.setBackgroundColor(context.getResources().getColor(R.color.light_green));

            parentContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) listener.onMyOrdersItemClicked(myOrdersItem, position);
                }
            });
        }
    }
}
