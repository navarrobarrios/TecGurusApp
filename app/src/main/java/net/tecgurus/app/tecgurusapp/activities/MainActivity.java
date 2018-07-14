package net.tecgurus.app.tecgurusapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import net.tecgurus.app.tecgurusapp.R;
import net.tecgurus.app.tecgurusapp.adapters.UserAdapter;
import net.tecgurus.app.tecgurusapp.db.beans.UserBean;
import net.tecgurus.app.tecgurusapp.db.helpers.UserHelper;
import net.tecgurus.app.tecgurusapp.services.TimerService;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //region Variables
    private NavigationView mNavigationView;
    private LinearLayout mNoDataFoundLayout;
    private RecyclerView mDataLayout;
    //endregion

    //region Activity Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDataLayout = findViewById(R.id.content_main_recycler);
        mNoDataFoundLayout = findViewById(R.id.content_main_users_not_found);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationView.getMenu().getItem(0).setChecked(true);

        CircleImageView logo = mNavigationView.getHeaderView(0).findViewById(R.id.nav_header_image);

        if (logo != null)

            Picasso.get()
                    .load("https://yt3.ggpht.com/-r3LX4YqxsZQ/AAAAAAAAAAI/AAAAAAAAAAA/rq7roIAaD1I/s288-mo-c-c0xffffffff-rj-k-no/photo.jpg")
                    .into(logo);

        Intent serviceIntent = new Intent(getApplicationContext(), TimerService.class);
        startService(serviceIntent);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        inflateViews();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
    //endregion

    //region NavigationView Methods
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        switch (item.getItemId()){
            case R.id.nav_add_user:
                Intent addUserActivityIntent = new Intent(getApplicationContext(), AddUserActivity.class);
                startActivity(addUserActivityIntent);
                mNavigationView.setCheckedItem(R.id.nav_home);
                drawer.closeDrawer(GravityCompat.START);
                return false;
            case R.id.nav_delete_user:
                Intent deleteUserActivityIntent = new Intent(getApplicationContext(), DeleteUserActivity.class);
                startActivity(deleteUserActivityIntent);
                mNavigationView.setCheckedItem(R.id.nav_home);
                drawer.closeDrawer(Gravity.START);
                return false;
            case R.id.nav_update_user:
                Intent updateUserActivityIntent = new Intent(getApplicationContext(), UpdateUserActivity.class);
                startActivity(updateUserActivityIntent);
                mNavigationView.setCheckedItem(R.id.nav_home);
                drawer.closeDrawer(Gravity.START);
                return false;
            case R.id.nav_contacts:
                Intent myContactsActiviy = new Intent(getApplicationContext(), MyContactsActivity.class);
                startActivity(myContactsActiviy);
                mNavigationView.setCheckedItem(R.id.nav_home);
                drawer.closeDrawer(Gravity.START);
                return false;
            case R.id.nav_map:
                Intent mapActivity = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(mapActivity);
                mNavigationView.setCheckedItem(R.id.nav_home);
                drawer.closeDrawer(Gravity.START);
                return false;
            case R.id.nav_web_services:
                Intent soapServiceActivityIntent = new Intent(getApplicationContext(), WebServices.class);
                startActivity(soapServiceActivityIntent);
                mNavigationView.setCheckedItem(R.id.nav_home);
                drawer.closeDrawer(Gravity.START);
                return false;
            case R.id.nav_go_to_tec_gurus:

                double startLatitude = 20.6674736;
                double startLongitude = -103.3710259;

                String uriBegin = "geo:" + startLatitude + "," + startLongitude;
                String query = "" + startLatitude + "," + startLongitude + "(Tec Gurus)";
                String encodedQuery = Uri.encode(query);
                String uriString = uriBegin + "?q=" + encodedQuery;
                Uri uri = Uri.parse(uriString);
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
                startActivity(intent);

                Uri gmmIntentUri = Uri.parse("geo:" + startLatitude + "," + startLongitude);
                /*Google Navigation*/
//                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + startLatitude + "," + startLongitude);
                /*Street View*/
//                Uri gmmIntentUri = Uri.parse("google.streetview:cbll=" + startLatitude + "," + startLongitude);

//                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//                mapIntent.setPackage("com.google.android.apps.maps");
//                startActivity(mapIntent);

                mNavigationView.setCheckedItem(R.id.nav_home);
                drawer.closeDrawer(Gravity.START);
                return false;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //endregion

    //region Local Methods
    private void inflateViews(){

        List<UserBean> userBeans =
                UserHelper.getInstance(getApplicationContext()).getUsers();
        if (userBeans.size() == 0){
            mNoDataFoundLayout.setVisibility(View.VISIBLE);
            mDataLayout.setVisibility(View.GONE);
        }else{
            mNoDataFoundLayout.setVisibility(View.GONE);
            mDataLayout.setVisibility(View.VISIBLE);
            UserAdapter adapter = new UserAdapter(userBeans, getApplicationContext());
            mDataLayout.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//            mDataLayout.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1)); /*Con este Layoutmanager puedes agregar mas de uno item por fila*/
            mDataLayout.setAdapter(adapter);
        }
    }
    //endregion
}
