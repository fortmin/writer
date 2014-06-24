package com.fortmin.nfc;

import java.net.URISyntaxException;

import com.fortmin.proshopapi.ProShopMgr;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.nfc.NdefMessage;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;

public class GrabarUrl extends Activity {

	// DECLARACION DE VARIABLES
	private String textoUrl;
	private Button btn_grabar_url;
	private EditText ingreso_url;
	ProShopMgr proshopmgr;
	private Context context;
	private Tag tag = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grabar_url);
		btn_grabar_url = (Button) findViewById(R.id.btnGrabarUrl);
		ingreso_url = (EditText) findViewById(R.id.ingresoUrl);
		ingreso_url.setText("http://www.");
		int textLength = ingreso_url.getText().length();
		ingreso_url.setSelection(textLength, textLength);
		// cambio la fuente del botón
		Typeface fuente = Typeface.createFromAsset(getAssets(),
				"gloriahallelujah.ttf");
		// agrego la fuente al botón
		btn_grabar_url.setTypeface(fuente);
		// Le pongo degrade plateado al botón
		btn_grabar_url.setBackgroundResource(R.drawable.degradado);

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
		proshopmgr.escucharTagNdefGrabar(this, context, getClass());
	}

	@Override
	public void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		tag = proshopmgr.obtenerTagDescubierto(intent);
		if (tag != null) {
			try {
				NdefMessage newMessage = proshopmgr
						.prepararMensNdefUrl(textoUrl);
				proshopmgr.escribirNdefMessageToTag(newMessage, tag);
			} catch (URISyntaxException e) {
				Mensaje(this, "URL con formato incorrecto");
			}
		}

	}

	@Override
	public void onPause() {
		super.onPause();
		proshopmgr.noEscucharTagNdefGrabar(this, context);
	}

}
