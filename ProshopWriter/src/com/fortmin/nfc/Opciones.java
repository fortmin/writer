package com.fortmin.nfc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Opciones extends Activity {
	private ImageButton btn_grabar_email;
	private ImageButton btn_grabar_url;
	private ImageButton btn_grabar_telefono;
	private ImageButton btn_grabar_sms;
	private ImageButton btn_grabar_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_opciones);
		btn_grabar_email = (ImageButton) findViewById(R.id.grabarEmail);
		btn_grabar_url = (ImageButton) findViewById(R.id.grabarUrl);
		btn_grabar_telefono = (ImageButton) findViewById(R.id.grabarTelefono);
		btn_grabar_sms = (ImageButton) findViewById(R.id.grabarSms);
		btn_grabar_id = (ImageButton) findViewById(R.id.grabarIdPropietario);

		btn_grabar_url.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				// textoUrl = editUrl.getText().toString();
				// Mensaje(view, "Toque el Tag NFC Tag para grabar \n");
				grabarUrl();
			}
		});
		btn_grabar_email.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				// textoUrl = editUrl.getText().toString();
				// Mensaje(view, "Toque el Tag NFC Tag para grabar \n");
				grabarEmail();
			}
		});
		btn_grabar_telefono.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				// textoUrl = editUrl.getText().toString();
				// Mensaje(view, "Toque el Tag NFC Tag para grabar \n");
				grabarTelefono();
			}
		});
		btn_grabar_sms.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				// textoUrl = editUrl.getText().toString();
				// Mensaje(view, "Toque el Tag NFC Tag para grabar \n");
				grabarSms();
			}
		});
		btn_grabar_id.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				// textoUrl = editUrl.getText().toString();
				// Mensaje(view, "Toque el Tag NFC Tag para grabar \n");
				grabarId();
			}
		});

	}

	private void grabarUrl() {
		Intent grabar_url = new Intent(this, GrabarUrl.class);
		startActivity(grabar_url);
	}

	private void grabarEmail() {
		Intent grabar_email = new Intent(this, GrabarEmail.class);
		startActivity(grabar_email);
	}

	private void grabarTelefono() {
		Intent grabar_telefono = new Intent(this, GrabarTelefono.class);
		startActivity(grabar_telefono);
	}

	private void grabarSms() {
		Intent grabar_sms = new Intent(this, GrabarSms.class);
		startActivity(grabar_sms);
	}

	private void grabarId() {
		Intent grabar_id = new Intent(this, GrabarNdefPropietario.class);
		startActivity(grabar_id);
	}

}
