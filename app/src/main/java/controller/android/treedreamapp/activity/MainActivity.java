package controller.android.treedreamapp.activity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import controller.android.treedreamapp.R;
import controller.android.treedreamapp.fragments.About;
import controller.android.treedreamapp.fragments.Dashboard;
import controller.android.treedreamapp.fragments.GiftTree;
import controller.android.treedreamapp.fragments.MyTeam;
import controller.android.treedreamapp.fragments.OrderHistory;
import controller.android.treedreamapp.fragments.PrivacyPolicyFragment;
import controller.android.treedreamapp.fragments.TrackTree;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
private Toolbar toolbar;
private FloatingActionButton fab;
private Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = MainActivity.this;
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

        onSelectItem(R.id.nav_dashboard);
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
            fragment = new GiftTree();
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
        }
        //replacing the fragment
        updateTitle(title);
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame, fragment);
            ft.commit();
        }
    }

    private void updateTitle(String title){
        toolbar.setTitle(title);
    }

    private void rateMe(){

        Uri uri = Uri . parse ("market://details?id=" + context.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
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
            i.putExtra(Intent.EXTRA_SUBJECT, ""+R.string.app_name);
            String sAux = "\nLet me recommend you this application\n\n";
            sAux = sAux + "https://play.google.com/store/apps/details?id=the.package.id \n\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "choose one"));
        } catch(Exception e) {
            //e.toString();
            Log.e("Exception: ",""+e.toString());
        }
    }

}
