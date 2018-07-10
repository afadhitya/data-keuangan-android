package com.example.apple.datakeuangan;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

public class Penyimpanan extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, PenyimpananMyRecyclerViewAdapter.ItemClickListener {
    final Context c = this;
    DBControllerPenyimpanan controller;
    private SQLiteDatabase db = null;

    private PenyimpananMyRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<PenyimpananClass> penyimpananClasses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penyimpanan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewPenyimpanan);
        controller = new DBControllerPenyimpanan(this, "", null, 1);

        showData();



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                View mView = layoutInflaterAndroid.inflate(R.layout.dialog_input_penyimpanan, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
                alertDialogBuilderUserInput.setView(mView);

                final EditText EditinputPenyimpananNama = (EditText) mView.findViewById(R.id.inputPenyimpananNama);
                final EditText EditinputPenyimpananAmount = (EditText) mView.findViewById(R.id.inputPenyimpananAmount);


                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                String namaPenyimpanan = EditinputPenyimpananNama.getText().toString();
                                String isiAwalPenyimpanan = EditinputPenyimpananAmount.getText().toString();

                                int isiAwalPenyimpananInt =  Integer.parseInt(isiAwalPenyimpanan);
                                addPenyimpananKeDB(namaPenyimpanan, isiAwalPenyimpananInt);
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

    public void addPenyimpananKeDB(String nama, int amount){
        try{
            long id = controller.insertPenyimpanan(nama, amount);
            Toast.makeText(getApplicationContext(), nama + " dan total sebesar "+amount+" berhasil disimpan",
                    Toast.LENGTH_LONG).show();
            PenyimpananClass penyimpananClass = controller.getPenyimpanan((int)id);
            if (penyimpananClass != null) {
                // adding new note to array list at 0 position
                penyimpananClasses.add(penyimpananClass);

                // refreshing the list
                adapter.notifyDataSetChanged();
            }
        }catch(SQLiteException e){
            Toast.makeText(getApplicationContext(), nama + " dan total sebesar "+amount+" gagal disimpan",
                    Toast.LENGTH_LONG).show();
            Log.e("MYAPP", "exception", e);
        }
    }

    public void showData(){

//        penyimpananClasses.add(new PenyimpananClass(1, "Dompet", 10000));
//        for (int i = 0; i<50; i++){
//            penyimpananClasses.add(new PenyimpananClass(1, "Dompet", 10000));
//        }

        penyimpananClasses = controller.getDataPenyimpanan();

        adapter = new PenyimpananMyRecyclerViewAdapter(getApplicationContext(), penyimpananClasses);
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
        getMenuInflater().inflate(R.menu.penyimpanan, menu);
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
            Intent intent = new Intent(Penyimpanan.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_penyimpanan) { //Penyimpanan
            // Kosongin

        } else if (id == R.id.nav_hutang) { //Hutang
            Intent intent = new Intent(Penyimpanan.this, Hutang.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_history_keungan) { //History keuangan
            Intent intent = new Intent(Penyimpanan.this, HistoryKeuangan.class);
            startActivity(intent);
            finish();

//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position).getNamaPenyimpanan() + " on row number " + position, Toast.LENGTH_SHORT).show();
    }
}
