package in.co.powerusers.travelapp;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Button proceedBtn;
    EditText fname,lname,address,mobile,email;
    DbConn db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        db = new DbConn(this);

        if(db.getUser().get("fname") != null)
        {
            Intent intent0 = new Intent(getApplicationContext(), PackageMainActivity.class);
            startActivity(intent0);
        }
        proceedBtn = (Button)findViewById(R.id.proceedBtn);
        fname = (EditText)findViewById(R.id.firstName);
        lname = (EditText)findViewById(R.id.lastName);
        address = (EditText)findViewById(R.id.address);
        mobile = (EditText)findViewById(R.id.mobileNo);
        email = (EditText)findViewById(R.id.emailId);

        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fname.getText().toString().equals("")) {
                    fname.requestFocus();
                    Snackbar.make(view, "First Name is Required!", Snackbar.LENGTH_SHORT)
                            .show();
                }
                else if(email.getText().toString().equals("")) {
                    email.requestFocus();
                    Snackbar.make(view, "Email Id is Required!", Snackbar.LENGTH_SHORT)
                            .show();
                }else if(mobile.getText().toString().length() > 0 && mobile.getText().toString().length() < 10) {
                    email.requestFocus();
                    Snackbar.make(view, "Invalid Mobile No!", Snackbar.LENGTH_SHORT)
                            .show();
                }else {
                    boolean stat = db.setUserName(fname.getText().toString(), lname.getText().toString(), address.getText().toString(), mobile.getText().toString(), email.getText().toString());
                    if (stat) {
                        Intent intent0 = new Intent(getApplicationContext(), PackageMainActivity.class);
                        startActivity(intent0);
                    }
                }
            }
        });
    }

}
