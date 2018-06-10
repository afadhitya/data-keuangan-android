package com.example.apple.datakeuangan;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class Hutang extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HutangMyRecyclerViewAdapter.ItemClickListener {
    final Context c = this;
    DBControllerHutang controller;
    private SQLiteDatabase db = null;

    private HutangMyRecyclerViewAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hutang);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewHutang);
        controller = new DBControllerHutang(this, "", null, 1);

        showData();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                View mView = layoutInflaterAndroid.inflate(R.layout.dialog_input_hutang, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
                alertDialogBuilderUserInput.setView(mView);

                final EditText EditinputHutangKeterangan = (EditText) mView.findViewById(R.id.inputKeteranganHutang);
                final EditText EditinputHutangAmount = (EditText) mView.findViewById(R.id.inputHutangAmount);


                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                String keteranganHutang = EditinputHutangKeterangan.getText().toString();
                                String banyaknyaHutang = EditinputHutangAmount.getText().toString();

                                int banyaknyaHutangInt =  Integer.parseInt(banyaknyaHutang);
                                addHutangKeDB(keteranganHutang, banyaknyaHutangInt);
                            }
                        })

                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void addHutangKeDB(String nama, int amount){
        try{
            controller.insertHutang(nama, amount);
            Toast.makeText(getApplicationContext(), nama + " dan total sebesar "+amount+" berhasil disimpan",
                    Toast.LENGTH_LONG).show();
            adapter.notifyDataSetChanged();
        }catch(SQLiteException e){
            Toast.makeText(getApplicationContext(), nama + " dan total sebesar "+amount+" gagal disimpan",
                    Toast.LENGTH_LONG).show();
            Log.e("MYAPP", "exception", e);
        }
    }

    public void showData(){
        ArrayList<HutangClass> hutangClasses = new ArrayList<HutangClass>();

//        penyimpananClasses.add(new PenyimpananClass(1, "Dompet", 10000));
//        for (int i = 0; i<50; i++){
//            penyimpananClasses.add(new PenyimpananClass(1, "Dompet", 10000));
//        }

        hutangClasses = controller.getDataHutang();

        adapter = new HutangMyRecyclerViewAdapter(getApplicationContext(), hutangClasses);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);


        recyclerView.setAdapter(adapter);
        adapter.setClickListener(this);

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
        getMenuInflater().inflate(R.menu.hutang, menu);
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
            Intent intent = new Intent(Hutang.this, MainActivity.class);
            startActivity(intent);
            finish();
            // Handle the camera action
        } else if (id == R.id.nav_penyimpanan) { //Penyimpanan
            Intent intent = new Intent(Hutang.this, Penyimpanan.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_hutang) { //Hutang
            //Kosongin

        } else if (id == R.id.nav_history_keungan) { //History keuangan
            Intent intent = new Intent(Hutang.this, HistoryKeuangan.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position).getKeteranganHutang() + " on row number " + position, Toast.LENGTH_SHORT).show();
    }
}
