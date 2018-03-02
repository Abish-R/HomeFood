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
import abish.veettusorudemo.Utils;
import abish.veettusorudemo.constants.Constants;
import abish.veettusorudemo.constants.UrlConstants;
import abish.veettusorudemo.network.GsonRequest;
import abish.veettusorudemo.network.VolleyApiClient;
import abish.veettusorudemo.network.response.FoodListResponse;
import butterknife.BindView;
import butterknife.ButterKnife;

import static abish.veettusorudemo.Utils.alertOkMessage;
import static abish.veettusorudemo.Utils.displayLoader;
import static abish.veettusorudemo.Utils.hideLoader;

public class SignUpActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolBar;

    @BindView(R.id.title)
    TextView toolbarTitle;

    @BindView(R.id.et_name)
    EditText etName;

    @BindView(R.id.et_phone)
    EditText etPhone;

    @BindView(R.id.et_email)
    EditText etEmail;

    @BindView(R.id.et_password)
    EditText etPassword;

    @BindView(R.id.et_confirm_password)
    EditText etConfirmPassword;

    @BindView(R.id.et_street)
    EditText etStreet;

    @BindView(R.id.et_city)
    EditText etCity;

    @BindView(R.id.et_state)
    EditText etState;

    @BindView(R.id.et_pinCode)
    EditText etPinCode;

    @BindView(R.id.cb_permanent_address)
    CheckBox cbPermanentAddress;

    @BindView(R.id.cb_temporary_address)
    CheckBox cbTemporaryAddress;

    @BindView(R.id.bt_continue)
    Button btContinue;

    @BindView(R.id.bt_create_account)
    Button btCreateAccount;

    @BindView(R.id.bt_logon)
    TextView btLogon;

    @BindView(R.id.layout_user_info)
    RelativeLayout layoutUserInfo;

    @BindView(R.id.layout_address)
    RelativeLayout layoutAddress;

    private StringBuilder signUpValues = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);
        setTitle();

    }

    @Override
    public void onBackPressed() {
        if (layoutAddress.getVisibility() == View.VISIBLE) {
            editAccountCreation();
        } else {
            super.onBackPressed();
        }
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
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void editAccountCreation() {
        layoutUserInfo.setVisibility(View.VISIBLE);
        layoutAddress.setVisibility(View.GONE);
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

        signUpValues.append(UrlConstants.SIGN_UP_PARAM_NAME).append(etName.getText().toString())
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
                .append(UrlConstants.SIGN_UP_PARAM_ADDRESS_FLAG).append(cbPermanentAddress.isChecked() ? 1 : 2)
                .append(UrlConstants.SIGN_UP_PARAM_DEVICE_TOKEN).append(Utils.getSavedUserDetail(this, Constants.FCM_TOKEN));
        processSignUp();
    }

    public void checkPAddress(View view) {
        if(cbPermanentAddress.isChecked()){
            setCheckBoxChecked(false);
        } else {
            setCheckBoxChecked(true);
        }
    }
    public void checkTAddress(View view) {
        if(cbTemporaryAddress.isChecked()){
            setCheckBoxChecked(true);
        } else {
            setCheckBoxChecked(false);
        }
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
                hideLoader();
                if (response.isSuccess()) {
                    Log.d("Sign Up Ok", response.toString());
                    Intent intent = new Intent(SignUpActivity.this, UserLoginControlActivity.class);
                    intent.putExtra(Constants.LOGIN_TRANSITION_DECIDER, Constants.LOGIN_AFTER_LOCATION);
                    startActivity(intent);
                    finish();
                    overridePendingTransitionExit();
                } else {
                    alertOkMessage(SignUpActivity.this, response.getStatus(), getString(R.string.mdtp_ok));
                }
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
