package controller.android.treedreamapp.activity;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;


import controller.android.treedreamapp.R;
import controller.android.treedreamapp.common.Config;
import controller.android.treedreamapp.common.UserSessionManager;
import controller.android.treedreamapp.fragments.About;
import controller.android.treedreamapp.fragments.Dashboard;
import controller.android.treedreamapp.fragments.GiftCategoryFragment;
import controller.android.treedreamapp.fragments.GiftTree;
import controller.android.treedreamapp.fragments.MyTeam;
import controller.android.treedreamapp.fragments.OrderHistory;
import controller.android.treedreamapp.fragments.PrivacyPolicyFragment;
import controller.android.treedreamapp.fragments.TrackTree;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
        private Toolbar toolbar;
        private FloatingActionButton fab;
        private Context context;
        private TextView userName,userEmail;
        private ImageView userProfile,goProfile;
        private UserSessionManager userSessionManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        userSessionManager = new UserSessionManager(this);
        context = MainActivity.this;

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        onSelectItem(R.id.nav_dashboard);

        View headerLayout = navigationView.getHeaderView(0);
        userProfile = headerLayout.findViewById(R.id.userProfile);
        userName = headerLayout.findViewById(R.id.userName);
        userEmail = headerLayout.findViewById(R.id.userEmail);
        goProfile = headerLayout.findViewById(R.id.goProfile);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        try {
            if(userSessionManager.isUserLoggedIn()) {
                if (user != null) {
                    String name = user.getDisplayName();
                    String email = user.getEmail();
                    Uri photoUrl = user.getPhotoUrl();
                    String uid = user.getUid();
                    // Log.d("pic path: ",""+photoUrl.getPath());
                    userName.setText(name);
                    userEmail.setText(email);
                    //Picasso.with(MainActivity.this).load().into(userProfile);
                    Picasso.get().load("https://graph.facebook.com/" + photoUrl.getPath() + "?type=large").into(userProfile);

                }else{
                   Bundle extras = getIntent().getExtras();
                   if(extras!= null){
                       userName.setText(userSessionManager.getUserDetails().get("name"));
                       userEmail.setText(userSessionManager.getUserDetails().get("email"));
                       //Picasso.with(MainActivity.this).load().into(userProfile);
                    //   Picasso.get().load("https://graph.facebook.com/" + photoUrl.getPath() + "?type=large").into(userProfile);

                   }
                }

            }else {
                goLoginScreen();
            }
        }catch (NullPointerException e){
            Log.e("NullPointerExzcep: ",""+e.getLocalizedMessage());
        }


        goProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goProfile = new Intent(MainActivity.this, MyProfile.class);
                startActivity(goProfile);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(Config.SHOWHOME){
                Fragment fragment = new Dashboard();
                Config.SHOWHOME = false;
                updateTitle("Dashboard");
                if (fragment != null) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.frame, fragment);
                    ft.commit();
                   // overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
                }
            } else if(Config.SHOWCATEGORY){
                Fragment fragment = new GiftCategoryFragment();
                Config.SHOWHOME = false;
                updateTitle("Gift Tree");
                if (fragment != null) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.frame, fragment);
                    ft.commit();
                   // overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
                }
            } else if(Config.SHOWORDER){
                Fragment fragment = new OrderHistory();
                Config.SHOWHOME = false;
                updateTitle("Order History");
                if (fragment != null) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.frame, fragment);
                    ft.commit();
                    // overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
                }
            }
            else if(!Config.SHOWHOME){
                super.onBackPressed();
            }
        }
    }

    private void goLoginScreen() {
        Intent intent = new Intent(this, Login_Activity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void logout(View view) {
        userSessionManager.updateUserLoggedIN(false);
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        goLoginScreen();
    }
    private void logout() {
        userSessionManager.updateUserLoggedIN(false);
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        goLoginScreen();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        //int id = item.getItemId();
         onSelectItem(item.getItemId());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void onSelectItem(int id){
        Fragment fragment = null;
        String title = "";
        if (id == R.id.nav_dashboard) {
            fragment = new Dashboard();
            title = "Dashboard";
        } else if (id == R.id.nav_gift) {
            fragment = new GiftCategoryFragment();
            title = "Gift Tree";
        } else if (id == R.id.nav_order_history) {
            fragment = new OrderHistory();
            title = "Order History";
        } else if (id == R.id.nav_myteam) {
            fragment = new MyTeam();
            title = "My Team";
        } else if(id == R.id.nav_tracktree){
            fragment = new TrackTree();
            title = "Order Track";
        }else if (id == R.id.nav_share) {
            share();
            fragment = new Dashboard();
            title = "Dashboard";
        } else if (id == R.id.nav_aboutus) {
            fragment = new About();
            title = "About Us";
        }else if( id == R.id.nav_rateus){
            rateMe();
            title = "Dashboard";
            fragment = new Dashboard();
        }else if(id == R.id.nav_privacy){
            title = "Privacy Policy";
            fragment = new PrivacyPolicyFragment();
        }else if(id == R.id.nav_login){
             logout();
            fragment = new Dashboard();
            title = "Dashboard";
        }
        //replacing the fragment
        updateTitle(title);
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame, fragment);
           // overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            ft.commit();
        }
    }

    public  void updateTitle(String title){
        try {
            toolbar.setTitle(title);
        }catch (NullPointerException e){
            Log.e("NullPointerExcep: ",""+e.getLocalizedMessage());
        }
    }

    private void rateMe(){

        Uri uri = Uri . parse ("market://details?id=" + context.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent (Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }

    }

    private  void share(){
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, ""+context.getResources().getString(R.string.app_name));
            String sAux = "\nJoin me "+context.getResources().getString(R.string.app_name)+"\n\n";
            sAux = sAux + "https://play.google.com/store/apps/details?id="+ context.getPackageName() +"\n\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "choose one"));
        } catch(Exception e) {
            //e.toString();
            Log.e("Exception: ",""+e.toString());
        }
    }

}
