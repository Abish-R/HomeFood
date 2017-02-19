package abish.veettusorudemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class PreStartActivity extends AppCompatActivity {

    EditText etLocation;
    RelativeLayout locLayout;
    Spinner spinLocation;
    Button btContinue;
    String selectedLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pre_start_page);

        btContinue = (Button) findViewById(R.id.bt_continue);
        spinLocation = (Spinner) findViewById(R.id.spin_location);
        locLayout = (RelativeLayout) findViewById(R.id.loc_layout);
        etLocation = (EditText) findViewById(R.id.et_location);

        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getLocationList());
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinLocation.setAdapter(adp);

        btContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedLocation != null) {
                    if (selectedLocation.isEmpty())
                        selectedLocation = etLocation.getText().toString();
                    if (selectedLocation.isEmpty())
                        Toast.makeText(PreStartActivity.this, "Select Location before Proceed.", Toast.LENGTH_SHORT).show();
                    else {
                        Intent mainIntent = new Intent(PreStartActivity.this, MainActivity.class);
                        startActivity(mainIntent);
                        finish();
                    }
                } else {
                    Toast.makeText(PreStartActivity.this, "Select Location before Proceed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        locLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(PreStartActivity.this)
                        //.setTitle("Delete entry")
                        .setMessage("This will be redirected to the google map to select the location. Will be handled later on.")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        })
//                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                // do nothing
//                            }
//                        })
//                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        spinLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                if (position > 0)
                    selectedLocation = getLocationList().get(position).toString();
                else
                    selectedLocation = null;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    private ArrayList<String> getLocationList() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("Select Location");
        list.add("Velachery - Bharathi Nagar");
        list.add("Velachery - Vijay Nagar");
        list.add("Velachery - Nadasen Nagar");
        list.add("Velachery - Vivekandha Nagar");
        list.add("Velachery - Krishna Nagar");
        list.add("Taramani - Pallavan Nagar");
        list.add("Taramani - Bharathi Nagar");
        list.add("Taramani - MGR Nagar");
        list.add("Taramani - Bharathidasan Nagar");
        list.add("Taramani - Bharathi Nagar");
        return list;
    }
}
