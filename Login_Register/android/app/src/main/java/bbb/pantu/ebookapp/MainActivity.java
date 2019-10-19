package bbb.pantu.ebookapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import bbb.pantu.ebookapp.Activities.AboutAppActivity;
import bbb.pantu.ebookapp.Activities.LoginActivity;
import bbb.pantu.ebookapp.Activities.ProfileActivity;
import bbb.pantu.ebookapp.Activities.IntroActivity;
import bbb.pantu.ebookapp.Fragments.HomeFragment;
import bbb.pantu.ebookapp.Helpers.AboutApp_Helper;
import bbb.pantu.ebookapp.Helpers.Login_Helper;
import bbb.pantu.ebookapp.Utils.Helper;
import de.hdodenhof.circleimageview.CircleImageView;

import static bbb.pantu.ebookapp.Constants.ApiConstants.URL;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Login_Helper login_helper;
    private AboutApp_Helper aboutapp_helper;

    private String Username;
    private String Email;
    private String Profile_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getAttributes().windowAnimations = R.style.Fade;
        login_helper = new Login_Helper(MainActivity.this);
        Username = login_helper.Preferences.getString(login_helper.user_Name_Pref, null);
        Email = login_helper.Preferences.getString(login_helper.user_Email_Pref, null);
        Profile_image = login_helper.Preferences.getString(login_helper.user_Profile_Image_Pref, null);

        Toolbar toolbar = (Toolbar) findViewById(R.id.Main_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername =  headerView.findViewById(R.id.Nav_Header_Name_Text);
        TextView navEmail =  headerView.findViewById(R.id.Nav_Header_Email_Text);
        CircleImageView profile_image = headerView.findViewById(R.id.Nav_Header_Profile_Image);

        navUsername.setText(Username);
        navEmail.setText(Email);
        Picasso.get().load(URL+"images/"+Profile_image)
                .into(profile_image);

        navigationView.setCheckedItem(R.id.nav_home);
        Fragment fragment = new HomeFragment();
        displaySelectedFragment(fragment);

        if (Helper.isNetworkAvailable(MainActivity.this)) {
            aboutapp_helper = new AboutApp_Helper(MainActivity.this);
            aboutapp_helper.aboutUs();
        } else {
            Toast.makeText(this, getResources().getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
        }



    }

    private void displaySelectedFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {

            login_helper = new Login_Helper(MainActivity.this);
            login_helper.logout();
            startActivity(new Intent(MainActivity.this, LoginActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));

            return true;
        }else if (id == R.id.action_profile) {


            startActivity(new Intent(MainActivity.this, ProfileActivity.class));

            return true;
        }else if (id == R.id.action_about_us) {

            startActivity(new Intent(MainActivity.this, AboutAppActivity.class));
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;
        if (id == R.id.nav_home) {
            fragment = new HomeFragment();
            displaySelectedFragment(fragment);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
