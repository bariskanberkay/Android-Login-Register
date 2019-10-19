package bbb.pantu.ebookapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import bbb.pantu.ebookapp.Constants.ApiConstants;
import bbb.pantu.ebookapp.R;
import bbb.pantu.ebookapp.Utils.Helper;
import bbb.pantu.ebookapp.Utils.Tools;
import cz.msebera.android.httpclient.Header;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText EditText_Forgot_Pass_Email;
    private String Email;
    private Helper helper;

    private String AsyncMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getWindow().getAttributes().windowAnimations = R.style.Fade;
        initToolbar();
        helper = new Helper(ForgotPasswordActivity.this);
        EditText_Forgot_Pass_Email =  findViewById(R.id.TextBox_Forgot_Pass_Email);


        Button Button_Recover_Password = findViewById(R.id.Button_Recover_Password);

        Button_Recover_Password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Email = EditText_Forgot_Pass_Email.getText().toString();
                Pre_Send_Check(Email);
            }
        });

    }


    public void Pre_Send_Check(String Email) {

        EditText_Forgot_Pass_Email.setError(null);

        if (!isValidMail(Email) || Email.isEmpty()) {
            EditText_Forgot_Pass_Email.requestFocus();
            EditText_Forgot_Pass_Email.setError(getResources().getString(R.string.please_enter_email));
        }  else {

            EditText_Forgot_Pass_Email.setText("");
            if(Helper.isNetworkAvailable(ForgotPasswordActivity.this)){
                Full_Send(Email);
            }else {
                helper.showCheckInternetDialog();
            }

        }
    }

    private boolean isValidMail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }



    public void Full_Send(String sendEmail) {

        String register = ApiConstants.FORGOTPASS + "&email=" + sendEmail;

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(register, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                Log.d("Response", new String(responseBody));
                String res = new String(responseBody);

                try {
                    JSONObject jsonObject = new JSONObject(res);

                    JSONArray jsonArray = jsonObject.getJSONArray("APP");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);
                        String msg = object.getString("msg");
                        String success = object.getString("success");

                        if (success.equals("1")) {
                            helper.snackBarFloating("Success",AsyncMessage, "Got It!", true);

                            startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
                            finishAffinity();
                        } else {
                            AsyncMessage = object.getString("msg");
                            helper.snackBarFloating("Failed",AsyncMessage, "Got It!", true);

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


    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.verification_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.grey_900);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {

        }
        return super.onOptionsItemSelected(item);
    }
}
