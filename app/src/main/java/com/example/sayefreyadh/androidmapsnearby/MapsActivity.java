package com.example.sayefreyadh.androidmapsnearby;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.location.LocationListener;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
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

import java.io.IOException;
import java.sql.SQLTransactionRollbackException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener{

    private Button nearby;
    private GoogleMap mMap;
    //private  Geocoder geocoder;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private Location lastlocation;
    private Marker currentLocationmMarker;
    public static final int REQUEST_LOCATION_CODE = 99;
    int PROXIMITY_RADIUS = 10000; /// within 10 kilo meter
    double latitude,longitude;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkLocationPermission();

        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode)
        {
            case REQUEST_LOCATION_CODE:
                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=  PackageManager.PERMISSION_GRANTED)
                    {
                        if(client == null)
                        {
                            bulidGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }
                else
                {
                    Toast.makeText(this,"Permission Denied" , Toast.LENGTH_LONG).show(); //izin reddedildi
                }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            bulidGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }

    protected synchronized void bulidGoogleApiClient() {
        client = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        client.connect();

    }

    @Override
    public void onLocationChanged(Location location) {

        latitude = location.getLatitude();
        longitude = location.getLongitude();
        lastlocation = location;
        if(currentLocationmMarker != null)
        {
            currentLocationmMarker.remove();

        }
        Log.d("lat = ",""+latitude);
        LatLng latLng = new LatLng(location.getLatitude() , location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Location"); //mevcut konum
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        currentLocationmMarker = mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(20));

        Intent intent = getIntent();
        String kelime = intent.getStringExtra("arama");

        nearby = findViewById(R.id.nearby);

        nearby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this,Listele.class);
                intent.putExtra("konumUrl",url);
                startActivity(intent);
            }
        });
        String hastane="hastane";
        String kafe ="cafe";
        String muze ="müze";
        String resim_galerisi ="resim galerisi";
        String kilise = "kilise";
        String cami ="cami";
        String banka ="banka";
        String okul= "okul";
        String hayvanat_bahcesi= "hayvanat bahçesi";
        String universite= "üniversite";
        String super_market="süpermarket";
        String magaza="mağaza";
        String taksi_duragi="taksi durağı";
        String postane="postane";
        String restoran="restoran";
        String kutuphane="kütüphane";
        String hava_limani="hava limanı";
        String firin="fırın";
        String bar1="bar";
        String guzellik_salonu="güzellik salonu";
        String kitapci="kitapçı";
        String bowling_pisti="bowling";
        String otobus_duragi="otobüs durağı";
        String kamp_alani="kamp alanı";
        String mezarlik="mezarlık";
        String belediye_binasi="belediye";
        String giyim_magazasi="giyim mağazası";
        String adliye="adliye";
        String dis_hekimi="diş hekimi";
        String eczane="eczane";
        String itfaiye="itfaiye";
        String cicekci="çiçekçi";
        String sac_bakimi="kuaför";
        String kuyumcu="kuyumcu";
        String tekel="tekel";
        String gece_kulubu="gece kulübü";
        String otopark="otopark";
        String evcil_hayvan_dukkani="petshop";
        String fizyoterapist="fizyoterapist";
        String tesisatci="tesisatçı";
        String ilkokul="ilkokul";
        String emlakci="emlakçı";
        String ayakkabi_magazasi="ayakkabı mağazası";
        String alis_veris_merkezi="alış veriş merkezi";
        String spa1="spa";
        String stadyum="stadyum";
        String metro_istasyonu="metro istasyonu";
        String tren_istasyonu="tren istasyonu";
        String veteriner_bakimi="veteriner";


        Object dataTransfer[] = new Object[2];
        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();

        if(kelime.equalsIgnoreCase(hastane)){
            mMap.clear();
            String hospital = "hospital";
             url = getUrl(latitude, longitude, hospital);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki hastaneler");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki hastaneler gösteriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(kafe)){
            mMap.clear();
            String cafe = "cafe";
             url = getUrl(latitude, longitude, cafe);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki kafeler");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki kafeler gösteriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(taksi_duragi)){
            mMap.clear();
            String taxi_stand = "taxi_stand";
             url = getUrl(latitude, longitude, taxi_stand);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki taksi durakları");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki taksi durakları gösteriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(magaza)){
            mMap.clear();
            String store = "store";
             url = getUrl(latitude, longitude, store);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki mağazalar");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki mağazalar gösteriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(muze)){
            mMap.clear();
            String museum = "museum";
             url = getUrl(latitude, longitude, museum);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki otobüs durakları");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki müzeler gösteriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(universite)){
            mMap.clear();
            String university = "university";
             url = getUrl(latitude, longitude, university);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki üniversiteler");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki üniversiteler gösteriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(super_market)){
            mMap.clear();
            String supermarket = "supermarket";
             url = getUrl(latitude, longitude, supermarket);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki süpermarketler");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki süpermarketler gösteriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(hayvanat_bahcesi)){
            mMap.clear();
            String zoo = "zoo";
             url = getUrl(latitude, longitude, zoo);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki hayvanat bahçeleri");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki hayvanat bahçeleri gösteriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(resim_galerisi)){
            mMap.clear();
            String art_gallery = "art_gallery";
             url = getUrl(latitude, longitude, art_gallery);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki resim galerileri");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki resim galerileri gösteriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(okul)){
            mMap.clear();
            String school = "school";
             url = getUrl(latitude, longitude, school);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki okullar");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki okullar gösteriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(kilise)){
            mMap.clear();
            String church = "church";
             url = getUrl(latitude, longitude, church);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki kiliseler");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki kiliseler gösteriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(cami)){
            mMap.clear();
            String mosque = "mosque";
             url = getUrl(latitude, longitude, mosque);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki camiler");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki camiler gösteriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(banka)){
            mMap.clear();
            String bank = "bank";
             url = getUrl(latitude, longitude, bank);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki bankalar");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki bankalar gösteriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(postane)){
            mMap.clear();
            String post_office = "post_office";
             url = getUrl(latitude, longitude, post_office);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki postaneler");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki postaneler gösteriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(restoran)){
            mMap.clear();
            String restaurant = "restaurant";
             url = getUrl(latitude, longitude, restaurant);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki restoranlar");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki restoranlar gösteriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(kutuphane)){
            mMap.clear();
            String library = "library";
             url = getUrl(latitude, longitude, library);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki kütüphaneler");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki kütüphaneler gösteriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(hava_limani)){
            mMap.clear();
            String airport = "airport";
             url = getUrl(latitude, longitude, airport);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki havalimanları");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki havalimanları gösteriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(firin)){
            mMap.clear();
            String bakery = "bakery";
             url = getUrl(latitude, longitude, bakery);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki fırınlar");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki fırınlar gösteriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(bar1)){
            mMap.clear();
            String bar = "bar";
             url = getUrl(latitude, longitude, bar);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki barlar");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki barlar gösteriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(guzellik_salonu)){
            mMap.clear();
            String beauty_salon = "beauty_salon";
             url = getUrl(latitude, longitude, beauty_salon);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki güzellik salonları");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki güzellik salonları gösteriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(kitapci)){
            mMap.clear();
            String book_store = "book_store";
             url = getUrl(latitude, longitude, book_store);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki kitapçılar");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki kitapçılar gösteriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(bowling_pisti)){
            mMap.clear();
            String bowling_alley = "bowling_alley";
             url = getUrl(latitude, longitude, bowling_alley);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki bowling pistleri");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki bowling pistleri gösteriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(otobus_duragi)){
            mMap.clear();
            String bus_station = "bus_station";
             url = getUrl(latitude, longitude, bus_station);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki otobüs durakları");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki otobüs durakları gösteriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(kamp_alani)){
            mMap.clear();
            String campground = "campground";
             url = getUrl(latitude, longitude, campground);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki kamp alanları");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki kamp alanları gösteriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(mezarlik)){
            mMap.clear();
            String cemetery = "cemetery";
             url = getUrl(latitude, longitude, cemetery);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki mezarlıklar");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki mezarlıklar gösteriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(belediye_binasi)){
            mMap.clear();
            String city_hall = "city_hall";
             url = getUrl(latitude, longitude, city_hall);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki belediyeler");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki belediye binaları gösteriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(giyim_magazasi)){
            mMap.clear();
            String clothing_store = "clothing_store";
             url = getUrl(latitude, longitude, clothing_store);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki giyim mağazaları");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki giyim mağazaları gösteriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(adliye)){
            mMap.clear();
            String courthouse = "courthouse";
             url = getUrl(latitude, longitude, courthouse);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki adliyeler");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki adliyeler gösteriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(dis_hekimi)){
            mMap.clear();
            String dentist = "dentist";
             url = getUrl(latitude, longitude, dentist);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki diş hekimleri");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki diş hekimleri gösteriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(eczane)){
            mMap.clear();
            String drugstore = "drugstore";
             url = getUrl(latitude, longitude, drugstore);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki eczaneler");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki eczaneler gösteriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(itfaiye)){
            mMap.clear();
            String fire_station = "fire_station";
             url = getUrl(latitude, longitude, fire_station);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki itfaiyeler");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki itfaiyeler gösteriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(cicekci)){
            mMap.clear();
            String florist = "florist";
             url = getUrl(latitude, longitude, florist);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki çiçekçiler");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki çiçekçiler gösteriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(sac_bakimi)){
            mMap.clear();
            String hair_care = "hair_care";
             url = getUrl(latitude, longitude, hair_care);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki kuaförler");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki kuaförler gösteriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(kuyumcu)){
            mMap.clear();
            String jewelry_store = "jewelry_store";
             url = getUrl(latitude, longitude, jewelry_store);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki kuyumcular");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki kuyumcular gösteriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(tekel)){
            mMap.clear();
            String liquor_store = "liquor_store";
             url = getUrl(latitude, longitude, liquor_store);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki tekeller");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki tekeller gösteriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(gece_kulubu)){
            mMap.clear();
            String night_club = "night_club";
             url = getUrl(latitude, longitude, night_club);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki gece kulüpleri");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki gece kulüpleri gösteriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(otopark)){
            mMap.clear();
            String parking = "parking";
             url = getUrl(latitude, longitude, parking);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki otoparklar");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki otoparklar gösteriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(evcil_hayvan_dukkani)){
            mMap.clear();
            String pet_store = "pet_store";
             url = getUrl(latitude, longitude, pet_store);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki evcil hayvan dükkanları");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki evcil hayvan dükkanları gösteriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(fizyoterapist)){
            mMap.clear();
            String physiotherapist = "physiotherapist";
             url = getUrl(latitude, longitude, physiotherapist);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki fizyoterapistler");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki fizyoterapistler gösteriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(tesisatci)){
            mMap.clear();
            String plumber = "plumber";
             url = getUrl(latitude, longitude, plumber);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki tesisatçılar");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki tesisatçılar gösteriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(ilkokul)){
            mMap.clear();
            String primary_school = "primary_school";
             url = getUrl(latitude, longitude, primary_school);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki ilkokullar");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki ilkokullar gösteriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(emlakci)){
            mMap.clear();
            String real_estate_agency = "real_estate_agency";
             url = getUrl(latitude, longitude, real_estate_agency);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki emlakçılar");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki emlakçılar gösteriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(ayakkabi_magazasi)){
            mMap.clear();
            String shoe_store = "shoe_store";
             url = getUrl(latitude, longitude, shoe_store);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki ayakkabı mağazaları");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki ayakkabı mağazaları gösteriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(alis_veris_merkezi)){
            mMap.clear();
            String shopping_mall = "shopping_mall";
             url = getUrl(latitude, longitude, shopping_mall);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki Alış-Veriş Merkezleri");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki AVM'ler gösteriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(spa1)){
            mMap.clear();
            String spa = "spa";
             url = getUrl(latitude, longitude, spa);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki spalar");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki spalar gösteriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(stadyum)){
            mMap.clear();
            String stadium = "stadium";
             url = getUrl(latitude, longitude, stadium);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki stadyumlar");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki stadyumlar gösteriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(metro_istasyonu)){
            mMap.clear();
            String subway_station = "subway_station";
             url = getUrl(latitude, longitude, subway_station);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki metro istasyonları");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki metro istasyonları gösteriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(tren_istasyonu)){
            mMap.clear();
            String train_station = "train_station";
             url = getUrl(latitude, longitude, train_station);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki tren istasyonları");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki tren istasyonları gösteriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(veteriner_bakimi)){
            mMap.clear();
            String veterinary_care = "veterinary_care";
             url = getUrl(latitude, longitude, veterinary_care);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yakındaki veterinerler");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yakındaki veterinerler gösteriliyor", Toast.LENGTH_SHORT).show();
        }


        if(client != null)
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(client,this);
        }
    }

    public void onClick(View v)
    {
        switch(v.getId())
        {

        }
    }
    private String getUrl(double latitude , double longitude , String nearbyPlace)
    {
        /// visit this website shaafi
        /// https://developers.google.com/places/web-service/
        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location="+latitude+","+longitude);
        googlePlaceUrl.append("&radius="+PROXIMITY_RADIUS);
        googlePlaceUrl.append("&type="+nearbyPlace);
        googlePlaceUrl.append("&sensor=true");
        googlePlaceUrl.append("&key="+"AIzaSyCs5EEepE-C2ZbRNcaAUrfHQ5XAo0LKyUo");

        Log.d("MapsActivity", "url = "+googlePlaceUrl.toString());

        return googlePlaceUrl.toString();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        locationRequest = new LocationRequest();
        locationRequest.setInterval(100);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);


        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED)
        {
            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this);
        }
    }


    public boolean checkLocationPermission()
    {
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)  != PackageManager.PERMISSION_GRANTED )
        {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION },REQUEST_LOCATION_CODE);
            }
            else
            {
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION },REQUEST_LOCATION_CODE);
            }
            return false;

        }
        else
            return true;
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }
}
