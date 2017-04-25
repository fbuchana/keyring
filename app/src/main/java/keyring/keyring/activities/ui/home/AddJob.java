package keyring.keyring.activities.ui.home;

import android.os.Bundle;
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


public class AddJob extends BaseActivity {
    private DatabaseReference mDatabase;
    private Button addJobButton;

    private EditText customerField;
    private EditText vinField;
    private EditText startField;
    private EditText endField;
    private EditText descripField;
    private EditText costField;
    private EditText paymentField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_job);
        setupToolbar();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Jobs");
        addJobButton = (Button) findViewById(R.id.add_job_btn);

        customerField = (EditText) findViewById(R.id.job_customer);
        vinField = (EditText) findViewById(R.id.job_vin);
        startField = (EditText) findViewById(R.id.start);
        endField = (EditText) findViewById(R.id.end);
        descripField = (EditText) findViewById(R.id.decription);
        costField = (EditText) findViewById(R.id.cost);
        paymentField = (EditText) findViewById(R.id.payment);

        addJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String customer = customerField.getText().toString().trim();
                String vin = vinField.getText().toString().trim();
                String start = startField.getText().toString().trim();
                String end = endField.getText().toString().trim();
                String description = descripField.getText().toString().trim();
                String cost = costField.getText().toString().trim();
                String payment = paymentField.getText().toString().trim();

                HashMap<String, String> dataMap = new HashMap<String, String>();
                dataMap.put("Customer", customer);
                dataMap.put("VIN", vin);
                dataMap.put("Start Date", start);
                dataMap.put("End Date", end);
                dataMap.put("Description", description);
                dataMap.put("Cost", cost);
                dataMap.put("Payment", payment);

                mDatabase.push().setValue(dataMap);

                Toast.makeText(AddJob.this, "Job Added!", Toast.LENGTH_SHORT).show();
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
        return R.id.nav_job;
    }

    @Override
    public boolean providesActivityToolbar() {
        return true;
    }
}
