package it.polito.tdp.borders.model;

public class Evento implements Comparable<Evento> {
	
	private int t;	//passo della simulazione
	private int numeroPersone; //numero persone che sono arrivate e si sposteranno
	private Country stato; //paese in cui le persone arrivano e da cui si sposteranno
	
	public Evento(int t, int numeroPersone, Country stato) {
		super();
		this.t = t;
		this.numeroPersone = numeroPersone;
		this.stato = stato;
	}
	public int getT() {
		return t;
	}
	public int getNumeroPersone() {
		return numeroPersone;
	}
	public Country getStato() {
		return stato;
	}
	@Override
	public int compareTo(Evento other) {
		return this.t - other.t;
	}


}
