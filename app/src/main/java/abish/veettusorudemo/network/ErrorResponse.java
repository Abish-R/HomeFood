package abish.veettusorudemo.network;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

/**
 * Created by Abish on 3/2/2018.
 * </p>
 */

public class ErrorResponse implements Response.ErrorListener {


    public ErrorResponse(){

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {

        } else if (error instanceof AuthFailureError) {
            //TODO
        } else if (error instanceof ServerError) {
            //TODO
        } else if (error instanceof NetworkError) {
            //TODO
        } else if (error instanceof ParseError) {
            //TODO
        }
    }
}
