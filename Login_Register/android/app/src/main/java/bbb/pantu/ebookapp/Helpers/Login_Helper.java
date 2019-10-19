package bbb.pantu.ebookapp.Helpers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.widget.CheckBox;

public class Login_Helper {

    public static Activity activity;

    public SharedPreferences Preferences;
    public SharedPreferences.Editor Preference_Editor;



    private final String LoginSessionPreference = "Login_Pref_Session";
    public String is_logged_Pref = "is_logged_Pref";
    public String remember_user_session = "remember_user_session";
    public String user_id_Pref = "user_id_Pref";
    public String is_first_time = "is_first_time";
    public String user_Email_Pref = "user_Email_Pref";
    public String user_Password_Pref = "user_Password_Pref";
    public String user_Name_Pref = "user_Name_Pref";
    public String user_Profile_Image_Pref = "user_Profile_Image_Pref";

    @SuppressLint("CommitPrefEdits")
    public Login_Helper(Activity activity) {
        Login_Helper.activity = activity;

        Preferences = activity.getSharedPreferences(LoginSessionPreference, 0); // 0 - for private mode
        Preference_Editor = Preferences.edit();

    }



    public void logout() {
        if (Preferences.getBoolean(is_logged_Pref, false)) {
            Preference_Editor.putBoolean(is_logged_Pref, false);
            Preference_Editor.putBoolean(remember_user_session, false);
            Preference_Editor.commit();
        }
    }

    public void create_session(CheckBox sendedCheckbox, String sendedUserId, String sendedUserName,String sendedEmail,String sendedPassword, String sendedProfileImage) {

        Preference_Editor.putBoolean(is_logged_Pref,true);
        Preference_Editor.putString(user_Email_Pref,sendedEmail);
        Preference_Editor.putString(user_Password_Pref,sendedPassword);
        Preference_Editor.putString(user_id_Pref,sendedUserId);
        Preference_Editor.putString(user_Name_Pref,sendedUserName);
        Preference_Editor.putString(user_Profile_Image_Pref,sendedProfileImage);


        if (sendedCheckbox.isChecked()) {
            Preference_Editor.putBoolean(remember_user_session,true);
        }else{
            Preference_Editor.putBoolean(remember_user_session,false);
        }

        Preference_Editor.commit();
    }


}
