package com.facci.geoposicionamientokyms;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.List;
import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity {

    LocationManager locManager; // administra la lista de prove q tiene el dispositivo para aceceder a las coodenadas, wifi ,red administra todo

    private double latitud;
    private double longitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //inicializacion de LocManager
        locManager= (LocationManager)getSystemService(LOCATION_SERVICE); // HABILITA EL ESCUCHA

        // OBETENER LA LISTA DE PROVEEDORES obtener las coodenadas

        List<String> listaProviders = locManager.getAllProviders();

        //Obtener el primer elememnto dentro de la lista

        // LocationProvider es es un proveedor o gps o administra especificamente uno
        LocationProvider provider  = locManager.getProvider(listaProviders.get(0));

    }
    public void ActualizarLatLongClick(View v){
        if(ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED){

        }
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2*60*1000,10, locationListenerGPS);


    }
    //suscribe a un listener se declara clase dentro del main activity
    // location listener.- gMANEJA LA ESCUCHA DEL ELEMENTO DE UBICACION
    private final LocationListener locationListenerGPS = new LocationListener() { // al llegar aqui se generan solos los codigos
        @Override
        public void onLocationChanged(Location location) { // aqui se programa la ubicacion
            longitud = location.getLongitude();
            latitud = location.getLatitude();

            //parte de la rubrica
            double altitud = location.getAltitude();
            float velocidad = location.getSpeed();


            // Para hilo de proceso se llama al metodo runOn
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    EditText txtLongitud = (EditText)findViewById(R.id.txtLongitud);
                    EditText txtLatitud = (EditText)findViewById(R.id.txtLatitud);


                    //eN ESTA PARTE VA LO QUE PIDE EL DEBER RUBRICA ALTITUD

                    txtLatitud.setText(latitud+"");// FORMA DE convertir un valor double
                    txtLongitud.setText(String.valueOf(longitud));

                }
            });

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {// cualquier propiedaDA del prove cambia

        }

        @Override
        public void onProviderEnabled(String provider) { // cuando el proveedor se habilita

        }

        @Override
        public void onProviderDisabled(String provider) { // cuando el proveedor se desabilita

        }
    }   ;

}

