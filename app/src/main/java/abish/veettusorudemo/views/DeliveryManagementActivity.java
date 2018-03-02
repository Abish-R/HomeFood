package abish.veettusorudemo.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import abish.veettusorudemo.R;
import abish.veettusorudemo.Utils;
import abish.veettusorudemo.constants.Constants;
import abish.veettusorudemo.constants.UrlConstants;
import abish.veettusorudemo.network.GsonRequest;
import abish.veettusorudemo.network.VolleyApiClient;
import abish.veettusorudemo.network.model.AddressData;
import abish.veettusorudemo.network.model.OrderData;
import abish.veettusorudemo.network.request.OrderRequest;
import abish.veettusorudemo.network.response.AddressDeleteResponse;
import abish.veettusorudemo.network.response.AddressResponse;
import abish.veettusorudemo.network.response.OrderResponse;
import butterknife.BindView;
import butterknife.ButterKnife;

import static abish.veettusorudemo.R.id.tv_date_time;
import static abish.veettusorudemo.Utils.displayLoader;
import static abish.veettusorudemo.Utils.getSavedUserDetail;
import static abish.veettusorudemo.Utils.hideLoader;

public class DeliveryManagementActivity extends AppCompatActivity implements
        com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener, com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {

    @BindView(R.id.bt_continue)
    Button btContinue;

    @BindView(R.id.bt_cancel)
    Button cancelAddress;

    @BindView(R.id.bt_add_address)
    Button addAddress;

    @BindView(R.id.bt_retry)
    Button retry;

    @BindView(tv_date_time)
    TextView dateTime;

    @BindView(R.id.toolbar)
    Toolbar toolBar;

    @BindView(R.id.door_street)
    EditText doorStreet;

    @BindView(R.id.city)
    EditText city;

    @BindView(R.id.pincode)
    EditText pinCode;

    @BindView(R.id.title)
    TextView toolbarTitle;

    @BindView(R.id.rv_address_list)
    RecyclerView rvAddressList;

    @BindView(R.id.ll_address_entry)
    LinearLayout llAddressEntry;

    private Calendar calendar = Calendar.getInstance();
    private StringBuilder dateTimeText;
    private List<AddressData> addressList;
    private List<String> deliveryTime;
    private AddressListAdapter mAdapter;
    private boolean isPermanentEdit;
    private ArrayList<OrderData> orderDatas = new ArrayList<>();
    private OrderRequest requestData;
    private String foodCategoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_management_page);

        ButterKnife.bind(this);
        setTitle();

        if (getIntent().getExtras() != null) {
            orderDatas = getIntent().getExtras().getParcelableArrayList(Constants.ALL_ORDERS);
        }

        requestData = new OrderRequest();
        if (orderDatas != null && !orderDatas.isEmpty()) {
            requestData.setOrderDetail(orderDatas);
            foodCategoryId = getIntent().getExtras().getString(Constants.SELECTED_FOOD_CATEGORY_ID);
        }

        getUserAddress("");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransitionExit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

    private void setTitle() {
        toolbarTitle.setText(R.string.title_delivery);
        setSupportActionBar(toolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }
    }

    public void displayCalendarView(View view) {
        DatePickerDialog datePicker = DatePickerDialog.newInstance(DeliveryManagementActivity.this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePicker.show(getFragmentManager(), "Datepickerdialog");
        datePicker.setMinDate(setMinMaxDate(true));
        //datePicker.setMaxDate(setMinMaxDate(false));
    }

    public void addAddress(View view) {
        if (addAddress.getText().toString().equalsIgnoreCase(getString(R.string.text_add_address)) && addressList.size() < 4) {
            enableAddressEntryMode();
        } else if (addAddress.getText().toString().equalsIgnoreCase(getString(R.string.text_add_or_update_address))) {
            try {
                validateAddress();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Added Max addresses. Delete one to add new Address.", Toast.LENGTH_SHORT).show();
        }
    }

    private Calendar setMinMaxDate(boolean isMinDate) {
        if (isMinDate) {
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                    calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
        } else {
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH) + 2,
                    calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
        }
        return calendar;
    }

    @Override
    public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        dateTimeText = new StringBuilder();
        int months = monthOfYear + 1;
        String month = monthOfYear < 9 ? ("0" + months) : ("" + months);

        dateTimeText.append(year).append("-").append(month).append("-").append(dayOfMonth);
        TimePickerDialog dpd = TimePickerDialog.newInstance(DeliveryManagementActivity.this,
                calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), false);

        //dpd.show(getFragmentManager(), "TimePickerDialog");

        deliveryTimeSlots();
    }

    private void deliveryTimeSlots() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        builderSingle.setTitle("Select Delivery Slot");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_singlechoice);

        if (deliveryTime == null) return;

        for (int i = 0; i < deliveryTime.size(); i++) {
            arrayAdapter.add(deliveryTime.get(i));
        }

        // cancel button
        builderSingle.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dateTimeText = null;
                        dialog.dismiss();
                    }
                });

        builderSingle.setAdapter(arrayAdapter,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("Selected Item : ", arrayAdapter.getItem(which));
                        String time = arrayAdapter.getItem(which) != null ? arrayAdapter.getItem(which).replace(" ", "") : "";
                        dateTimeText.append(" ").append(time);
                        dateTime.setText(dateTimeText);
                        dialog.dismiss();

                    }
                });
        builderSingle.show();
    }

    @Override
    public void onTimeSet(com.wdullaer.materialdatetimepicker.time.TimePickerDialog view, int hourOfDay, int minute, int second) {
        String hour = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        int minutes = minute + 1;
        String min = minute < 9 ? ("0" + minutes) : "" + minutes;

        dateTimeText.append(" ").append(hour).append(":").append(min).append(":00");
        dateTime.setText(dateTimeText);
    }

    private void goToMakeOrders() {
        displayLoader(this, "Sending your Order...");

        String param = "";
        try {
            param = URLEncoder.encode(requestData.getOrderRequest(requestData), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = UrlConstants.SEND_ORDERS_URL + UrlConstants.ORDERS_DATA + param;

        GsonRequest request = new GsonRequest<OrderResponse>(
                url, null,
                OrderResponse.class, null, new Response.Listener<OrderResponse>() {
            @Override
            public void onResponse(OrderResponse response) {
                if (response.isSuccess()) {
                    alertOkMessage(DeliveryManagementActivity.this,
                            response.getStatus(), "OK");
                } else {
                    Toast.makeText(DeliveryManagementActivity.this, "Error in Server", Toast.LENGTH_SHORT).show();
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
        VolleyApiClient.getInstance().addToRequestQueue(request, "Order Food");
    }

    public void alertOkMessage(Context mContext, String message, String buttonName) {
        AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, buttonName,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        DeliveryManagementActivity.this.finish();
                    }
                });
        alertDialog.show();
    }

    private void getUserAddress(String userId) {
        String url = UrlConstants.GET_ADDRESS_URL
                + UrlConstants.ADDRESS_USER_ID + getSavedUserDetail(this, Constants.LOGIN_USER_ID)
                + UrlConstants.ADDRESS_CATEGORY_ID + foodCategoryId;

        displayLoader(this, "Receiving Address...");

        GsonRequest request = new GsonRequest<AddressResponse>(url, null,
                AddressResponse.class, null, new Response.Listener<AddressResponse>() {
            @Override
            public void onResponse(AddressResponse response) {
                if (response.isSuccess()) {
                    addressList = response.getAddressList();
                    deliveryTime = response.getDeliveryTime();
                    setOrWriteAddress();
                }
                hideLoader();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error: " + error.getMessage());
                retry.setVisibility(View.VISIBLE);
                hideLoader();
            }
        });
        VolleyApiClient.getInstance().addToRequestQueue(request, "Food Categories");
    }

    private void setOrWriteAddress() {
        if (addressList != null && !addressList.isEmpty()) {
            mAdapter = new AddressListAdapter(DeliveryManagementActivity.this, addressList);
            rvAddressList.setLayoutManager(new LinearLayoutManager(DeliveryManagementActivity.this));
            rvAddressList.setAdapter(mAdapter);

            if (addressList.size() >= 3) {
                addAddress.setVisibility(View.INVISIBLE);
            } else {
                addAddress.setText(R.string.text_add_address);
            }
            enableAddressListMode();
        } else {
            enableAddressEntryMode();
        }
    }

    private void enableAddressEntryMode() {
        rvAddressList.setVisibility(View.GONE);
        llAddressEntry.setVisibility(View.VISIBLE);
        //addAddress.setVisibility(View.GONE);
        dateTime.setVisibility(View.INVISIBLE);
        btContinue.setVisibility(View.INVISIBLE);
        addAddress.setText(R.string.text_add_or_update_address);
        cancelAddress.setVisibility(View.VISIBLE);
    }

    private void enableAddressListMode() {
        rvAddressList.setVisibility(View.VISIBLE);
        llAddressEntry.setVisibility(View.GONE);
        dateTime.setVisibility(View.VISIBLE);
        btContinue.setVisibility(View.VISIBLE);
        addAddress.setText(R.string.text_add_address);
        cancelAddress.setVisibility(View.INVISIBLE);
    }

    public void orderFood(View view) {
        if (!dateTime.getText().toString().equals(getString(R.string.text_pick_delivery_time))) {
            String deliveryTime = dateTime.getText().toString();
            requestData.setDeliveryTime(deliveryTime.substring(0, deliveryTime.length() - 2));

            if (rvAddressList.getVisibility() == View.VISIBLE && addressList != null && !addressList.isEmpty()) {
                boolean isCheckedAddress = false;
                for (AddressData address : addressList) {
                    if (address.isChecked()) {
                        requestData.setUserLocation(address.getAddressId());
                        isCheckedAddress = true;
                    }
                }

                if (isCheckedAddress) {
                    requestData.setUserID(getSavedUserDetail(this, Constants.LOGIN_USER_ID));
                    goToMakeOrders();
                } else {
                    Toast.makeText(this, "Select a delivery Address", Toast.LENGTH_SHORT).show();
                }
            } else {
                try {
                    validateAddress();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            Toast.makeText(this, "Select the delivery time", Toast.LENGTH_SHORT).show();
        }
    }

    private void validateAddress() throws Exception {
        if (doorStreet.getText().toString().length() < 5) {
            doorStreet.setError("Enter correct Door & Street");
            throw new Exception("Enter a correct door number, street name");
        }
        if (city.getText().toString().length() < 3) {
            city.setError("Enter correct City");
            throw new Exception("Enter a correct city name");
        }
        if (pinCode.getText().toString().length() < 6) {
            pinCode.setError("Enter correct area code");
            throw new Exception("Enter a correct area code");
        }
        addOrUpdateAddress();
    }

    private void addOrUpdateAddress() {
        String url = UrlConstants.GET_ADDRESS_UPDATE_URL
                + UrlConstants.ADDRESS_UPDATE_USER_ID + getSavedUserDetail(this, Constants.LOGIN_USER_ID)
                + UrlConstants.ADDRESS_UPDATE_STREET + doorStreet.getText().toString()
                + UrlConstants.ADDRESS_UPDATE_CITY + city.getText().toString()
                + UrlConstants.ADDRESS_UPDATE_STATE + Constants.CURRENT_STATE
                + UrlConstants.ADDRESS_UPDATE_COUNTRY + Constants.CURRENT_COUNTRY
                + UrlConstants.ADDRESS_UPDATE_PIN_CODE + pinCode.getText().toString()
                + UrlConstants.ADDRESS_UPDATE_FLAG + (isPermanentEdit ? Constants.ADDRESS_PERMANENT_FLAG : Constants.ADDRESS_TEMPORARY_FLAG);
        displayLoader(this, "Updating Address...");

        url = url.replaceAll(" ", "%20");

        GsonRequest request = new GsonRequest<AddressResponse>(url, null,
                AddressResponse.class, null, new Response.Listener<AddressResponse>() {
            @Override
            public void onResponse(AddressResponse response) {
                if (response.isSuccess()) {
                    getUserAddress("");
                } else if (response.getResponse() == 2) {
                    Utils.alertOkMessage(DeliveryManagementActivity.this, "Maximum Address Reached, select one of them and change", "Ok");
                    getUserAddress("");
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


    /**
     * Overrides the pending Activity transition by performing the "Enter" animation.
     */
    protected void overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation.
     */
    protected void overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    // From Adapter edit Address
    public void editAddress(AddressData addressDetail) {
        isPermanentEdit = addressDetail.getAddressType().equals(Constants.ADDRESS_PERMANENT_FLAG);
        rvAddressList.setVisibility(View.GONE);
        llAddressEntry.setVisibility(View.VISIBLE);
        doorStreet.setText(addressDetail.getStreet());
        city.setText(addressDetail.getCity());
        pinCode.setText(addressDetail.getPincode());
        addAddress.setText(R.string.text_add_or_update_address);
        cancelAddress.setVisibility(View.VISIBLE);
    }

    // From Adapter edit Address
    public void deleteAddress(final AddressData addressDetail) {
        String url = UrlConstants.GET_ADDRESS_DELETE_URL
                + UrlConstants.ADDRESS_DELETE_USER_ID + getSavedUserDetail(this, Constants.LOGIN_USER_ID)
                + UrlConstants.ADDRESS_DELETE_TEMP_ID + addressDetail.getAddressId();
        displayLoader(this, "Updating Address...");

        GsonRequest request = new GsonRequest<AddressDeleteResponse>(url, null,
                AddressDeleteResponse.class, null, new Response.Listener<AddressDeleteResponse>() {
            @Override
            public void onResponse(AddressDeleteResponse response) {
                if (response.isSuccess()) {
                    addressList.remove(addressDetail);
                    mAdapter.notifyDataSetChanged();
                } else {
                    Utils.alertOkMessage(DeliveryManagementActivity.this, response.getStatus(), "Ok");
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

    public void clickCancel(View view) {
        enableAddressListMode();
    }

    public void noAddress(View view) {
        view.setVisibility(View.GONE);
        getUserAddress("");
    }

    public class AddressListAdapter extends RecyclerView.Adapter {
        private Context context;
        private LayoutInflater inflater;
        private List<AddressData> addressList;

        private AddressListAdapter(Context context, List<AddressData> deliveryFoodList) {
            this.context = context;
            inflater = LayoutInflater.from(context);
            this.addressList = deliveryFoodList;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(inflater.inflate(R.layout.item_address, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof MyViewHolder) {
                ((MyViewHolder) holder).bind(addressList.get(position), position);
            }
        }

        @Override
        public int getItemCount() {
            return addressList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.ib_edit_address)
            ImageButton ibEditAddress;

            @BindView(R.id.ib_delete_address)
            ImageButton ibDeleteAddress;

            @BindView(R.id.tv_address)
            TextView tvAddress;

            @BindView(R.id.cb_address)
            CheckBox cbAddress;

            private MyViewHolder(View itemView) {
                super(itemView);

                ButterKnife.bind(this, itemView);
            }

            public void bind(final AddressData addressDetail, final int position) {
                tvAddress.setText(addressList.get(position).getAddress());
                cbAddress.setChecked(addressList.get(position).isChecked());

                cbAddress.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        setChecking(isChecked);
                        addressDetail.setChecked(isChecked);
                    }
                });

                ibEditAddress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editAddress(addressDetail);
                    }
                });

                ibDeleteAddress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteAddress(addressDetail);
                    }
                });
            }

            private void setChecking(boolean isChecked) {
                if (isChecked) {
                    for (int i = 0; i < addressList.size(); i++) {
                        addressList.get(i).setChecked(false);
                    }
                }
                cbAddress.post(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
            }
        }
    }
}
