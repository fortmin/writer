package com.fortmin.nfc;

import java.net.URISyntaxException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.fortmin.proshopapi.ProShopMgr;

public class GrabarUrl extends Activity {

	// DECLARACION DE VARIABLES
	private String textoUrl;
	private ImageButton btn_grabar_url;
	private EditText ingreso_url;
	ProShopMgr proshopmgr;
	private Context context;
	private Tag tag = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grabar_url);
		btn_grabar_url = (ImageButton) findViewById(R.id.btnGrabarUrl);
		ingreso_url = (EditText) findViewById(R.id.ingresoUrl);
		ingreso_url.setText("http://www.");
		int textLength = ingreso_url.getText().length();
		ingreso_url.setSelection(textLength, textLength);

		btn_grabar_url.setOnClickListener(new View.OnClickListener()

		{
			public void onClick(View view) {
				textoUrl = ingreso_url.getText().toString();
				Mensaje(view, "Toque el Tag NFC Tag para grabar \n");
			}
		});
		context = this.getApplicationContext();
		proshopmgr = new ProShopMgr(context);

	}

	public void Mensaje(View v, String mensaje) {
		Toast toast = Toast.makeText(this, mensaje, Toast.LENGTH_LONG);
		toast.show();
	}

	public void Mensaje(Activity act, String mensaje) {
		Toast toast = Toast.makeText(this, mensaje, Toast.LENGTH_LONG);
		toast.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.grabar_url, menu);
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

	@Override
	public void onResume() {
		super.onResume();
		proshopmgr.getNFC().escucharTagNdefEscribir(this, getClass());
	}

	@Override
	public void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		tag = proshopmgr.getNFC().obtenerTagDescubierto(intent);
		if (tag != null) {
			try {
				NdefMessage newMessage = proshopmgr.getNFC()
						.prepararMensNdefUrl(textoUrl);
				proshopmgr.getNFC().escribirNdefMessageToTag(newMessage, tag);
			} catch (URISyntaxException e) {
				Mensaje(this, "URL con formato incorrecto");
			}
		}

	}

	@Override
	public void onPause() {
		super.onPause();
		proshopmgr.getNFC().noEscucharTagNdefGrabar(this);
	}

}
