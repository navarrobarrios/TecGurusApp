package net.tecgurus.app.tecgurusapp.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import net.tecgurus.app.tecgurusapp.R;
import net.tecgurus.app.tecgurusapp.db.beans.UserBean;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    //region Variables
    private GoogleMap mMap;
    private FloatingActionButton mCurrentLocationButton;
    private Location mMyLastLocation;
    //endregion

    //region ActvityMethods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mCurrentLocationButton = findViewById(R.id.activity_maps_current_location);
        mCurrentLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MapsActivityPermissionsDispatcher.getCurrentLocationWithPermissionCheck(MapsActivity.this);
            }
        });
    }
    //endregion

    //region OnMapReadyCallBack
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        double startLatitude = 20.6674736;
        double startLongitude = -103.3710259;
        double sum = 0.0050;
        for (int i = 0; i< 100; i++){
            LatLng tecGurus = new LatLng(startLatitude, startLongitude);

            Marker marker = mMap.addMarker(new MarkerOptions().position(tecGurus).title("Tec Gurus" + (i+1)));

            marker.setTag(new UserBean("navarrobarrios@gmail.com"+ (i+1),
                    "password" + (i+1),
                    "Jose Alberto"+ (i+1),
                    "Navarro Barrios"+ (i+1),
                    "Casiopea 5050"+ (i+1)));

            startLatitude = startLatitude + sum;
            startLongitude = startLongitude + sum;
        }

        MapsActivityPermissionsDispatcher.getCurrentLocationWithPermissionCheck(this);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                UserBean userClicked = (UserBean) marker.getTag();

                if (userClicked != null)
                Toast.makeText(MapsActivity.this,
                        "Aqui esta" +
                                userClicked.getName() + " " +
                                userClicked.getLastname(),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });

//        LatLngBounds latLngBounds = new LatLngBounds(tecGurus, tecGurus);
//        CameraUpdate c = CameraUpdateFactory.newLatLngBounds(latLngBounds, 24);
//        mMap.animateCamera(c);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        final LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.getFusedLocationProviderClient(getApplicationContext())
                .requestLocationUpdates(locationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);

                        if (ActivityCompat.checkSelfPermission(
                                MapsActivity.this,
                                Manifest.permission.ACCESS_FINE_LOCATION) !=
                                PackageManager.PERMISSION_GRANTED &&
                                ActivityCompat.checkSelfPermission(
                                        MapsActivity.this,
                                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        if (locationResult.getLastLocation() != null){
                            mMyLastLocation = locationResult.getLastLocation();
                            mMap.setMyLocationEnabled(true);
                        }
                    }
                }, null);

        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                Location camera = new Location("");
                camera.setLatitude(mMap.getCameraPosition().target.latitude);
                camera.setLongitude(mMap.getCameraPosition().target.longitude);

                if (mMyLastLocation != null) {
                    if (mMyLastLocation.distanceTo(camera) > 5000) {
                        mCurrentLocationButton.show();
                    } else {
                        mCurrentLocationButton.hide();
                    }
                }
            }
        });
    }


    //endregion


    @OnPermissionDenied({Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION})
    public void onLocationPermissionDenied(){
        new AlertDialog.Builder(this)
                .setTitle("Permissos requeridos")
                .setMessage("Actualmente no cuentas con los permisos de geolocalización.")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    @OnNeverAskAgain({Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION})
    public void neverAskLocationPermissions(){
        new AlertDialog.Builder(this)
                .setTitle("Permissos requeridos")
                .setMessage("Ve a settings y activa los permisos.")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    @NeedsPermission({Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION})
    public void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        LocationServices.getFusedLocationProviderClient(getApplicationContext())
                .getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location == null) return;
                mCurrentLocationButton.hide();
                LatLngBounds latLngBounds = new LatLngBounds(new LatLng(location.getLatitude(), location.getLongitude()), new LatLng(location.getLatitude(), location.getLongitude()));
                CameraUpdate c = CameraUpdateFactory.newLatLngBounds(latLngBounds, 24);
                mMap.animateCamera(c);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MapsActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}
