package abish.veettusorudemo.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import java.util.ArrayList;

import abish.veettusorudemo.R;
import abish.veettusorudemo.constants.UrlConstants;
import abish.veettusorudemo.network.GsonRequest;
import abish.veettusorudemo.network.VolleyApiClient;
import abish.veettusorudemo.network.response.LocationResponse;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static abish.veettusorudemo.Utils.displayLoader;
import static abish.veettusorudemo.Utils.hideLoader;

public class LocationSelectionActivity extends AppCompatActivity {

    @BindView(R.id.spin_location)
    Spinner spinLocation;

    @BindView(R.id.bt_continue)
    Button btContinue;

    @BindView(R.id.tv_contact_us)
    TextView tvContactUs;

    String selectedLocation;
    private ArrayList<String> locationList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_location_selection);
        ButterKnife.bind(this);

        getLocationList();

        spinLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position > 0)
                    selectedLocation = locationList.get(position);
                else
                    selectedLocation = null;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick(R.id.bt_continue)
    public void pressContinue() {
        if (btContinue.getText().toString().equalsIgnoreCase(getString(R.string.continue_text)) && selectedLocation != null) {
            if (selectedLocation.isEmpty())
                Toast.makeText(LocationSelectionActivity.this, "Select Location before Proceed.", Toast.LENGTH_SHORT).show();
            else {
                Intent mainIntent = new Intent(LocationSelectionActivity.this, FoodCategorySelectionActivity.class);
                startActivity(mainIntent);
                finish();
                overridePendingTransitionExit();
            }
        } else if (btContinue.getText().toString().equalsIgnoreCase(getString(R.string.retry))) {
            getLocationList();
        } else if (selectedLocation == null) {
            Toast.makeText(LocationSelectionActivity.this, "Select Location before Proceed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void getLocationList() {

        String url = UrlConstants.GET_LOCATION_URL;

        displayLoader(this, "Receiving Locations...");

        GsonRequest request = new GsonRequest<LocationResponse>(url, null, LocationResponse.class, null, new Response.Listener<LocationResponse>() {
            @Override
            public void onResponse(LocationResponse response) {
                Log.d("Locations Ok", response.toString());
                onLocationReceived(response);
                btContinue.setText(R.string.continue_text);
                hideLoader();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error: " + error.getMessage());
                btContinue.setText(R.string.retry);
                hideLoader();
            }
        });
        VolleyApiClient.getInstance().addToRequestQueue(request, "Get Location");
    }

    private void onLocationReceived(LocationResponse response) {
        btContinue.setText(R.string.continue_text);
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getLoc(response));
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinLocation.setAdapter(adp);
    }

    private ArrayList<String> getLoc(LocationResponse response) {
        locationList.add("Select Location");
        for (int i = 0; i < response.getLocationList().size(); i++) {
            locationList.add(response.getLocationList().get(i).getCity() + " - " +
                    response.getLocationList().get(i).getStreet());
        }
        return locationList;
    }

    public void onClickContactUs(View view) {
        new AlertDialog.Builder(this)
                .setMessage("Will be implemented later with some contact number dial facility and review or comment facility")
                .setPositiveButton(R.string.dial, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setNegativeButton(R.string.message, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
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
}
