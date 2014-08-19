package com.fortmin.nfc;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.fortmin.proshopapi.ProShopMgr;

public class Inicio extends Activity {

	// DECLARACION DE VARIABLES
	protected String textoUrl;
	ProShopMgr proshopmgr;
	private Context context;
	private Tag tag = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.);

		ImageButton writeItemButton = (ImageButton) findViewById(R.id.botonWrite);
		writeItemButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				mostrarOpciones();
			}
		});
		// context=getApplicationContext();

		Button btnIbeacon = (Button) findViewById(R.id.botonIbeacon);

		btnIbeacon.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				mostrarBle();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void Mensaje(View v, String mensaje) {
		Toast toast = Toast.makeText(this, mensaje, Toast.LENGTH_LONG);
		toast.show();
	}

	public void Mensaje(Activity act, String mensaje) {
		Toast toast = Toast.makeText(this, mensaje, Toast.LENGTH_LONG);
		toast.show();
	}

	private void mostrarOpciones() {
		Intent opciones = new Intent(this, Opciones.class);
		startActivity(opciones);
	}

	private void mostrarBle() {
		Intent ble = new Intent(this, Ble.class);
		startActivity(ble);
	}

}
