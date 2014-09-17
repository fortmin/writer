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

public class GrabarTelefono extends Activity {
	// DECLARACION DE VARIABLES
	private String telefono;
	private ImageButton btn_grabar_telefono;
	private EditText ingreso_telefono;
	ProShopMgr proshopmgr;
	private Context context;
	private Tag tag = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grabar_telefono);
		btn_grabar_telefono = (ImageButton) findViewById(R.id.btnGrabarTelefono);
		ingreso_telefono = (EditText) findViewById(R.id.ingresoTelefono);

		btn_grabar_telefono.setOnClickListener(new View.OnClickListener()

		{
			public void onClick(View view) {
				telefono = ingreso_telefono.getText().toString();
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
		getMenuInflater().inflate(R.menu.grabar_telefono, menu);
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
			NdefMessage newMessage = proshopmgr.getNFC().prepararMensNdefTel(
					telefono);
			proshopmgr.getNFC().escribirNdefMessageToTag(newMessage, tag);

		}

	}

	@Override
	public void onPause() {
		super.onPause();
		proshopmgr.getNFC().noEscucharTagNdefGrabar(this);
	}

}
