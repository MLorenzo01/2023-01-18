package it.polito.tdp.nyc.model;

import java.util.Objects;

public class Location implements Comparable<Location>{

	private String nome;
	private double lat;
	private double longi;
	private int vicini;
	
	
	public Location(String nome, double lat, double longi) {
		super();
		this.nome = nome;
		this.lat = lat;
		this.longi = longi;
	}
	
	
	public int getVicini() {
		return vicini;
	}


	public void setVicini(int vicini) {
		this.vicini = vicini;
	}


	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLongi() {
		return longi;
	}
	public void setLongi(double longi) {
		this.longi = longi;
	}


	@Override
	public String toString() {
		return  nome;
	}


//	@Override
//	public int hashCode() {
//		return Objects.hash(lat, longi, nome, vicini);
//	}
//
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Location other = (Location) obj;
//		return Double.doubleToLongBits(lat) == Double.doubleToLongBits(other.lat)
//				&& Double.doubleToLongBits(longi) == Double.doubleToLongBits(other.longi)
//				&& Objects.equals(nome, other.nome);
//	}


	@Override
	public int compareTo(Location o) {
		return this.nome.compareTo(o.getNome());
	}
	
	
	
}
