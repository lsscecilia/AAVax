package com.example.aavax.ui;

import android.content.res.Resources;
import androidx.fragment.app.Fragment;
import android.view.View;
import com.example.aavax.R;
import java.util.Arrays;

public class DestinationMgr extends Fragment {

    private String[] popular_countries;
    private String[] all_countries;
    private String[] asia_countries;
    private String[] north_america_countries;
    private String[] south_america_countries;
    private String[] europe_countries;
    private String[] africa_countries;
    private String[] oceania_countries;

    public String[] getPopularCountries(String message, Resources res){
        switch(message){
            case "Asia":
                popular_countries = res.getStringArray(R.array.asia_popular_countries);
                break;
            case "Europe":
                popular_countries = res.getStringArray(R.array.europe_popular_countries);
                break;
            case "North America":
                popular_countries = res.getStringArray(R.array.north_america_popular_countries);
                break;
            case "South America":
                popular_countries = res.getStringArray(R.array.south_america_popular_countries);
                break;
            case "Oceania":
                popular_countries = res.getStringArray(R.array.oceania_popular_countries);
                break;
            case "Africa":
                popular_countries = res.getStringArray(R.array.africa_popular_countries);
                break;
        }
        return popular_countries;
    }

    public String[] getAllCountries(String message, Resources res){
        switch(message){
            case "Asia":
                all_countries = res.getStringArray(R.array.asia_all_countries);
                break;
            case "Europe":
                all_countries = res.getStringArray(R.array.europe_all_countries);
                break;
            case "North America":
                all_countries = res.getStringArray(R.array.north_america_all_countries);
                break;
            case "South America":
                all_countries = res.getStringArray(R.array.south_america_all_countries);
                break;
            case "Oceania":
                all_countries = res.getStringArray(R.array.oceania_popular_countries);
                break;
            case "Africa":
                all_countries = res.getStringArray(R.array.africa_all_countries);
                break;
        }
        return all_countries;
    }

    public int findImage(View view, String countryName, Resources res){
        asia_countries = res.getStringArray(R.array.asia_all_countries);
        north_america_countries = res.getStringArray(R.array.north_america_all_countries);
        south_america_countries = res.getStringArray(R.array.south_america_all_countries);
        europe_countries = res.getStringArray(R.array.europe_all_countries);
        africa_countries = res.getStringArray(R.array.africa_all_countries);
        oceania_countries = res.getStringArray(R.array.oceania_popular_countries);

        if (Arrays.asList(asia_countries).contains(countryName))
            return R.drawable.asia;
        else if (Arrays.asList(north_america_countries).contains(countryName))
            return R.drawable.north_america;
        else if (Arrays.asList(south_america_countries).contains(countryName))
            return R.drawable.south_america;
        else if (Arrays.asList(europe_countries).contains(countryName))
            return R.drawable.europe;
        else if (Arrays.asList(africa_countries).contains(countryName))
            return R.drawable.africa;
        else if (Arrays.asList(oceania_countries).contains(countryName))
            return R.drawable.oceania;
        else
            return R.drawable.antarctica;

    }

}
