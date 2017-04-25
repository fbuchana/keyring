package keyring.keyring.activities.ui.home;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import keyring.keyring.activities.R;


public class HomeActivity extends BaseActivity {

    private boolean twoPaneMode;

    private Query custRef, jobRef, vehRef;
    private ListView custList, jobList, vehList;

    private ArrayList<String> customerArray = new ArrayList<>();
    private ArrayList<String> jobArray = new ArrayList<>();
    private ArrayList<String> vehicleArray = new ArrayList<>();

    private ArrayList<String> custKeys = new ArrayList<>();
    private ArrayList<String> jobKeys = new ArrayList<>();
    private ArrayList<String> vehicleKeys = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        setupToolbar();

        custRef  = FirebaseDatabase.getInstance().getReference().child("Customers").orderByChild("Name");

        custList = (ListView) findViewById(R.id.customer_list);

        final ArrayAdapter<String> custArrayAdapter  = new ArrayAdapter<String>(this, R.layout.list_item, R.id.search_list_item, customerArray);

        custList.setAdapter(custArrayAdapter);

        custRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String intro = (String) snapshot.child("Name").getValue();
                    String key = snapshot.getKey();
                    custKeys.add(key);
                    customerArray.add(intro);
                    custArrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        jobRef = FirebaseDatabase.getInstance().getReference().child("Jobs").orderByChild("Customer");

        jobList = (ListView) findViewById(R.id.job_list);

        final ArrayAdapter<String> jobArrayAdapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.search_list_item, jobArray);

        jobList.setAdapter(jobArrayAdapter);

        jobRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String intro = (String) snapshot.child("Customer").getValue() + " " + snapshot.child("End Date").getValue();
                    String key = snapshot.getKey();
                    jobKeys.add(key);
                    jobArray.add(intro);
                    jobArrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        vehRef = FirebaseDatabase.getInstance().getReference().child("Vehicles").orderByChild("Manufacturer");

        vehList = (ListView) findViewById(R.id.vehicle_list);

        final ArrayAdapter<String> vehArrayAdapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.search_list_item, vehicleArray);

        vehList.setAdapter(vehArrayAdapter);

        vehRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String intro = (String) snapshot.child("Year").getValue() + " " + snapshot.child("Manufacturer").getValue() + " " + snapshot.child("Model").getValue();
                    String key = snapshot.getKey();
                    vehicleKeys.add(key);
                    vehicleArray.add(intro);
                    vehArrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        custList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getApplicationContext(), CustomerDetailActivity.class);
                intent.putExtra("Key", (String) custKeys.get(i));

                startActivityForResult(intent, 1);

            }
        });

        jobList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), JobDetailActivity.class);
                intent.putExtra("Key", (String) jobKeys.get(i));

                startActivityForResult(intent, 1);
            }
        });

        vehList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), VehicleDetailActivity.class);
                intent.putExtra("Key", (String) vehicleKeys.get(i));

                startActivityForResult(intent, 1);
            }
        });

        EditText customerSearch =  (EditText) findViewById(R.id.search_customer);

        customerSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                custArrayAdapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

        EditText jobSearch = (EditText) findViewById(R.id.search_job);

        jobSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                jobArrayAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        EditText vehSearch = (EditText) findViewById(R.id.search_vehicle);

        vehSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                vehArrayAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void onItemSelected(String id) {

    }

    private void setupToolbar() {
        final ActionBar ab = getActionBarToolbar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void setupDetailFragment() {
    }

    private boolean isTwoPaneLayoutUsed() {
        return findViewById(R.id.article_detail_container) != null;
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
        return R.id.nav_home;
    }

    @Override
    public boolean providesActivityToolbar() {
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {

            if (resultCode == RESULT_OK) {
                finish();
                startActivity(getIntent());
            }
        }
    }
}
