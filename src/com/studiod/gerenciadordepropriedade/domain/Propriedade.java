package com.studiod.gerenciadordepropriedade.domain;

public class Propriedade {
	
	private Integer id;
	private String nome_propriedade;
	private Double lat_propriedade;
	private Double long_propriedade;
	
	public Propriedade(){
		
	}

	public Propriedade(Integer id, String nome_propriedade,
			Double lat_propriedade, Double long_propriedade) {
		this.id = id;
		this.nome_propriedade = nome_propriedade;
		this.lat_propriedade = lat_propriedade;
		this.long_propriedade = long_propriedade;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome_propriedade() {
		return nome_propriedade;
	}

	public void setNome_propriedade(String nome_propriedade) {
		this.nome_propriedade = nome_propriedade;
	}

	public Double getLat_propriedade() {
		return lat_propriedade;
	}

	public void setLat_propriedade(Double lat_propriedade) {
		this.lat_propriedade = lat_propriedade;
	}

	public Double getLong_propriedade() {
		return long_propriedade;
	}

	public void setLong_propriedade(Double long_propriedade) {
		this.long_propriedade = long_propriedade;
	}
	
	
	

}
