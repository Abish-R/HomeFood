package abish.veettusorudemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Abish on 8/13/2018.
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
        if (pDialog.isShowing()) {
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
}
