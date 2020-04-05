package com.example.aavax.ui.travel;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.aavax.R;
import com.example.aavax.ui.IMainActivity;


public class TravelCountriesFragment extends Fragment {

    private static final String TAG = "TravelCountriesFragment";

    //widgets
    ListView countriesListView;
    //ArrayList<Country> countries;
    String[] popular_countries;
    String[] all_countries;

    //vars
    private IMainActivity mIMainActivity;
    private String mIncomingMessage = "";
    private ArrayAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIMainActivity.setToolbarTitle(getTag());

        Bundle bundle = this.getArguments();
        if (bundle != null){
            mIncomingMessage = bundle.getString((getString(R.string.intent_message)));
        }

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_travel_countries, container, false);

        return view;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mIMainActivity = (IMainActivity) getActivity();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        countriesListView = (ListView) getView().findViewById(R.id.countriesListView);
        Resources res = getResources();
        switch(mIncomingMessage){
            case "Asia":
                popular_countries = res.getStringArray(R.array.asia_popular_countries);
                all_countries = res.getStringArray(R.array.asia_all_countries);
                break;
            case "Europe":
                popular_countries = res.getStringArray(R.array.europe_popular_countries);
                all_countries = res.getStringArray(R.array.europe_all_countries);
                break;
            case "North America":
                popular_countries = res.getStringArray(R.array.north_america_popular_countries);
                all_countries = res.getStringArray(R.array.north_america_all_countries);
                break;
            case "South America":
                popular_countries = res.getStringArray(R.array.south_america_popular_countries);
                all_countries = res.getStringArray(R.array.south_america_all_countries);
                break;
            case "Oceania":
                popular_countries = res.getStringArray(R.array.oceania_popular_countries);
                all_countries = res.getStringArray(R.array.oceania_popular_countries);
                break;
            case "Africa":
                popular_countries = res.getStringArray(R.array.africa_popular_countries);
                all_countries = res.getStringArray(R.array.africa_all_countries);
                break;
        }

        adapter = new ArrayAdapter(getActivity(), R.layout.country_row, popular_countries);
        countriesListView.setAdapter(adapter);

        EditText searchFilter = (EditText) getView().findViewById(R.id.searchFilter);
        searchFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter = new ArrayAdapter(getActivity(), R.layout.country_row, all_countries);
                countriesListView.setAdapter(adapter);
                adapter.getFilter().filter(s);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        countriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String country_chosen = (String) countriesListView.getItemAtPosition(position);
                mIMainActivity.inflateFragment(country_chosen, country_chosen);
            }
        });



    }

    @Override
    public void onResume() {
        super.onResume();
        // Set title
        mIMainActivity.setToolbarTitle(getTag());
    }
}