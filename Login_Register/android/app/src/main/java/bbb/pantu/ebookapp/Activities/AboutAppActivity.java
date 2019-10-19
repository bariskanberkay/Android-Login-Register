package bbb.pantu.ebookapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import bbb.pantu.ebookapp.Helpers.AboutApp_Helper;
import bbb.pantu.ebookapp.R;

public class AboutAppActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        getWindow().getAttributes().windowAnimations = R.style.Fade;
        initToolbar();

        TextView textView_app_name = (TextView) findViewById(R.id.textView_aboutApp_app_name_about_us);
        TextView textView_app_version = (TextView) findViewById(R.id.textView_aboutApp_app_version_about_us);
        TextView textView_app_description = (TextView) findViewById(R.id.textView_aboutApp_app_description_about_us);

        textView_app_name.setText(AboutApp_Helper.aboutUsList.getApp_name());
        textView_app_version.setText(AboutApp_Helper.aboutUsList.getApp_version());
        textView_app_description.setText(Html.fromHtml(AboutApp_Helper.aboutUsList.getApp_description()));

    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.about_app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("About");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
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
