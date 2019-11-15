package com.smartkids.akillicocuklar2.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;

public class SharedPrefManager {




    private SharedPreferences sharedPrefs;
    private static SharedPrefManager instance;

    public SharedPrefManager(Context context) {
        sharedPrefs = context.getApplicationContext().getSharedPreferences(Constants.PACKAGE, Context.MODE_PRIVATE);
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (instance == null)
            instance = new SharedPrefManager(context);

        return instance;
    }

    public boolean putStringtoSP(String key, String string) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(key, string);
        editor.apply();
        return true;
    }

    public boolean putBooleantoSP(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putBoolean(key, value);
        editor.apply();
        return true;
    }


    public String getStringFromSP(String key, String defaultval) {

        return sharedPrefs.getString(key, defaultval);
    }

    public boolean putIntegertoSP(String key, Integer integer) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putInt(key, integer);
        editor.apply();
        return true;
    }


    public Integer getIntegerFromSP(String key, int defaultval) {

        return sharedPrefs.getInt(key, defaultval);
    }

    public boolean getBooleanFromSP(String key, boolean defaultval) {

        return sharedPrefs.getBoolean(key, defaultval);
    }

    public boolean putLongtoSP(String key, long value) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putLong(key, value);
        editor.apply();

        return true;
    }


    public long getLongFromSP(String key, long defaultval) {

        return sharedPrefs.getLong(key, defaultval);
    }

    public boolean putLastLocationToSP(Location lastlocation) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString("lastLocation_Lat",String.valueOf(lastlocation.getLatitude()));
        editor.putString("lastLocation_Long",String.valueOf(lastlocation.getLongitude()));
        editor.putString("lastLocation_Provider",lastlocation.getProvider());
        // Log.i("LocationProvider",lastlocation.getProvider());
        editor.apply();
        return true;
    }

    public Location getLastLocationFromSp() {
        Location lastLocation;
        String lat = sharedPrefs.getString("lastLocation_Lat", "39.925054");
        String longi = sharedPrefs.getString("lastLocation_Long", "32.8369439");
        String provider = sharedPrefs.getString("lastLocation_Provider", "null");

        if (lat!=null && longi!=null && provider!=null){
            lastLocation = new Location(provider);
            lastLocation.setProvider(provider);
            lastLocation.setLatitude(Double.parseDouble(lat));
            lastLocation.setLongitude(Double.parseDouble(longi));
            return lastLocation;
        }else {
            return null;
        }




    }


}
