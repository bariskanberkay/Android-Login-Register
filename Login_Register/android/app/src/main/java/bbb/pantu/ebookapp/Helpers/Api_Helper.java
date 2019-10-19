package bbb.pantu.ebookapp.Helpers;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import bbb.pantu.ebookapp.Constants.ApiConstants;
import bbb.pantu.ebookapp.Items.AboutApp.AboutUsList;
import bbb.pantu.ebookapp.MainActivity;
import bbb.pantu.ebookapp.SplashActivity;
import cz.msebera.android.httpclient.Header;

public class Api_Helper {

    private static Activity activity;

    public static Boolean API_STATUS_BOOLEAN = false;

    public Api_Helper(Activity activity) {
        Api_Helper.activity = activity;
        get_API_Status();

    }

    public void get_API_Status() {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(ApiConstants.API_STATUS, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                Log.d("Response", new String(responseBody));
                String res = new String(responseBody);

                try {
                    JSONObject jsonObject = new JSONObject(res);

                    JSONArray jsonArray = jsonObject.getJSONArray("APP");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);
                        String success = object.getString("success");
                        API_STATUS_BOOLEAN = success.equals("1");


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }


}
