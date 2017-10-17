package abish.veettusorudemo.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import abish.veettusorudemo.R;
import abish.veettusorudemo.constants.UrlConstants;
import abish.veettusorudemo.network.GsonRequest;
import abish.veettusorudemo.network.VolleyApiClient;
import abish.veettusorudemo.network.response.FoodListResponse;
import butterknife.Bind;
import butterknife.ButterKnife;

import static abish.veettusorudemo.Utils.displayLoader;
import static abish.veettusorudemo.Utils.hideLoader;

public class SignUpActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolBar;

    @Bind(R.id.title)
    TextView toolbarTitle;

    @Bind(R.id.et_name)
    EditText etName;

    @Bind(R.id.et_phone)
    EditText etPhone;

    @Bind(R.id.et_email)
    EditText etEmail;

    @Bind(R.id.et_password)
    EditText etPassword;

    @Bind(R.id.et_confirm_password)
    EditText etConfirmPassword;

    @Bind(R.id.et_street)
    EditText etStreet;

    @Bind(R.id.et_city)
    EditText etCity;

    @Bind(R.id.et_state)
    EditText etState;

    @Bind(R.id.et_pinCode)
    EditText etPinCode;

    @Bind(R.id.cb_permanent_address)
    CheckBox cbPermanentAddress;

    @Bind(R.id.cb_temporary_address)
    CheckBox cbTemporaryAddress;

    @Bind(R.id.bt_continue)
    Button btContinue;

    @Bind(R.id.bt_create_account)
    Button btCreateAccount;

    @Bind(R.id.bt_logon)
    TextView btLogon;

    @Bind(R.id.layout_user_info)
    RelativeLayout layoutUserInfo;

    @Bind(R.id.layout_address)
    RelativeLayout layoutAddress;

    private StringBuilder signUpValues = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);
        setTitle();

    }

    private void setTitle() {
        toolbarTitle.setText(R.string.title_sign_up);
        setSupportActionBar(toolBar);
    }

    public void continueCreation(View v) {
        try {
            validateUserEntries();
            layoutUserInfo.setVisibility(View.GONE);
            layoutAddress.setVisibility(View.VISIBLE);
            btLogon.setVisibility(View.GONE);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void validateUserEntries() throws Exception {
        if (etName.getText().toString().length() < 3) {
            etName.setError("Name min 3 characters");
            throw new Exception("Name min 3 characters");
        }
        if (etPhone.getText().toString().length() < 10) {
            etPhone.setError("Enter mobile number");
            throw new Exception("Enter mobile number");
        }
        if (etEmail.getText().toString().length() < 7) {
            etEmail.setError("Enter email");
            throw new Exception("Enter email");
        }
        if (etPassword.getText().toString().length() < 5) {
            etPassword.setError("Password min 5 character");
            throw new Exception("Password min 5 character");
        }
        if (etConfirmPassword.getText().toString().length() < 5) {
            etConfirmPassword.setError("Password min 5 character");
            throw new Exception("Password min 5 character");
        }
        if (!etConfirmPassword.getText().toString().equals(etPassword.getText().toString())) {
            etConfirmPassword.setError("Passwords not matching");
            throw new Exception("Passwords not matching");
        }

        signUpValues.append(UrlConstants.SIGN_UP_PARAM_FIRST_NAME).append(etName.getText().toString())
                .append(UrlConstants.SIGN_UP_PARAM_LAST_NAME).append(etPhone.getText().toString())
                .append(UrlConstants.SIGN_UP_PARAM_USER_NAME).append(etPhone.getText().toString())
                .append(UrlConstants.SIGN_UP_PARAM_PASSWORD).append(etPassword.getText().toString())
                .append(UrlConstants.SIGN_UP_PARAM_EMAIL).append(etEmail.getText().toString())
                .append(UrlConstants.SIGN_UP_PARAM_MOBILE).append(etPhone.getText().toString());
    }

    public void createAccount(View v) {
        try {
            validateAddressEntries();

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void validateAddressEntries() throws Exception {
        if (etStreet.getText().toString().isEmpty()) {
            etStreet.setError("Enter etStreet");
            throw new Exception("Enter etStreet");
        }
        if (etCity.getText().toString().isEmpty()) {
            etCity.setError("Enter etCity");
            throw new Exception("Enter etCity");
        }
        if (etState.getText().toString().isEmpty()) {
            etState.setError("Enter etState");
            throw new Exception("Enter etState");
        }
        if (etPinCode.getText().toString().isEmpty()) {
            etPinCode.setError("Enter etPinCode");
            throw new Exception("Enter etPinCode");
        }

        signUpValues.append(UrlConstants.SIGN_UP_PARAM_STREET).append(etStreet.getText().toString())
                .append(UrlConstants.SIGN_UP_PARAM_CITY).append(etCity.getText().toString())
                .append(UrlConstants.SIGN_UP_PARAM_STATE).append(etState.getText().toString())
                .append(UrlConstants.SIGN_UP_PARAM_Country).append("India")
                .append(UrlConstants.SIGN_UP_PARAM_PINCODE).append(etPinCode.getText().toString())
                .append(UrlConstants.SIGN_UP_PARAM_DEVICE_TOKEN).append(etPinCode.getText().toString());
        processSignUp();
    }

    public void checkAddress(View view) {
        setCheckBoxChecked(cbPermanentAddress.isChecked());
    }

    private void setCheckBoxChecked(boolean isCheckedPermanentAddress) {
        cbPermanentAddress.setChecked(!isCheckedPermanentAddress);
        cbTemporaryAddress.setChecked(isCheckedPermanentAddress);
    }

    public void goToLogin(View view) {
        startActivity(new Intent(this, UserLoginControlActivity.class));
        finish();
        overridePendingTransitionExit();
    }

    private void processSignUp() {
        String url = UrlConstants.GET_SIGN_UP_URL + signUpValues;
        displayLoader(this, "Signing Up...");

        GsonRequest request = new GsonRequest<FoodListResponse>(url, null,
                FoodListResponse.class, null, new Response.Listener<FoodListResponse>() {
            @Override
            public void onResponse(FoodListResponse response) {
                Log.d("Sign Up Ok", response.toString());

                hideLoader();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error: " + error.getMessage());
                hideLoader();
            }
        });
        VolleyApiClient.getInstance().addToRequestQueue(request, "Sign Up");
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
