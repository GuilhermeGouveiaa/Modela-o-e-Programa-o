package tp4;

import java.util.ArrayList;


public class Liga {
	private String name;
	private String local;

	private ArrayList<EquipaFutebol> equipas;
	private int numero_equipas;
	private ArrayList<Jogo> jogos = new ArrayList<>();
	
	public Liga(String name, String local, int numero_equipas) {
		this.name = name;
		this.local = local;
		this.numero_equipas = numero_equipas;
		this.equipas = new ArrayList<EquipaFutebol>();
	}
	
	public ArrayList<Jogo> getJogos(){
		return this.jogos;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getLocal() {
		return this.local;
	}
	
	public int getNumEquipas() {
		return this.equipas.size();
	}
	
	
	public ArrayList<EquipaFutebol> getEquipas() {
		return this.equipas;
	}
	public void setClubes(ArrayList<EquipaFutebol> equipas) {
		this.equipas = equipas;
	}
	
	public void setJogos(ArrayList<Jogo> jogos) {
		this.jogos = jogos;
	}
	public void addEquipa(EquipaFutebol equipa) {
		this.equipas.add(equipa);
		this.numero_equipas+=1;
	}
	
	public void removeEquipa(EquipaFutebol equipa) {
		this.equipas.remove(equipa);
		this.numero_equipas-=1;
	}
	
	public void addJogo(Jogo jogo) {
		this.jogos.add(jogo);
	}
	public void removeJogo(Jogo jogo) {
		this.jogos.remove(jogo);
	}
	public void atualizarEstatisticas(Jogo jogo) {
		
		jogo.getTeamA().setGolosMarcados(jogo.getTeamA().getGolosMarcados() + jogo.getTeamAScore());
		jogo.getTeamB().setGolosMarcados(jogo.getTeamB().getGolosMarcados() + jogo.getTeamBScore());
		jogo.getTeamA().setGolosSofridos(jogo.getTeamA().getGolosSofridos() + jogo.getTeamBScore());
		jogo.getTeamB().setGolosSofridos(jogo.getTeamB().getGolosSofridos() + jogo.getTeamAScore());
		jogo.getTeamA().setJogosJogados(jogo.getTeamA().getJogosJogados() +1);
		jogo.getTeamB().setJogosJogados(jogo.getTeamB().getJogosJogados() +1);
		
		if(jogo.getTeamAScore() > jogo.getTeamBScore()) {
			
			jogo.getTeamA().setPontos(jogo.getTeamA().getPontos()+3);
			jogo.getTeamA().setVitorias(jogo.getTeamA().getVitorias()+1);
			jogo.getTeamB().setDerrotas(jogo.getTeamB().getDerrotas()+1);
		}
		else if (jogo.getTeamAScore() < jogo.getTeamBScore()) {
			
			jogo.getTeamB().setPontos(jogo.getTeamB().getPontos()+3);
			jogo.getTeamB().setVitorias(jogo.getTeamB().getVitorias()+1);
			jogo.getTeamA().setDerrotas(jogo.getTeamA().getDerrotas()+1);
		}
		else {
			jogo.getTeamA().setPontos(jogo.getTeamA().getPontos()+1);
			jogo.getTeamB().setPontos(jogo.getTeamB().getPontos()+1);
			jogo.getTeamA().setDerrotas(jogo.getTeamA().getEmpates()+1);
			jogo.getTeamB().setDerrotas(jogo.getTeamB().getEmpates()+1);
		}		
	}
	
	public EquipaFutebol getEquipaByName(String nome) {
	    for (EquipaFutebol equipa : equipas) {
	        if (equipa.getNome().equals(nome)) {
	            return equipa;
	        }
	    }
	    return null;
	}
	@Override
	public String toString() {
	    return this.equipas.toString();
	}
}
