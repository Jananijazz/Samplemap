package com.example.jananikumar.samplemap;

import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jananikumar on 28-02-2018.
 */

public class GetNearbyPlacesData extends AsyncTask<Object, String, String> {
    String GooglePlacesData;
    GoogleMap mMap;
    String url;

    @Override
    protected void onPostExecute(String s) {

        List<HashMap<String, String>> nearByplacesList =null;
        DataParser parser=new DataParser();
        nearByplacesList=parser.parse(s);
        showNearbyPlaces(nearByplacesList);
    }

    @Override
    protected String doInBackground(Object... objects) {
        mMap=(GoogleMap)objects[0];
        url=(String)objects[1];

        DownloadUrl downloadUrl=new DownloadUrl();
        try {
            GooglePlacesData=downloadUrl.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return GooglePlacesData;
    }
     private void showNearbyPlaces(List<HashMap<String,String>> nearbyPlacesList)
     {
         for(int i=0;i<nearbyPlacesList.size(); i++)
         {
             MarkerOptions markerOptions=new MarkerOptions();
             HashMap<String, String> googlePlace=nearbyPlacesList.get(i);

             String placeName=googlePlace.get("place_name");
             String vicinity=googlePlace.get("vicinity");
             double lat=Double.parseDouble(googlePlace.get("lat"));
             double lng=Double.parseDouble(googlePlace.get("lng"));

             LatLng latLng=new LatLng(lat, lng);
             markerOptions.position(latLng);
             markerOptions.title(placeName +":"+ vicinity);
             markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

             mMap.addMarker(markerOptions);
             mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
             mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
         }
     }
}
