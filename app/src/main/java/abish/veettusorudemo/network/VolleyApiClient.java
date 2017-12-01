package abish.veettusorudemo.network;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by INDP on 16-Oct-16.
 */
public class VolleyApiClient extends Application {
    public static final String TAG = VolleyApiClient.class.getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private String UNIQUE_ID_PREF = "unique_id_pref";
    private String UNIQUE_ID = "unique_id";
    private String NAME = "name";
    private String MOBILE = "mobile";

    private static VolleyApiClient mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized VolleyApiClient getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
        Log.i("Url Request", req.toString());
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public void setUniqueValue() {
//        String tmDevice, tmSerial, androidId;
//        final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
//
//        tmDevice = "" + tm.getDeviceId();
//        tmSerial = "" + tm.getSimSerialNumber();
//        androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
//
//        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
//        return deviceUuid.toString();
        String ss = Settings.Secure.getString(mInstance.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        setUniqueId(ss);
    }

    public String getUniqueId() {
        SharedPreferences userDetails = mInstance.getSharedPreferences(UNIQUE_ID_PREF, Context.MODE_PRIVATE);
        return userDetails.getString(UNIQUE_ID, "");
    }

    public void setUniqueId(String id) {
        SharedPreferences.Editor editor = mInstance.getSharedPreferences(UNIQUE_ID_PREF, Context.MODE_PRIVATE).edit();
        editor.putString(UNIQUE_ID, id);
        editor.commit();
    }

    public void saveNameMobile(String name, String mob) {
        SharedPreferences.Editor editor = mInstance.getSharedPreferences(UNIQUE_ID_PREF, Context.MODE_PRIVATE).edit();
        editor.putString(NAME, name);
        editor.putString(MOBILE, mob);
        editor.commit();
    }

    public String getName() {
        SharedPreferences userDetails = mInstance.getSharedPreferences(UNIQUE_ID_PREF, Context.MODE_PRIVATE);
        return userDetails.getString(NAME, "");
    }

    public String getMobile() {
        SharedPreferences userDetails = mInstance.getSharedPreferences(UNIQUE_ID_PREF, Context.MODE_PRIVATE);
        return userDetails.getString(MOBILE, "");
    }
}
