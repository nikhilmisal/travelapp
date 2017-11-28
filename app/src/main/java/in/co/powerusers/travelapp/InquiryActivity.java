package in.co.powerusers.travelapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Map;

public class InquiryActivity extends AppCompatActivity {
    DbConn db;
    EditText fname,lname,mobile,email,message;
    Button inqBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = new DbConn(this);
        Map<String,String> userMap = db.getUser();
        fname = (EditText)findViewById(R.id.ifirstName);
        lname = (EditText)findViewById(R.id.ilastName);
        mobile = (EditText)findViewById(R.id.imobileNo);
        email = (EditText)findViewById(R.id.iemailId);
        message = (EditText)findViewById(R.id.iMessage);
        inqBtn = (Button)findViewById(R.id.inqBtn);

        fname.setText(userMap.get("fname"));
        lname.setText(userMap.get("lname"));
        mobile.setText(userMap.get("mobile"));
        email.setText(userMap.get("email"));
        message.requestFocus();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        inqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(message.getText().toString().equals(""))
                {
                    Snackbar.make(view, "Please Enter Message", Snackbar.LENGTH_LONG)
                            .show();
                    message.requestFocus();
                }else {
                    SendMail sm = new SendMail();
                    String stat = sm.sendInquiry(fname.getText().toString(), lname.getText().toString(), email.getText().toString(), mobile.getText().toString(), message.getText().toString());
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
    }
}
