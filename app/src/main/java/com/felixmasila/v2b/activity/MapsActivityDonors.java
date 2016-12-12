package com.felixmasila.v2b.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.felixmasila.v2b.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
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

import static com.felixmasila.v2b.R.layout.markerdialogbox;

public class MapsActivityDonors extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,GoogleMap.OnMarkerClickListener,
        LocationListener {

    private GoogleMap mMap;
    //API Client
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    private SharedPreferences pref;
    AlertDialog dialog;
    private AppCompatButton button1, button2,button3, btn_message;
    private EditText user_message;

    String[] donor_blood_type;
    Double[] latitude;
    Double[] longitude;
    int[] donor_id;
    int[] donor_id_no;
    String[]donor_first_contact;
    String[]donor_optional_conatct;
    String[]donor_eligibility;

    private ProgressDialog loading;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_donors);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getData();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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
        //mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() );
        // mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        //Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
        mMap.setOnMarkerClickListener(this);
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("My Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
        //distanceTo


        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }

    //getData from mysql db function
    private void getData() {
        loading = ProgressDialog.show(this, "Please wait...", "loading nearest donors ...", false, false);

        String url = AppConfig.DONOR_URL;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MapsActivityDonors.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response) {
        ArrayList<Donorlocationconstructor> details = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("information");
            donor_blood_type = new String[result.length()];
            latitude = new Double[result.length()];
            longitude = new Double[result.length()];
            donor_first_contact= new String[result.length()];
            donor_id_no = new int[result.length()];
            donor_eligibility=new String[result.length()];
            donor_id=new int[result.length()];
            donor_optional_conatct=new String[result.length()];



            for (int i = 0; i < result.length(); i++) {

                JSONObject locationData = result.getJSONObject(i);
                Donorlocationconstructor location = new Donorlocationconstructor();
                location.setDonor_id(locationData.getInt("donor_id"));
                location.setDonor_id_no(locationData.getInt("donor_id_no"));
                location.setDonor_first_contact(locationData.getString("donor_first_contact"));
                location.setDonor_optional_conatct(locationData.getString("donor_optional_contact"));
                location.setDonor_blood_type(locationData.getString("donor_blood_type"));
                location.setDonor_eligibility(locationData.getString("donor_eligibility"));
                location.setLatitude(locationData.getDouble("latitude"));
                location.setLongitude(locationData.getDouble("longitude"));
//                location.setLast_donation_date(locationData.getDate("last_donation_date"));
                details.add(location);

//                donor_blood_type[i] = location.getDonor_blood_type();
                latitude[i] = location.getLatitude();
                longitude[i] = location.getLongitude();
//                donor_id[i] =location.getDonor_id();
//                donor_id_no[i] =location.getDonor_id_no();
                donor_first_contact[i]=location.getDonor_first_contact();
//                donor_optional_conatct[i]=location.getDonor_optional_conatct();
//                donor_eligibility[i]=location.getDonor_eligibility();





//                Log.e("Am Here", donor_blood_type[i]);
                Toast.makeText(MapsActivityDonors.this, donor_first_contact[i], Toast.LENGTH_LONG).show();

                Location newLocation = new Location("newlocation");
                newLocation.setLatitude(latitude[i]);
                newLocation.setLongitude(longitude[i]);

//                Location crntLocation=new Location("crntlocation");
//                crntLocation.setLatitude(crntLocation.getLatitude());
//                crntLocation.setLongitude(crntLocation.getLongitude());
//                float distance =crntLocation.distanceTo(newLocation)/1000; // in km

//                LatLng ourPOint = new LatLng(Double.parseDouble(latitude[i]), Double.parseDouble(longitude[i]));
//                if (distance<5100) {
                LatLng ourPOint = new LatLng((latitude[i]), (longitude[i]));
                mMap.addMarker(new MarkerOptions().position(ourPOint).title(donor_first_contact[i]).snippet(donor_eligibility[i]));

//                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Maps Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
    private void showDialog(final String phone){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(markerdialogbox, null);
        button1 = (AppCompatButton)view.findViewById(R.id.button1);
        button2 = (AppCompatButton)view.findViewById(R.id.button2);
        button3 = (AppCompatButton)view.findViewById(R.id.button3);
        builder.setView(view);
        builder.setTitle("SELECT AN OPTION");
        button1.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v) {

               dialog.dismiss();
               showmessageDialog(phone);
//               SmsManager smsManager = SmsManager.getDefault();
////               String sendTo = phone;
//               String message = "Message send via virtual blood bank, please acknowledge on receipt for more communication. thanks, in advance";
//               smsManager.sendTextMessage(phone,null,message,null,null);

           }
       });
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                String p = "tel:" + phone;
                i.setData(Uri.parse(p));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    private void showmessageDialog(final String phone){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.messagedialogbox, null);
        btn_message = (AppCompatButton)view.findViewById(R.id.btn_message);
        user_message = (EditText)view.findViewById(R.id.user_message);
        builder.setView(view);
        builder.setTitle("Type the message");
        btn_message.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


                SmsManager smsManager = SmsManager.getDefault();
//               String sendTo = phone;
//                String message = "Message send via virtual blood bank, please acknowledge on receipt for more communication. thanks, in advance";
                smsManager.sendTextMessage(phone,null, String.valueOf(user_message.getText()),null,null);
                dialog.dismiss();
                Toast.makeText(MapsActivityDonors.this, "message sent successfully", Toast.LENGTH_LONG).show();


            }

        });
//        ((ViewGroup) mainListView.getParent()).removeView(mainListView);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        String phone = marker.getTitle();
        showDialog(phone);

        return false;
    }


}