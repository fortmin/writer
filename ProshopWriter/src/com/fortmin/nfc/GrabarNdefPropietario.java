package com.fortmin.nfc;

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

public class GrabarNdefPropietario extends Activity {
	// DECLARACION DE VARIABLES
	private String id;
	private ImageButton btn_grabar_id;
	private EditText ingreso_id;
	ProShopMgr proshopmgr;
	private Context context;
	private Tag tag = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grabar_ndef_propietario);
		btn_grabar_id = (ImageButton) findViewById(R.id.btnGrabarIdPropietario);
		ingreso_id = (EditText) findViewById(R.id.ingresoIdPropietario);

		btn_grabar_id.setOnClickListener(new View.OnClickListener()

		{
			public void onClick(View view) {
				id = ingreso_id.getText().toString();
				Mensaje(view, "Toque el Tag NFC Tag para grabar \n");
			}
		});
		context = this.getApplicationContext();
		proshopmgr = new ProShopMgr(context);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.grabar_ndef_propietario, menu);
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
			NdefMessage newMessage = proshopmgr.getNFC()
					.prepararMensNdefPropietario(id);

			proshopmgr.getNFC().escribirNdefMessageToTag(newMessage, tag);

		}

	}

	@Override
	public void onPause() {
		super.onPause();
		proshopmgr.getNFC().noEscucharTagNdefGrabar(this);
	}

}
