package keyring.keyring.activities.ui.home;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import keyring.keyring.activities.R;


public class AddVehicle extends BaseActivity {
    private DatabaseReference mDatabase;
    private Button addVehicleButton;

    private EditText ownerField;
    private EditText VINField;
    private EditText yearField;
    private EditText makeField;
    private EditText modelField;
    private EditText colorField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_vehicle);
        setupToolbar();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Vehicles");
        addVehicleButton = (Button) findViewById(R.id.add_vehicle_btn);

        ownerField = (EditText) findViewById(R.id.owner);
        VINField = (EditText) findViewById(R.id.vin);
        yearField = (EditText) findViewById(R.id.year);
        makeField = (EditText) findViewById(R.id.manufacturer);
        modelField = (EditText) findViewById(R.id.model);
        colorField = (EditText) findViewById(R.id.color);

        addVehicleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String owner = ownerField.getText().toString().trim();
                String VIN = VINField.getText().toString().trim();
                String year = yearField.getText().toString().trim();
                String make = makeField.getText().toString().trim();
                String model = modelField.getText().toString().trim();
                String color = colorField.getText().toString().trim();

                HashMap<String, String> dataMap = new HashMap<String, String>();
                dataMap.put("Owner", owner);
                dataMap.put("VIN", VIN);
                dataMap.put("Year", year);
                dataMap.put("Manufacturer", make);
                dataMap.put("Model", model);
                dataMap.put("Color", color);

                mDatabase.push().setValue(dataMap);

                Toast.makeText(AddVehicle.this, "Vehicle Added!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setupToolbar() {
        final ActionBar ab = getActionBarToolbar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                openDrawer();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getSelfNavDrawerItem() {
        return R.id.nav_vehicle;
    }

    @Override
    public boolean providesActivityToolbar() {
        return true;
    }

    public static class SettingsFragment extends PreferenceFragment {
        public SettingsFragment() {}

    }
}
