package com.example.winecellarapp.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.winecellarapp.R;
import com.example.winecellarapp.login.LoginActivity;
import com.example.winecellarapp.notification.GlobalNotificationSharedPref;
import com.example.winecellarapp.notification.NotificationService;
import com.example.winecellarapp.notification.StartService;
import com.example.winecellarapp.notification.restarter.RestartServiceBroadcastReceiver;
import com.example.winecellarapp.views.fragments.AirFragment;
import com.example.winecellarapp.views.fragments.HumidityFragment;
import com.example.winecellarapp.views.fragments.TemperatureFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private StartService bck;
    private GlobalNotificationSharedPref notificationSharedPref;
   //private DataUpdateReceiver mCustomReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationSharedPref = new GlobalNotificationSharedPref(getApplicationContext());
        if(notificationSharedPref.getServiceSharedPreference().equalsIgnoreCase("nothing"))
            notificationSharedPref.editServiceSharedPreferences("off");
        //mCustomReceiver = new DataUpdateReceiver();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new HomeFragment()).commit();
        setTitle(null);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.subnavbar_thermometer:
                            MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TemperatureFragment()).commit();
                            break;
                        case R.id.subnavbar_humidity:
                            MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HumidityFragment()).commit();
                            break;
                        case R.id.subnavbar_air:
                            MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AirFragment()).commit();
                            break;
                        case R.id.subnavbar_home:
                            MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                            break;
                    }
                    return true;
                }
            };

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                if(notificationSharedPref.getServiceSharedPreference().equalsIgnoreCase("on"))
                {
                    notificationSharedPref.editServiceSharedPreferences("off");
                    NotificationService.notificationService.stopForeground(true);
                }
                finish();
                break;
            case R.id.nav_settings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //unregisterReceiver(mCustomReceiver);
        Log.i("MAINACT", "onDestroy!");
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        notificationCheck();
        restartService();
    }

    /**
     * Check what notification is displayed and depends on it open fragment or change shared
     * preference for default notification
     */
    private void notificationCheck()
    {
        String notif = notificationSharedPref.getSharedPreference();
        if(notif.equalsIgnoreCase("humidity"))
        {
            MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HumidityFragment()).commit();
        }
        else if(notif.equalsIgnoreCase("temperature"))
        {
            MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TemperatureFragment()).commit();
        }
        else if(notif.equalsIgnoreCase("CO2"))
        {
            MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AirFragment()).commit();
        }
        else
        {
            notificationSharedPref.editSharedPreferences("default");
        }
    }

    /**
     * restart running service
     */
    private void restartService()
    {
        if(notificationSharedPref.getServiceSharedPreference().equalsIgnoreCase("on"))
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                RestartServiceBroadcastReceiver.scheduleJob(getApplicationContext());
            } else {
                bck = new StartService();
                bck.startService(getApplicationContext());
            }
        }
    }



}
