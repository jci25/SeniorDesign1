package com.example.seniordesign1;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.maps.MapActivity;

public class MapActivity_ extends Fragment implements LocationListener{
    int fragVal;
    LocationManager locationManager;
    static Context myContext;
    private String provider;
    private GoogleMap map;
    
    static MapActivity_ init(int val, Context cont) {
    	MapActivity_ map = new MapActivity_();
        // Supply val input as an argument.
        Bundle args = new Bundle();
        args.putInt("val", val);
        map.setArguments(args);
        myContext = cont;
        return map;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragVal = getArguments() != null ? getArguments().getInt("val") : 1;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.map_frag, container,
                false);
        
     // Get the location manager
        locationManager = (LocationManager) myContext.getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);
        LatLng latLng;
        if (location != null) {
        	latLng = new LatLng(location.getLatitude(), location.getLongitude());
          } else {
        	latLng = new LatLng(39.864052799999996, -75.12900809999996);
          }
        
        
        map = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        

    // Move the camera instantly to hamburg with a zoom of 15.
    map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

    // Zoom in, animating the camera.
    map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
        
        return layoutView;
    }

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}
    
    
 
}