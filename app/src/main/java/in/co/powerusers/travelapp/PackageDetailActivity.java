package in.co.powerusers.travelapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class PackageDetailActivity extends AppCompatActivity {
    TextView fromLoc,places,duration,food,hotel,flight,sights,packageName,amount,itinerary;
    Bundle bundle;
    DbConn db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bundle = getIntent().getExtras();
        db = new DbConn(this);

        fromLoc = (TextView)findViewById(R.id.fromLoc);
        places = (TextView)findViewById(R.id.places);
        duration = (TextView)findViewById(R.id.duration);
        food = (TextView)findViewById(R.id.food);
        hotel = (TextView)findViewById(R.id.hotel);
        flight = (TextView)findViewById(R.id.flight);
        sights = (TextView)findViewById(R.id.sights);
        packageName = (TextView)findViewById(R.id.packageName);
        amount = (TextView)findViewById(R.id.amount);
        itinerary = (TextView)findViewById(R.id.itinerary);

        //String dtls = db.getPackDetails(bundle.getString("pack_title"));
        Package dtls = db.getPackDetails(bundle.getString("pack_title"));
        /*fromLoc.setText(dtls.split("\\|")[0]);
        places.setText(dtls.split("\\|")[1]);
        duration.setText(dtls.split("\\|")[2]);
        food.setText(dtls.split("\\|")[3]);
        hotel.setText(dtls.split("\\|")[4].equals("Y") ? "Yes" : "No");
        flight.setText(dtls.split("\\|")[5].equals("Y") ? "Yes" : "No");
        sights.setText(dtls.split("\\|")[6].equals("Y") ? "Yes" : "No");
        amount.setText(dtls.split("\\|")[7]);*/
        fromLoc.setText(dtls.getFromloc());
        places.setText(dtls.getPlaces());
        duration.setText(dtls.getDuration());
        food.setText(dtls.getFood());
        hotel.setText(dtls.getHotel().equals("Y") ? "Yes" : "No");
        flight.setText(dtls.getFlight().equals("Y") ? "Yes" : "No");
        sights.setText(dtls.getSights().equals("Y") ? "Yes" : "No");
        amount.setText(dtls.getPrice());
        System.out.println("=======================================");
        System.out.println(dtls.getItinerary());
        System.out.println("=======================================");
        itinerary.setText(dtls.getItinerary());
        packageName.setText(bundle.getString("pack_title"));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("packName",packageName.getText().toString());
                Intent intent = new Intent(getApplicationContext(),BookingActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Package Details");
    }

}
