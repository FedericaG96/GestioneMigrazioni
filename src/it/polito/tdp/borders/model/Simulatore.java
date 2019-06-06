package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;

public class Simulatore {
	
	// Stato del sistema ad ogni passo
	private Graph<Country, DefaultEdge> grafo;
	
	// Tipi di evento/ coda prioritaria
	// 1 solo evento
	private PriorityQueue<Evento> queue;
	
	// Parametri della simulazione
	private int N_MIGRANTI = 1000;
	private Country partenza;
	
	// Valori in output
	private int T;	//numero di passi T simulati
	private Map<Country, Integer> stanziali;

	public void init(Country partenza, Graph<Country, DefaultEdge> grafo) {
		
		// Ricevo i parametri
		this.partenza = partenza;
		this.grafo = grafo;
		
		// Impostazione dello stato iniziale
		this.T = 1;
		stanziali = new HashMap<Country, Integer>();
		for(Country c : this.grafo.vertexSet()) {
			stanziali.put(c, 0);	//inizialmente il numero degli stanziali in ogni stato � 0
		}
		
		queue = new PriorityQueue<Evento>();
		
		// Inserisco il primo evento
		this.queue.add(new Evento (T, N_MIGRANTI, partenza));		
	}
	
	public void run() {
		
		// Estraggo un evento per volta dalla coda e lo eseguo 
		// finch� la coda non si svuota
		Evento e;
		while((e = queue.poll()) != null) {
			
			//Eseguo l'evento
			
			this.T = e.getT(); // per tenere traccia dell'ultimo T
			
			int nPersone = e.getNumeroPersone();
			Country stato = e.getStato();
			List<Country> confinanti = Graphs.neighborListOf(this.grafo, stato);
			
			int migranti = (nPersone/2)/confinanti.size();  //divisione troncata per difetto poich� migranti � un int
															//(restituisce la parte intera del risultato)
			
			if(migranti>0) {
				// le persone si possono muovere
				for (Country confinante : confinanti) {
					queue.add(new Evento(e.getT()+1, migranti, confinante));
				}
			}
			
			int stanziali = nPersone - migranti * confinanti.size();
			this.stanziali.put(stato, this.stanziali.get(stato) + stanziali);	
									  //Somma al numero degli stanziali gi� presenti i nuovi stanziali
		}
	}

	public int getLastT() {
		return T;
	}
	
	public Map<Country, Integer> getStanziali(){
		return stanziali;
	}
}
