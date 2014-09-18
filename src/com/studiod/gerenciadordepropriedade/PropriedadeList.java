package com.studiod.gerenciadordepropriedade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.studiod.gerenciadordepropriedade.dao.PropriedadesDAO;
import com.studiod.gerenciadordepropriedade.domain.Propriedade;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;

public class PropriedadeList extends ListActivity implements OnItemClickListener, OnClickListener{

	private AlertDialog alertDialog;
	private int propriedadeSelecionada;
	private AlertDialog dialogConfirmação;
	private List<Map<String, Object>> propriedades;
	private boolean modoSelecionarPropriedade;
	
	private DatabaseHelper helper;
	private PropriedadesDAO dao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		dao = new PropriedadesDAO(this);
		
		getListView().setOnItemClickListener(this);
		alertDialog = criarAlertDialog();
		dialogConfirmação = criarDialogConfirmacao();
		
		if(getIntent().hasExtra(Constantes.MODO_SELECIONAR_PROPRIEDADE)){
			modoSelecionarPropriedade = getIntent().getExtras().getBoolean(Constantes.MODO_SELECIONAR_PROPRIEDADE);
		}
		
		new Task().execute();
	}
	
	private class Task extends AsyncTask<Void, Void, List<Map<String, Object>>>{
		
		@Override
		protected List<Map<String, Object>> doInBackground(Void... params){
			return listarPropriedades();
		}
		
		@Override
		protected void onPostExecute(List<Map<String, Object>> result){
			String[] de = {"nome_propriedade"};
			
			int[] para = {R.id.nome_propriedade};
			
			SimpleAdapter adapter = new SimpleAdapter(PropriedadeList.this, result, R.layout.activity_propriedade_list, de, para);
			
			setListAdapter(adapter);
		}
	}
	
	public List<Map<String, Object>> listarPropriedades(){
		
		propriedades = new ArrayList<Map<String, Object>>();
		List<Propriedade> listarPropriedades = dao.listarPropriedades();
		
		for(Propriedade propriedade : listarPropriedades){
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("id", propriedade.getId());
			
			item.put("nome_propriedade", propriedade.getNome_propriedade());
			item.put("lat_propriedade", propriedade.getLat_propriedade());
			item.put("long_propriedade", propriedade.getLong_propriedade());
			propriedades.add(item);
		}
		return propriedades;
	}
	
	private AlertDialog criarAlertDialog(){
		final CharSequence[] items = {
				getString(R.string.editar),
				getString(R.string.novo_animal),
				getString(R.string.listar_animal),
				getString(R.string.remover)
		};
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.opcoes);
		builder.setItems(items, this);
		
		return builder.create();
	}
	
	@Override
	public void onClick(DialogInterface dialog, int item){
		
		Intent intent;
		String id = (String)propriedades.get(propriedadeSelecionada).get("id");
		
		switch(item){
		case 0://editar propriedade
			intent = new Intent(this, NovaPropriedade.class);
			intent.putExtra(Constantes.PROPRIEDADE_ID, id);
			startActivity(intent);
			break;
		case 1://novo animal
			String nova_propriedade = propriedades.get(propriedadeSelecionada).get("nome_propriedade").toString();
			intent = new Intent(this, NovoAnimal.class);
			intent.putExtra(Constantes.PROPRIEDADE_ID, id);
			intent.putExtra(Constantes.PROPRIEDADE_NOME, nova_propriedade);
			startActivity(intent);
			break;
		case 2://lista de animais
			intent = new Intent(this, AnimalList.class);
			intent.putExtra(Constantes.PROPRIEDADE_ID, id);
			startActivity(intent);
			break;
		case 3://confirmação de exclusao
			dialogConfirmação.show();
			break;
		case DialogInterface.BUTTON_POSITIVE: //exclusao
			propriedades.remove(propriedadeSelecionada);
			dao.removerPropriedade(Long.valueOf(id));
			getListView().invalidateViews();
			break;
		case DialogInterface.BUTTON_NEGATIVE:
			dialogConfirmação.dismiss();
			break;
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id){
		
		if(modoSelecionarPropriedade){
			String nome_propriedade = (String) propriedades.get(position).get("nome_propriedade");
			String idPropriedade = (String) propriedades.get(position).get("id");
			
			Intent data = new Intent();
			data.putExtra(Constantes.PROPRIEDADE_ID, idPropriedade);
			data.putExtra(Constantes.PROPRIEDADE_NOME, nome_propriedade);
			setResult(Activity.RESULT_OK, data);
			finish();
		}else{
			propriedadeSelecionada = position;
			alertDialog.show();
		}
	}
	
	private AlertDialog criarDialogConfirmacao(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.confirmacao_exclusao_propriedade);
		builder.setPositiveButton(getString(R.string.sim), this);
		builder.setNegativeButton(getString(R.string.nao), this);
		
		return builder.create();
	}
	
	@Override
	protected void onDestroy(){
		dao.close();
		super.onDestroy();
	}


	
}
