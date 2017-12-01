package abish.veettusorudemo.service;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import abish.veettusorudemo.Utils;

/**
 * Created by Abish on 11/25/2017.
 */

public class HotelAppGenerateToken extends FirebaseInstanceIdService {
    private static final String TAG = HotelAppGenerateToken.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        // Saving reg id to shared preferences
        storeRegIdInPref(refreshedToken);
    }

    private void sendRegistrationToServer(final String token) {
        // sending gcm token to server
        Log.e(TAG, "sendRegistrationToServer: " + token);
    }

    private void storeRegIdInPref(String token) {
        Utils.saveToken(token, getApplicationContext());
    }
}
