package com.tzabochen.homework_007;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;

public class AppMain extends AppCompatActivity implements ItemSelectedListener
{

    // VALUE'S
    public static ArrayList<HashMap<String, String>> data = new ArrayList<>();

    private int itemPosition = 0;           // ITEM SELECTED BY DEFAULT
    private boolean withContent = true;     // SHOW TWO-PANE LAYOUT

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // CONNECTION
        if (!connectionStatus())
        {
            alertDialog();
        }

        // ASYNC
        new HttpAsyncTask().execute();

        // TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.app_name);
        }

        // LOAD SAVED ITEM POSITION
        if (savedInstanceState != null)
        {
            this.itemPosition = savedInstanceState.getInt("itemPosition");
        }

        // SEARCH FRAGMENT WITH CONTENT
        this.withContent = (findViewById(R.id.main_fragment_content) != null);

        if (withContent)
        {
            showContent(itemPosition);
        }
    }

    // SAVE ITEM POSITION
    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putInt("itemPosition", itemPosition);
    }

    // GET ITEM POSITION WITH FRAGMENT
    @Override
    public void itemSelected(int position)
    {
        this.itemPosition = position;
        showContent(position);
    }

    private void showContent(int position)
    {
        if (withContent)
        {
            // TWO-PANE LAYOUT
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentContent fragmentContent = (FragmentContent) fragmentManager.findFragmentById(R.id.main_fragment_content);

            if (fragmentContent == null || fragmentContent.getPosition() != position)
            {
                fragmentContent = FragmentContent.newInstance(position);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_fragment_content, fragmentContent);
                fragmentTransaction.commit();
            }
        }
        else
        {
            // ONE-PANE LAYOUT
            startActivity(new Intent(this, ActivityContent.class).putExtra("itemPosition", position));
        }
    }

    private void alertDialog()
    {
        AlertDialog.Builder aBuilder = new AlertDialog.Builder(this);
        aBuilder.setTitle(R.string.alert_dialog);
        aBuilder.setCancelable(false);
        aBuilder.setPositiveButton(R.string.positive_button, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                finish();
            }
        });
        AlertDialog alertDialog = aBuilder.create();
        alertDialog.show();
    }

    private boolean connectionStatus()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public class HttpAsyncTask extends AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>
    {
        @Override
        protected ArrayList<HashMap<String, String>> doInBackground(Void... params)
        {
            return WeatherJson.getData(WeatherHttp.getWeatherDate());
        }

        @Override
        protected void onPostExecute(ArrayList<HashMap<String, String>> hashMaps)
        {
            super.onPostExecute(hashMaps);
            data = hashMaps;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}