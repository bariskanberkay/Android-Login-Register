package bbb.pantu.ebookapp.Helpers;

import android.app.Activity;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import bbb.pantu.ebookapp.Constants.ApiConstants;
import bbb.pantu.ebookapp.Items.AboutApp.AboutUsList;
import cz.msebera.android.httpclient.Header;

public class AboutApp_Helper {

    public static AboutUsList aboutUsList;
    public static Activity activity;


    public AboutApp_Helper(Activity activity) {
        AboutApp_Helper.activity = activity;


    }

    public void aboutUs() {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(ApiConstants.ABOUT_APP, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                Log.d("Response", new String(responseBody));
                String res = new String(responseBody);

                try {
                    JSONObject jsonObject = new JSONObject(res);

                    JSONArray jsonArray = jsonObject.getJSONArray("APP");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);
                        String app_name = object.getString("app_name");
                        String app_logo = object.getString("app_logo");
                        String app_version = object.getString("app_version");
                        String app_author = object.getString("app_author");
                        String app_contact = object.getString("app_contact");
                        String app_email = object.getString("app_email");
                        String app_website = object.getString("app_website");
                        String app_description = object.getString("app_description");
                        String app_privacy_policy = object.getString("app_privacy_policy");

                        aboutUsList = new AboutUsList(app_name, app_logo, app_version, app_author, app_contact, app_email, app_website, app_description, app_privacy_policy);

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
