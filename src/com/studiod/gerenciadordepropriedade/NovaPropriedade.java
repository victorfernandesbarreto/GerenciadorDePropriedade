package com.studiod.gerenciadordepropriedade;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NovaPropriedade extends Activity {
	
	private DatabaseHelper helper;
	private EditText nome_propriedade, lat_propriedade, long_propriedade;
	private String id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nova_propriedade);
		
		nome_propriedade = (EditText) findViewById(R.id.nome_propriedade);
		lat_propriedade = (EditText) findViewById(R.id.lat_propriedade);
		long_propriedade = (EditText) findViewById(R.id.long_propriedade);
		
		helper = new DatabaseHelper(this);
		
		id = getIntent().getStringExtra(Constantes.PROPRIEDADE_ID);
		
		if(id != null){
			prepararEdicao();
		}
		
	}
	
	private void prepararEdicao(){
		SQLiteDatabase db = helper.getReadableDatabase();
		
		Cursor cursor = db.rawQuery("SELECT nome_propriedade, lat_propriedade, long_propriedade FROM viagem WHERE _id = ?", new String[]{id});
		cursor.moveToFirst();
		
		nome_propriedade.setText(cursor.getString(0));
		lat_propriedade.setText(cursor.getString(2));
		long_propriedade.setText(cursor.getString(3));
		cursor.close();
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.propriedade_menu, menu);
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item){
		
		switch(item.getItemId()){
			case R.id.novo_animal:
				startActivity(new Intent(this, NovoAnimal.class));
				return true;
			case R.id.remover:
				removerPropriedade(id);
				return true;
			default:
				return super.onMenuItemSelected(featureId, item);
		}
	}
	
	public void removerPropriedade(String id){
		SQLiteDatabase db = helper.getWritableDatabase();
		String where[] = new String[]{id};
		db.delete("animal", "propriedade_id", where);
		db.delete("propriedade", "_id", where);
	}
	
	public void salvarPropriedade(View view){
		SQLiteDatabase db = helper.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put("nome_propriedade", nome_propriedade.getText().toString());
		values.put("lat_propriedade", lat_propriedade.getText().toString());
		values.put("long_propriedade", long_propriedade.getText().toString());
		
		long resultado;
		
		if(id == null){
			resultado = db.insert("propriedade", null, values);
		}else{
			resultado = db.update("viagem", values, "_id=?", new String[]{id});
		}
		
		if(resultado != 1){
			Toast.makeText(this, getString(R.string.registro_salvo), Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(this, getString(R.string.erro_salvar), Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	protected void onDestroy(){
		helper.close();
		super.onDestroy();
	}
}
