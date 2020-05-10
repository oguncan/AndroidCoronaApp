package com.example.androidcoronaapi.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.androidcoronaapi.AffectedCountries;
import com.example.androidcoronaapi.Models.CountryModel;
import com.example.androidcoronaapi.R;

import java.util.ArrayList;
import java.util.List;

public class ListCountryAdapter extends ArrayAdapter<CountryModel> {

    private Context context;
    private List<CountryModel> countryModelList;
    private List<CountryModel> countryModelsListFiltered;

    public ListCountryAdapter(@NonNull Context context, List<CountryModel> countryModelList) {
        super(context, R.layout.list_country_item, countryModelList);

        this.context = context;
        this.countryModelList = countryModelList;
        this.countryModelsListFiltered = countryModelList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_country_item, null, true);
        TextView txtCountryName =  view.findViewById(R.id.txtCountryName);
        ImageView imgFlag =  view.findViewById(R.id.imgFlag);

        txtCountryName.setText(countryModelsListFiltered.get(position).getCountry());
        Glide.with(context).load(countryModelsListFiltered.get(position).getFlat()).into(imgFlag);
        return view;
    }

    @Override
    public int getCount() {
            return countryModelsListFiltered.size();
    }

    @Nullable
    @Override
    public CountryModel getItem(int position) {
        return countryModelsListFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        final Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                if(charSequence==null || charSequence.length()==0){
                    filterResults.count = countryModelList.size();
                    filterResults.values = countryModelList;
                }
                else
                {
                    List<CountryModel> resultsModel = new ArrayList<>();
                    String searchStr = charSequence.toString().toLowerCase();

                    for(CountryModel itemsModel: countryModelList){
                        if(itemsModel.getCountry().toLowerCase().contains(searchStr)){
                            resultsModel.add(itemsModel);
                        }
                        filterResults.count=resultsModel.size();
                        filterResults.values = resultsModel;
                    }
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                countryModelsListFiltered = (List<CountryModel>) filterResults.values;
                AffectedCountries.countryModelList = (List<CountryModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
}
