package com.example.aavax.ui;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.aavax.R;
import com.example.aavax.ui.homepage.VaccineAdapter;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.data.geojson.GeoJsonFeature;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.maps.android.data.geojson.GeoJsonPoint;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final String TAG = "MapActivity";

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;

    //vars
    private Boolean mLocationPermissionsGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location currentLocation=null;
    private Location lastLocation=null;
    private GeoJsonLayer layer;
    private HashMap<GeoJsonFeature, Float> distance;
    boolean flag = false;
    private RecyclerView recyclerView;
    private ClinicAdapter adapter;
    private String clinicName;
    ArrayList<String> clinicArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        try {
            layer = new GeoJsonLayer(mMap, R.raw.chas_clinics, getApplicationContext());
            //layer.addLayerToMap();
            //LatLngBounds bounds = getPolygonBounds();
            //mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 0));
            // mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(1.3384, 103.8454)));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        getLocationPermission();
        //get current location
        if (mLocationPermissionsGranted) {
            System.out.println("try to get device location");
            if (lastLocation == null && currentLocation != null) {
                lastLocation = currentLocation;
                System.out.println("last location set once only");
                flag = true;
                distance = new HashMap<>();
                System.out.println("last location: " + lastLocation.getLatitude() + "lol " + lastLocation.getLongitude());
                for (GeoJsonFeature feature : layer.getFeatures()) {
                    GeoJsonPoint point = (GeoJsonPoint) feature.getGeometry();
                    Location l = new Location(""); // i hav no idea how to constructor empty
                    //point.getCoordinates().latitude
                    l.setLatitude(point.getCoordinates().latitude);
                    l.setLongitude(point.getCoordinates().longitude);

                    distance.put(feature, l.distanceTo(lastLocation));

                    // do something to the feature
                }

                sortByValue(distance);
                //System.out.println(distance);


                //
                Iterator hmIterator1 = distance.entrySet().iterator();

                // Iterate through the hashmap
                // and add some bonus marks for every student
                System.out.println("HashMap for ascending");


                while (hmIterator1.hasNext()) {
                    Map.Entry mapElement = (Map.Entry)hmIterator1.next();
                    GeoJsonFeature feature = (GeoJsonFeature) mapElement.getKey();
                    Float marks = ((Float) mapElement.getValue());
                    System.out.println(feature.getProperty("Name") + " : " + marks);
                }

                GeoJsonFeature g;
                GeoJsonPoint p;

                Iterator hmIterator = distance.entrySet().iterator();
                String desc;
                int index, clinicIndex, endIndex;
                String details, clinicName;
                while (hmIterator.hasNext()) {
                    Map.Entry mapElement = (Map.Entry) hmIterator.next();
                    Float marks = ((Float) mapElement.getValue());
                    System.out.println(mapElement.getKey() + " : " + marks);

                    g = (GeoJsonFeature) mapElement.getKey();
                    desc = g.getProperty("Description");
                    index = desc.indexOf("<th>HCI_NAME</th>");
                    clinicIndex = index+22;
                    details = desc.substring(clinicIndex);
                    endIndex = details.indexOf("<");
                    clinicName = details.substring(0,endIndex);
                    System.out.println("clinic name YOH" + clinicName);
                    clinicArrayList.add(clinicName);

                    p = (GeoJsonPoint) g.getGeometry();
                    //Marker maker= new MarkerOptions().position(new LatLng(p.getCoordinates().latitude,p.getCoordinates().longitude));

                    mMap.addMarker(new MarkerOptions().position(new LatLng(p.getCoordinates().latitude,p.getCoordinates().longitude)).title(clinicName));
                }

                recyclerView = findViewById(R.id.clinic_recycler);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                // add line after each vaccine row
                recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
                adapter = new ClinicAdapter(this,clinicArrayList );//string array list
                recyclerView.setAdapter(adapter);
            }


            getDeviceLocation();


            System.out.println("how many times is this running");
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

        }
        //moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM);

        //find nearest clinic

    }


    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        try{
            if(mLocationPermissionsGranted){

                final Task location = mFusedLocationProviderClient.getLastLocation();
                final Task lastLocationTask = mFusedLocationProviderClient.getLastLocation();


                location.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: found location!");
                            currentLocation = (Location) task.getResult();

                            if (currentLocation!=null)
                                moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    13f);

                        } else {
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(MapsActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
    }

    private void moveCamera(LatLng latLng, float zoom){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private void initMap(){
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(MapsActivity.this);
    }

    private void offLocation(){
        mLocationPermissionsGranted = false;
    }

    private void getLocationPermission(){

        if (!flag){
            Log.d(TAG, "getLocationPermission: getting location permissions");
            String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION};

            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                        COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    mLocationPermissionsGranted = true;
                    initMap();
                }else{
                    ActivityCompat.requestPermissions(this,
                            permissions,
                            LOCATION_PERMISSION_REQUEST_CODE);
                }
            }else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }




    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }

    public static HashMap<GeoJsonFeature, Float> sortByValue(HashMap<GeoJsonFeature, Float> hm) {
        HashMap<GeoJsonFeature, Float> sorted = hm
                .entrySet()
                .stream()
                .filter(d -> !Double.isNaN(d.getValue()))
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
        return sorted;
    }
        /*
        // Create a list from elements of HashMap
        List<Map.Entry<GeoJsonFeature, Float> > list = new LinkedList<>(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<GeoJsonFeature, Float> >() {
            public int compare(Map.Entry<GeoJsonFeature, Float> o1,
                               Map.Entry<GeoJsonFeature, Float> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<GeoJsonFeature, Float> temp = new LinkedHashMap<GeoJsonFeature, Float>();
        for (Map.Entry<GeoJsonFeature, Float> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;*/
}


