package com.example.vancouvertourismappfinal.RestRoomFinderClasses.servers;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

public final class RequestHandler {

    private RequestHandler() {}

    /**
     * @param onSuccessListener Response.Listener<JSONObject>  on success listener
     * @param errorListener     Response.ErrorListener on Error Listener
     * @param queue             RequestQueue
     * @param url               String For Url
     */
    public static void requestJsonObject(

            Response.Listener<JSONObject> onSuccessListener,
            Response.ErrorListener errorListener, RequestQueue queue, String url) {
        /*
		 * Method.GET, url, null, onSuccessListener, errorListener
		 */
        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(url,
                null, onSuccessListener, errorListener);

        jsonObjectReq.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjectReq);
    }

    /**
     * @param method            GET / POST
     * @param onSuccessListener Response.Listener<JSONObject>  on success listener
     * @param errorListener     Response.ErrorListener on Error Listener
     * @param queue             RequestQueue
     * @param url               String For Url
     */

    public static void requestJsonObject(int method,
                                         Response.Listener<JSONObject> onSuccessListener,
                                         Response.ErrorListener errorListener, RequestQueue queue, String url) {
        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(method,
                url,null, onSuccessListener, errorListener);

        jsonObjectReq.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjectReq);
    }

    /**
     * @param onSuccessListener Response.Listener<JSONObject>  on success listener
     * @param errorListener     Response.ErrorListener on Error Listener
     * @param queue             RequestQueue
     * @param url               String For Url
     */
    public static void requestJsonArray(
            Response.Listener<JSONArray> onSuccessListener,
            Response.ErrorListener errorListener, RequestQueue queue, String url) {
        JsonArrayRequest jsonObjectReq = new JsonArrayRequest(url,
                onSuccessListener, errorListener);
        jsonObjectReq.setRetryPolicy(new DefaultRetryPolicy(12000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjectReq);
    }
}


