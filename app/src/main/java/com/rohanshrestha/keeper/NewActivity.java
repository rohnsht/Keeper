package com.rohanshrestha.keeper;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.rohanshrestha.keeper.data.Credential;
import com.rohanshrestha.keeper.utils.FirebaseUtils;

import java.util.Map;

public class NewActivity extends AppCompatActivity {

    public static final int MODE_ADD = 0;
    public static final int MODE_EDIT = 1;

    public static final String PARAM_MODE = "mode";
    public static final String PARAM_CREDENTIAL = "credential";
    public static final String PARAM_KEY = "key";

    private Spinner titleSpinner;
    private EditText et_username, et_password;
    private ProgressDialog progressDialog;

    private Credential credential;
    private String credentialKey;
    private int mode;
    private DatabaseReference mDatabase;
    ArrayAdapter<CharSequence> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDatabase = FirebaseDatabase.getInstance().getReference().child(FirebaseUtils.getUid());
        mode = getIntent().getIntExtra(PARAM_MODE, MODE_ADD);
        if (mode == MODE_EDIT) {
            credential = (Credential) getIntent().getSerializableExtra(PARAM_CREDENTIAL);
            credentialKey = getIntent().getStringExtra(PARAM_KEY);
        }

        initVars();

        if (mode == MODE_EDIT) {
            getSupportActionBar().setTitle("Edit Credential");
            titleSpinner.setSelection(adapter.getPosition(credential.title));
            et_username.setText(credential.username);
            et_password.setText(credential.password);
        }
    }

    private void initVars() {
        titleSpinner = (Spinner) findViewById(R.id.title);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);

        adapter = ArrayAdapter.createFromResource(this,
                R.array.account_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        titleSpinner.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_save:

                //Utils.closeSoftKeyboard(getActivity(), toolbar);
                String title = titleSpinner.getSelectedItem().toString();
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();

                if (title.trim().isEmpty() || username.trim().isEmpty() || password.trim().isEmpty()) {
                    return true;
                }
                showProgressDialog("Saving...");

                if (mode == MODE_ADD) {
                    Credential pass = new Credential(title, username, password);
                    Map<String, Object> postValues = pass.toMap();
                    mDatabase.push().setValue(postValues)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        hideProgressDialog();
                                        onBackPressed();
                                    } else {
                                        hideProgressDialog();
                                        Toast.makeText(NewActivity.this, "Unable to save credential.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else if (mode == MODE_EDIT) {
                    credential.title = title;
                    credential.username = username;
                    credential.password = password;
                    mDatabase.child(credentialKey).setValue(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                hideProgressDialog();
                                onBackPressed();
                            } else {
                                hideProgressDialog();
                                Toast.makeText(NewActivity.this, "Unable to update credential.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                break;
        }

        return true;
    }

    private void showProgressDialog(String msg) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(msg);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void hideProgressDialog() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }
}
