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
import android.widget.CheckBox;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import bbb.pantu.ebookapp.Constants.ApiConstants;
import bbb.pantu.ebookapp.Helpers.Login_Helper;
import bbb.pantu.ebookapp.MainActivity;
import bbb.pantu.ebookapp.R;
import bbb.pantu.ebookapp.Utils.Helper;
import bbb.pantu.ebookapp.Utils.Tools;
import cz.msebera.android.httpclient.Header;

public class VerificationActivity extends AppCompatActivity {

    private Login_Helper login_helper;
    private EditText EditText_Verification;
    private String Text_Verification;
    private String user_id;
    private String AsyncMessage;
    private Helper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        getWindow().getAttributes().windowAnimations = R.style.Fade;

        helper = new Helper(VerificationActivity.this);
        login_helper = new Login_Helper(VerificationActivity.this);


        initToolbar();


        user_id = getIntent().getExtras().getString("user_id");

        EditText_Verification =  findViewById(R.id.TextBox_Verification_Code);

        Button Continue_Verification = findViewById(R.id.Button_Verification_Continue);

        Continue_Verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Text_Verification = EditText_Verification.getText().toString();
                Pre_Verification_Check(Text_Verification);
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


    public void Pre_Verification_Check(String Text_Verification) {

        EditText_Verification.setError(null);

        if (Text_Verification.isEmpty()) {
            EditText_Verification.requestFocus();
            EditText_Verification.setError(getResources().getString(R.string.please_enter_password));
        } else {
            if (Helper.isNetworkAvailable(VerificationActivity.this)) {
                Full_Verification(Text_Verification,user_id);
            } else {
                helper.showCheckInternetDialog();
            }

        }
    }

    public void Full_Verification(final String sendedVerificationCode, final String sendedUserid) {



        String verify = ApiConstants.VERIFY + "&user_id=" + sendedUserid + "&verification_code=" + sendedVerificationCode;

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(verify, null, new AsyncHttpResponseHandler() {


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


                            startActivity(new Intent(VerificationActivity.this, LoginActivity.class)
                                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));


                        }else{

                            AsyncMessage = object.getString("msg");
                            helper.snackBarFloating("Verification Failed",AsyncMessage, "Got It!", true);
                            EditText_Verification.setText("");

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
