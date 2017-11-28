package in.co.powerusers.travelapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PackageMainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private RecyclerView _recyclerView;
    private RVAdapter _adapter;
    private RecyclerView.LayoutManager _layoutManager;
    private SearchView searchView;
    private MenuItem searchMenuItem;
    DbConn db;
    List<String> PACKAGES = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_main);

        db = new DbConn(this);
        _recyclerView = (RecyclerView) findViewById(R.id.recyclerVw);
        _recyclerView.setHasFixedSize(true);
        //_layoutManager = new LinearLayoutManager(this);
        _layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        _recyclerView.setLayoutManager(_layoutManager);
        _recyclerView.setItemAnimator(new SlideUpItemAnimator());

        db.loadPackages();
        PACKAGES = db.getPackages();
        //_layoutManager = new LinearLayoutManager(this);
        _layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        _recyclerView.setLayoutManager(_layoutManager);
        _adapter = new RVAdapter(PACKAGES);
        _recyclerView.setAdapter(_adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),InquiryActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu,menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchMenuItem = menu.findItem(R.id.app_bar_search);

        searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if(id == R.id.action_about)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("About")
                    .setIcon(R.drawable.logo)
                    .setMessage(getText(R.string.aboutus_text))
                    .setNeutralButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
            AlertDialog dg = builder.create();
            dg.show();
        }
        if(id == R.id.action_plan)
        {
            Intent intent = new Intent(getApplicationContext(),TripPlanActivity.class);
            startActivity(intent);
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        _adapter.getFilter().filter(newText);
        return false;
    }
}
