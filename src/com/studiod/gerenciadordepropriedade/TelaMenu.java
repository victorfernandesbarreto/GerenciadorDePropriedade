package com.studiod.gerenciadordepropriedade;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class TelaMenu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_menu);
	}
	
	public void selecionarOpcao(View view){
		switch(view.getId()){
			case R.id.nova_propriedade:
				startActivity(new Intent(this, NovaPropriedade.class));
				break;
			case R.id.minhas_propriedades:
				startActivity(new Intent(this, PropriedadeList.class));
				break;
			case R.id.ajuda:
				startActivity(new Intent(this, Ajuda.class));
				break;
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.tela_menu, menu);
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item){
		finish();
		return true;
	}
}
