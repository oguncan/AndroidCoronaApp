package com.example.androidcoronaapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView txtCases, txtRecovered, txtCritical, txtActive, txtTodayCase, txtTotalDeath, txtTodayDeath, txtAffectedCountries;
    SimpleArcLoader simpleArcLoader;
    ScrollView scrollView;
    PieChart pieChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCases = findViewById(R.id.txtCases);
        txtRecovered = findViewById(R.id.txtRecovery);
        txtCritical = findViewById(R.id.txtCritical);
        txtActive = findViewById(R.id.txtActive);
        txtTodayCase = findViewById(R.id.txtTodayCase);
        txtTotalDeath = findViewById(R.id.txtTotalDeath);
        txtTodayDeath = findViewById(R.id.txtTodayDeath);
        txtAffectedCountries = findViewById(R.id.txtAffectedCountries);

        simpleArcLoader = findViewById(R.id.loader);
        scrollView = findViewById(R.id.scrollStats);
        pieChart = findViewById(R.id.pieChart);

        fetchData();


    }

    private void fetchData() {
        String url = "https://corona.lmao.ninja/v2/all";
        simpleArcLoader.start();
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    txtCases.setText(jsonObject.getString("cases"));
                    txtRecovered.setText(jsonObject.getString("recovered"));
                    txtCritical.setText(jsonObject.getString("critical"));
                    txtActive.setText(jsonObject.getString("active"));
                    txtTodayCase.setText(jsonObject.getString("todayCases"));
                    txtTotalDeath.setText(jsonObject.getString("deaths"));
                    txtTodayDeath.setText(jsonObject.getString("todayDeaths"));
                    txtAffectedCountries.setText(jsonObject.getString("affectedCountries"));

                    pieChart.addPieSlice(new PieModel("Vaka Sayısı",Float.parseFloat(txtCases.getText().toString()), Color.parseColor("#FFA726")));
                    pieChart.addPieSlice(new PieModel("İyileşen Sayısı",Float.parseFloat(txtRecovered.getText().toString()), Color.parseColor("#66BB6A")));
                    pieChart.addPieSlice(new PieModel("Ölü Sayısı",Float.parseFloat(txtTotalDeath.getText().toString()), Color.parseColor("#EF5350")));
                    pieChart.addPieSlice(new PieModel("Aktif Hasta Sayısı",Float.parseFloat(txtActive.getText().toString()), Color.parseColor("#29B6F6")));

                    pieChart.startAnimation();

                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);


                } catch (Exception e) {
                    e.printStackTrace();
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public void goTrackCountries(View view) {
        startActivity(new Intent(getApplicationContext(),AffectedCountries.class));
    }
}
