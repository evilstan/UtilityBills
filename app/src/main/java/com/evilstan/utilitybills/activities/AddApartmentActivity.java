package com.evilstan.utilitybills.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatActivity;
import com.evilstan.utilitybills.Apartment;
import com.evilstan.utilitybills.App;
import com.evilstan.utilitybills.AppDataBase;
import com.evilstan.utilitybills.Meter;
import com.evilstan.utilitybills.R;
import com.evilstan.utilitybills.Tariff;
import com.evilstan.utilitybills.enums.MeterType;
import com.evilstan.utilitybills.interfaces.Gauge;
import com.evilstan.utilitybills.interfaces.daos.ApartmentDao;
import com.evilstan.utilitybills.interfaces.daos.MeterDao;
import com.evilstan.utilitybills.interfaces.daos.TariffDao;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AddApartmentActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputLayout addressInput;
    ListView metersListView;
    ArrayAdapter<String> metersAdapter;
    List<String> paymentsList;
    ExtendedFloatingActionButton addMeter;
    ExtendedFloatingActionButton addPayment;
    ExtendedFloatingActionButton addApartments;
    Context context;
    Apartment apartment;
    List<Gauge> meters;
    Map<String, Double> typeTariffMap = new LinkedHashMap<>();
    Tariff tariff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_apartment);
        setTitle(R.string.add_apartments_title);
    }

    @Override
    protected void onStart() {
        super.onStart();
        context = AddApartmentActivity.this;
        init();
    }

    private void init() {
        addressInput = findViewById(R.id.address_input);
        metersListView = findViewById(R.id.meters_list);
        addMeter = findViewById(R.id.add_meter);
        addPayment = findViewById(R.id.add_payment);
        addApartments = findViewById(R.id.add_apartments);

        paymentsList = new ArrayList<>();
        metersAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1,
            paymentsList);
        metersListView.setAdapter(metersAdapter);

        meters = new ArrayList<>();
        addMeter.setOnClickListener(this);
        addPayment.setOnClickListener(this);
        addApartments.setOnClickListener(this);

        tariff = new Tariff();
    }

    private void addMeterDialog() {
        LayoutInflater inflater = LayoutInflater.from(context);
        List<String> types = Arrays.asList(getResources().getStringArray(R.array.meters_types));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.list_item, types);

        final View metersPickerDialog = inflater.inflate(R.layout.meters_picker_dialog, null);
        final TextInputEditText idPicker = metersPickerDialog.findViewById(R.id.id_picker);
        final TextInputEditText placementPicker = metersPickerDialog
            .findViewById(R.id.name_picker);
        final TextInputEditText tariffSelector = metersPickerDialog
            .findViewById(R.id.cost_picker);
        final AutoCompleteTextView typeSelector = metersPickerDialog
            .findViewById(R.id.type_selector);
        final TextInputEditText valueSelector = metersPickerDialog
            .findViewById(R.id.value_picker);



        typeSelector.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = (String) parent.getItemAtPosition(position);

                if (typeTariffMap.containsKey(s)) {
                    String tariff = String.valueOf(typeTariffMap.get(s));
                    if (tariff.endsWith(".0")) {
                        tariff = tariff.substring(0, tariff.length() - 2);
                    }
                    tariffSelector.setText(tariff);
                } else {
                    Toast.makeText(context, "Value not found", Toast.LENGTH_LONG).show();
                    tariffSelector.setText("");
                }
            }
        });

        typeSelector.setAdapter(adapter);
        AlertDialog dialog = new Builder(context)
            .setTitle("AddMeter")
            .setView(metersPickerDialog)
            .setCancelable(false)
            .setPositiveButton(R.string.add_meter, null)
            .setNegativeButton(R.string.cancel_selector, null).create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String s = typeSelector.getText().toString();
                        String place = placementPicker.getText().toString();
                        String cost = tariffSelector.getText().toString();
                        String value = valueSelector.getText().toString();

                        if (!types.contains(s)) {
                            placementPicker.requestFocus();
                        } else if (place.equals("")) {
                            typeSelector.requestFocus();
                        } else if (cost.equals("")) {
                            tariffSelector.requestFocus();
                        } else if (value.equals("")) {
                            valueSelector.requestFocus();
                        } else {
                            double tariff = Double.parseDouble(cost);
                            double currentValue = Double.parseDouble(value);
                            int id;

                            try {
                                id = Integer.parseInt(idPicker.getText().toString());
                            } catch (NumberFormatException | NullPointerException e) {
                                id = 0;
                            }

                            int type = types.indexOf(s);
                            MeterType meterType = selectType(type);
                            addMeter(place, id, meterType, currentValue);
                            setTariff(tariff, meterType);
                            addMeterToList(type, id, place, tariff);
                            dialog.dismiss();
                            typeTariffMap.put(s, tariff);
                        }
                    }
                });
            }
        });
        dialog.show();
    }

    private void setTariff(double cost, MeterType meterType) {
        tariff.setCost(meterType, cost);
    }

    private void addMeter(String place, int id, MeterType meterType, double value) {
        Meter meter = new Meter(place, meterType, id, value);
        Toast.makeText(context, "Id= " + meter.getId(), Toast.LENGTH_SHORT).show();
        meters.add(meter);
    }

    private void addMeterToList(int type, int id, String place, double cost) {
        String n = getResources().getString(R.string.number);
        String number = id != 0 ? n + id : "";
        String buf = selectTypeString(type) + place.toLowerCase() + number;
        buf += getResources().getString(R.string.cost_per_one) + cost;
        //if cost integer, trim decimal part
        if (cost % 1 == 0) {
            buf = buf.substring(0, buf.length() - 2);
        }

        paymentsList.add(buf);
        metersAdapter.notifyDataSetChanged();
    }

    private MeterType selectType(int type) {
        switch (type) {
            case 0:
                return MeterType.HOT_WATER;
            case 1:
                return MeterType.COLD_WATER;
            case 2:
                return MeterType.HEAT_JOULE;
            case 3:
                return MeterType.HEAT_CALORIE;
            case 4:
                return MeterType.ELECTRICITY;
            case 5:
                return MeterType.GAS;
            default:
                System.out.println("Not defined, set to cold water");
                return MeterType.COLD_WATER;
        }
    }

    private String selectTypeString(int type) {

        switch (type) {
            case 0:
                return getResources().getString(R.string.of_hot_water);
            case 1:
                return getResources().getString(R.string.of_cold_water);
            case 2:
                return getResources().getString(R.string.of_heat_joules);
            case 3:
                return getResources().getString(R.string.of_heat_calories);
            case 4:
                return getResources().getString(R.string.of_electricity);
            case 5:
                return getResources().getString(R.string.of_gas);
            default:
                System.out.println("Not defined, set to cold water");
                return getResources().getString(R.string.of_cold_water);
        }
    }

    private void addPaymentsDialog() {
        LayoutInflater inflater = LayoutInflater.from(context);
        final View paymentsPickerDialog = inflater
            .inflate(R.layout.other_payment_picker_dialog, null);
        final TextInputEditText namePicker = paymentsPickerDialog.findViewById(R.id.name_picker);
        final TextInputEditText costPicker = paymentsPickerDialog
            .findViewById(R.id.id_picker);

        AlertDialog dialog = new Builder(context)
            .setTitle("AddPayment")
            .setView(paymentsPickerDialog)
            .setCancelable(false)
            .setPositiveButton(R.string.add_payment, null)
            .setNegativeButton(R.string.cancel_selector, null).create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name = namePicker.getText().toString();
                        String cost = costPicker.getText().toString();
                        if (name.equals("")) {
                            namePicker.requestFocus();
                        } else if (cost.equals("")) {
                            costPicker.requestFocus();
                        } else {
                            double tariff = Double.parseDouble(cost);
                            addPayment(name, tariff);
                            dialog.dismiss();
                        }
                    }
                });
            }
        });
        dialog.show();
    }

    private void addPayment(String name, double cost) {
        tariff.addUtilityPayments(name, cost);
        String buf = getResources().getString(R.string.payment_for);
        buf += name.toLowerCase(Locale.ROOT) + getResources().getString(R.string.cost) + cost;
        //if cost integer, trim decimal part
        if (cost % 1 == 0) {
            buf = buf.substring(0, buf.length() - 2);
        }
        paymentsList.add(buf);
        metersAdapter.notifyDataSetChanged();
    }

    private void pushDataBase() {
        String name = addressInput.getEditText().getText().toString();
        apartment = new Apartment(name, meters, tariff);
        apartment.generateId();

        AppDataBase db = App.getInstance().getDatabase();
        MeterDao meterDao = db.meterDao();
        TariffDao tariffDao = db.tariffDao();
        ApartmentDao apartmentDao = db.apartmentDao();

        int apartmentId = apartment.getId();
        tariff.setApartmentId(apartmentId);
        apartmentDao.insert(apartment);
        tariffDao.insert(tariff);

        for (Gauge meter : meters) {
            meter.setApartmentId(apartmentId);
            meterDao.insert((Meter) meter);
        }
    }

    @Override
    public void onClick(View view) {
        String id = getResources().getResourceEntryName(view.getId());
        switch (id) {
            case "add_meter":
                addMeterDialog();
                break;
            case "add_payment":
                addPaymentsDialog();
                break;
            case "add_apartments":
                pushDataBase();
                finish();
                break;
        }
    }

}