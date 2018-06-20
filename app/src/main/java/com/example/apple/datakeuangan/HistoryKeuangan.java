package com.example.apple.datakeuangan;

import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class HistoryKeuangan extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HistoryMyRecyclerViewAdapter.ItemClickListener, AdapterView.OnItemSelectedListener {

    final Context c = this;
    DBControllerHistoryKeuangan controller;
    DBControllerPenyimpanan controllerPenyimpanan;
    private SQLiteDatabase db = null;

    private HistoryMyRecyclerViewAdapter adapter;
    RecyclerView recyclerView;

    HashMap<Integer, String> lablesMap;
    Spinner dariKeS;
    EditText tanggalET;

    private Calendar calendar;
    private int year, month, day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_keuangan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewHistory);
        controller = new DBControllerHistoryKeuangan(this, "", null, 1);
        controllerPenyimpanan = new DBControllerPenyimpanan(this, "", null, 1);

        showData();


        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
        final View mView = layoutInflaterAndroid.inflate(R.layout.dialog_input_history, null);
        dariKeS = (Spinner) mView.findViewById(R.id.inputSumberTujuanHistory);


        loadSpinnerData();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
                alertDialogBuilderUserInput.setView(mView);

                final EditText keteranganET = (EditText) mView.findViewById(R.id.inputKeteranganHistory);
                tanggalET = (EditText) mView.findViewById(R.id.inputTanggalHistory);
                final EditText hariET = (EditText) mView.findViewById(R.id.inputHariHistory);
                final EditText jumlahET = (EditText) mView.findViewById(R.id.inputHistoryAmount);
                final EditText jenisET = (EditText) mView.findViewById(R.id.inputJenisHistory);

                tanggalET.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setDate(v);
                    }

                });

//                final Spinner dariKeET = (Spinner) mView.findViewById(R.id.inputSumberTujuanHistory);


                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                HistoryKeuanganClass historyTemp = new HistoryKeuanganClass();
                                historyTemp.setKeteranganHistory(keteranganET.getText().toString());
                                historyTemp.setTanggalHistory(tanggalET.getText().toString());
                                historyTemp.setHariHistory(hariET.getText().toString());
                                historyTemp.setJumlahHistory(Integer.parseInt(jumlahET.getText().toString()));
                                historyTemp.setMasukAtauKeluar(jenisET.getText().toString());
                                historyTemp.setIdPenyimpanan(dariKeS.getSelectedItemPosition());

                                addHistoryKeDB(historyTemp);
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

    public void addHistoryKeDB(HistoryKeuanganClass param1){
        try{
            controller.insertHistory(param1);
            Toast.makeText(getApplicationContext(), param1.getKeteranganHistory() + " dan total sebesar "+param1.getJumlahHistory()+" berhasil disimpan",
                    Toast.LENGTH_LONG).show();
            adapter.notifyDataSetChanged();
        }catch(SQLiteException e){
            Toast.makeText(getApplicationContext(), param1.getKeteranganHistory()  + " dan total sebesar "+param1.getJumlahHistory()+" gagal disimpan",
                    Toast.LENGTH_LONG).show();
            Log.e("MYAPP", "exception", e);
        }
    }

    public void showData(){
        ArrayList<HistoryKeuanganClass> historyKeuanganClasses = new ArrayList<HistoryKeuanganClass>();

//        penyimpananClasses.add(new PenyimpananClass(1, "Dompet", 10000));
//        for (int i = 0; i<50; i++){
//            penyimpananClasses.add(new PenyimpananClass(1, "Dompet", 10000));
//        }

        historyKeuanganClasses = controller.getDataHistory();

        adapter = new HistoryMyRecyclerViewAdapter(getApplicationContext(), historyKeuanganClasses);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);


        recyclerView.setAdapter(adapter);
        adapter.setClickListener(this);

    }

    private void loadSpinnerData() {
        ArrayList<PenyimpananClass> penyimpananSpinner = new ArrayList<PenyimpananClass>();
        // database handler
        penyimpananSpinner = controllerPenyimpanan.getDataPenyimpanan();

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
        dariKeS.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        // On selecting a spinner item
        String label = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "You selected: " + label,
                Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

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
        getMenuInflater().inflate(R.menu.history_keuangan, menu);
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
            Intent intent = new Intent(HistoryKeuangan.this, MainActivity.class);
            startActivity(intent);
            finish();
            // Handle the camera action
        } else if (id == R.id.nav_penyimpanan) { //Penyimpanan
            Intent intent = new Intent(HistoryKeuangan.this, Penyimpanan.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_hutang) { //Hutang
            Intent intent = new Intent(HistoryKeuangan.this, Hutang.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_history_keungan) { //History keuangan
            //Kosongin

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position).getKeteranganHistory() + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "Date Picker Opened",
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        tanggalET.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }
}
