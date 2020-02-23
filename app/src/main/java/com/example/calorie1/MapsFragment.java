package com.example.calorie1;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_BLUE;

/**
 * this class displays the google map and show the user's location and nearest park
 *
 * @author Rongzhi Wang
 * @version 1.1
 */
public class MapsFragment extends Fragment implements OnMapReadyCallback {
    View vMaps;
    private GoogleMap mMap;
    private String[] parks;
    private String userAddress,name;
    private double lat,lng;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Maps");
        vMaps = inflater.inflate(R.layout.fragment_maps, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        SharedPreferences spMyusers = getActivity().getSharedPreferences("myUsers"
                , Context.MODE_PRIVATE);
        name = spMyusers.getString("firstName",null);
        GetUserLocationAsyncTask getUserLocationAsyncTask = new GetUserLocationAsyncTask();
        try {
            userAddress=getUserLocationAsyncTask.execute(name).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Geocoder gc = new Geocoder(getContext());
        if (gc.isPresent()){
            List<Address> list = null;
            try {
                list = gc.getFromLocationName(userAddress,1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = list.get(0);
            lat = address.getLatitude();
            lng = address.getLongitude();
        }
        String geocode = lat +","+lng;
        SearchMapAsyncTask searchMapAsyncTask = new SearchMapAsyncTask();
        try {
            searchMapAsyncTask.execute(geocode).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return vMaps;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng userLocation = new LatLng(lat, lng);
        LatLng park = new LatLng(Double.valueOf(parks[1]),Double.valueOf(parks[2]));
        mMap.addMarker(new MarkerOptions().position(userLocation).title("Your Home"));
        mMap.addMarker(new MarkerOptions().position(park).title(parks[0]).
                icon(BitmapDescriptorFactory.defaultMarker(HUE_BLUE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(12));
    }

    /**
     * display user's location and nearest park in radius 5000
     */
    private class SearchMapAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            String[] keywords = {"ll","intent","radius","categoryId","sortByDistance"};
            String[] values = {params[0],"browse","5000","4bf58dd8d48988d163941735","1"};
            parks = FoursquareAPI.getUserMap(FoursquareAPI.mapDisplay(keywords,values));
            return FoursquareAPI.mapDisplay(keywords,values);
        }
        @Override
        protected void onPostExecute(String result) {
            parks = FoursquareAPI.getUserMap(result);
        }
    }

    /**
     * get userlocation through user's firstname in server
     */
    private class GetUserLocationAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            userAddress= RestClient.getUserAddress(RestClient.getEnduser(params[0]));
            return userAddress;
        }

        @Override
        protected void onPostExecute(String message) {
        }
    }
}
