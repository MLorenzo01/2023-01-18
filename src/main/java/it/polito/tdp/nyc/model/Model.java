package it.polito.tdp.nyc.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.nyc.db.NYCDao;

public class Model {
	
	private NYCDao dao;
	private SimpleWeightedGraph<Location, DefaultWeightedEdge> graph;
	int best = 0;
	ArrayList<Location> migliore;
	Location target;
	ArrayList<Location> lista;
	TreeSet<Location> vietati;
	
	public Model() {
		this.dao = new NYCDao();
	}
	
	public void CreaGrafo(String p, double distance) {
		ArrayList<Location> lista = dao.getAllLocationByProvider(p);
		graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(graph, lista);
		
		for(Location l1: lista) {
			for(Location l2: lista) {
				LatLng primo = new LatLng(l1.getLat(), l1.getLongi());
				LatLng secondo = new LatLng(l2.getLat(), l2.getLongi());
				if(!l1.equals(l2))
					if(LatLngTool.distance(primo, secondo,LengthUnit.KILOMETER) <= distance) {
						if(!graph.containsEdge(l1, l2)) {
							graph.addEdge(l1, l2);
						}
					}
				}
			}
		System.out.println("il grafo ha " + graph.vertexSet().size() + " vertici e " + graph.edgeSet().size());
		for(Location l:graph.vertexSet()) {
			System.out.println("grafo " + l+ "con i vicini " + Graphs.neighborListOf(graph, l));
		}
		}	
	
	public ArrayList<String> getProvider() {
		return dao.getProvider();
	}
	public Set<Location> getLocation(){
		return graph.vertexSet();
	}
	
	public ArrayList<String> getMax() {
		lista = new ArrayList<>();
		int max = 0;
		for(Location l: graph.vertexSet()) {
			if(max< Graphs.neighborListOf(graph, l).size()) {
				max = Graphs.neighborListOf(graph, l).size();
				lista.clear();
				lista.add(l);
				l.setVicini(max);
			}else if(max == Graphs.neighborListOf(graph, l).size()) {
				lista.add(l);
				l.setVicini(max);
			}
		}
		ArrayList<String> nomi = new ArrayList<>();
		for(Location l1: lista) {
			nomi.add("\n" + l1.getNome() + " ha " + l1.getVicini() + " vicini");
		}
		return nomi;
	}
	
	public ArrayList<Location> trovaPercorso(String s, Location l){
		migliore = new ArrayList<>();
		best = 0;
		target = l;
		vietati = new TreeSet<> ();
		for(Location l1: graph.vertexSet()) {
			if(l1.getNome().contains(s))
				vietati.add(l1);
		}
		ArrayList<Location> parziale = new ArrayList<>();
		Random random = new Random();
        int randomIndex = random.nextInt(lista.size());
        Location randomValue = lista.get(randomIndex);
        parziale.add(randomValue);
		cerca(parziale);
		return migliore;
	}

	private void cerca(ArrayList<Location> parziale) {
		if(parziale.size() > best && parziale.get(parziale.size()-1).equals(target)) {
			best = parziale.size();
			migliore = new ArrayList<>(parziale);
			return;
		}
//		if(parziale.size()>best) {
//			migliore = new ArrayList<>(parziale);
////			System.out.println(migliore);
//		}
		Set<Location> vicini = Graphs.neighborSetOf(graph, parziale.get(parziale.size()-1));
//		System.out.println(parziale.get(parziale.size()-1) + "ha questi vertici " + vicini);
		if(vicini.contains(target)) {
			vicini.removeAll(vietati);
			if(!vicini.contains(target))
				vicini.add(target);
		}else
			vicini.removeAll(vietati);
		for(Location l: vicini) {
			if(!parziale.contains(l)) {
				parziale.add(l);
				cerca(parziale);
				parziale.remove(l);
			}
		}
		

	}
	
}
