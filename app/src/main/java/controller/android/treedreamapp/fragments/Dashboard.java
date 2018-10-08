package controller.android.treedreamapp.fragments;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

import controller.android.treedreamapp.R;


public class Dashboard extends Fragment implements OnMapReadyCallback,LocationListener {
    private View rootView;
    private SupportMapFragment mapFragment;

    private LocationManager locationManager;
    private double lattitude=0.0;
    private double longitude = 0.0;
    private GoogleMap googleMaps;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_dashboard, null,false);

        mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }else {

             getLocation();
        }


        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMaps = googleMap;
                googleMaps.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

                getLocation();
            }
        });

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMaps = googleMap;
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(27.282365, 78.326144))
                .title("Himmatpur")
                .snippet("Peepal Tree")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(28.535517,77.391029))
                .title("Noida")
                .snippet("Banyan Tree")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(27.226200, 78.241900))
                .title("Tundla Firozabad")
                 .snippet("Mango Tree")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(27.282365, 78.326144), 10));

    }

    void getLocation() {
        try {
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);

        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(getActivity(), "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onLocationChanged(Location location) {
        lattitude = location.getLatitude();
        longitude = location.getLongitude();
        //locationText.setText("Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude());

        try {
            Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);


            googleMaps.addMarker(new MarkerOptions()
                    .position(new LatLng(lattitude, longitude))
                    .title(""+addresses.get(0).getAddressLine(0)+", "+
                            addresses.get(0).getAddressLine(1)+", "+addresses.get(0).getAddressLine(2)));

            googleMaps.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lattitude, longitude), 10));



            googleMaps.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                @Override
                public void onMapClick(LatLng latLng) {

                    // Creating a marker
                    MarkerOptions markerOptions = new MarkerOptions();

                    // Setting the position for the marker
                    markerOptions.position(latLng);

                    // Setting the title for the marker.
                    // This will be displayed on taping the marker
                    markerOptions.title(latLng.latitude + " : " + latLng.longitude);

                    // Clears the previously touched position
                    googleMaps.clear();

                    // Animating to the touched position
                    googleMaps.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                    // Placing a marker on the touched position
                    googleMaps.addMarker(markerOptions);
                }
            });

        }catch(Exception e)
        {

        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }
}
