package com.fortmin.nfc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Inicio extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inicio);

		ImageButton writeItemButton = (ImageButton) findViewById(R.id.botonWrite);
		writeItemButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				mostrarOpciones();
			}
		});
		// context=getApplicationContext();

		ImageButton btnIbeacon = (ImageButton) findViewById(R.id.botonIbeacon);

		btnIbeacon.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				mostrarBle();
			}
		});

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
