package com.studiod.gerenciadordepropriedade;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {

	private static final String MANTER_CONECTADO = "manter_conectado";
	private EditText usuario;
	private EditText senha;
	private CheckBox manterConectado;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		usuario = (EditText) findViewById(R.id.usuario);
		senha = (EditText) findViewById(R.id.senha);
		manterConectado = (CheckBox) findViewById(R.id.manterConectado);
		
		SharedPreferences preferencias = getPreferences(MODE_PRIVATE);
		boolean conectado = preferencias.getBoolean(MANTER_CONECTADO, false);
		
		if(conectado){
			startActivity(new Intent(this, TelaMenu.class));
		}
	}
	
	public void logarOnClick(View view){
		
		String usuarioInformado = usuario.getText().toString();
		String senhaInformada = senha.getText().toString();
		
		if("victor".equals(usuarioInformado) && "123".equals(senhaInformada)){
			SharedPreferences preferencias = getPreferences(MODE_PRIVATE);
			
			Editor editor = preferencias.edit();
			editor.putBoolean(MANTER_CONECTADO, manterConectado.isChecked());
			editor.commit();
			
			startActivity(new Intent(this, TelaMenu.class));
		}else{
			String mensagemErro = getString(R.string.erro_autenticacao);
			Toast.makeText(this, mensagemErro, Toast.LENGTH_SHORT).show();
		}
	}
}
