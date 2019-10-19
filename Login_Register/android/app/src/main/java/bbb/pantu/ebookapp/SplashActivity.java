package bbb.pantu.ebookapp;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import bbb.pantu.ebookapp.Activities.IntroActivity;
import bbb.pantu.ebookapp.Activities.LoginActivity;
import bbb.pantu.ebookapp.Activities.RegisterActivity;
import bbb.pantu.ebookapp.Constants.ApiConstants;
import bbb.pantu.ebookapp.Helpers.Api_Helper;
import bbb.pantu.ebookapp.Helpers.Login_Helper;
import bbb.pantu.ebookapp.Utils.Helper;
import cz.msebera.android.httpclient.Header;

import static bbb.pantu.ebookapp.Helpers.Api_Helper.API_STATUS_BOOLEAN;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3500;
    private Helper helper;

    private Login_Helper Login_helper;

    private Api_Helper Api_helper;

    TextView l1,l2;

    Animation uptodown,downtoup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        l1 =  findViewById(R.id.Text_Logo);
        l2 =  findViewById(R.id.Text_Desc);
        uptodown = AnimationUtils.loadAnimation(this,R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup);
        l1.setAnimation(uptodown);
        l2.setAnimation(downtoup);


        Login_helper = new Login_Helper(SplashActivity.this);
        Api_helper = new Api_Helper(SplashActivity.this);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }


        if (Helper.isNetworkAvailable(SplashActivity.this)) {
            new Handler().postDelayed(new Runnable() {



                @Override
                public void run() {
                            if(API_STATUS_BOOLEAN){
                                if (Login_helper.Preferences.getBoolean(Login_helper.remember_user_session, false)) {
                                    Log.d("value", String.valueOf(Login_helper.Preferences.getBoolean(Login_helper.remember_user_session, false)));
                                    login(Login_helper.Preferences.getString(Login_helper.user_Email_Pref, null), Login_helper.Preferences.getString(Login_helper.user_Password_Pref, null));
                                } else {
                                    if(Login_helper.Preferences.getBoolean(Login_helper.is_first_time, true)){
                                        Intent i = new Intent(SplashActivity.this, IntroActivity.class);
                                        startActivity(i);
                                    }else{
                                        Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                                        Log.d("value", String.valueOf(Login_helper.Preferences.getBoolean(Login_helper.remember_user_session, false)));
                                        startActivity(i);
                                    }


                                }

                            }else{
                                Toast.makeText(SplashActivity.this, "Api Deactive", Toast.LENGTH_SHORT).show();
                            }


                    finish();
                }
            }, SPLASH_TIME_OUT);
        } else {
            Login_helper.Preference_Editor.putBoolean(Login_helper.remember_user_session, false);
            Login_helper.Preference_Editor.commit();
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));

        }

    }


    public void login(final String sendEmail, final String sendPassword) {

        String login = ApiConstants.LOGIN + "&email=" + sendEmail + "&password=" + sendPassword;

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(login, null, new AsyncHttpResponseHandler() {
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
                        if (success.equals("1")) {
                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Login_helper.Preference_Editor.putBoolean(Login_helper.remember_user_session, false);
                            Login_helper.Preference_Editor.commit();
                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        }

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
