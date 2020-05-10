package com.example.androidcoronaapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    private int positionCounty;
    TextView txtCases, txtRecovered, txtCritical, txtActive, txtTodayCase, txtTotalDeath, txtTodayDeath, txtAffectedCountries;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        positionCounty = intent.getIntExtra("position",0);

        getSupportActionBar().setTitle(AffectedCountries.countryModelList.get(positionCounty).getCountry() + " ülkesi için Detaylar");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        txtCases = findViewById(R.id.txtCases);
        txtRecovered = findViewById(R.id.txtRecovery);
        txtCritical = findViewById(R.id.txtCritical);
        txtActive = findViewById(R.id.txtActive);
        txtTodayCase = findViewById(R.id.txtTodayCase);
        txtTotalDeath = findViewById(R.id.txtTotalDeath);
        txtTodayDeath = findViewById(R.id.txtTodayDeath);
        txtAffectedCountries = findViewById(R.id.txtAffectedCountries);



        txtCases.setText(AffectedCountries.countryModelList.get(positionCounty).getCases());
        txtRecovered.setText(AffectedCountries.countryModelList.get(positionCounty).getRecovered());
        txtCritical.setText(AffectedCountries.countryModelList.get(positionCounty).getCritical());
        txtActive.setText(AffectedCountries.countryModelList.get(positionCounty).getActive());
        txtTodayCase.setText(AffectedCountries.countryModelList.get(positionCounty).getTodayCases());
        txtTotalDeath.setText(AffectedCountries.countryModelList.get(positionCounty).getDeaths());
        txtTodayDeath.setText(AffectedCountries.countryModelList.get(positionCounty).getTodayDeaths());


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
