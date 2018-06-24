package com.example.apple.datakeuangan;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView totalKotorTV;
    TextView totalHutangTV;
    TextView totalBersihTV;

    DBControllerPenyimpanan dbPenyimpanan;
    DBControllerHutang dbHutang;

    ArrayList<PenyimpananClass> penyimpananClasses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbPenyimpanan= new DBControllerPenyimpanan(this, "", null, 1);

        totalKotorTV = (TextView) findViewById(R.id.uangTotalHome);
        totalHutangTV = (TextView) findViewById(R.id.hutangTotalHome);
        totalBersihTV = (TextView) findViewById(R.id.totalBersihHome);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        int total = getTotalKotor();

        totalKotorTV.setText(Integer.toString(total));



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private int getTotalKotor(){
        int total = 0;
        penyimpananClasses = new ArrayList<PenyimpananClass>();

        penyimpananClasses = dbPenyimpanan.getDataPenyimpanan();

        for(int i = 0; i < penyimpananClasses.size(); i++){
            total = total + penyimpananClasses.get(i).getIsiPenyimpanan();
        }

        return total;
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
        int id = item.getItemId();

        if (id == R.id.nav_home) { //Home
            // Kosongin

        } else if (id == R.id.nav_penyimpanan) { //Penyimpanan
            Intent intent = new Intent(MainActivity.this, Penyimpanan.class);
            startActivity(intent);
            finish();
//

        } else if (id == R.id.nav_hutang) { //Hutang
            Intent intent = new Intent(MainActivity.this, Hutang.class);
            startActivity(intent);
            finish();
//            setTitle("Hutang");

        } else if (id == R.id.nav_history_keungan) { //History keuangan
            Intent intent = new Intent(MainActivity.this, HistoryKeuangan.class);
            startActivity(intent);
            finish();
//            setTitle("History Keuangan");

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
