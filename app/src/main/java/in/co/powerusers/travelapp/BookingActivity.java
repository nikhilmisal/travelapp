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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Map;

public class BookingActivity extends AppCompatActivity {
    DbConn db;
    TextView flname,selPackage;
    //EditText bMessage;
    EditText bCheckIn,bCheckOut,bNoGuests;
    Button bookBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = new DbConn(this);
        Map<String,String> userMap = db.getUser();
        Bundle bundle = getIntent().getExtras();

        flname = (TextView)findViewById(R.id.flname);
        selPackage = (TextView)findViewById(R.id.selPackage);
        //bMessage = (EditText)findViewById(R.id.bMessage);
        bCheckIn = (EditText)findViewById(R.id.bCheckIn);
        bCheckOut = (EditText)findViewById(R.id.bCheckOut);
        bNoGuests = (EditText)findViewById(R.id.bNoGuests);
        bookBtn = (Button)findViewById(R.id.bookBtn);

        flname.setText(userMap.get("fname")+" "+userMap.get("lname"));
        selPackage.setText(bundle.getString("packName"));
        final String fname = userMap.get("fname");
        final String lname = userMap.get("lname");
        final String email = userMap.get("email");
        final String mobile = userMap.get("mobile");

        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bCheckIn.getText().toString().equals(""))
                {
                    Snackbar.make(view, "Please Select Check In Date", Snackbar.LENGTH_LONG)
                            .show();
                    bCheckIn.requestFocus();
                }else if(bCheckOut.getText().toString().equals(""))
                {
                    Snackbar.make(view, "Please Select Check Out Date", Snackbar.LENGTH_LONG)
                            .show();
                    bCheckOut.requestFocus();
                }else if(bNoGuests.getText().toString().equals(""))
                {
                    Snackbar.make(view, "Please Enter No of Guests", Snackbar.LENGTH_LONG)
                            .show();
                    bNoGuests.requestFocus();
                }else {
                    SendMail sm = new SendMail();
                    String stat = sm.requestBooking(fname, lname, email, mobile, selPackage.getText().toString(), bCheckIn.getText().toString(), bCheckOut.getText().toString(), bNoGuests.getText().toString());
                    Snackbar.make(view, stat, Snackbar.LENGTH_LONG)
                            .addCallback(new Snackbar.Callback() {
                                @Override
                                public void onDismissed(Snackbar snackbar, int event) {
                                    finish();
                                }

                                @Override
                                public void onShown(Snackbar snackbar) {

                                }
                            }).show();
                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
            if (((EditText) getActivity().findViewById(R.id.bCheckIn)).isFocused()) {
                ((EditText) getActivity().findViewById(R.id.bCheckIn)).setText(day + "/" + month + "/" + year);
                c1.set(year, month, day);

            }
            if (((EditText) getActivity().findViewById(R.id.bCheckOut)).isFocused()) {
                ((EditText) getActivity().findViewById(R.id.bCheckOut)).setText(day + "/" + month + "/" + year);
                c2.set(year,month,day);
            }
            if(c2.before(c1))
            {
                Snackbar.make(view.getRootView(), "Invalid Check out date!", Snackbar.LENGTH_LONG)
                        .show();
                ((EditText) getActivity().findViewById(R.id.bCheckOut)).requestFocus();
            }
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new BookingActivity.DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}
