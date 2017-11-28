package in.co.powerusers.travelapp;

import android.os.AsyncTask;
import android.util.JsonReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Powerusers on 26-07-2017.
 */

public class SendMail {

    public String sendInquiry(String fname,String lname,String email,String mobile,String message)
    {
        String stat = "";
        try {
            String url = "http://nikhilmisal.com/travelapp/sendMail.php?etype=INQ&first_name="+fname+"&last_name="+lname+"&email="+email+"&mobile="+mobile+"&message="+message;
            stat = new JsonTask().execute(url).get();

        }catch(InterruptedException ie){ie.printStackTrace();}
        catch(ExecutionException ee){ee.printStackTrace();}
        return stat;
    }

    public String requestBooking(String fname,String lname,String email,String mobile,String pkg,String checkIn,String checkOut,String noGuests)
    {
        String stat = "";
        try {
            String url = "http://nikhilmisal.com/travelapp/sendMail.php?etype=BOOK&first_name="+fname+"&last_name="+lname+"&email="+email+"&mobile="+mobile+"&pkg="+pkg+"&checkIn="+checkIn+"&checkOut="+checkOut+"&noGuests="+noGuests;
            stat = new JsonTask().execute(url).get();

        }catch(InterruptedException ie){ie.printStackTrace();}
        catch(ExecutionException ee){ee.printStackTrace();}
        return stat;
    }

    public String checkBooking(String fname,String lname,String email,String mobile,String checkIn,String checkOut,String noGuests,String stCity,String Locs,boolean flight,boolean accom)
    {
        String stat = "";
        String flightFlag = flight ? "Y" : "N";
        String accomFlag = accom ? "Y" : "N";
        try {
            String url = "http://nikhilmisal.com/travelapp/sendMail.php?etype=CHECK&first_name="+fname+"&last_name="+lname+"&email="+email+"&mobile="+mobile+"&checkIn="+checkIn+"&checkOut="+checkOut+"&noGuests="+noGuests+"&stCity="+stCity+"&locs="+Locs+"&flight="+flightFlag+"&accom="+accomFlag;
            stat = new JsonTask().execute(url).get();

        }catch(InterruptedException ie){ie.printStackTrace();}
        catch(ExecutionException ee){ee.printStackTrace();}
        return stat;
    }

    class JsonTask extends AsyncTask<String, Void, String>
    {
        protected String doInBackground(String... urls)
        {
            String stat = "";
            try {
                HttpURLConnection connection = null;
                URL url = new URL(urls[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                InputStream is = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                StringBuffer lines = new StringBuffer();
                String line = "";
                while ((line = reader.readLine())!=null) {
                    lines.append(line);
                }
                stat = lines.toString();
            }catch (IOException ie){ie.printStackTrace();}
            return stat;
        }
    }
}
