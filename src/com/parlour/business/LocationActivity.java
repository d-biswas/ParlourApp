package com.parlour.business;

import android.app.Dialog;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
 
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parlour.business.R;

public class LocationActivity extends FragmentActivity {
    private GoogleMap googleMap;
    private static final LatLng DHK = new LatLng(23.709921, 90.407143);
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_layout);
        // Getting Google Play availability status
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
 
        // Showing status
        if(status!=ConnectionResult.SUCCESS){ // Google Play Services are not available
 
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();
 
        }else { // Google Play Services are available
 
            // Getting reference to the SupportMapFragment of activity_main.xml
            SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
 
            // Getting GoogleMap object from the fragment
            googleMap = fm.getMap();
 
            // Enabling MyLocation Layer of Google Map
            googleMap.setMyLocationEnabled(true);
 
         // Showing the current location in Google Map
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(DHK));
     
            // Zoom in the Google Map
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        }
    }
    
    
    private void drawMarker(Location location){
    	googleMap.clear();
    	LatLng currentPosition = new LatLng(location.getLatitude(),
    	location.getLongitude());
    	googleMap.addMarker(new MarkerOptions()
    	.position(currentPosition)
    	.title("Dhaka")
    	.snippet("Lat:" + location.getLatitude() + "Lng:"+ location.getLongitude())
    	.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));

	}
}
