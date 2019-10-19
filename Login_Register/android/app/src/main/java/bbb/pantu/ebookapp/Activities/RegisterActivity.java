package bbb.pantu.ebookapp.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import bbb.pantu.ebookapp.Constants.ApiConstants;
import bbb.pantu.ebookapp.R;
import bbb.pantu.ebookapp.Utils.Helper;
import cz.msebera.android.httpclient.Header;

public class RegisterActivity extends AppCompatActivity {


    private EditText EditText_UserName, EditText_Email, EditText_Password, EditText_Phone;
    private String UserName, Email, Password, PhoneNo;
    private String AsyncMessage;

    private Helper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().getAttributes().windowAnimations = R.style.Fade;

        helper = new Helper(RegisterActivity.this);

        EditText_UserName =  findViewById(R.id.TextBox_Register_UserName);
        EditText_Email =  findViewById(R.id.TextBox_Register_Email);
        EditText_Password =  findViewById(R.id.TextBox_Register_Password);
        EditText_Phone =  findViewById(R.id.TextBox_Register_Phone);

        TextView TextView_Button_Login =  findViewById(R.id.Button_Login);

        TextView_Button_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
               // finishAffinity();
            }
        });

        TextView TextView_Button_Register =  findViewById(R.id.Button_Register);
        TextView_Button_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserName = EditText_UserName.getText().toString();
                Email = EditText_Email.getText().toString();
                Password = EditText_Password.getText().toString();
                PhoneNo = EditText_Phone.getText().toString();

                Pre_Register_Check(UserName,Email,Password,PhoneNo);

            }
        });




    }
    private boolean isValidMail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void Pre_Register_Check(String UserName,String Email,String Password, String PhoneNo) {

        EditText_UserName.setError(null);
        EditText_Email.setError(null);
        EditText_Password.setError(null);
        EditText_Phone.setError(null);

        if (UserName.isEmpty() || UserName.equals("")) {
            EditText_UserName.requestFocus();
            EditText_UserName.setError(getResources().getString(R.string.please_enter_name));
        } else if (!isValidMail(Email) || Email.isEmpty()) {
            EditText_Email.requestFocus();
            EditText_Email.setError(getResources().getString(R.string.please_enter_email));
        } else if (Password.isEmpty() || Password.equals("")) {
            EditText_Password.requestFocus();
            EditText_Password.setError(getResources().getString(R.string.please_enter_password));
        } else if (PhoneNo.isEmpty() || PhoneNo.equals("")) {
            EditText_Phone.requestFocus();
            EditText_Phone.setError(getResources().getString(R.string.please_enter_phone));
        } else {
            EditText_UserName.setText("");
            EditText_Email.setText("");
            EditText_Password.setText("");
            EditText_Phone.setText("");
            if(Helper.isNetworkAvailable(RegisterActivity.this)){
                Full_Register(UserName, Email, Password, PhoneNo);
            }else {
                helper.showCheckInternetDialog();
            }

        }
    }


    public void Full_Register(String sendUserName, String sendEmail, String sendPassword, String sendPhone) {

        String register = ApiConstants.REGISTER + "username=" + sendUserName + "&email=" + sendEmail + "&password=" + sendPassword + "&phone=" + sendPhone;

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
                            Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            finishAffinity();
                        } else {
                            AsyncMessage = object.getString("msg");
                            helper.snackBarFloating("Register Failed",AsyncMessage, "Got It!", true);

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
