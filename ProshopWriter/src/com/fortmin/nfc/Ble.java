package com.fortmin.nfc;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.os.Build;

public class Ble extends Activity {
	 private com.fortmin.proshopapi.ble.Ibeacon beacon;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ble);
		Button calibrarIbeacon = (Button) findViewById(R.id.calibrarIbeacon);
		// cambio la fuente del botón
		Typeface fuente = Typeface.createFromAsset(getAssets(),"gloriahallelujah.ttf");
		// agrego la fuente al botón
		calibrarIbeacon.setTypeface(fuente);
		// Le pongo degrade plateado al botón
		calibrarIbeacon.setBackgroundResource(R.drawable.degradado);
		beacon = new com.fortmin.proshopapi.ble.Ibeacon(this);
		calibrarIbeacon.setOnClickListener(new View.OnClickListener()

				{
					public void onClick(View view) {
                     beacon.calibrarBeacon(beacon.darRssiBeacon());
					 Toast.makeText(getBaseContext(), "Dispositivo calibrado con exito", Toast.LENGTH_LONG).show();
					}
				});
		Button testingIbeacon = (Button) findViewById(R.id.testingIbeacon);
		testingIbeacon.setTypeface(fuente);
		testingIbeacon.setBackgroundResource(R.drawable.degradado);
		testingIbeacon.setOnClickListener(new View.OnClickListener()

		{
			public void onClick(View view) {
			 String posicion;
			
             if (beacon.estaBeaconCalibrado()){
              
               if(beacon.clienteCerca())
                 posicion="Cliente esta cerca";
               else
            	 posicion="Cliente esta lejos";
			   Toast.makeText(getBaseContext(), posicion, Toast.LENGTH_LONG).show();
			  
             }
             else
            	  Toast.makeText(getBaseContext(), "Debe calibrar primero", Toast.LENGTH_LONG).show();
			}
		});
		  // checheo el hardware  de BLE
		  if (beacon.checkBleHardwareAvailable() == false)
	        {
	            Toast.makeText(this, "BLE Problemas de incompatibilidad", Toast.LENGTH_SHORT).show();
	            finish();
	        }
		  
	     
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ble, menu);
		return true;
	}
	  @Override
	    protected void onResume() {
	        super.onResume();

	        // check for Bluetooth enabled on each resume
	        if (beacon.isBtEnabled() == false)
	        {
	            // BT not enabled. Request to turn it on. User needs to restart app once it's turned on.
	            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	            startActivity(enableBtIntent);
	            //finish();
	        }

	        // inicializacion del ble 
	        beacon.initialize();
	        beacon.startScanning();
	    }

	    @Override
	    protected void onPause() {
	        super.onPause();
	        beacon.stopScanning();

	        onStop();
	      
	    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	

}
