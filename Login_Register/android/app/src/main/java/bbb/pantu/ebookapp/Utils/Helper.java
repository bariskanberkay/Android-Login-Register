package bbb.pantu.ebookapp.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import bbb.pantu.ebookapp.R;

public class Helper {

    public static Activity activity;



    public Helper(Activity activity) {
        this.activity = activity;


    }

    public static boolean isNetworkAvailable(Activity activity) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void snackBarFloating(String sendedHeader,String sendedMessage,String sendedButtonText,Boolean sendedButtonActive) {

        final Snackbar make = Snackbar.make(activity.findViewById(android.R.id.content), (CharSequence) "", 0);
        View inflate = activity.getLayoutInflater().inflate(R.layout.snackbar_floating_dark, null);
        make.getView().setBackgroundColor(0);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) make.getView();
        snackbarLayout.setPadding(0, 0, 0, 0);
        TextView Header = (TextView) inflate.findViewById(R.id.SnackBar_Header);
        TextView Message = (TextView) inflate.findViewById(R.id.SnackBar_Message);
        TextView ButtonText = (TextView) inflate.findViewById(R.id.SnackBar_Button);

        Header.setText(sendedHeader);
        Message.setText(sendedMessage);
        ButtonText.setText(sendedButtonText);


        inflate.findViewById(R.id.SnackBar_Button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                make.dismiss();

            }
        });
        snackbarLayout.addView(inflate, 0);
        make.show();
    }


    public void showCheckInternetDialog() {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_check_internet_conntection);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        ((AppCompatButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }


}
