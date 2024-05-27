package tp4;

import java.util.ArrayList;

public class Futebol {
	private ArrayList<Liga> ligas;
	
	public Futebol(ArrayList<Liga> ligas) {
		this.ligas = ligas;
		
	}
	
	public ArrayList<Liga> getLigas() {
		return this.ligas;
	}
	
	public void setLigas(ArrayList<Liga> ligas) {
		this.ligas = ligas;
	}	
	
	public void addLiga(Liga liga) {
		
		this.ligas.add(liga);
		
	}
}
