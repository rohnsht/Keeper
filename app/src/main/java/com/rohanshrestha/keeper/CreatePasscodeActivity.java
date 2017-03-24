package com.rohanshrestha.keeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.rohanshrestha.keeper.utils.Prefs;

public class CreatePasscodeActivity extends AppCompatActivity {

    public static final int MODE_CREATE = 0;
    public static final int MODE_CHANGE = 1;

    private TextView tv_message, tv_error;
    private EditText tv_passcode;
    private String passcode;

    private Prefs prefs;
    private int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_passcode);

        mode = getIntent().getIntExtra("Mode", 0);
        prefs = new Prefs(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (mode == MODE_CHANGE)
                getSupportActionBar().setTitle("Change Passcode");
        }

        initVars();

        if (mode == MODE_CHANGE) {
            tv_message.setText("Enter you old passcode");
        }

        tv_passcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 6) {
                    if (mode == MODE_CHANGE)
                        verifyPasscode();
                    else
                        createPasscode();
                }
            }
        });

    }

    private void initVars() {
        tv_passcode = (EditText) findViewById(R.id.tv_passcode);
        tv_message = (TextView) findViewById(R.id.tv_message);
        tv_error = (TextView) findViewById(R.id.tv_error);
    }


    private void verifyPasscode() {
        if (tv_passcode.getText().toString().equals(prefs.getPasscode())) {
            mode = MODE_CREATE;
            tv_passcode.getText().clear();
            tv_message.setText("Enter your new passcode");
        } else {
            tv_passcode.getText().clear();
            tv_error.setVisibility(View.VISIBLE);
        }
    }

    private void createPasscode() {
        if (passcode != null) {
            if (passcode.equals(tv_passcode.getText().toString())) {
                prefs.createPasscode(passcode);
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            } else {
                tv_error.setVisibility(View.VISIBLE);
                tv_message.setText("Enter your new passcode");
                tv_passcode.getText().clear();
                passcode = null;
            }
        } else {
            tv_error.setVisibility(View.INVISIBLE);
            passcode = tv_passcode.getText().toString();
            tv_message.setText("Verify your new passcode");
            tv_passcode.getText().clear();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
