package com.fortmin.nfc;

import java.net.URISyntaxException;

import com.fortmin.proshopapi.ProShopMgr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.graphics.Typeface;
import android.nfc.NdefMessage;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	// DECLARACION DE VARIABLES
	protected String textoUrl;
	ProShopMgr proshopmgr;
	private Context context;
	private Tag tag = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button writeItemButton = (Button) findViewById(R.id.botonWrite);
		// cambio la fuente del botón
		Typeface fuente = Typeface.createFromAsset(getAssets(),
				"gloriahallelujah.ttf");
		// agrego la fuente al botón
		writeItemButton.setTypeface(fuente);
		// Le pongo degrade plateado al botón
		writeItemButton.setBackgroundResource(R.drawable.degradado);

		writeItemButton.setOnClickListener(new View.OnClickListener()

		{
			public void onClick(View view) {

				mostrarOpciones();
			}
		});
		// context=getApplicationContext();
		Button salirButton = (Button) this.findViewById(R.id.botonSalir);
		// agrego la fuente al botón
		salirButton.setTypeface(fuente);
		// Le pongo degrade plateado al botón
		salirButton.setBackgroundResource(R.drawable.degradado);
		salirButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				finish();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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

}
