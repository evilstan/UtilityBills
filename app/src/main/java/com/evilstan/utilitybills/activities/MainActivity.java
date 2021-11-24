package com.evilstan.utilitybills.activities;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.evilstan.utilitybills.App;
import com.evilstan.utilitybills.AppDataBase;
import com.evilstan.utilitybills.R;
import com.evilstan.utilitybills.data.Apartment;
import com.evilstan.utilitybills.data.Meter;
import com.evilstan.utilitybills.interfaces.daos.ApartmentDao;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
    AdapterView.OnItemClickListener {

    ListView metersListView;
    ExtendedFloatingActionButton addApartmentButton;
    ExtendedFloatingActionButton testButton;
    MaterialCardView cardView;
    boolean firstRun = true;

    public static Context context;

    ArrayAdapter<String> metersAdapter;
    List<Meter> meters;
    List<String> adapterList;
    List<Apartment> apartments;

    @Override
    protected void onStart() {
        super.onStart();
        context = MainActivity.this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (firstRun) {
            Intent intent = new Intent(MainActivity.this, FirstLaunchKt.class);
            startActivity(intent);

        }

        setTitle(getResources().getString(R.string.main_activity_title));
        init();
    }

    private void init() {
        testButton = findViewById(R.id.testDB);
        cardView = findViewById(R.id.card1);
        metersListView = findViewById(R.id.list_meters);
        addApartmentButton = findViewById(R.id.add_apartments);
        metersListView.setOnItemClickListener(this);

        testButton.setOnClickListener(this);
        addApartmentButton.setOnClickListener(this);
        adapterList = new ArrayList<>();
        metersAdapter = new ArrayAdapter<String>(MainActivity.this,
            android.R.layout.simple_list_item_1, adapterList);

        metersListView.setAdapter(metersAdapter);

        readSharedPreferences();
    }

    private void readSharedPreferences() {
        SharedPreferences sPref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        firstRun = sPref.getBoolean("FirstRun", true);
        SharedPreferences.Editor sPrefEdit = sPref.edit();
        sPrefEdit.putBoolean("FirstRun", false);
        sPrefEdit.apply();
    }

    private void addApartment() {
        Intent intent = new Intent(MainActivity.this, AddApartmentActivity.class);
        startActivity(intent);
    }

    private void testDB() {
        adapterList.clear();

        AppDataBase db = App.getInstance().getDatabase();
        ApartmentDao apartmentDao = db.apartmentDao();
        apartments = apartmentDao.getAll();

        for (Apartment ap : apartments) {
            adapterList.add(ap.toString());
        }
        metersAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        String id = getResources().getResourceEntryName(view.getId());
        switch (id) {
            case "add_apartments":
                addApartment();
                break;
            case "testDB":
                testDB();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(context, AddMonthlyPaymentActivity.class);
        intent.putExtra("apartment_id", apartments.get(i).getId());
        startActivity(intent);
    }
}