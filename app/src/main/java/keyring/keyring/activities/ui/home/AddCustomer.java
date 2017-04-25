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


public class AddCustomer extends BaseActivity {
    private DatabaseReference mDatabase;
    private Button addCustomerButton;

    private EditText nameField;
    private EditText streetField;
    private EditText cityStateField;
    private EditText zipCodeField;
    private EditText emailField;
    private EditText phoneField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_customer);
        setupToolbar();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Customers");
        addCustomerButton = (Button) findViewById(R.id.add_customer_btn);

        nameField = (EditText) findViewById(R.id.cust_first_name);
        streetField = (EditText) findViewById(R.id.cust_street);
        cityStateField = (EditText) findViewById(R.id.cust_city);
        zipCodeField = (EditText) findViewById(R.id.cust_zip);
        emailField = (EditText) findViewById(R.id.cust_email);
        phoneField = (EditText) findViewById(R.id.cust_phone);

        addCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = nameField.getText().toString().trim();
                String street = streetField.getText().toString().trim();
                String cityState = cityStateField.getText().toString().trim();
                String zipCode = zipCodeField.getText().toString().trim();
                String email = emailField.getText().toString().trim();
                String phone = phoneField.getText().toString().trim();

                HashMap<String, String> dataMap = new HashMap<String, String>();
                dataMap.put("Name", name);
                dataMap.put("Street", street);
                dataMap.put("City and State", cityState);
                dataMap.put("Zip Code", zipCode);
                dataMap.put("Email", email);
                dataMap.put("Phone", phone);

                mDatabase.push().setValue(dataMap);

                Toast.makeText(AddCustomer.this, "Customer Added!", Toast.LENGTH_SHORT).show();
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
        return R.id.nav_customer;
    }

    @Override
    public boolean providesActivityToolbar() {
        return true;
    }

}
