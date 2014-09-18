package com.studiod.gerenciadordepropriedade.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.studiod.gerenciadordepropriedade.DatabaseHelper;
import com.studiod.gerenciadordepropriedade.domain.Propriedade;


public class PropriedadesDAO {
	
	private DatabaseHelper helper;
	private SQLiteDatabase db;
	
	public PropriedadesDAO(Context context){
		helper = new DatabaseHelper(context);
	}
	
	public SQLiteDatabase getDB(){
		if(db == null){
			db = helper.getWritableDatabase();
		}
		
		return db;
	}
	
	public void close(){
		helper.close();
	}
	
	public List<Propriedade> listarPropriedades(){
		
		Cursor cursor = getDB().query(DatabaseHelper.Propriedade.TABELA, DatabaseHelper.Propriedade.COLUNAS, null, null, null, null, null);
	
		List<Propriedade> propriedades = new ArrayList<Propriedade>();
		while(cursor.moveToNext()){
			Propriedade propriedade = criarPropriedade(cursor);
			propriedades.add(propriedade);
		}
		cursor.close();
		return propriedades;
	}
	
	public Propriedade buscarPropriedadePorId(Integer id){
		
		Cursor cursor = getDB().query(DatabaseHelper.Propriedade.TABELA, DatabaseHelper.Propriedade.COLUNAS, DatabaseHelper.Propriedade._ID+"=?", new String[]{id.toString()}, null, null, null);
	
		if(cursor.moveToNext()){
			Propriedade propriedade = criarPropriedade(cursor);
			cursor.close();
			return propriedade;
		}
		return null;
	}
	
	public long inserir(Propriedade propriedade){
		ContentValues values = new ContentValues();
		
		values.put(DatabaseHelper.Propriedade.NOME_PROPRIEDADE, propriedade.getNome_propriedade());
		values.put(DatabaseHelper.Propriedade.LAT_PROPRIEDADE, propriedade.getLat_propriedade());
		values.put(DatabaseHelper.Propriedade.LONG_PROPRIEDADE, propriedade.getLong_propriedade());
	
		return getDB().insert(DatabaseHelper.Propriedade.TABELA, null, values);
	}
	
	public boolean removerPropriedade(Long id){
		String whereClause = DatabaseHelper.Propriedade._ID+" = ?";
		String[] whereArgs = new String[]{id.toString()};
		int removidos = getDB().delete(DatabaseHelper.Propriedade.TABELA, whereClause, whereArgs);
		
		return removidos > 0;
	}
	
	private Propriedade criarPropriedade(Cursor cursor){
		Propriedade propriedade = new Propriedade(
				cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Propriedade._ID)),
				cursor.getString(cursor.getColumnIndex(DatabaseHelper.Propriedade.NOME_PROPRIEDADE)),
				cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.Propriedade.LAT_PROPRIEDADE)),
				cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.Propriedade.LONG_PROPRIEDADE))
		);
		return propriedade;
	}

}
