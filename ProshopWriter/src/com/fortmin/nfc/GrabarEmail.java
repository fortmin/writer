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

public class GrabarEmail extends Activity {

	// DECLARACION DE VARIABLES
	private String email, mensaje, subject;
	private ImageButton btn_grabar_email;
	private EditText ingreso_email, ingreso_subject, ingreso_mensaje;
	ProShopMgr proshopmgr;
	private Context context;
	private Tag tag = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grabar_email);
		btn_grabar_email = (ImageButton) findViewById(R.id.btnGrabarEmail);
		ingreso_email = (EditText) findViewById(R.id.ingresoEmail);
		ingreso_subject = (EditText) findViewById(R.id.ingresoSubject);
		ingreso_mensaje = (EditText) findViewById(R.id.ingresoMensaje);

		btn_grabar_email.setOnClickListener(new View.OnClickListener()

		{
			public void onClick(View view) {
				email = ingreso_email.getText().toString();
				subject = ingreso_subject.getText().toString();
				mensaje = ingreso_mensaje.getText().toString();
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
		getMenuInflater().inflate(R.menu.grabar_email, menu);
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
			NdefMessage newMessage = proshopmgr.getNFC()
					.prepararMensNdefMailto(email, subject, mensaje);
			proshopmgr.getNFC().escribirNdefMessageToTag(newMessage, tag);

		}

	}

	@Override
	public void onPause() {
		super.onPause();
		proshopmgr.getNFC().noEscucharTagNdefGrabar(this);
	}

}
