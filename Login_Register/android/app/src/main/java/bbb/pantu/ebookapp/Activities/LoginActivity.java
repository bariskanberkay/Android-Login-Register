package bbb.pantu.ebookapp.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

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
import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {


    private EditText EditText_Email, EditText_Password;
    private CheckBox CheckBox_Remember;
    private String Text_Email, Text_Password;


    private String AsyncMessage;

    public static final String LoginPreference = "Login_Pref";
    public static final String Pref_Email = "Pref_Email";
    public static final String Pref_Password = "Pref_Password";
    public static final String Pref_Check = "Pref_Check";


    private static SharedPreferences Preferences_Login;
    private static SharedPreferences.Editor Preference_Editor_Login;

    private Login_Helper login_helper;

    private Helper helper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().getAttributes().windowAnimations = R.style.Fade;


        helper = new Helper(LoginActivity.this);
        login_helper = new Login_Helper(LoginActivity.this);

        Preferences_Login = getSharedPreferences(LoginPreference, 0); // 0 - for private mode
        Preference_Editor_Login = Preferences_Login.edit();

        EditText_Email =  findViewById(R.id.TextBox_Login_Email);
        EditText_Password =  findViewById(R.id.TextBox_Login_Password);


        CheckBox_Remember = findViewById(R.id.CheckBox_Login_Remember);
        CheckBox_Remember.setChecked(false);

        TextView TextView_Button_Register =  findViewById(R.id.Button_Register);

        TextView TextView_Button_Forgot_Password =  findViewById(R.id.Button_Forgot_Password);

        TextView_Button_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

            }
        });

        TextView_Button_Forgot_Password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));

            }
        });


        TextView TextView_Button_Login =  findViewById(R.id.Button_Login);


        if (Preferences_Login.getBoolean(Pref_Check, false)) {
            EditText_Email.setText(Preferences_Login.getString(Pref_Email, null));
            EditText_Password.setText(Preferences_Login.getString(Pref_Password, null));
            CheckBox_Remember.setChecked(true);
        } else {
            EditText_Email.setText("");
            EditText_Password.setText("");
            CheckBox_Remember.setChecked(false);
        }

        CheckBox_Remember.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("SmoothCheckBox", String.valueOf(isChecked));
                if (isChecked) {
                    Preference_Editor_Login.putString(Pref_Email, EditText_Email.getText().toString());
                    Preference_Editor_Login.putString(Pref_Password, EditText_Password.getText().toString());
                    Preference_Editor_Login.putBoolean(Pref_Check, true);
                    Preference_Editor_Login.commit();
                } else {
                    Preference_Editor_Login.putBoolean(Pref_Check, false);
                    Preference_Editor_Login.putString(Pref_Email, "");
                    Preference_Editor_Login.putString(Pref_Password, "");
                    Preference_Editor_Login.commit();
                }
            }
        });

        TextView_Button_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Text_Email = EditText_Email.getText().toString();
                Text_Password = EditText_Password.getText().toString();
                Pre_Login_Check(CheckBox_Remember,Text_Email,Text_Password);
            }
        });


    }

    private boolean isValidMail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void Pre_Login_Check(CheckBox CheckBoxStatus, String Text_Email, String Text_Password) {

        EditText_Email.setError(null);
        EditText_Password.setError(null);

        if (!isValidMail(Text_Email) || Text_Email.isEmpty()) {
            EditText_Email.requestFocus();
            EditText_Email.setError(getResources().getString(R.string.please_enter_email));
        } else if (Text_Password.isEmpty()) {
            EditText_Password.requestFocus();
            EditText_Password.setError(getResources().getString(R.string.please_enter_password));
        } else {
            if (Helper.isNetworkAvailable(LoginActivity.this)) {
                Full_login(Text_Email, Text_Password, CheckBoxStatus);
            } else {
                helper.showCheckInternetDialog();
            }

        }
    }

    public void Full_login(final String sendEmail, final String sendPassword, final CheckBox sendcheckBox) {



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
                            String user_id = object.getString("user_id");
                            String name = object.getString("name");
                            String profile_image = object.getString("profile_image");


                            if (sendcheckBox.isChecked()) {
                                Preference_Editor_Login.putString(Pref_Email, EditText_Email.getText().toString());
                                Preference_Editor_Login.putString(Pref_Password, EditText_Password.getText().toString());
                                Preference_Editor_Login.putBoolean(Pref_Check, true);
                                Preference_Editor_Login.commit();



                            }else{

                                Preference_Editor_Login.putString(Pref_Email, "");
                                Preference_Editor_Login.putString(Pref_Password, "");
                                Preference_Editor_Login.putBoolean(Pref_Check, false);
                                Preference_Editor_Login.commit();




                            }
                            login_helper.create_session(sendcheckBox,user_id,name,sendEmail,sendPassword,profile_image);




                            EditText_Email.setText("");
                            EditText_Password.setText("");


                        startActivity(new Intent(LoginActivity.this, MainActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));


                        }else if(success.equals("2")) {
                            String user_id = object.getString("user_id");
                            Intent intent =new Intent(LoginActivity.this, VerificationActivity.class);
                            intent.putExtra("user_id", user_id);
                            startActivity(intent);



                        }else{
                            AsyncMessage = object.getString("msg");
                            helper.snackBarFloating("Login Failed",AsyncMessage, "Got It!", true);

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
