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
        String muze ="m??ze";
        String resim_galerisi ="resim galerisi";
        String kilise = "kilise";
        String cami ="cami";
        String banka ="banka";
        String okul= "okul";
        String hayvanat_bahcesi= "hayvanat bah??esi";
        String universite= "??niversite";
        String super_market="s??permarket";
        String magaza="ma??aza";
        String taksi_duragi="taksi dura????";
        String postane="postane";
        String restoran="restoran";
        String kutuphane="k??t??phane";
        String hava_limani="hava liman??";
        String firin="f??r??n";
        String bar1="bar";
        String guzellik_salonu="g??zellik salonu";
        String kitapci="kitap????";
        String bowling_pisti="bowling";
        String otobus_duragi="otob??s dura????";
        String kamp_alani="kamp alan??";
        String mezarlik="mezarl??k";
        String belediye_binasi="belediye";
        String giyim_magazasi="giyim ma??azas??";
        String adliye="adliye";
        String dis_hekimi="di?? hekimi";
        String eczane="eczane";
        String itfaiye="itfaiye";
        String cicekci="??i??ek??i";
        String sac_bakimi="kuaf??r";
        String kuyumcu="kuyumcu";
        String tekel="tekel";
        String gece_kulubu="gece kul??b??";
        String otopark="otopark";
        String evcil_hayvan_dukkani="petshop";
        String fizyoterapist="fizyoterapist";
        String tesisatci="tesisat????";
        String ilkokul="ilkokul";
        String emlakci="emlak????";
        String ayakkabi_magazasi="ayakkab?? ma??azas??";
        String alis_veris_merkezi="al???? veri?? merkezi";
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
            nearby.setText("Yak??ndaki hastaneler");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki hastaneler g??steriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(kafe)){
            mMap.clear();
            String cafe = "cafe";
             url = getUrl(latitude, longitude, cafe);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yak??ndaki kafeler");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki kafeler g??steriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(taksi_duragi)){
            mMap.clear();
            String taxi_stand = "taxi_stand";
             url = getUrl(latitude, longitude, taxi_stand);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yak??ndaki taksi duraklar??");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki taksi duraklar?? g??steriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(magaza)){
            mMap.clear();
            String store = "store";
             url = getUrl(latitude, longitude, store);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yak??ndaki ma??azalar");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki ma??azalar g??steriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(muze)){
            mMap.clear();
            String museum = "museum";
             url = getUrl(latitude, longitude, museum);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yak??ndaki otob??s duraklar??");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki m??zeler g??steriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(universite)){
            mMap.clear();
            String university = "university";
             url = getUrl(latitude, longitude, university);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yak??ndaki ??niversiteler");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki ??niversiteler g??steriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(super_market)){
            mMap.clear();
            String supermarket = "supermarket";
             url = getUrl(latitude, longitude, supermarket);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yak??ndaki s??permarketler");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki s??permarketler g??steriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(hayvanat_bahcesi)){
            mMap.clear();
            String zoo = "zoo";
             url = getUrl(latitude, longitude, zoo);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yak??ndaki hayvanat bah??eleri");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki hayvanat bah??eleri g??steriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(resim_galerisi)){
            mMap.clear();
            String art_gallery = "art_gallery";
             url = getUrl(latitude, longitude, art_gallery);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yak??ndaki resim galerileri");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki resim galerileri g??steriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(okul)){
            mMap.clear();
            String school = "school";
             url = getUrl(latitude, longitude, school);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yak??ndaki okullar");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki okullar g??steriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(kilise)){
            mMap.clear();
            String church = "church";
             url = getUrl(latitude, longitude, church);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yak??ndaki kiliseler");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki kiliseler g??steriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(cami)){
            mMap.clear();
            String mosque = "mosque";
             url = getUrl(latitude, longitude, mosque);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yak??ndaki camiler");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki camiler g??steriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(banka)){
            mMap.clear();
            String bank = "bank";
             url = getUrl(latitude, longitude, bank);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yak??ndaki bankalar");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki bankalar g??steriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(postane)){
            mMap.clear();
            String post_office = "post_office";
             url = getUrl(latitude, longitude, post_office);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yak??ndaki postaneler");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki postaneler g??steriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(restoran)){
            mMap.clear();
            String restaurant = "restaurant";
             url = getUrl(latitude, longitude, restaurant);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yak??ndaki restoranlar");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki restoranlar g??steriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(kutuphane)){
            mMap.clear();
            String library = "library";
             url = getUrl(latitude, longitude, library);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yak??ndaki k??t??phaneler");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki k??t??phaneler g??steriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(hava_limani)){
            mMap.clear();
            String airport = "airport";
             url = getUrl(latitude, longitude, airport);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yak??ndaki havalimanlar??");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki havalimanlar?? g??steriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(firin)){
            mMap.clear();
            String bakery = "bakery";
             url = getUrl(latitude, longitude, bakery);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yak??ndaki f??r??nlar");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki f??r??nlar g??steriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(bar1)){
            mMap.clear();
            String bar = "bar";
             url = getUrl(latitude, longitude, bar);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yak??ndaki barlar");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki barlar g??steriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(guzellik_salonu)){
            mMap.clear();
            String beauty_salon = "beauty_salon";
             url = getUrl(latitude, longitude, beauty_salon);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yak??ndaki g??zellik salonlar??");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki g??zellik salonlar?? g??steriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(kitapci)){
            mMap.clear();
            String book_store = "book_store";
             url = getUrl(latitude, longitude, book_store);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yak??ndaki kitap????lar");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki kitap????lar g??steriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(bowling_pisti)){
            mMap.clear();
            String bowling_alley = "bowling_alley";
             url = getUrl(latitude, longitude, bowling_alley);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yak??ndaki bowling pistleri");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki bowling pistleri g??steriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(otobus_duragi)){
            mMap.clear();
            String bus_station = "bus_station";
             url = getUrl(latitude, longitude, bus_station);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yak??ndaki otob??s duraklar??");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki otob??s duraklar?? g??steriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(kamp_alani)){
            mMap.clear();
            String campground = "campground";
             url = getUrl(latitude, longitude, campground);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yak??ndaki kamp alanlar??");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki kamp alanlar?? g??steriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(mezarlik)){
            mMap.clear();
            String cemetery = "cemetery";
             url = getUrl(latitude, longitude, cemetery);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yak??ndaki mezarl??klar");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki mezarl??klar g??steriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(belediye_binasi)){
            mMap.clear();
            String city_hall = "city_hall";
             url = getUrl(latitude, longitude, city_hall);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yak??ndaki belediyeler");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki belediye binalar?? g??steriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(giyim_magazasi)){
            mMap.clear();
            String clothing_store = "clothing_store";
             url = getUrl(latitude, longitude, clothing_store);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yak??ndaki giyim ma??azalar??");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki giyim ma??azalar?? g??steriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(adliye)){
            mMap.clear();
            String courthouse = "courthouse";
             url = getUrl(latitude, longitude, courthouse);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yak??ndaki adliyeler");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki adliyeler g??steriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(dis_hekimi)){
            mMap.clear();
            String dentist = "dentist";
             url = getUrl(latitude, longitude, dentist);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yak??ndaki di?? hekimleri");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki di?? hekimleri g??steriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(eczane)){
            mMap.clear();
            String drugstore = "drugstore";
             url = getUrl(latitude, longitude, drugstore);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yak??ndaki eczaneler");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki eczaneler g??steriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(itfaiye)){
            mMap.clear();
            String fire_station = "fire_station";
             url = getUrl(latitude, longitude, fire_station);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yak??ndaki itfaiyeler");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki itfaiyeler g??steriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(cicekci)){
            mMap.clear();
            String florist = "florist";
             url = getUrl(latitude, longitude, florist);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yak??ndaki ??i??ek??iler");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki ??i??ek??iler g??steriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(sac_bakimi)){
            mMap.clear();
            String hair_care = "hair_care";
             url = getUrl(latitude, longitude, hair_care);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yak??ndaki kuaf??rler");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki kuaf??rler g??steriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(kuyumcu)){
            mMap.clear();
            String jewelry_store = "jewelry_store";
             url = getUrl(latitude, longitude, jewelry_store);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yak??ndaki kuyumcular");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki kuyumcular g??steriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(tekel)){
            mMap.clear();
            String liquor_store = "liquor_store";
             url = getUrl(latitude, longitude, liquor_store);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yak??ndaki tekeller");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki tekeller g??steriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(gece_kulubu)){
            mMap.clear();
            String night_club = "night_club";
             url = getUrl(latitude, longitude, night_club);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yak??ndaki gece kul??pleri");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki gece kul??pleri g??steriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(otopark)){
            mMap.clear();
            String parking = "parking";
             url = getUrl(latitude, longitude, parking);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yak??ndaki otoparklar");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki otoparklar g??steriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(evcil_hayvan_dukkani)){
            mMap.clear();
            String pet_store = "pet_store";
             url = getUrl(latitude, longitude, pet_store);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yak??ndaki evcil hayvan d??kkanlar??");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki evcil hayvan d??kkanlar?? g??steriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(fizyoterapist)){
            mMap.clear();
            String physiotherapist = "physiotherapist";
             url = getUrl(latitude, longitude, physiotherapist);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yak??ndaki fizyoterapistler");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki fizyoterapistler g??steriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(tesisatci)){
            mMap.clear();
            String plumber = "plumber";
             url = getUrl(latitude, longitude, plumber);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yak??ndaki tesisat????lar");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki tesisat????lar g??steriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(ilkokul)){
            mMap.clear();
            String primary_school = "primary_school";
             url = getUrl(latitude, longitude, primary_school);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yak??ndaki ilkokullar");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki ilkokullar g??steriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(emlakci)){
            mMap.clear();
            String real_estate_agency = "real_estate_agency";
             url = getUrl(latitude, longitude, real_estate_agency);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yak??ndaki emlak????lar");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki emlak????lar g??steriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(ayakkabi_magazasi)){
            mMap.clear();
            String shoe_store = "shoe_store";
             url = getUrl(latitude, longitude, shoe_store);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yak??ndaki ayakkab?? ma??azalar??");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki ayakkab?? ma??azalar?? g??steriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(alis_veris_merkezi)){
            mMap.clear();
            String shopping_mall = "shopping_mall";
             url = getUrl(latitude, longitude, shopping_mall);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yak??ndaki Al????-Veri?? Merkezleri");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki AVM'ler g??steriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(spa1)){
            mMap.clear();
            String spa = "spa";
             url = getUrl(latitude, longitude, spa);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yak??ndaki spalar");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki spalar g??steriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(stadyum)){
            mMap.clear();
            String stadium = "stadium";
             url = getUrl(latitude, longitude, stadium);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yak??ndaki stadyumlar");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki stadyumlar g??steriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(metro_istasyonu)){
            mMap.clear();
            String subway_station = "subway_station";
             url = getUrl(latitude, longitude, subway_station);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yak??ndaki metro istasyonlar??");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki metro istasyonlar?? g??steriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(tren_istasyonu)){
            mMap.clear();
            String train_station = "train_station";
             url = getUrl(latitude, longitude, train_station);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yak??ndaki tren istasyonlar??");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki tren istasyonlar?? g??steriliyor", Toast.LENGTH_SHORT).show();
        }
        else if(kelime.equalsIgnoreCase(veteriner_bakimi)){
            mMap.clear();
            String veterinary_care = "veterinary_care";
             url = getUrl(latitude, longitude, veterinary_care);
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            nearby.setText("Yak??ndaki veterinerler");

            getNearbyPlacesData.execute(dataTransfer);
            Toast.makeText(MapsActivity.this, "Yak??ndaki veterinerler g??steriliyor", Toast.LENGTH_SHORT).show();
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
