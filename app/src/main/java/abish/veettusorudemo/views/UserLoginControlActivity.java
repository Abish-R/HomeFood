package abish.veettusorudemo.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import abish.veettusorudemo.R;
import abish.veettusorudemo.Utils;
import abish.veettusorudemo.constants.Constants;
import abish.veettusorudemo.constants.UrlConstants;
import abish.veettusorudemo.network.GsonRequest;
import abish.veettusorudemo.network.VolleyApiClient;
import abish.veettusorudemo.network.response.LoginResponse;
import butterknife.Bind;
import butterknife.ButterKnife;

import static abish.veettusorudemo.Utils.displayLoader;
import static abish.veettusorudemo.Utils.hideLoader;

//import abish.veettusorudemo.constants.UrlConstants;

//import abish.veettusorudemo.constants.UrlConstants;

public class UserLoginControlActivity extends AppCompatActivity {

    @Bind(R.id.input_layout_email)
    TextInputLayout inputLayoutEmail;

    @Bind(R.id.input_layout_mobile)
    TextInputLayout inputLayoutMobile;

    @Bind(R.id.et_email)
    EditText etEmail;

    @Bind(R.id.et_mobile)
    EditText etMobile;

    @Bind(R.id.et_password)
    EditText etPassword;

    @Bind(R.id.iv_switch_mobile_email)
    ImageView ivSwitchMobileEmail;

    @Bind(R.id.bt_login)
    Button btLogin;

    @Bind(R.id.tv_sign_up)
    TextView tvSignUp;

    @Bind(R.id.tv_skip_login)
    TextView tvSkipLogin;

    @Bind(R.id.toolbar)
    Toolbar toolBar;

    @Bind(R.id.title)
    TextView toolbarTitle;

    private int loginTransition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login_control);
        ButterKnife.bind(this);
        setTitle();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            loginTransition = extras.getInt(Constants.LOGIN_TRANSITION_DECIDER);
        }
    }

    private void setTitle() {
        toolbarTitle.setText(R.string.title_login);
        setSupportActionBar(toolBar);
    }

    public void userLogin(View view) {
        try {
            validateData();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    private void validateData() throws Exception {
        if (ivSwitchMobileEmail.getTag() != null &&
                ivSwitchMobileEmail.getTag().toString().equals("Email") ||
                etPassword.getText().toString().contains(" ")) {
            if (etEmail.getText().length() < 3) {
                etEmail.setError("Enter correct username");
                throw new Exception("Check username");
            }
            if (etPassword.getText().length() < 3 || etPassword.getText().toString().contains(" ")) {
                etPassword.setError("Enter correct password");
                throw new Exception("Check password");
            }
            goForLogin(etEmail.getText().toString(), etPassword.getText().toString(), "deviceToken");
        } else {
            if (etMobile.getText().length() < 3) {
                etMobile.setError("Enter correct username");
                throw new Exception("Check username");
            }
            if (etPassword.getText().length() < 5 || etPassword.getText().toString().contains(" ")) {
                etPassword.setError("Enter correct password");
                throw new Exception("Check password");
            }
            goForLogin(etMobile.getText().toString(), etPassword.getText().toString(), "deviceTToken");
        }
    }

    public void goToSignUp(View view) {
        startActivity(new Intent(this, SignUpActivity.class));
        finish();
        overridePendingTransitionExit();
    }

    public void skipLogin(View view) {
        startActivity(new Intent(this, LocationSelectionActivity.class));
        finish();
        overridePendingTransitionExit();
    }

    public void switchImage(View view) {
        if (ivSwitchMobileEmail.getTag() != null && ivSwitchMobileEmail.getTag().toString().equals("Email")) {
            ivSwitchMobileEmail.setImageResource(R.drawable.phone);
            ivSwitchMobileEmail.setTag("Mobile");
            etEmail.setText("");
            etEmail.clearFocus();
            inputLayoutEmail.setVisibility(View.GONE);
            inputLayoutMobile.setVisibility(View.VISIBLE);
            etMobile.requestFocus();
        } else {
            ivSwitchMobileEmail.setImageResource(R.drawable.at);
            ivSwitchMobileEmail.setTag("Email");
            etMobile.setText("");
            etMobile.clearFocus();
            inputLayoutMobile.setVisibility(View.GONE);
            inputLayoutEmail.setVisibility(View.VISIBLE);
            etEmail.requestFocus();
        }
    }

    private void goForLogin(String username, String password, String deviceToken) {
        String url = UrlConstants.GET_LOGIN_URL + UrlConstants.LOGIN_PARAM_USERNAME + username
                + UrlConstants.LOGIN_PARAM_PASSWORD + password +
                UrlConstants.LOGIN_PARAM_DEVICE_TOKEN + Utils.getSavedUserDetail(this, Constants.FCM_TOKEN);
        displayLoader(this, "Logging in...");

        GsonRequest request = new GsonRequest<LoginResponse>(url, null,
                LoginResponse.class, null, new Response.Listener<LoginResponse>() {
            @Override
            public void onResponse(LoginResponse response) {
                Log.d("Food categories Ok", response.toString());
                if (response.getResponse() != null && response.getResponse().equals("1")) {
                    Utils.saveUser(response, UserLoginControlActivity.this);
                    if (loginTransition == Constants.LOGIN_AFTER_DELIVERY) {
                        startActivity(new Intent(UserLoginControlActivity.this, DeliveryManagementActivity.class));
                    } else if (loginTransition == Constants.LOGIN_AFTER_LOCATION) {
                        startActivity(new Intent(UserLoginControlActivity.this, LocationSelectionActivity.class));
                    }
                    finish();
                    overridePendingTransitionExit();
                } else {
                    Utils.alertOkMessage(UserLoginControlActivity.this, response.getStatus(), "OK");
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
}
