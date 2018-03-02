package abish.veettusorudemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;

import abish.veettusorudemo.constants.Constants;
import abish.veettusorudemo.network.response.LoginResponse;

import static abish.veettusorudemo.constants.Constants.FCM_TOKEN;
import static abish.veettusorudemo.constants.Constants.LOGIN_USER_ID;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Abish on 8/13/2018.
 * </p>
 */

public class Utils {

    private static ProgressDialog pDialog;

    public static void displayLoader(Context context, String message) {
        if (pDialog == null) {
            pDialog = new ProgressDialog(context);
        }
        if (!pDialog.isShowing()) {
            pDialog.setMessage(message);
            pDialog.show();
        }
    }

    public static void displayLoader(Activity context, String message) {
        if (pDialog == null) {
            pDialog = new ProgressDialog(context);
        }
        if (!pDialog.isShowing()) {
            pDialog.setMessage(message);
            pDialog.show();
        }
    }

    public static void hideLoader(){
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.hide();
            pDialog.cancel();
            pDialog = null;
        }
    }

    public static void alertOkMessage(Context mContext, String message, String buttonName) {
        AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, buttonName,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public static void alertOkCancelMessage(Context mContext, String message,
                                                   DialogInterface cancel, DialogInterface ok) {
        AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setMessage(message);
        if(cancel != null) {
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
        }
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public static void saveUser(LoginResponse response, Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Constants.LOGIN_ADUPANKARAI_PREF, MODE_PRIVATE).edit();
        editor.putString(LOGIN_USER_ID, response.getUserId());
        editor.putString(Constants.LOGIN_NAME, response.getName());
        editor.putString(Constants.LOGIN_MOBILE, response.getMobile());
        editor.putString(Constants.LOGIN_EMAIL, response.getEmail());
        editor.apply();
    }

    public static void saveToken(String token, Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Constants.LOGIN_ADUPANKARAI_PREF, MODE_PRIVATE).edit();
        editor.putString(FCM_TOKEN, token);
        editor.apply();
    }

    public static String getSavedUserDetail(Context context, String detail){
        SharedPreferences prefs = context.getSharedPreferences(Constants.LOGIN_ADUPANKARAI_PREF, MODE_PRIVATE);

        switch (detail){
            case Constants.LOGIN_USER_ID:
                return prefs.getString(Constants.LOGIN_USER_ID, "null");
            case Constants.LOGIN_NAME:
                return prefs.getString(Constants.LOGIN_NAME, "null");
            case Constants.LOGIN_EMAIL:
                return prefs.getString(Constants.LOGIN_EMAIL, "null");
            case Constants.LOGIN_MOBILE:
                return prefs.getString(Constants.LOGIN_MOBILE, "null");
            case Constants.FCM_TOKEN:
                return prefs.getString(Constants.FCM_TOKEN, "null");
            default:
                return "null";
        }
    }
}
