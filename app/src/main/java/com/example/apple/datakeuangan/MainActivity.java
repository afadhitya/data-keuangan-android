package com.example.apple.datakeuangan;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    final Context c = this;

    TextView totalKotorTV;
    TextView totalHutangTV;
    TextView totalBersihTV;
    Spinner dariPenyimpananS;
    Spinner kePenyimpananS;
    EditText banyaknyaET;

    ArrayList<PenyimpananClass> penyimpananSpinner;

    DBControllerPenyimpanan dbPenyimpanan;
    DBControllerHutang dbHutang;

    ArrayList<PenyimpananClass> penyimpananClasses;
    ArrayList<HutangClass> hutangClasses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbPenyimpanan= new DBControllerPenyimpanan(this, "", null, 1);
        dbHutang= new DBControllerHutang(this, "", null, 1);

        totalKotorTV = (TextView) findViewById(R.id.uangTotalHome);
        totalHutangTV = (TextView) findViewById(R.id.hutangTotalHome);
        totalBersihTV = (TextView) findViewById(R.id.totalBersihHome);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        int total = getTotalKotor();
        int totalHutang = getHutang();
        int uangBersih = total - totalHutang;

        totalKotorTV.setText(NumberFormat.getNumberInstance(Locale.US).format(total));
        totalHutangTV.setText(NumberFormat.getNumberInstance(Locale.US).format(totalHutang));
        totalBersihTV.setText(NumberFormat.getNumberInstance(Locale.US).format(uangBersih));



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
    private int getHutang(){
        int totalHutang = 0;
        hutangClasses = new ArrayList<HutangClass>();
        hutangClasses = dbHutang.getDataHutang();

        for(int i = 0; i < hutangClasses.size(); i++){
            totalHutang = totalHutang + hutangClasses.get(i).getJumlahHutang();
        }

        return totalHutang;
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

    private void loadSpinnerData() {
        penyimpananSpinner = new ArrayList<PenyimpananClass>();

        // database handler
        penyimpananSpinner = dbPenyimpanan.getDataPenyimpanan();

        // Spinner Drop down elements
        List<String> lables = new ArrayList<>();

        for(int i=0;i<penyimpananSpinner.size();i++) {
            lables.add(penyimpananSpinner.get(i).getNamaPenyimpanan());

        }

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        dariPenyimpananS.setAdapter(dataAdapter);
        kePenyimpananS.setAdapter(dataAdapter);
    }

    public void switching(int idDari, int idKe, int jumlah){
        PenyimpananClass penyimpananDari;
        PenyimpananClass penyimpananKe;

        //Pengurangan dari
        penyimpananDari = dbPenyimpanan.getPenyimpanan(idDari);
        int uangDari = penyimpananDari.getIsiPenyimpanan();
        uangDari = uangDari - jumlah;

        dbPenyimpanan.updateData(idDari, uangDari);

        //Penambahan Ke
        penyimpananKe = dbPenyimpanan.getPenyimpanan(idKe);
        int uangKe = penyimpananKe.getIsiPenyimpanan();
        uangKe = uangKe + jumlah;

        dbPenyimpanan.updateData(idKe, uangKe);
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
            LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
            View mView = layoutInflaterAndroid.inflate(R.layout.dialog_input_switching, null);
            AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
            alertDialogBuilderUserInput.setView(mView);

            dariPenyimpananS = (Spinner) mView.findViewById(R.id.dariPenyimpanan);
            kePenyimpananS = (Spinner) mView.findViewById(R.id.kePenyimpanan);
            banyaknyaET = (EditText) mView.findViewById(R.id.banyaknya);

            loadSpinnerData();

            alertDialogBuilderUserInput
                    .setCancelable(false)
                    .setPositiveButton("Switch", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogBox, int id) {
//                            String keteranganHutang = EditinputHutangKeterangan.getText().toString();
//                            String banyaknyaHutang = EditinputHutangAmount.getText().toString();

//                            int banyaknyaHutangInt =  Integer.parseInt(banyaknyaHutang);

//                            addHutangKeDB(keteranganHutang, banyaknyaHutangInt);

                            int dariPenyimpanan = penyimpananSpinner.get(dariPenyimpananS.getSelectedItemPosition()).getIdPenyimpanan();
                            int kePeyimpanan = penyimpananSpinner.get(kePenyimpananS.getSelectedItemPosition()).getIdPenyimpanan();
                            int banyaknya = Integer.parseInt(banyaknyaET.getText().toString());

                            switching(dariPenyimpanan, kePeyimpanan, banyaknya);

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

//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
