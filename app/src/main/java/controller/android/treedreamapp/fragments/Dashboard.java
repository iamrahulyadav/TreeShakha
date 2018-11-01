package controller.android.treedreamapp.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.WeakHashMap;

import controller.android.treedreamapp.R;
import controller.android.treedreamapp.activity.MainActivity;
import controller.android.treedreamapp.common.Api_Url;
import controller.android.treedreamapp.common.CallBackInterface;
import controller.android.treedreamapp.common.CallWebService;
import controller.android.treedreamapp.common.Config;
import controller.android.treedreamapp.model.Plant;


public class Dashboard extends Fragment implements OnMapReadyCallback,LocationListener {
    private View rootView;
    private SupportMapFragment mapFragment;

    private LocationManager locationManager;
    private double lattitude=0.0;
    private double longitude = 0.0;
    private GoogleMap googleMaps;
    private List<Plant> plantList;
    private HashMap<String, Integer> mHashMap = new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_dashboard, null,false);
        try {
                Config.SHOWHOME = false;
                setData();
                Button fab = (Button) rootView.findViewById(R.id.fab);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Fragment fragment = new GiftCategoryFragment();
                        Config.SHOWHOME = false;
                        if (fragment != null) {
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.frame, fragment);
                            ft.commit();
                        }
                    }
                });

                mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);
                if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
                } else {
                    statusCheck();
                    getLocation();
                }
                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        googleMaps = googleMap;
                        googleMaps.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        getLocation();
                    }
                });
        }catch (Exception e){
            Log.e("Exception: ",""+e.getLocalizedMessage());
        }

        return rootView;
    }

    private void setData(){
        try {
            ArrayList<String> imagesList = new ArrayList<>();
            imagesList.add("http://i.imgur.com/DvpvklR.png");
            imagesList.add("https://www.simplifiedcoding.net/wp-content/uploads/2015/10/advertise.png");
            imagesList.add("http://i.imgur.com/DvpvklR.png");
            imagesList.add("https://www.simplifiedcoding.net/wp-content/uploads/2015/10/advertise.png");
            imagesList.add("http://i.imgur.com/DvpvklR.png");
            imagesList.add("https://www.simplifiedcoding.net/wp-content/uploads/2015/10/advertise.png");
            imagesList.add("http://i.imgur.com/DvpvklR.png");
            imagesList.add("https://www.simplifiedcoding.net/wp-content/uploads/2015/10/advertise.png");
            imagesList.add("http://i.imgur.com/DvpvklR.png");
            imagesList.add("https://www.simplifiedcoding.net/wp-content/uploads/2015/10/advertise.png");
            imagesList.add("http://i.imgur.com/DvpvklR.png");
            imagesList.add("https://www.simplifiedcoding.net/wp-content/uploads/2015/10/advertise.png");
            imagesList.add("http://i.imgur.com/DvpvklR.png");
            imagesList.add("https://www.simplifiedcoding.net/wp-content/uploads/2015/10/advertise.png");
            imagesList.add("http://i.imgur.com/DvpvklR.png");
            imagesList.add("https://www.simplifiedcoding.net/wp-content/uploads/2015/10/advertise.png");
            imagesList.add("http://i.imgur.com/DvpvklR.png");
            imagesList.add("https://www.simplifiedcoding.net/wp-content/uploads/2015/10/advertise.png");
            imagesList.add("http://i.imgur.com/DvpvklR.png");
            imagesList.add("https://www.simplifiedcoding.net/wp-content/uploads/2015/10/advertise.png");
            plantList = new ArrayList<>();
            Plant plant1 = new Plant();
            plant1.setId(1);
            plant1.setTitle("Peepal Tree");
            plant1.setSubTitle("Birthday Gift to Rachit");
            plant1.setPlantationDate("14/08/2015");
            plant1.setAddress("Himmatpur Tundla Firozabad");
            plant1.setLattitude(27.282365);
            plant1.setLongitude(78.326144);
            plant1.setImages(imagesList);


            Plant plant2 = new Plant();
            plant2.setId(2);
            plant2.setTitle("Peepal Tree");
            plant2.setSubTitle("Birthday Gift to Mukul");
            plant2.setPlantationDate("14/12/2016");
            plant2.setAddress("Tundla Firozabad");
            plant2.setLattitude(27.226200);
            plant2.setLongitude(78.241900);
            plant2.setImages(imagesList);

            Plant plant3 = new Plant();
            plant3.setId(3);
            plant3.setTitle("Mango Tree");
            plant3.setSubTitle("Birthday Gift to Kesari");
            plant3.setPlantationDate("21/03/2018");
            plant3.setAddress("Sector-44 noida UP");
            plant3.setLattitude(28.5555);
            plant3.setLongitude(77.3339);
            plant3.setImages(imagesList);

            Plant plant4 = new Plant();
            plant4.setId(4);
            plant4.setTitle("Neem Tree");
            plant4.setSubTitle("Birthday Gift to Ekta");
            plant4.setPlantationDate("21/07/2015");
            plant4.setAddress("Sector-45 Noida UP");
            plant4.setLattitude(28.535517);
            plant4.setLongitude(77.391029);
            plant4.setImages(imagesList);

            plantList.add(plant1);
            plantList.add(plant2);
            plantList.add(plant3);
            plantList.add(plant4);
        }catch (NullPointerException e){
            Log.e("NullPointerExcep: ",""+e.getLocalizedMessage());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            googleMaps = googleMap;
            for (int i = 0; i < plantList.size(); i++) {
                Plant plant = plantList.get(i);
                Marker marker = googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(plant.getLattitude(), plant.getLongitude()))
                        .title(plant.getTitle() + " " + plant.getSubTitle())
                        .snippet(plant.getAddress())
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.tree)));
                String id = marker.getId();
                mHashMap.put(id, i);
            }
            if (googleMap != null)
                googleMaps.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        int pos = mHashMap.get(marker.getId());
                        Fragment fragment = new PlantDetailsFragment();
                        Config.SHOWHOME = false;
                        Bundle extras = new Bundle();
                        extras.putParcelable("plant", plantList.get(pos));
                        if (fragment != null) {
                            fragment.setArguments(extras);
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.frame, fragment);
                            ft.commit();
                            mHashMap = null;
                        }
                    }
                });
        }catch (Exception e){
            Log.e("GoogleMapExcep: ",""+e.getLocalizedMessage());
        }

    }
    private void buildAlertMessageNoGps() {
        try {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(" Your GPS seems to be disabled, do you want to enable it?")
                    .setCancelable(false)
                    .setPositiveButton("ّyes", new DialogInterface.OnClickListener() {
                        public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("no", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            dialog.dismiss();
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
        }catch (Exception e){
            Log.e("Exception: ",""+e.getLocalizedMessage());
        }
    }


    public void statusCheck() {
        final LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
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
       // Toast.makeText(getActivity(), "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
       // buildAlertMessageNoGps();
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
            Log.e("Exception: ",""+e.getLocalizedMessage());
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    private JSONObject addJsonObjects() {
        try {
            JSONObject user = new JSONObject();


            return user;
        } catch (Exception e) {
            Log.e("Exception: ",""+e.getLocalizedMessage());
            return null;
        }
    }

    void getCategories(){
        CallWebService.getInstance(getActivity(),true).hitJSONObjectVolleyWebServiceforPost(Request.Method.POST, Api_Url.registationUrl, addJsonObjects(), true, new CallBackInterface() {
            @Override
            public void onJsonObjectSuccess(JSONObject object) {
                Log.d("Quiz List: ",""+object.toString());
                try {
                    ParseData(object.toString());

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onJsonArrarSuccess(JSONArray array) {
                Log.d("Contacts List: ",""+array.toString());
            }

            @Override
            public void onFailure(String str) {
                Log.e("failure: ",""+str);

            }
        });

    }

    private void ParseData(String data1) {
        try {
            JSONObject obj=new JSONObject(data1);
            if(obj.getBoolean("success")) {

                String ss = obj.getString("user");
                JSONObject objdata = new JSONObject(ss);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
