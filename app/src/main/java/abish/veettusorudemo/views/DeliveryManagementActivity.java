package abish.veettusorudemo.views;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import abish.veettusorudemo.R;
import abish.veettusorudemo.constants.Constants;
import abish.veettusorudemo.network.response.FoodDetail;
import butterknife.Bind;
import butterknife.ButterKnife;

public class DeliveryManagementActivity extends AppCompatActivity implements
        com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener, com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {

    @Bind(R.id.bt_continue)
    Button btContinue;

    @Bind(R.id.date_time)
    TextView dateTime;

    @Bind(R.id.toolbar)
    Toolbar toolBar;

    @Bind(R.id.title)
    TextView toolbarTitle;

    @Bind(R.id.delivery_recycler_view)
    RecyclerView deliveryRecyclerView;

    SimpleDateFormat df;
    Calendar c = Calendar.getInstance();
    StringBuilder dateTimeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_management_page);

        ButterKnife.bind(this);
        setTitle();

        ArrayList<FoodDetail> selectedFoodDetailList = getIntent().getExtras().
                getParcelableArrayList(Constants.SELECTED_MAIN_FOODS);

        if (selectedFoodDetailList != null) {
            FoodDeliveryListAdapter mAdapter = new FoodDeliveryListAdapter(this, selectedFoodDetailList);
            deliveryRecyclerView.setAdapter(mAdapter);
        }

        btContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(DeliveryManagementActivity.this)
                        .setMessage("Your order is confirmed. You will get the order on time. Stay with us and comment your experience with us.")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                NotificationCompat.Builder b = new NotificationCompat.Builder(DeliveryManagementActivity.this);

                                b.setAutoCancel(true)
                                        .setDefaults(Notification.DEFAULT_ALL)
                                        .setWhen(System.currentTimeMillis())
                                        .setSmallIcon(R.drawable.heart_filled)
                                        .setTicker("Food Order placed")
                                        .setContentTitle("Food Order placed")
                                        .setContentText("Your placed order will be delivered to your address on time. Have a great day.")
                                        .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                                        .setContentInfo("Order Ok");


                                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                notificationManager.notify(1, b.build());
                                finish();
                            }
                        }).show();
            }
        });
        dateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dpd = DatePickerDialog.newInstance(DeliveryManagementActivity.this,
                        c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                dpd.show(getFragmentManager(), "Datepickerdialog");
                dpd.setMinDate(setMinMaxDate(true));
                dpd.setMaxDate(setMinMaxDate(false));
                //dpd.setVersion(DatePickerDialog.Version.VERSION_2);
            }
        });
    }

    private Calendar setMinMaxDate(boolean b) {
        Calendar cal  = Calendar.getInstance();
        if(b){
            cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH),
                    cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE));
        } else {
            cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)+2,
                    cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE));
        }
        return cal;
    }

    private void setTitle() {
        toolbarTitle.setText(R.string.title_delivery);
        setSupportActionBar(toolBar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    /**
     * Default Date Display in Fields
     */
    @SuppressLint("SimpleDateFormat")
    public void defaultDateCal() {
        int day, month, year, hour, minute;
        Calendar c = Calendar.getInstance();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        df = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);
        SimpleDateFormat timeformat = new SimpleDateFormat("HH:MM", Locale.ENGLISH);
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        Log.i("Initial Time", "" + c.getTime());
        //dateTime.setText(df.format(c.getTime()));

        getDateDiffString();
    }

    /**
     * Find Date difference
     */
    public Double getDateDiffString() {
        Date oldDate, newDate, oldDate1, newDate1;

        long diff = 0, findfromdatefault = 0;
        Date now, nowdte;
        double difference = 0.0;
        try {
            oldDate = df.parse(dateTime.getText().toString());
            newDate = df.parse(dateTime.getText().toString());
            oldDate1 = df.parse(df.format(oldDate));
            newDate1 = df.parse(df.format(newDate));
            nowdte = df.parse(df.format(c.getTime()));
            now = df.parse(df.format(nowdte));
            findfromdatefault = oldDate1.getTime() - now.getTime();
            if (findfromdatefault < 0)
                Toast.makeText(this, "Wrong From date", Toast.LENGTH_SHORT).show();
            else {
                diff = newDate1.getTime() - oldDate1.getTime();
                difference = (double) diff;
                if (difference < 0)
                    Toast.makeText(this, "Wrong To date", Toast.LENGTH_SHORT).show();
                else {
                    difference = difference / 86400000;
                    if (difference <= 1 && difference >= 0) {
                        return difference = 1;
                    } else if (difference > 1) {
                        if (difference > 15)
                            Toast.makeText(this, "Wrong To date", Toast.LENGTH_SHORT).show();
                        else
                            return difference;
                    } else {
                        Toast.makeText(this, "Wrong To date", Toast.LENGTH_SHORT).show();
                        return 0.0;
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return difference;
    }

    @Override
    public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        dateTimeText = new StringBuilder();
        String month = "";
        if (monthOfYear < 9)
            month = "0" + (monthOfYear + 1);
        else
            month = "" + monthOfYear + 1;
        dateTimeText.append(dayOfMonth).append(" - ").append(month).append(" - ").append(year);
        TimePickerDialog dpd = TimePickerDialog.newInstance(DeliveryManagementActivity.this,
                c.get(Calendar.HOUR), c.get(Calendar.MINUTE), false);
        dpd.show(getFragmentManager(), "TimePickerDialog");
    }

    @Override
    public void onTimeSet(com.wdullaer.materialdatetimepicker.time.TimePickerDialog view, int hourOfDay, int minute, int second) {
        String hour = "", min = "";
        if (hourOfDay < 10)
            hour = "0" + hourOfDay;
        else
            hour = "" + hourOfDay;
        if (minute < 10)
            min = "0" + minute + 1;
        else
            min = "" + minute;
        dateTimeText.append(", ").append(hour).append(" : ").append(min);
        dateTime.setText(dateTimeText);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }
}
