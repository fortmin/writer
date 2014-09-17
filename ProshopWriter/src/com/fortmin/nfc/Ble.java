package com.fortmin.nfc;

import java.util.Locale;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fortmin.proshopapi.ProShopMgr;
import com.fortmin.proshopapi.ble.Ibeacon;

public class Ble extends Activity implements OnInitListener {
	private com.fortmin.proshopapi.ble.EscucharIbeacons beacons;
	boolean scanning = false;
	private TextView mensaje;
	private Ibeacon ibeacon;
	private int MY_DATA_CHECK_CODE = 0;
	private TextToSpeech myTTS;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ble);
		ImageButton calibrarIbeacon = (ImageButton) findViewById(R.id.calibrarIbeacon);
		ImageButton testearDistancia = (ImageButton) findViewById(R.id.testingIbeacon);
		mensaje = (TextView) findViewById(R.id.mensaje);
		beacons = new com.fortmin.proshopapi.ble.EscucharIbeacons(this);
		Intent checkTTSIntent = new Intent();
		checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
		startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);

		calibrarIbeacon.setOnClickListener(new View.OnClickListener()

		{

			public void onClick(View view) {
				if (scanning)
					ibeacon = beacons.darIbeacon();
				ibeacon.setCalibracion(beacons.getRssi());

				mostrarMensaje("CALIBRADO OK");
				mensaje.setText("Para ver si esta mas cerca o lejos del lugar donde calibro presione testear");

			}
		});

		testearDistancia.setOnClickListener(new View.OnClickListener()

		{

			public void onClick(View view) {

				beacons.stopScanning();
				ibeacon.setRssi(beacons.getRssi());
				if (ibeacon.clienteCerca()) {
					speakWords("CERCA");
					// mostrarMensaje(String.valueOf(ibeacon.getTxPower()));
				} else {
					speakWords("LEJOS");
					// mostrarMensaje(String.valueOf(ibeacon.getTxPower()));
				}
				beacons.startScanning();
			}
		});
		// checheo el hardware de BLE
		ProShopMgr mgr = new ProShopMgr(getApplicationContext());

		if (mgr.getBLE(this).bleSoportado(this) == false) {
			Toast.makeText(this, "BLE Problemas de incompatibilidad",
					Toast.LENGTH_SHORT).show();
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
		boolean inicializo = true;
		ProShopMgr mgr = new ProShopMgr(getApplicationContext());
		if (mgr.getBLE(this).bleSoportado(this) == false) {
			mostrarMensaje("Su dispositivo no es compatible para recibir Ibeacon");
			inicializo = false;
		}
		// check for Bluetooth enabled on each resume

		else if (mgr.bluetoothHabilitado(this) == false) {
			// BT not enabled. Request to turn it on. User needs to restart app
			// once it's turned on.
			/*
			 * Intent enableBtIntent = new Intent(
			 * BluetoothAdapter.ACTION_REQUEST_ENABLE);
			 * startActivity(enableBtIntent);
			 */
			// finish();
			BluetoothAdapter mBluetoothAdapter = BluetoothAdapter
					.getDefaultAdapter();
			mBluetoothAdapter.enable();
		}

		// inicializacion del ble
		if (inicializo) {
			beacons.initialize();
			beacons.startScanning();
			scanning = true;
		}
		// ListaIbeacon Ibeacons=beacons.getIbeacons();
		// ArrayList<Ibeacon> dispositivos=Ibeacons.IbeaconsEncendidos();
		// beacon=dispositivos.get(0);

	}

	@Override
	protected void onPause() {
		super.onPause();
		beacons.stopScanning();

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

	public void mostrarMensaje(String mensaje) {
		Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG)
				.show();
	}

	// speak the user text
	private void speakWords(String speech) {

		// speak straight away
		myTTS.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
	}

	// act on result of TTS data check
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == MY_DATA_CHECK_CODE) {
			if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
				// the user has the necessary data - create the TTS
				myTTS = new TextToSpeech(this, this);
			} else {
				// no data - install it now
				Intent installTTSIntent = new Intent();
				installTTSIntent
						.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
				startActivity(installTTSIntent);
			}
		}
	}

	// setup TTS
	public void onInit(int initStatus) {

		// check for successful instantiation
		if (initStatus == TextToSpeech.SUCCESS) {
			if (myTTS.isLanguageAvailable(Locale.getDefault()) == TextToSpeech.LANG_AVAILABLE)
				myTTS.setLanguage(Locale.getDefault());
		} else if (initStatus == TextToSpeech.ERROR) {
			Toast.makeText(this, "Sorry! Text To Speech failed...",
					Toast.LENGTH_LONG).show();
		}
	}

}
