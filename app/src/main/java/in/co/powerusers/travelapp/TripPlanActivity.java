package in.co.powerusers.travelapp;

import android.app.DatePickerDialog;
import android.app.Dialog;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class TripPlanActivity extends AppCompatActivity {
    DbConn db;
    EditText startDate,endDate,noPeople,startCity,locs;
    Button planBtn;
    Switch flSwitch,accSwitch;
    TextView flText,accText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_plan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = new DbConn(this);
        final Map<String,String> userMap = db.getUser();

        startDate = (EditText)findViewById(R.id.startDate);
        endDate = (EditText)findViewById(R.id.endDate);
        noPeople = (EditText)findViewById(R.id.noPeople);
        startCity = (EditText)findViewById(R.id.startCity);
        locs = (EditText)findViewById(R.id.locations);
        planBtn = (Button)findViewById(R.id.planBtn);
        flSwitch = (Switch)findViewById(R.id.flSwitch);
        accSwitch = (Switch)findViewById(R.id.accSwitch);
        flText = (TextView)findViewById(R.id.flText);
        accText = (TextView)findViewById(R.id.accText);

        flSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked)
                    flText.setText("Required");
                else
                    flText.setText("Not Required");
            }
        });

        accSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked)
                    accText.setText("Required");
                else
                    accText.setText("Not Required");
            }
        });


        planBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(noPeople.getText().toString() !="" && startCity.getText().toString() != "" && locs.getText().toString() != "") {
                    SendMail sm = new SendMail();
                    String stat = sm.checkBooking(userMap.get("fname"), userMap.get("lname"), userMap.get("email"), userMap.get("mobile"), startDate.getText().toString(),endDate.getText().toString(),noPeople.getText().toString(),startCity.getText().toString(),locs.getText().toString(),flSwitch.isChecked(),accSwitch.isChecked());
                    Snackbar.make(view, stat, Snackbar.LENGTH_LONG)
                            .addCallback(new Snackbar.Callback() {
                                @Override
                                public void onDismissed(Snackbar snackbar, int event) {
                                    finish();
                                }

                                @Override
                                public void onShown(Snackbar snackbar) {

                                }
                            })
                            .show();
                }
            }
        });
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        Calendar c1, c2;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            c1 = Calendar.getInstance();
            c2 = Calendar.getInstance();

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            if (((EditText) getActivity().findViewById(R.id.startDate)).isFocused()) {
                ((EditText) getActivity().findViewById(R.id.startDate)).setText(day + "/" + month + "/" + year);
                c1.set(year, month, day);

            }
            if (((EditText) getActivity().findViewById(R.id.endDate)).isFocused()) {
                ((EditText) getActivity().findViewById(R.id.endDate)).setText(day + "/" + month + "/" + year);
                c2.set(year,month,day);
            }
            if(c2.before(c1))
            {
                Snackbar.make(view.getRootView(), "Invalid Check out date!", Snackbar.LENGTH_LONG)
                        .show();
                ((EditText) getActivity().findViewById(R.id.endDate)).requestFocus();
            }
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}
