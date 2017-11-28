package in.co.powerusers.travelapp;

import android.app.ProgressDialog;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.util.JsonReader;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import dalvik.system.PathClassLoader;

/**
 * Created by Powerusers on 25-07-2017.
 */

public class DbConn extends SQLiteAssetHelper {
    //DB NAME
    private static final String DATABASE_NAME = "packages.db";

    //DB VERSION
    private static final int DATABASE_VERSION = 1;

    //Primary Table
    private static final String MAIN_TABLE = "packages";
    private static final String USER_TABLE = "user";

    public DbConn(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    public ArrayList<String> getPackages()
    {
        ArrayList<String> packages = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT DISTINCT title,price FROM "+MAIN_TABLE;
        Cursor cr =db.rawQuery(selectQuery,null);
        if(cr.moveToFirst()){
            do{
                packages.add(cr.getString(0)+"|"+cr.getString(1));
            }while (cr.moveToNext());
        }
        return packages;
    }

    public Package getPackDetails(String packTitle)
    {
        String dtls = "";
        Package pkDtls = new Package();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT source_city,duration,places,flights,hotel,sights,food,price,itinerary FROM "+MAIN_TABLE+" where title = '"+packTitle+"'";
        Cursor cr =db.rawQuery(selectQuery,null);
        if(cr.moveToFirst()){
            dtls = cr.getString(0)+"|"+cr.getString(1)+"|"+cr.getString(2)+"|"+cr.getString(3)+"|"+cr.getString(4)+"|"+cr.getString(5)+"|"+cr.getString(6)+"|"+cr.getString(7);
            pkDtls = new Package(packTitle,cr.getString(0),cr.getString(1),cr.getString(2),cr.getString(3),cr.getString(4),cr.getString(5),null,null,cr.getString(6),cr.getString(7),cr.getString(8));
        }
        return pkDtls;
    }

    public boolean setUserName(String fname,String lname,String address,String mobile,String email)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String deleteQuery = "DELETE FROM "+USER_TABLE;
        db.execSQL(deleteQuery);

        String insertQuery = "insert into "+USER_TABLE+"(fname,lname,address,mobile,emailid) " +
                "values('"+fname+"','"+lname+"','"+address+"',"+mobile+",'"+email+"')";
        try{
            db.execSQL(insertQuery);
        }
        catch (SQLiteConstraintException se){
            se.getMessage();
            return false;
        }
        catch (SQLiteException qe){
            qe.getMessage();
            return false;
        }
        catch (Exception e){
            e.getMessage();
            return false;
        }
        return true;
    }

    public HashMap<String,String> getUser()
    {
        Map<String,String> userMap = new HashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT fname,lname,address,mobile,emailid FROM "+USER_TABLE;
        Cursor cr =db.rawQuery(selectQuery,null);
        if(cr.moveToFirst()){
            userMap.put("fname",cr.getString(0));
//            System.out.println(cr.getString(0));
            userMap.put("lname",cr.getString(1));
            userMap.put("address",cr.getString(2));
            userMap.put("mobile",cr.getString(3));
            userMap.put("email",cr.getString(4));
        }

        return (HashMap<String, String>) userMap;
    }

    public void loadPackages()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Package> packs = new ArrayList<>();
        String deleteQuery = "DELETE FROM "+MAIN_TABLE;
        db.execSQL(deleteQuery);
        try {
            packs = new JsonTask().execute("http://nikhilmisal.com/travelapp/getpackages.php").get();
            for(Package pack : packs){
                String insertQuery = "insert into "+MAIN_TABLE+"(title,source_city,places,duration,food,hotel,flights,sights,price,itinerary) " +
                        "values('"+pack.getPktitle()+"','"+pack.getFromloc()+"','"+pack.getPlaces()+"','"+pack.getDuration()+"','"+pack.getFood()+"','"+pack.getHotel()+"','"+pack.getFlight()+"','"+pack.getSights()+"','"+pack.getPrice()+"','"+pack.getItinerary()+"')";
                try{
                    db.execSQL(insertQuery);
                }catch (SQLiteConstraintException se){
                    System.out.println("se: "+se.getMessage());
                }
                catch (SQLiteException qe){
                    System.out.println("qe: "+qe.getMessage());
                }
                catch (Exception e){
                    System.out.println("e: "+e.getMessage());
                }
            }
        }catch (InterruptedException ine){ine.printStackTrace();}
        catch (ExecutionException ee){ee.printStackTrace();}
    }

    private List<Package> readJsonStream(String param) throws IOException {
        HttpURLConnection connection = null;
        List<Package> packs = new ArrayList<>();
        URL url = new URL(param);
        connection = (HttpURLConnection) url.openConnection();
        connection.connect();

        InputStream is = connection.getInputStream();
        JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
        try {
            return readPackageArray(reader);
        } finally {
            reader.close();
        }
    }

    private List<Package> readPackageArray(JsonReader reader) throws IOException {
        List<Package> packs = new ArrayList<Package>();

        reader.beginArray();
        while (reader.hasNext()) {
            packs.add(readMessage(reader));
        }
        reader.endArray();
        return packs;
    }

    private Package readMessage(JsonReader reader) throws IOException {
        String pktitle="";
        String fromloc="";
        String duration="";
        String places="";
        String flight="";
        String hotel="";
        String sights="";
        String pickup="";
        String drop="";
        String food="";
        String price="";
        String itinerary="";

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if("pktitle".equals(name))
                pktitle = reader.nextString();
            else if("fromloc".equals(name))
                fromloc = reader.nextString();
            else if("duration".equals(name))
                duration = reader.nextString();
            else if("places".equals(name))
                places = reader.nextString();
            else if("flight".equals(name))
                flight = reader.nextString();
            else if("hotel".equals(name))
                hotel = reader.nextString();
            else if("sights".equals(name))
                sights = reader.nextString();
            else if("pickup".equals(name))
                pickup = reader.nextString();
            else if("pdrop".equals(name))
                drop = reader.nextString();
            else if("food".equals(name))
                food = reader.nextString();
            else if("price".equals(name))
                price = reader.nextString();
            else if("itinerary".equals(name))
                itinerary = reader.nextString();
        }
        reader.endObject();
        return new Package(pktitle,fromloc,duration,places,flight,hotel,sights,pickup,drop,food,price,itinerary);
    }

    class JsonTask extends AsyncTask<String, Void, List<Package>>
    {
        protected List<Package> doInBackground(String... urls)
        {
            List<Package> lMap = new ArrayList<>();
            try {
                lMap = readJsonStream(urls[0]);
            }catch (IOException ie){ie.printStackTrace();}
            return lMap;
        }
    }
}
