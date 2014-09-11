package com.studiod.gerenciadordepropriedade;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	private static final String BANCO_DADOS = "PropriedadesDB";
	private static int VERSAO = 1;
	
	public static class Propriedade{
		public static final String TABELA = "propriedade";
		public static final String _ID = "_id";
		public static final String NOME_PROPRIEDADE = "nome_propriedade";
		public static final String LAT_PROPRIEDADE = "lat_propriedade";
		public static final String LONG_PROPRIEDADE = "long_propriedade";
		
		public static final String[] COLUNAS = new String[]{
			_ID, NOME_PROPRIEDADE, LAT_PROPRIEDADE, LONG_PROPRIEDADE
		};
	}
	
	public static class Animal{
		public static final String TABELA = "animal";
		public static final String _ID = "_id";
		public static final String PROPRIEDADE_ID = "propriedade_id";
		public static final String NOME_ANIMAL = "nome_animal";
		public static final String TIPO_ANIMAL = "tipo_animal";
		public static final String NASC_ANIMAL = "nasc_animal";
		
		public static final String[] COLUNAS = new String[]{
			_ID, PROPRIEDADE_ID, NOME_ANIMAL, TIPO_ANIMAL, NASC_ANIMAL
		};
	}
	
	public DatabaseHelper(Context context){
		super(context, BANCO_DADOS, null, VERSAO);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db){
		db.execSQL("CREATE TABLE propriedade (_id INTEGER PRIMARY KEY,"+
				   " nome_propriedade TEXT, lat_propriedade INTEGER,"+
				   " long_propriedade INTEGER);");
		
		db.execSQL("CREATE TABLE animal (_id INTEGER PRIMARY KEY, "+
				   " nome_animal TEXT, tipo_animal TEXT, nasc_animal DATE,"+
				   " CONSTRAINT propriedade_id FOREIGN KEY(_id) REFERENCES propriedade(_id));");
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL("DROP TABLE propriedade;");
		db.execSQL("DROP TABLE animal;");
		onCreate(db);
	}

}
