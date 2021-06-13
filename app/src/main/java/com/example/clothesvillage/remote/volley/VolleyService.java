package com.example.clothesvillage.remote.volley;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;

import com.example.clothesvillage.L;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class VolleyService {

    private VolleyResult resultCallback = null;
    private Context mContext = null;


    private Response.Listener<JSONObject> successListener = response -> resultCallback.notifySuccess(null, response);

    private Response.ErrorListener errorListener = error -> resultCallback.notifyError(error);

    public VolleyService(VolleyResult resultCallback, Context context) {
        this.resultCallback = resultCallback;
        mContext = context;
    }



    public void getGeoWTM(Location location) {
        //위치정보 좌표를 TM좌표로 변환한다.
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);


        String url = "https://dapi.kakao.com/v2/local/geo/coord2address.json?" + "x=" + location.getLongitude() + "&y=" + location.getLatitude();

        L.e("::::getGeoWTM URL : " + url);

        Request<JSONObject> req = new Request<JSONObject>(Request.Method.GET, url, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                resultCallback.notifyError(error);
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "KakaoAK " + "261b5c63a15536286de812713e966601");
                return params;
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    String json = new String(
                            response.data, HttpHeaderParser.parseCharset(response.headers));
                    return Response.success(new JSONObject(json), HttpHeaderParser.parseCacheHeaders(response));

                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (JsonSyntaxException e) {
                    return Response.error(new ParseError(e));
                } catch (JSONException e) {
                    return Response.error(new ParseError(e));
                }
            }

            @Override
            protected void deliverResponse(JSONObject response) {
                resultCallback.notifySuccess("", response);
            }
        };
        requestQueue.add(req);
    }

}
