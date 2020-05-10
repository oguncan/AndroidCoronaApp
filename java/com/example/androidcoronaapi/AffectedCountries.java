package com.example.androidcoronaapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidcoronaapi.Adapters.ListCountryAdapter;
import com.example.androidcoronaapi.Models.CountryModel;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AffectedCountries extends AppCompatActivity {

    EditText edtTextSearch;
    SimpleArcLoader simpleArcLoader;
    ListView listView;

    public static List<CountryModel> countryModelList = new ArrayList<>();

    CountryModel countryModel;
    ListCountryAdapter countryAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affected_countries);
        edtTextSearch = findViewById(R.id.edtTextSearch);
        simpleArcLoader = findViewById(R.id.loader);
        listView = findViewById(R.id.lstViewCountries);

        getSupportActionBar().setTitle("Etkilenmiş Ülkeler");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        fetchData();

        edtTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                countryAdapter.getFilter().filter(charSequence);
                countryAdapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(getApplicationContext(), DetailActivity.class).putExtra("position",i));

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void fetchData() {
        String url = "https://corona.lmao.ninja/v2/countries/";
        simpleArcLoader.start();
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                        for(int i=0; i<jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String countryName = jsonObject.getString("country");
                            String cases = jsonObject.getString("cases");
                            String todayCases = jsonObject.getString("todayCases");
                            String deaths = jsonObject.getString("deaths");
                            String todayDeaths = jsonObject.getString("todayDeaths");
                            String recovered = jsonObject.getString("recovered");
                            String active = jsonObject.getString("active");
                            String critical = jsonObject.getString("critical");

                            JSONObject object = jsonObject.getJSONObject("countryInfo");
                            String flag = object.getString("flag");

                            countryModel = new CountryModel(flag, countryName, cases,todayCases, deaths, todayDeaths,recovered,active,critical);
                            countryModelList.add(countryModel);
                        }
                        countryAdapter = new ListCountryAdapter(AffectedCountries.this, countryModelList);
                        listView.setAdapter(countryAdapter);
                        simpleArcLoader.stop();
                        simpleArcLoader.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);
                Toast.makeText(AffectedCountries.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}
